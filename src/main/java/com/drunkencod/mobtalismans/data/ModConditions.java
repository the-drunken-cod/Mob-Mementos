package com.drunkencod.mobtalismans.data;

import com.drunkencod.mobtalismans.MobTalismans;
import com.drunkencod.mobtalismans.data.condition.ConfigEnabledCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import com.mojang.serialization.MapCodec;

public class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS = DeferredRegister
            .create(NeoForgeRegistries.CONDITION_SERIALIZERS, MobTalismans.MOD_ID);

    static {
        CONDITIONS.register(ConfigEnabledCondition.REGISTRY_NAME, () -> ConfigEnabledCondition.CODEC);
    }

    public static void register(IEventBus eventBus) {
        CONDITIONS.register(eventBus);
    }
}