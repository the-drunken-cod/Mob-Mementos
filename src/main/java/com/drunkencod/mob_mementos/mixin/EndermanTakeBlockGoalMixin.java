package com.drunkencod.mob_mementos.mixin;

import com.drunkencod.mob_mementos.item.memento.EndermanAnkletItem;
import com.drunkencod.mob_mementos.MobMementos;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanTakeBlockGoal")
public class EndermanTakeBlockGoalMixin {
    @Final
    @Shadow
    private EnderMan enderman;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void mob_mementos$preventGriefing(CallbackInfoReturnable<Boolean> cir) {
        try {
            if (enderman.getPersistentData().getBoolean(EndermanAnkletItem.PREVENT_ENDERMAN_GRIEFING_NBT_KEY))
                cir.setReturnValue(false);
        } catch (Exception e) {
            MobMementos.LOGGER.error("Error checking Enderman NBT", e);
            cir.setReturnValue(true);
        }
    }
}
