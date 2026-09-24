package com.drunkencod.mobtalismans.recipe;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.item.ModItems;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = MobTalismans.MOD_ID)
public class ModBrewingRecipes {
    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(
                Potions.WATER,
                ModItems.SILVERFISH_SCALE.get(),
                Potions.INFESTED);
    }
}
