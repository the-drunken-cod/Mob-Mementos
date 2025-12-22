package com.drunkencod.mobtalismans.gui;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, MobTalismans.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOB_TALISMANS_TAB = CREATIVE_MODE_TABS
            .register("mob_talismans_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.mobtalismans"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.CONDUIT_TALISMAN.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CONDUIT_TALISMAN.get());
                    }).build());
}
