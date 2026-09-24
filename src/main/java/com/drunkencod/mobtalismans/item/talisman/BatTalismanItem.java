package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.config.ModStartupConfig;

public class BatTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "bat_talisman";

    public BatTalismanItem() {
        super(REGISTRY_NAME,
                getCustomModelDefaultProps(REGISTRY_NAME, "head", ModStartupConfig.BAT_TALISMAN.DURABILITY.get()));
    }

    public boolean isEnabled() {
        return ModStartupConfig.BAT_TALISMAN.ENABLED.get();
    }
}
