package com.drunkencod.mobtalismans;

import org.slf4j.Logger;
import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.item.*;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MobTalismans.MOD_ID)
public class MobTalismans {
    public static final String MOD_ID = "mobtalismans";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MobTalismans(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.STARTUP, ModStartupConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
}
