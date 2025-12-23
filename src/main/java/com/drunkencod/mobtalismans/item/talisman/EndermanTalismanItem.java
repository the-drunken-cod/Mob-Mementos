package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.MobTalismans;
import io.wispforest.accessories.api.slot.SlotReference;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class EndermanTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "enderman_talisman";

    public static final String PREVENT_ENDERMAN_GRIEFING_NBT_KEY = "mobtalismans:prevent_enderman_griefing";

    public EndermanTalismanItem() {
        super(getDefaultProps(ModStartupConfig.ENDERMAN_TALISMAN.DURABILITY.get())
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips, TooltipContext tooltipContext,
            TooltipFlag tooltipType) {
        tooltips.add(Component.translatable("item.mobtalismans.enderman_talisman.tooltip"));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Level level = reference.entity().level();

        double radius = ModStartupConfig.ENDERMAN_TALISMAN.RADIUS.get();
        int tickInterval = ModStartupConfig.ENDERMAN_TALISMAN.CHECK_INTERVAL_TICKS.get();

        // if talisman is broken, do nothing
        if (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage())
            return;

        // find nearby endermen
        List<Entity> nearbyEndermen = getNearbyEndermen(level, reference.entity(), radius);
        for (Entity enderman : nearbyEndermen) {
            if (enderman.tickCount > 0 && enderman.tickCount % tickInterval == 0) {
                if (!level.isClientSide()) {
                    MobTalismans.LOGGER.debug("[Enderman Talisman]: Preventing mob griefing for {}", enderman);

                    // set NBT `mobtalismans:prevent_enderman_griefing` to true
                    enderman.getPersistentData().putBoolean(
                            EndermanTalismanItem.PREVENT_ENDERMAN_GRIEFING_NBT_KEY, true);

                    // trigger advancement
                    triggerTalismanAdvancement(reference, stack);

                    // damage talisman
                    damageTalisman(stack);
                }
            }
        }
    }

    private List<Entity> getNearbyEndermen(Level level, Entity entity, double radius) {
        return level.getEntities(entity, entity.getBoundingBox().inflate(radius), e -> {
            return e instanceof Mob && e.getType() == EntityType.ENDERMAN;
        });
    }
}
