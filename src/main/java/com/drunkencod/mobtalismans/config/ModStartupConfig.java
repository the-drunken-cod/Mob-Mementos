package com.drunkencod.mobtalismans.config;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class ModStartupConfig {
    // #region Conduit Talisman:

    public static class ConduitTalisman {
        public final ModConfigSpec.DoubleValue RADIUS;

        public final ModConfigSpec.DoubleValue DAMAGE;

        public final ModConfigSpec.IntValue DAMAGE_INTERVAL_TICKS;

        public final ModConfigSpec.IntValue DURABILITY;

        ConduitTalisman(ModConfigSpec.Builder builder) {
            builder.push("conduit_talisman")
                    .translation("config.mobtalismans.conduit_talisman");

            RADIUS = builder
                    .translation("config.mobtalismans.conduit_talisman.radius")
                    .comment("config.mobtalismans.conduit_talisman.radius.comment")
                    .defineInRange("conduit_talisman.radius", 14.0, 1.0, 64.0);

            DAMAGE = builder
                    .translation("config.mobtalismans.conduit_talisman.damage")
                    .comment("The amount of damage to apply to hostile aquatic mobs within the radius.")
                    .translation("config.mobtalismans.conduit_talisman.damage.comment")
                    .defineInRange("conduit_talisman.damage", 4, 1, Double.MAX_VALUE);

            DAMAGE_INTERVAL_TICKS = builder
                    .translation("config.mobtalismans.conduit_talisman.damage_interval_ticks")
                    .comment("The interval in ticks at which damage is applied to hostile aquatic mobs.")
                    .translation("config.mobtalismans.conduit_talisman.damage_interval_ticks.comment")
                    .defineInRange("conduit_talisman.damage_interval_ticks", 40, 1,
                            Integer.MAX_VALUE);

            DURABILITY = builder
                    .translation("config.mobtalismans.conduit_talisman.durability")
                    .comment("The durability of the Conduit Talisman.")
                    .translation("config.mobtalismans.conduit_talisman.durability.comment")
                    .defineInRange("conduit_talisman.durability", 64, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region Silverfish Talisman:

    public static class SilverfishTalisman {
        public final ModConfigSpec.DoubleValue RADIUS;
        public final ModConfigSpec.IntValue CHECK_INTERVAL_TICKS;
        public final ModConfigSpec.IntValue DURABILITY;

        SilverfishTalisman(ModConfigSpec.Builder builder) {
            builder.push("silverfish_talisman")
                    .translation("config.mobtalismans.silverfish_talisman");

            RADIUS = builder
                    .translation("config.mobtalismans.silverfish_talisman.radius")
                    .comment("The radius in blocks around the player in which silverfish will be detected.")
                    .translation("config.mobtalismans.silverfish_talisman.radius.comment")
                    .defineInRange("silverfish_talisman.radius", 5.0, 1.0, 64.0);

            CHECK_INTERVAL_TICKS = builder
                    .translation("config.mobtalismans.silverfish_talisman.check_interval_ticks")
                    .comment("The interval in ticks at which nearby silverfish are checked.")
                    .translation("config.mobtalismans.silverfish_talisman.check_interval_ticks.comment")
                    .defineInRange("silverfish_talisman.check_interval_ticks", 20, 5,
                            Integer.MAX_VALUE);

            DURABILITY = builder
                    .translation("config.mobtalismans.silverfish_talisman.durability")
                    .comment("The durability of the Silverfish Talisman.")
                    .translation("config.mobtalismans.silverfish_talisman.durability.comment")
                    .defineInRange("silverfish_talisman.durability", 256, 1, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    // #region SPEC

    public static final ModConfigSpec SPEC;

    public static final ConduitTalisman CONDUIT_TALISMAN;
    public static final SilverfishTalisman SILVERFISH_TALISMAN;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        CONDUIT_TALISMAN = new ConduitTalisman(builder);
        SILVERFISH_TALISMAN = new SilverfishTalisman(builder);

        SPEC = builder.build();
    }
}
