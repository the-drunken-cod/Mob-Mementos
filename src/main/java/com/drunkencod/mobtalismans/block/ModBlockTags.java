package com.drunkencod.mobtalismans.block;

import com.drunkencod.mobtalismans.MobTalismans;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> INFESTED = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(MobTalismans.MOD_ID, "infested"));
}
