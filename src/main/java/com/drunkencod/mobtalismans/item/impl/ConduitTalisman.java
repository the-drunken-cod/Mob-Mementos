package com.drunkencod.mobtalismans.item.impl;

import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.item.AbstractTalismanItem;
import io.wispforest.accessories.api.slot.SlotReference;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ConduitTalisman extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "conduit_talisman";

    public ConduitTalisman() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips, TooltipContext tooltipContext,
            TooltipFlag tooltipType) {
        tooltips.add(Component.translatable("item.mobtalismans.conduit_talisman.tooltip"));
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        Level level = reference.entity().level();

        double radius = ModStartupConfig.CONDUIT_TALISMAN_RADIUS.getAsDouble();
        double damage = ModStartupConfig.CONDUIT_TALISMAN_DAMAGE.getAsDouble();
        int tickInterval = ModStartupConfig.CONDUIT_TALISMAN_TICK_INTERVAL.getAsInt();

        // find nearby hostile aquatic mobs
        List<Entity> nearbyHostileAquaticMobs = getNearbyHostileAquaticMobs(level, reference.entity(), radius);
        for (Entity mob : nearbyHostileAquaticMobs) {
            // hurt mob if tick count is multiple of tick interval
            if (mob.tickCount > 0 && mob.tickCount % tickInterval == 0) {
                DamageSources damageSources = level.damageSources();
                mob.hurt(damageSources.magic(), (float) damage);

                // play conduit attack sound
                level.playLocalSound(mob.blockPosition(), SoundEvents.CONDUIT_ATTACK_TARGET, mob.getSoundSource(),
                        1.0F, 1.0F, false);
                // spawn conduit particles
                RandomSource randomSource = level.getRandom();
                for (int i = 0; i < 15; i++) {
                    Vec3 mobPos = new Vec3(mob.getX(), mob.getEyeY(), mob.getZ());
                    float vecX = (-0.5f + randomSource.nextFloat()) * (3.0f + mob.getBbWidth());
                    float vecY = -1.0f + randomSource.nextFloat() * mob.getBbHeight();
                    float vecZ = (-0.5f + randomSource.nextFloat()) * (3.0f + mob.getBbWidth());
                    Vec3 particleSpeed = new Vec3(vecX, vecY, vecZ);
                    level.addParticle(ParticleTypes.NAUTILUS, mobPos.x, mobPos.y, mobPos.z, particleSpeed.x,
                            particleSpeed.y, particleSpeed.z);
                }
            }
        }
    }

    private List<Entity> getNearbyHostileAquaticMobs(Level level, Entity entity, double radius) {
        return level.getEntities(entity, entity.getBoundingBox().inflate(radius), e -> {
            return e.getType().getTags()
                    .anyMatch(tag -> tag.location().toString().equals("mobtalismans:hostile_aquatic"))
                    && e instanceof Mob;
        });
    }
}
