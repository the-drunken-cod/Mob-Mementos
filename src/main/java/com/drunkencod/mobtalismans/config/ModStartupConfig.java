package com.drunkencod.mobtalismans.config;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class ModStartupConfig {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        // Conduit Talisman
        public static final ModConfigSpec.DoubleValue CONDUIT_TALISMAN_RADIUS = BUILDER
                        .comment("The radius in blocks around the player in which hostile aquatic mobs will be damaged.")
                        .defineInRange("conduit_talisman_radius", 10.0, 1.0, 64.0);

        public static final ModConfigSpec.DoubleValue CONDUIT_TALISMAN_DAMAGE = BUILDER
                        .comment("The amount of damage to apply to hostile aquatic mobs within the radius.")
                        .defineInRange("conduit_talisman_damage", 4, 1, Double.MAX_VALUE);

        public static final ModConfigSpec.IntValue CONDUIT_TALISMAN_TICK_INTERVAL = BUILDER
                        .comment("The interval in ticks at which damage is applied to hostile aquatic mobs.")
                        .defineInRange("conduit_talisman_tick_interval", 40, 1, Integer.MAX_VALUE);

        static {

        }

        public static final ModConfigSpec SPEC = BUILDER.build();

        // public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
        // .comment("Whether to log the dirt block on common setup")
        // .define("logDirtBlock", true);

        // public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
        // .comment("A magic number")
        // .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

        // public static final ModConfigSpec.ConfigValue<String>
        // MAGIC_NUMBER_INTRODUCTION = BUILDER
        // .comment("What you want the introduction message to be for the magic number")
        // .define("magicNumberIntroduction", "The magic number is... ");

        // // a list of strings that are treated as resource locations for items
        // public static final ModConfigSpec.ConfigValue<List<? extends String>>
        // ITEM_STRINGS = BUILDER
        // .comment("A list of items to log on common setup.")
        // .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "",
        // ModStartupConfig::validateItemName);

        // private static boolean validateItemName(final Object obj) {
        // return obj instanceof String itemName
        // && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
        // }
}
