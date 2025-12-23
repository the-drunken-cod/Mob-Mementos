package com.drunkencod.mobtalismans.mixin;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.item.talisman.EndermanTalismanItem;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanTakeBlockGoal")
public class EndermanTakeBlockGoalMixin {

    @Final
    @Shadow
    private EnderMan enderman;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void mobtalismans$preventGriefingWithTalisman(CallbackInfoReturnable<Boolean> cir) {
        try {
            if (enderman.getPersistentData().getBoolean(EndermanTalismanItem.PREVENT_ENDERMAN_GRIEFING_NBT_KEY))
                cir.setReturnValue(false);
        } catch (Exception e) {
            MobTalismans.LOGGER.error("Error checking Enderman talisman NBT", e);
            cir.setReturnValue(true);
        }
    }
}
