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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CreeperTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "creeper_talisman";

    public static final String PREVENT_EXPLOSION_DAMAGE_NBT_KEY = MobTalismans.MOD_ID
            + ":prevent_explosion_block_damage";

    public CreeperTalismanItem() {
        super(REGISTRY_NAME, getDefaultProps(ModStartupConfig.CREEPER_TALISMAN.DURABILITY.get())
                .rarity(Rarity.UNCOMMON));
    }

    public boolean isEnabled() {
        return ModStartupConfig.CREEPER_TALISMAN.ENABLED.get();
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!isEnabled())
            return;

        Entity playerEntity = reference.entity();
        Level level = reference.entity().level();

        double radius = ModStartupConfig.CREEPER_TALISMAN.RADIUS.get();
        int tickInterval = ModStartupConfig.CREEPER_TALISMAN.CHECK_INTERVAL_TICKS.get();

        // if talisman is broken, do nothing
        if ((stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage()))
            return;

        if (reference.entity().tickCount > 0 && reference.entity().tickCount % tickInterval == 0) {
            // find nearby creepers about to explode
            List<Entity> nearbyCreepers = getNearbyCreepers(level, reference.entity(), radius);
            for (Entity creeper : nearbyCreepers) {
                if (creeper.getPersistentData().getBoolean(PREVENT_EXPLOSION_DAMAGE_NBT_KEY))
                    continue;

                MobTalismans.LOGGER.debug("[Creeper Talisman]: Defusing explosion for {}", creeper);

                // set NBT key
                creeper.getPersistentData().putBoolean(PREVENT_EXPLOSION_DAMAGE_NBT_KEY, true);

                if (!level.isClientSide()) {
                    // trigger advancement
                    triggerTalismanAdvancement(reference, stack);

                    // make defuse sound
                    level.playSound(null, creeper.blockPosition(),
                            SoundEvents.CREEPER_HURT, SoundSource.HOSTILE, 0.9f,
                            1.5f + level.getRandom().nextFloat() * 0.25f);

                    // damage talisman
                    if (playerEntity instanceof Player player)
                        damageTalisman(stack, (ServerLevel) level, (Player) player);
                } else {
                    // dust poof particles
                    RandomSource randomSource = level.getRandom();
                    for (int i = 0; i < 20; i++) {
                        Vec3 mobPos = new Vec3(
                                creeper.getX()
                                        + randomSource.nextFloat() * creeper.getBbWidth(),
                                creeper.getEyeY(),
                                creeper.getZ()
                                        + randomSource.nextFloat() * creeper.getBbWidth());

                        Vec3 particleSpeed = new Vec3(
                                -0.15f + randomSource.nextFloat() * 0.3f,
                                0.1f + randomSource.nextFloat() * 0.25f,
                                -0.15f + randomSource.nextFloat() * 0.3f);

                        level.addParticle(ParticleTypes.DUST_PLUME, mobPos.x, mobPos.y, mobPos.z, particleSpeed.x,
                                particleSpeed.y, particleSpeed.z);
                    }
                }
            }
        }
    }

    private List<Entity> getNearbyCreepers(Level level, Entity entity, double radius) {
        return level.getEntities(entity, entity.getBoundingBox().inflate(radius), e -> {
            return e instanceof Mob mob && mob.getType() == EntityType.CREEPER;
        });
    }
}
