package com.drunkencod.mobtalismans.mixin;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.advancement.ModCriteriaTriggers;
import com.drunkencod.mobtalismans.item.ModItems;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomSweepAttackGoal")
public class PhantomSweepAttackGoalMixin {

    @Final
    @Shadow
    Phantom this$0;

    @Unique
    private boolean mobtalismans$isScaredOfTalisman;

    @Unique
    private int mobtalismans$talismanSearchTick;

    @Inject(method = "canContinueToUse", at = @At("RETURN"), cancellable = true)
    private void mobtalismans$checkForTalisman(CallbackInfoReturnable<Boolean> cir) {
        try {
            if (!ModItems.PHANTOM_TALISMAN.get().isEnabled())
                return;

            if (!cir.getReturnValue())
                return;

            Phantom phantom = this$0;
            LivingEntity target = phantom.getTarget();
            if (target == null)
                return;

            // check periodically (every 20 ticks) like the cat check does
            if (phantom.tickCount > mobtalismans$talismanSearchTick) {
                mobtalismans$talismanSearchTick = phantom.tickCount + 20;
                mobtalismans$isScaredOfTalisman = false;

                // check if target is a player with a functional phantom talisman equipped
                if (target instanceof Player player) {
                    var capability = AccessoriesCapability.get(player);
                    if (capability != null) {
                        var equipped = capability.getEquipped(ModItems.PHANTOM_TALISMAN.get());
                        for (var entry : equipped) {
                            ItemStack stack = entry.stack();
                            // check if talisman is not broken
                            if (stack.getMaxDamage() <= 0 || stack.getDamageValue() < stack.getMaxDamage()) {
                                mobtalismans$isScaredOfTalisman = true;

                                MobTalismans.LOGGER.debug(
                                        "[Phantom Talisman]: Set {} to be scared of player {}",
                                        phantom, player.getName().getString());

                                var level = phantom.level();

                                if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer)
                                    ModCriteriaTriggers.TALISMAN_TRIGGERED.trigger(serverPlayer, stack.copy(),
                                            stack.copy());

                                // damage the talisman
                                if (stack.getMaxDamage() > 0)
                                    stack.setDamageValue(stack.getDamageValue() + 1);
                                break;
                            }
                        }
                    }
                }
            }

            if (mobtalismans$isScaredOfTalisman)
                cir.setReturnValue(false);
        } catch (Exception e) {
            MobTalismans.LOGGER.error("Error checking Phantom talisman effect:", e);
            cir.setReturnValue(true);
        }
    }
}
