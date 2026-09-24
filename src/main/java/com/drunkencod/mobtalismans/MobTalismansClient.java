package com.drunkencod.mobtalismans;

import java.util.List;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = MobTalismans.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = MobTalismans.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class MobTalismansClient {
    public MobTalismansClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    // @SubscribeEvent
    // static void onClientSetup(FMLClientSetupEvent event) {
    // MobTalismans.LOGGER.info("HELLO FROM CLIENT SETUP");
    // MobTalismans.LOGGER.info("MINECRAFT NAME >> {}",
    // Minecraft.getInstance().getUser().getName());
    // }

    static List<String> additionalModelPaths = List.of(
            "accessories/bat_talisman");

    @SubscribeEvent
    static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
        for (String path : additionalModelPaths) {
            event.register(ModelResourceLocation.standalone(
                    ResourceLocation.fromNamespaceAndPath(MobTalismans.MOD_ID, path)));
        }
    }
}
