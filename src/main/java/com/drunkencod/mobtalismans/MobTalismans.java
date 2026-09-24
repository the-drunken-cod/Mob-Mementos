package com.drunkencod.mobtalismans;

import org.slf4j.Logger;

import com.drunkencod.mobtalismans.advancement.ModCriteriaTriggers;
import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.data.ModConditions;
import com.drunkencod.mobtalismans.item.*;
import com.drunkencod.mobtalismans.sound.ModSoundEvents;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MobTalismans.MOD_ID)
public class MobTalismans {
    public static final String MOD_ID = "mobtalismans";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MobTalismans(IEventBus modEventBus, ModContainer modContainer) {
        // modEventBus.addListener(this::commonSetup);

        ModConditions.register(modEventBus);

        ModItems.ITEMS.register(modEventBus);

        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        ModSoundEvents.register(modEventBus);

        ModCriteriaTriggers.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.STARTUP, ModStartupConfig.SPEC);
    }

    // private void commonSetup(FMLCommonSetupEvent event) {
    // // Some common setup code
    // }
}
