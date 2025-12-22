package com.drunkencod.mobtalismans.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class CustomItemTrigger
        extends SimpleCriterionTrigger<CustomItemTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack input, ItemStack output) {
        this.trigger(player, (triggerInstance) -> triggerInstance.matches(input, output));
    }

    public record TriggerInstance(
            Optional<ContextAwarePredicate> player,
            Optional<ItemPredicate> input,
            Optional<ItemPredicate> output) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        ContextAwarePredicate.CODEC.optionalFieldOf("player")
                                .forGetter(TriggerInstance::player),
                        ItemPredicate.CODEC.optionalFieldOf("input")
                                .forGetter(TriggerInstance::input),
                        ItemPredicate.CODEC.optionalFieldOf("output")
                                .forGetter(TriggerInstance::output))
                        .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> getItem(ItemPredicate.Builder input,
                ItemPredicate.Builder output) {
            return ModCriteriaTriggers.TALISMAN_TRIGGERED.createCriterion(
                    new TriggerInstance(Optional.empty(), Optional.of(input.build()), Optional.of(output.build())));
        }

        public boolean matches(ItemStack input, ItemStack output) {
            if (this.input.isPresent() && !this.input.get().test(input)) {
                return false;
            }
            return !this.output.isPresent() || this.output.get().test(output);
        }
    }
}
