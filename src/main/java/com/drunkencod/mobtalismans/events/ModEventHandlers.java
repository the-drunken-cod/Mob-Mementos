package com.drunkencod.mobtalismans.events;

import com.drunkencod.mobtalismans.item.talisman.CreeperTalismanItem;
import com.drunkencod.mobtalismans.MobTalismans;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Explosion;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ExplosionEvent;

@EventBusSubscriber(modid = MobTalismans.MOD_ID)
public class ModEventHandlers {
    // #region creeper talisman - prevent creeper explosion block damage:
    @SubscribeEvent
    public static void onBeforeCreeperExplode(ExplosionEvent.Start event) {
        var explosion = event.getExplosion();
        var level = event.getLevel();

        if (!level.isClientSide && explosion.getDirectSourceEntity() instanceof Creeper creeper) {
            if (creeper.getPersistentData().getBoolean(CreeperTalismanItem.PREVENT_EXPLOSION_DAMAGE_NBT_KEY)) {
                // create new explosion that does not break blocks
                Explosion newExplosion = new Explosion(
                        level,
                        creeper,
                        creeper.getX(),
                        creeper.getY(),
                        creeper.getZ(),
                        (creeper.isPowered() ? 5f : 2.5f),
                        false,
                        Explosion.BlockInteraction.KEEP);

                // trigger the new explosion
                newExplosion.explode();
                newExplosion.finalizeExplosion(true);

                MobTalismans.LOGGER.debug("[Creeper Talisman]: Prevented block damage from {}", creeper);
                event.setCanceled(true);
            }
        }
    }

    // #region enderman talisman - enderman griefing prevention:
}
