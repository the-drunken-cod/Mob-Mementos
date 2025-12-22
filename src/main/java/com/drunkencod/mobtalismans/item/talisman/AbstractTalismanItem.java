package com.drunkencod.mobtalismans.item.talisman;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;

import java.util.List;

import com.drunkencod.mobtalismans.advancement.ModCriteriaTriggers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
        // noop
    }

    protected static Item.Properties getDefaultProps(int durability) {
        Item.Properties props = new Item.Properties().stacksTo(1);
        if (durability > 0)
            props = props.durability(durability);
        return props;
    }

    protected void damageTalisman(ItemStack stack) {
        if (stack.getMaxDamage() > 0 && stack.getDamageValue() < stack.getMaxDamage())
            stack.setDamageValue(stack.getDamageValue() + 1);
    }

    protected Component getTooltip(String registryName) {
        return Component.translatable("item.mobtalismans." + registryName + ".tooltip")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
    }

    protected void triggerTalismanAdvancement(SlotReference reference, ItemStack stack) {
        var level = reference.entity().level();
        if (!level.isClientSide() && reference.entity() instanceof ServerPlayer serverPlayer)
            ModCriteriaTriggers.TALISMAN_TRIGGERED.trigger(serverPlayer, stack.copy(), stack.copy());
    }
}
