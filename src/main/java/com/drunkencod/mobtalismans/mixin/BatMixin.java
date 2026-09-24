package com.drunkencod.mobtalismans.mixin;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.advancement.ModCriteriaTriggers;
import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.item.ModItems;

import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.ambient.Bat")
public class BatMixin {

    @Unique
    private static final String mobtalismans$PENDING_NBT_KEY = "mobtalismans:bat_talisman_pending";

    @Unique
    private static final String mobtalismans$ASLEEP_NBT_KEY = "mobtalismans:bat_talisman_asleep";

    @Inject(method = "customServerAiStep", at = @At("HEAD"), cancellable = true)
    private void mobtalismans$forceSleepWithTalisman(CallbackInfo ci) {
        try {
            Bat bat = (Bat) (Object) this;
            var persistentData = bat.getPersistentData();

            // already naturally settled on a ceiling while pending: stay resting forever,
            // ignoring the vanilla wake-up/flight logic entirely
            if (persistentData.getBoolean(mobtalismans$ASLEEP_NBT_KEY)) {
                bat.setResting(true);
                ci.cancel();
                return;
            }

            // already triggered, just waiting for the vanilla AI to find a ceiling and
            // rest naturally, so let the vanilla logic keep running this tick
            if (persistentData.getBoolean(mobtalismans$PENDING_NBT_KEY))
                return;

            if (!ModItems.BAT_TALISMAN.get().isEnabled())
                return;

            if (!(bat.level() instanceof ServerLevel serverLevel))
                return;

            double radius = ModStartupConfig.BAT_TALISMAN.RADIUS.get();
            double radiusSq = radius * radius;

            Player closestWearer = null;
            ItemStack closestStack = ItemStack.EMPTY;
            double closestDistSq = Double.MAX_VALUE;

            for (Player player : serverLevel.players()) {
                double distSq = player.distanceToSqr(bat.getX(), bat.getY(), bat.getZ());
                if (distSq > radiusSq)
                    continue;

                var capability = AccessoriesCapability.get(player);
                if (capability == null)
                    continue;

                for (var entry : capability.getEquipped(ModItems.BAT_TALISMAN.get())) {
                    ItemStack stack = entry.stack();
                    if (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage())
                        continue;

                    if (distSq < closestDistSq) {
                        closestDistSq = distSq;
                        closestWearer = player;
                        closestStack = stack;
                    }
                    break;
                }
            }

            if (closestWearer == null)
                return;

            bat.setSilent(true);

            if (closestWearer instanceof ServerPlayer serverPlayer)
                ModCriteriaTriggers.TALISMAN_TRIGGERED.trigger(serverPlayer, closestStack.copy(), closestStack.copy());

            if (closestStack.isDamageableItem())
                closestStack.hurtAndBreak(1, serverLevel, null, item -> {
                    // noop
                });

            int searchDistance = ModStartupConfig.BAT_TALISMAN.CEILING_SEARCH_DISTANCE.get();
            BlockPos ceilingSpot = searchDistance > 0
                    ? mobtalismans$findCeilingSpot(serverLevel, bat.blockPosition(), searchDistance)
                    : null;

            if (ceilingSpot != null) {
                // a solid ceiling was found nearby: teleport straight there and sleep for good
                bat.teleportTo(ceilingSpot.getX() + 0.5, ceilingSpot.getY(), ceilingSpot.getZ() + 0.5);
                bat.setResting(true);
                persistentData.putBoolean(mobtalismans$ASLEEP_NBT_KEY, true);
                ci.cancel();

                MobTalismans.LOGGER.debug("[Bat Talisman]: Teleported Bat to ceiling near player {}",
                        closestWearer.getName().getString());
            } else {
                // no ceiling found within range: fall back to waiting for the vanilla AI to
                // find one and rest naturally
                persistentData.putBoolean(mobtalismans$PENDING_NBT_KEY, true);

                MobTalismans.LOGGER.debug(
                        "[Bat Talisman]: No ceiling found near player {}, Bat will fall asleep once it finds a perch",
                        closestWearer.getName().getString());
            }
        } catch (Exception e) {
            MobTalismans.LOGGER.error("[Bat Talisman] Error while triggering Bat sleep", e);
        }
    }

    @Nullable
    @Unique
    private static BlockPos mobtalismans$findCeilingSpot(ServerLevel level, BlockPos start, int maxDistance) {
        BlockPos.MutableBlockPos pos = start.mutable();
        int maxY = Math.min(start.getY() + maxDistance, level.getMaxBuildHeight() - 1);

        for (int y = start.getY(); y <= maxY; y++) {
            pos.setY(y);
            if (!level.isEmptyBlock(pos))
                continue;

            BlockPos above = pos.above();
            if (level.getBlockState(above).isRedstoneConductor(level, above))
                return pos.immutable();
        }

        return null;
    }

    @Inject(method = "customServerAiStep", at = @At("TAIL"))
    private void mobtalismans$lockSleepOnceRested(CallbackInfo ci) {
        try {
            Bat bat = (Bat) (Object) this;
            var persistentData = bat.getPersistentData();

            if (persistentData.getBoolean(mobtalismans$ASLEEP_NBT_KEY))
                return;

            // once the bat has naturally found a ceiling and started resting on its own,
            // lock it into permanent sleep from now on
            if (persistentData.getBoolean(mobtalismans$PENDING_NBT_KEY) && bat.isResting())
                persistentData.putBoolean(mobtalismans$ASLEEP_NBT_KEY, true);
        } catch (Exception e) {
            MobTalismans.LOGGER.error("[Bat Talisman] Error while locking Bat to sleep", e);
        }
    }
}
