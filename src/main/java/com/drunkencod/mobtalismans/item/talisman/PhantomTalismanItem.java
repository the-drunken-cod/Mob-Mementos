package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.config.ModStartupConfig;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class PhantomTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "phantom_talisman";

    public PhantomTalismanItem() {
        super(getDefaultProps(ModStartupConfig.PHANTOM_TALISMAN.DURABILITY.get()));
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips, TooltipContext tooltipContext,
            TooltipFlag tooltipType) {
        tooltips.add(Component.translatable("item.mobtalismans.phantom_talisman.tooltip"));
    }
}
