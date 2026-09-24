package com.drunkencod.mobtalismans.mixin;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.advancement.ModCriteriaTriggers;
import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.item.ModItems;
import com.drunkencod.mobtalismans.sound.ModSoundEvents;

import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(targets = "net.minecraft.world.entity.ambient.Bat")
public class BatMixin {

    @Unique
    private static final String mobtalismans$COOLDOWN_NBT_KEY = "mobtalismans:bat_talisman_cooldown_until";

    private record Wearer(Player player, ItemStack stack, double distSq) {
    }

    @Inject(method = "checkBatSpawnRules", at = @At("HEAD"), cancellable = true)
    private static void mobtalismans$preventSpawningWithTalisman(EntityType<Bat> entityType,
            LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource,
            CallbackInfoReturnable<Boolean> cir) {
        try {
            if (!ModItems.BAT_TALISMAN.get().isEnabled())
                return;

            double radius = ModStartupConfig.BAT_TALISMAN.RADIUS.get();
            double radiusSq = radius * radius;

            List<Wearer> wearers = new ArrayList<>();

            // 1. get all players within ModStartupConfig.BAT_TALISMAN.RADIUS
            for (Player player : levelAccessor.players()) {
                double distSq = player.distanceToSqr(blockPos.getX() + 0.5, blockPos.getY() + 0.5,
                        blockPos.getZ() + 0.5);
                if (distSq > radiusSq)
                    continue;

                // 2. if any player is wearing a talisman, prevent spawn
                var capability = AccessoriesCapability.get(player);
                if (capability == null)
                    continue;

                for (var entry : capability.getEquipped(ModItems.BAT_TALISMAN.get())) {
                    ItemStack stack = entry.stack();
                    if (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage())
                        continue;

                    wearers.add(new Wearer(player, stack, distSq));
                    break;
                }
            }

            if (wearers.isEmpty())
                return;

            // always prevent the spawn while any nearby player wears a functional talisman,
            // regardless of that player's trigger cooldown
            cir.setReturnValue(false);

            int cooldownTicks = ModStartupConfig.BAT_TALISMAN.COOLDOWN_TICKS.get();

            Wearer closestTriggered = null;
            double closestDistSq = Double.MAX_VALUE;

            // 3. apply 1 damage to the talisman of every nearby player whose cooldown has
            // elapsed
            for (Wearer wearer : wearers) {
                var playerLevel = wearer.player().level();
                long currentTick = playerLevel.getGameTime();
                var persistentData = wearer.player().getPersistentData();
                long cooldownUntil = persistentData.getLong(mobtalismans$COOLDOWN_NBT_KEY);
                if (currentTick < cooldownUntil)
                    continue;

                persistentData.putLong(mobtalismans$COOLDOWN_NBT_KEY, currentTick + cooldownTicks);

                if (!playerLevel.isClientSide() && wearer.player() instanceof ServerPlayer serverPlayer)
                    ModCriteriaTriggers.TALISMAN_TRIGGERED.trigger(serverPlayer, wearer.stack().copy(),
                            wearer.stack().copy());

                if (wearer.stack().isDamageableItem() && playerLevel instanceof ServerLevel serverLevel)
                    wearer.stack().hurtAndBreak(1, serverLevel, null, item -> {
                        // noop
                    });

                if (wearer.distSq() < closestDistSq) {
                    closestDistSq = wearer.distSq();
                    closestTriggered = wearer;
                }
            }

            // every wearer is still on cooldown, nothing left to do
            if (closestTriggered == null)
                return;

            MobTalismans.LOGGER.debug("[Bat Talisman]: Preventing Bat spawn near player {}",
                    closestTriggered.player().getName().getString());

            // play pitched down bat takeoff sound closer to the (closest triggered) player,
            // along the vector towards the bat spawn location, so it's actually audible
            // instead of playing far away
            if (levelAccessor instanceof ServerLevel level) {
                float pitchRand = randomSource.nextFloat() / 10;

                Vec3 playerPos = closestTriggered.player().position();
                Vec3 toSpawn = Vec3.atCenterOf(blockPos).subtract(playerPos);
                double soundDist = Math.min(5.0, toSpawn.length());
                Vec3 soundPos = toSpawn.lengthSqr() > 1.0E-4
                        ? playerPos.add(toSpawn.normalize().scale(soundDist))
                        : playerPos;

                level.playLocalSound(soundPos.x, soundPos.y, soundPos.z, ModSoundEvents.BAT_TALISMAN_TRIGGERED.get(),
                        SoundSource.NEUTRAL, 1.0F, 0.65F + pitchRand, false);
            }
        } catch (Exception e) {
            MobTalismans.LOGGER.error("[Bat Talisman] Error while preventing Bat spawn", e);
            cir.setReturnValue(true);
        }
    }
}
