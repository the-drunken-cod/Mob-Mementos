package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.MobTalismans;
import io.wispforest.accessories.api.slot.SlotReference;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ConduitTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "conduit_talisman";

    public ConduitTalismanItem() {
        super(REGISTRY_NAME, getDefaultProps(ModStartupConfig.CONDUIT_TALISMAN.DURABILITY.get())
                .rarity(Rarity.UNCOMMON));
    }

    public boolean isEnabled() {
        return ModStartupConfig.CONDUIT_TALISMAN.ENABLED.get();
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!isEnabled())
            return;

        Entity playerEntity = reference.entity();
        Level level = playerEntity.level();

        double radius = ModStartupConfig.CONDUIT_TALISMAN.RADIUS.get();
        double damage = ModStartupConfig.CONDUIT_TALISMAN.DAMAGE.get();
        int tickInterval = ModStartupConfig.CONDUIT_TALISMAN.DAMAGE_INTERVAL_TICKS.get();

        // if talisman is broken, do nothing
        if (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage())
            return;

        // find nearby hostile aquatic mobs
        List<Entity> nearbyHostileAquaticMobs = getNearbyHostileAquaticMobs(level, reference.entity(), radius);
        for (Entity mob : nearbyHostileAquaticMobs) {
            // hurt mob if tick count is multiple of tick interval
            if (mob.tickCount > 0 && mob.tickCount % tickInterval == 0) {
                if (level.isClientSide()) {
                    // spawn conduit particles
                    RandomSource randomSource = level.getRandom();
                    for (int i = 0; i < 10; i++) {
                        Vec3 mobPos = new Vec3(mob.getX(), mob.getEyeY(), mob.getZ());
                        float vecX = (-0.5f + randomSource.nextFloat()) * (2.5f + mob.getBbWidth());
                        float vecY = -1.0f + randomSource.nextFloat() * mob.getBbHeight();
                        float vecZ = (-0.5f + randomSource.nextFloat()) * (2.5f + mob.getBbWidth());
                        Vec3 particleSpeed = new Vec3(vecX, vecY, vecZ);
                        level.addParticle(ParticleTypes.NAUTILUS, mobPos.x, mobPos.y, mobPos.z, particleSpeed.x,
                                particleSpeed.y, particleSpeed.z);
                    }
                } else {
                    MobTalismans.LOGGER.debug("[Conduit Talisman]: Damaging mob {}", mob);

                    // prevent mob from dropping experience
                    if (mob instanceof LivingEntity livingEntity)
                        livingEntity.skipDropExperience();

                    // damage mob
                    DamageSources damageSources = level.damageSources();
                    mob.hurt(damageSources.magic(), (float) damage);

                    // play conduit attack sound
                    level.playLocalSound(mob.blockPosition(), SoundEvents.CONDUIT_ATTACK_TARGET, SoundSource.HOSTILE,
                            1.0F, 1.0F, false);

                    // trigger advancement
                    triggerTalismanAdvancement(reference, stack);

                    // damage talisman
                    if (playerEntity instanceof Player player)
                        damageTalisman(stack, (ServerLevel) level, (Player) player);
                }
            }
        }
    }

    private List<Entity> getNearbyHostileAquaticMobs(Level level, Entity entity, double radius) {
        return level.getEntities(entity, entity.getBoundingBox().inflate(radius), e -> {
            return e.getType().getTags()
                    .anyMatch(tag -> tag.location().toString().equals(MobTalismans.MOD_ID + ":hostile_aquatic"))
                    && e instanceof Mob;
        });
    }
}
