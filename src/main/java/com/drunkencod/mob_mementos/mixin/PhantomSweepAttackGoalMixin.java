package com.drunkencod.mob_mementos.mixin;

import com.drunkencod.mob_mementos.advancement.ModCriteriaTriggers;
import com.drunkencod.mob_mementos.item.ModItems;
import com.drunkencod.mob_mementos.MobMementos;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomSweepAttackGoal")
public class PhantomSweepAttackGoalMixin {
    @Final
    @Shadow
    Phantom this$0;

    @Unique
    private boolean mob_mementos$isScared;

    @Unique
    private int mob_mementos$mementoSearchTick;

    @Inject(method = "canContinueToUse", at = @At("RETURN"), cancellable = true)
    private void mob_mementos$checkForMemento(CallbackInfoReturnable<Boolean> cir) {
        try {
            if (!ModItems.PHANTOM_CLOAK.get().isEnabled())
                return;

            if (!cir.getReturnValue())
                return;

            Phantom phantom = this$0;
            LivingEntity target = phantom.getTarget();
            if (target == null)
                return;

            // check periodically (every 20 ticks) like the cat check does
            if (phantom.tickCount > mob_mementos$mementoSearchTick) {
                mob_mementos$mementoSearchTick = phantom.tickCount + 20;
                mob_mementos$isScared = false;

                // check if target is a player with a functional phantom memento equipped
                if (target instanceof Player player) {
                    var capability = AccessoriesCapability.get(player);
                    if (capability != null) {
                        var equipped = capability.getEquipped(ModItems.PHANTOM_CLOAK.get());
                        for (var entry : equipped) {
                            ItemStack stack = entry.stack();
                            // check if memento is not broken
                            if (stack.getMaxDamage() <= 0 || stack.getDamageValue() < stack.getMaxDamage()) {
                                mob_mementos$isScared = true;

                                MobMementos.LOGGER.debug(
                                        "[Phantom Cloak]: Set {} to be scared of player {}",
                                        phantom, player.getName().getString());

                                var level = phantom.level();

                                if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer)
                                    ModCriteriaTriggers.MEMENTO_TRIGGERED.trigger(serverPlayer, stack.copy(),
                                            stack.copy());

                                // damage the memento
                                if (stack.getMaxDamage() > 0)
                                    stack.setDamageValue(stack.getDamageValue() + 1);
                                break;
                            }
                        }
                    }
                }
            }

            if (mob_mementos$isScared)
                cir.setReturnValue(false);
        } catch (Exception e) {
            MobMementos.LOGGER.error("Error checking Phantom Cloak effect:", e);
            cir.setReturnValue(true);
        }
    }
}
