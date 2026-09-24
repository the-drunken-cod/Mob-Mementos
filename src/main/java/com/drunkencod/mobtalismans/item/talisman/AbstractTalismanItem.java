package com.drunkencod.mobtalismans.item.talisman;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.rendering.RenderingFunction;
import io.wispforest.accessories.api.client.Side;
import io.wispforest.accessories.api.client.Transformation;
import io.wispforest.accessories.api.components.AccessoriesDataComponents;
import io.wispforest.accessories.api.components.AccessoryCustomRendererComponent;
import io.wispforest.accessories.api.slot.SlotReference;

import java.util.List;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.advancement.ModCriteriaTriggers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public abstract class AbstractTalismanItem extends Item implements Accessory {
    protected String registryName;

    public AbstractTalismanItem(String registryName, Item.Properties properties) {
        super(properties);

        this.registryName = registryName;

        AccessoriesAPI.registerAccessory(this, this);
    }

    // #region abstract

    public abstract boolean isEnabled();

    // #region props

    protected static Item.Properties getDefaultProps(int durability) {
        Item.Properties props = new Item.Properties().stacksTo(1);
        if (durability > 0)
            props = props.durability(durability);
        return props;
    }

    protected static Item.Properties getCustomModelDefaultProps(String registryName, String modelPartIdentifier,
            int durability) {
        Item.Properties props = new Item.Properties().stacksTo(1);
        if (durability > 0)
            props = props.durability(durability);
        return props.component(AccessoriesDataComponents.CUSTOM_RENDERER,
                AbstractTalismanItem.wrapRenderer(registryName, modelPartIdentifier));
    }

    // #region damage

    protected void damageTalisman(ItemStack stack, ServerLevel level, Player player) {
        AbstractTalismanItem.applyTalismanDamage(stack, level, player);
    }

    public static void applyTalismanDamage(ItemStack stack, ServerLevel level, Player player) {
        if (!stack.isDamageableItem())
            return;

        // play break sound if going from 1 to 0
        if (stack.getDamageValue() == stack.getMaxDamage() - 1) {
            level.playSound(null, player.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.7f,
                    0.8f + level.getRandom().nextFloat() * 0.4f);
        }

        stack.hurtAndBreak(1, level, null, item -> {
            // noop
        });
    }

    // #region tooltip

    protected Component getTooltip(String registryName) {
        return Component.translatable("item.mobtalismans." + registryName + ".tooltip")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips, TooltipContext tooltipContext,
            TooltipFlag tooltipType) {
        if (!isEnabled())
            tooltips.add(Component.translatable("tooltip.mobtalismans.disabled")
                    .withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
        else
            tooltips.add(getTooltip(registryName));
    }

    // #region advancements

    protected void triggerTalismanAdvancement(SlotReference reference, ItemStack stack) {
        var level = reference.entity().level();
        if (!level.isClientSide() && reference.entity() instanceof ServerPlayer serverPlayer)
            ModCriteriaTriggers.TALISMAN_TRIGGERED.trigger(serverPlayer, stack.copy(), stack.copy());
    }

    // #region render custom model

    protected static AccessoryCustomRendererComponent wrapRenderer(String registryName, String modelPartIdentifier) {
        var model = RenderingFunction.ofModel(
                ResourceLocation.fromNamespaceAndPath(MobTalismans.MOD_ID, "accessories/" + registryName),
                "standalone");
        var transformation = RenderingFunction.ofTransformation(
                List.of(Transformation.modelTarget(modelPartIdentifier, (Side) null)),
                model);

        return new AccessoryCustomRendererComponent(List.of(transformation));
    }
}
