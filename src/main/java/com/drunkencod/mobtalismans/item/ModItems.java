package com.drunkencod.mobtalismans.item;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.item.impl.ConduitTalisman;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MobTalismans.MOD_ID);

    public static final DeferredItem<ConduitTalisman> CONDUIT_TALISMAN = ITEMS.register(
            ConduitTalisman.REGISTRY_NAME, ConduitTalisman::new);
}
