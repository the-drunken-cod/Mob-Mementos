package com.drunkencod.mobtalismans.item;

import java.util.List;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public abstract class AbstractTalismanItem extends Item implements Accessory {
    public AbstractTalismanItem(Item.Properties properties) {
        super(properties);

        AccessoriesAPI.registerAccessory(this, this);
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips, TooltipContext tooltipContext,
            TooltipFlag tooltipType) {
        tooltips.add(Component.literal("A mystical talisman imbued with ancient powers."));
    }
}
