package com.drunkencod.mobtalismans.data.condition;

import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConfigEnabledCondition(String configKey) implements ICondition {
    public static final String REGISTRY_NAME = "config_enabled";

    public static final MapCodec<ConfigEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("config_key").forGetter(ConfigEnabledCondition::configKey))
            .apply(instance, ConfigEnabledCondition::new));

    @Override
    public boolean test(IContext context) {
        return switch (configKey) {
            case "conduit_talisman" -> ModStartupConfig.CONDUIT_TALISMAN.ENABLED.get();
            case "creeper_talisman" -> ModStartupConfig.CREEPER_TALISMAN.ENABLED.get();
            case "enderman_talisman" -> ModStartupConfig.ENDERMAN_TALISMAN.ENABLED.get();
            case "phantom_talisman" -> ModStartupConfig.PHANTOM_TALISMAN.ENABLED.get();
            case "silverfish_talisman" -> ModStartupConfig.SILVERFISH_TALISMAN.ENABLED.get();
            default -> true;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
