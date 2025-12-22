package com.drunkencod.mobtalismans.item.vessel;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RefinedTalismanVesselItem extends Item {
    public static final String REGISTRY_NAME = "refined_talisman_vessel";

    public RefinedTalismanVesselItem() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }
}
