package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.config.ModStartupConfig;

public class PhantomTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "phantom_talisman";

    public PhantomTalismanItem() {
        super(REGISTRY_NAME, getDefaultProps(ModStartupConfig.PHANTOM_TALISMAN.DURABILITY.get()));
    }

    public boolean isEnabled() {
        return ModStartupConfig.PHANTOM_TALISMAN.ENABLED.get();
    }
}
