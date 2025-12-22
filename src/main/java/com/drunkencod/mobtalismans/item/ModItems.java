package com.drunkencod.mobtalismans.item;

import com.drunkencod.mobtalismans.item.ingredient.*;
import com.drunkencod.mobtalismans.item.talisman.*;
import com.drunkencod.mobtalismans.item.vessel.*;
import com.drunkencod.mobtalismans.MobTalismans;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MobTalismans.MOD_ID);

    // #region Ingredients:

    public static final DeferredItem<SilverfishScaleItem> SILVERFISH_SCALE = ITEMS.register(
            SilverfishScaleItem.REGISTRY_NAME, SilverfishScaleItem::new);

    // #region Vessels:

    public static final DeferredItem<CrudeTalismanVesselItem> CRUDE_TALISMAN_VESSEL = ITEMS.register(
            CrudeTalismanVesselItem.REGISTRY_NAME, CrudeTalismanVesselItem::new);

    public static final DeferredItem<RefinedTalismanVesselItem> REFINED_TALISMAN_VESSEL = ITEMS.register(
            RefinedTalismanVesselItem.REGISTRY_NAME, RefinedTalismanVesselItem::new);

    public static final DeferredItem<SupremeTalismanVesselItem> SUPREME_TALISMAN_VESSEL = ITEMS.register(
            SupremeTalismanVesselItem.REGISTRY_NAME, SupremeTalismanVesselItem::new);

    // #region Talismans:

    public static final DeferredItem<ConduitTalismanItem> CONDUIT_TALISMAN = ITEMS.register(
            ConduitTalismanItem.REGISTRY_NAME, ConduitTalismanItem::new);

    public static final DeferredItem<SilverfishTalismanItem> SILVERFISH_TALISMAN = ITEMS.register(
            SilverfishTalismanItem.REGISTRY_NAME, SilverfishTalismanItem::new);
}
