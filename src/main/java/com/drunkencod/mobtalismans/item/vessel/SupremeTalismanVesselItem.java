package com.drunkencod.mobtalismans.item.vessel;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class SupremeTalismanVesselItem extends Item {
    public static final String REGISTRY_NAME = "supreme_talisman_vessel";

    public SupremeTalismanVesselItem() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }
}
