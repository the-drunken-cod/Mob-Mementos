package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.config.ModStartupConfig;
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

public class CreeperTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "creeper_talisman";

    public static final String PREVENT_EXPLOSION_DAMAGE_NBT_KEY = "mobtalismans:prevent_explosion_block_damage";

    public CreeperTalismanItem() {
        super(getDefaultProps(ModStartupConfig.CREEPER_TALISMAN.DURABILITY.get())
                .rarity(Rarity.UNCOMMON));
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips, TooltipContext tooltipContext,
            TooltipFlag tooltipType) {
        tooltips.add(Component.translatable("item.mobtalismans.creeper_talisman.tooltip"));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Level level = reference.entity().level();

        double radius = ModStartupConfig.CREEPER_TALISMAN.RADIUS.get();
        int tickInterval = ModStartupConfig.CREEPER_TALISMAN.CHECK_INTERVAL_TICKS.get();

        // if talisman is broken, do nothing
        if (level.isClientSide() || (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage()))
            return;

        if (reference.entity().tickCount > 0 && reference.entity().tickCount % tickInterval == 0) {
            // find nearby creepers about to explode
            List<Entity> nearbyCreepers = getNearbyCreepers(level, reference.entity(), radius);
            for (Entity creeper : nearbyCreepers) {
                if (creeper.getPersistentData().getBoolean(PREVENT_EXPLOSION_DAMAGE_NBT_KEY))
                    continue;

                // set NBT `mobtalismans:prevent_explosion_block_damage` to true
                creeper.getPersistentData().putBoolean(PREVENT_EXPLOSION_DAMAGE_NBT_KEY, true);

                // trigger advancement
                triggerTalismanAdvancement(reference, stack);

                // damage talisman
                damageTalisman(stack);
            }
        }
    }

    private List<Entity> getNearbyCreepers(Level level, Entity entity, double radius) {
        return level.getEntities(entity, entity.getBoundingBox().inflate(radius), e -> {
            return e instanceof Mob mob && mob.getType() == EntityType.CREEPER;
        });
    }
}
