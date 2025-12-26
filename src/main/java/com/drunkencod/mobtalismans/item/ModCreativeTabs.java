package com.drunkencod.mobtalismans.item;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.item.talisman.AbstractTalismanItem;
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
                        addTalismanItem(output, ModItems.CONDUIT_TALISMAN.get());
                        addTalismanItem(output, ModItems.CREEPER_TALISMAN.get());
                        addTalismanItem(output, ModItems.ENDERMAN_TALISMAN.get());
                        addTalismanItem(output, ModItems.PHANTOM_TALISMAN.get());
                        addTalismanItem(output, ModItems.SILVERFISH_TALISMAN.get());

                        output.accept(ModItems.CRUDE_TALISMAN_VESSEL.get());
                        output.accept(ModItems.REFINED_TALISMAN_VESSEL.get());
                        // output.accept(ModItems.SUPREME_TALISMAN_VESSEL.get());

                        output.accept(ModItems.ENDERMITE_SCALE.get());
                        output.accept(ModItems.SILVERFISH_SCALE.get());
                    }).build());

    private static void addTalismanItem(CreativeModeTab.Output output, AbstractTalismanItem item) {
        if (item.isEnabled())
            output.accept(item);
    }
}
