package com.drunkencod.mob_mementos.config;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class ModStartupConfig {
    // #region Conduit Necklace:

    public static class ConduitNecklaceConfig {
        public final ModConfigSpec.BooleanValue ENABLED;
        public final ModConfigSpec.DoubleValue RADIUS;
        public final ModConfigSpec.DoubleValue DAMAGE;
        public final ModConfigSpec.IntValue DAMAGE_INTERVAL_TICKS;
        public final ModConfigSpec.IntValue DURABILITY;

        ConduitNecklaceConfig(ModConfigSpec.Builder builder) {
            builder.push("conduit_necklace")
                    .translation("config.mob_mementos.conduit_necklace");

            ENABLED = builder
                    .translation("config.mob_mementos.conduit_necklace.enabled")
                    .comment("Whether the Conduit Necklace is enabled.")
                    .define("conduit_necklace.enabled", true);

            RADIUS = builder
                    .translation("config.mob_mementos.conduit_necklace.radius")
                    .defineInRange("conduit_necklace.radius", 14.0, 1.0, 64.0);

            DAMAGE = builder
                    .translation("config.mob_mementos.conduit_necklace.damage")
                    .comment("The amount of damage to apply to hostile aquatic mobs within the radius.")
                    .defineInRange("conduit_necklace.damage", 2, 1, Double.MAX_VALUE);

            DAMAGE_INTERVAL_TICKS = builder
                    .translation("config.mob_mementos.conduit_necklace.damage_interval_ticks")
                    .comment("The interval in ticks at which damage is applied to hostile aquatic mobs.")
                    .defineInRange("conduit_necklace.damage_interval_ticks", 30, 1,
                            Integer.MAX_VALUE);

            DURABILITY = builder
                    .translation("config.mob_mementos.conduit_necklace.durability")
                    .comment("The durability of the Conduit Necklace.")
                    .defineInRange("conduit_necklace.durability", 2031, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region Creeper Belt:

    public static class CreeperBeltConfig {
        public final ModConfigSpec.BooleanValue ENABLED;
        public final ModConfigSpec.DoubleValue RADIUS;
        public final ModConfigSpec.IntValue CHECK_INTERVAL_TICKS;
        public final ModConfigSpec.IntValue DURABILITY;

        CreeperBeltConfig(ModConfigSpec.Builder builder) {
            builder.push("creeper_belt")
                    .translation("config.mob_mementos.creeper_belt");

            ENABLED = builder
                    .comment("Whether the Creeper Belt is enabled.")
                    .translation("config.mob_mementos.creeper_belt.enabled")
                    .define("creeper_belt.enabled", true);

            RADIUS = builder
                    .comment("The radius in blocks around the player in which creepers will be detected.")
                    .translation("config.mob_mementos.creeper_belt.radius")
                    .defineInRange("creeper_belt.radius", 2.5, 1.0, 64.0);

            CHECK_INTERVAL_TICKS = builder
                    .comment("The interval in ticks at which nearby creepers are checked.")
                    .translation("config.mob_mementos.creeper_belt.check_interval_ticks")
                    .defineInRange("creeper_belt.check_interval_ticks", 10, 2,
                            Integer.MAX_VALUE);

            DURABILITY = builder
                    .comment("The durability of the Creeper Belt.")
                    .translation("config.mob_mementos.creeper_belt.durability")
                    .defineInRange("creeper_belt.durability", 1561, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region Enderman Anklet:

    public static class EndermanAnkletConfig {
        public final ModConfigSpec.BooleanValue ENABLED;
        public final ModConfigSpec.DoubleValue RADIUS;
        public final ModConfigSpec.IntValue CHECK_INTERVAL_TICKS;
        public final ModConfigSpec.IntValue DURABILITY;

        EndermanAnkletConfig(ModConfigSpec.Builder builder) {
            builder.push("enderman_anklet")
                    .translation("config.mob_mementos.enderman_anklet");

            ENABLED = builder
                    .comment("Whether the Enderman Anklet is enabled.")
                    .translation("config.mob_mementos.enderman_anklet.enabled")
                    .define("enderman_anklet.enabled", true);

            RADIUS = builder
                    .comment("The radius in blocks around the player in which endermen will be detected.")
                    .translation("config.mob_mementos.enderman_anklet.radius")
                    .defineInRange("enderman_anklet.radius", 32.0, 1.0, 128.0);

            CHECK_INTERVAL_TICKS = builder
                    .comment("The interval in ticks at which nearby endermen are checked.")
                    .translation("config.mob_mementos.enderman_anklet.check_interval_ticks")
                    .defineInRange("enderman_anklet.check_interval_ticks", 60, 10,
                            Integer.MAX_VALUE);

            DURABILITY = builder
                    .comment("The durability of the Enderman Anklet.")
                    .translation("config.mob_mementos.enderman_anklet.durability")
                    .defineInRange("enderman_anklet.durability", 1561, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region Phantom Cloak:

    public static class PhantomCloakConfig {
        public final ModConfigSpec.BooleanValue ENABLED;
        public final ModConfigSpec.IntValue DURABILITY;

        PhantomCloakConfig(ModConfigSpec.Builder builder) {
            builder.push("phantom_cloak")
                    .translation("config.mob_mementos.phantom_cloak");

            ENABLED = builder
                    .comment("Whether the Phantom Cloak is enabled.")
                    .translation("config.mob_mementos.phantom_cloak.enabled")
                    .define("phantom_cloak.enabled", true);

            DURABILITY = builder
                    .comment("The durability of the Phantom Cloak.")
                    .translation("config.mob_mementos.phantom_cloak.durability")
                    .defineInRange("phantom_cloak.durability", 2031, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region Silverfish Bangle:

    public static class SilverfishBangleConfig {
        public final ModConfigSpec.BooleanValue ENABLED;
        public final ModConfigSpec.DoubleValue RADIUS;
        public final ModConfigSpec.IntValue CHECK_INTERVAL_TICKS;
        public final ModConfigSpec.IntValue DURABILITY;

        SilverfishBangleConfig(ModConfigSpec.Builder builder) {
            builder.push("silverfish_bangle")
                    .translation("config.mob_mementos.silverfish_bangle");

            ENABLED = builder
                    .comment("Whether the Silverfish Bangle is enabled.")
                    .translation("config.mob_mementos.silverfish_bangle.enabled")
                    .define("silverfish_bangle.enabled", true);

            RADIUS = builder
                    .comment("The radius in blocks around the player in which silverfish will be detected.")
                    .translation("config.mob_mementos.silverfish_bangle.radius")
                    .defineInRange("silverfish_bangle.radius", 5.0, 1.0, 64.0);

            CHECK_INTERVAL_TICKS = builder
                    .comment("The interval in ticks at which nearby silverfish are checked.")
                    .translation("config.mob_mementos.silverfish_bangle.check_interval_ticks")
                    .defineInRange("silverfish_bangle.check_interval_ticks", 20, 5,
                            Integer.MAX_VALUE);

            DURABILITY = builder
                    .comment("The durability of the Silverfish Bangle.")
                    .translation("config.mob_mementos.silverfish_bangle.durability")
                    .defineInRange("silverfish_bangle.durability", 2031, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region SPEC

    public static final ModConfigSpec SPEC;

    public static final ConduitNecklaceConfig CONDUIT_NECKLACE;
    public static final CreeperBeltConfig CREEPER_BELT;
    public static final EndermanAnkletConfig ENDERMAN_ANKLET;
    public static final PhantomCloakConfig PHANTOM_CLOAK;
    public static final SilverfishBangleConfig SILVERFISH_BANGLE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        CONDUIT_NECKLACE = new ConduitNecklaceConfig(builder);
        CREEPER_BELT = new CreeperBeltConfig(builder);
        ENDERMAN_ANKLET = new EndermanAnkletConfig(builder);
        PHANTOM_CLOAK = new PhantomCloakConfig(builder);
        SILVERFISH_BANGLE = new SilverfishBangleConfig(builder);

        SPEC = builder.build();
    }
}
