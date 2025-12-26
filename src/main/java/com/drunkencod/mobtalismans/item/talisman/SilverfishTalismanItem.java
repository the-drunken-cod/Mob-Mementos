package com.drunkencod.mobtalismans.item.talisman;

import com.drunkencod.mobtalismans.block.ModBlockTags;
import com.drunkencod.mobtalismans.config.ModStartupConfig;
import com.drunkencod.mobtalismans.MobTalismans;
import io.wispforest.accessories.api.slot.SlotReference;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

public class SilverfishTalismanItem extends AbstractTalismanItem {
    public static final String REGISTRY_NAME = "silverfish_talisman";

    private HashMap<Block, Block> uninfestedCache = new HashMap<>();

    public SilverfishTalismanItem() {
        super(REGISTRY_NAME, getDefaultProps(ModStartupConfig.SILVERFISH_TALISMAN.DURABILITY.get()));

        InfestedBlock.BLOCK_BY_HOST_BLOCK.forEach((hostBlock, infestedBlock) -> {
            uninfestedCache.put(infestedBlock, hostBlock);
        });
    }

    public boolean isEnabled() {
        return ModStartupConfig.SILVERFISH_TALISMAN.ENABLED.get();
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        if (!isEnabled())
            return;

        Entity playerEntity = reference.entity();
        Level level = playerEntity.level();

        double radius = ModStartupConfig.SILVERFISH_TALISMAN.RADIUS.get();
        int tickInterval = ModStartupConfig.SILVERFISH_TALISMAN.CHECK_INTERVAL_TICKS.get();

        // if talisman is broken, do nothing
        if (stack.getMaxDamage() > 0 && stack.getDamageValue() >= stack.getMaxDamage())
            return;

        // check for infested blocks nearby at specified tick interval
        if (playerEntity.tickCount > 0 && playerEntity.tickCount % tickInterval == 0) {
            List<BlockPos> nearbyInfestedBlocks = getNearbyInfestedBlocks(level, playerEntity, radius);
            if (!nearbyInfestedBlocks.isEmpty()) {
                int i = 0;
                for (BlockPos blockPos : nearbyInfestedBlocks) {
                    if (!level.isClientSide()) {
                        MobTalismans.LOGGER.debug("[Silverfish Talisman]: Uninfesting block at {}", blockPos);

                        // uninfest block
                        level.setBlockAndUpdate(blockPos, getUninfestedState(level, blockPos));

                        // trigger advancement
                        triggerTalismanAdvancement(reference, stack);

                        // damage talisman
                        if (playerEntity instanceof Player player)
                            damageTalisman(stack, (ServerLevel) level, (Player) player);
                    }

                    // play silverfish kill sound
                    if (i < 3)
                        level.playSound(null, blockPos, SoundEvents.SILVERFISH_DEATH, SoundSource.BLOCKS,
                                0.4f, 0.8f + level.getRandom().nextFloat() * 0.4f);
                    i++;
                }
            }
        }
    }

    private List<BlockPos> getNearbyInfestedBlocks(Level level, Entity entity, double radius) {
        BlockPos center = entity.blockPosition();
        int r = (int) Math.ceil(radius);

        return BlockPos.betweenClosedStream(center.offset(-r, -r, -r), center.offset(r, r, r))
                .filter(pos -> pos.closerToCenterThan(entity.position(), radius)) // spherical check
                .filter(pos -> level.getBlockState(pos).is(ModBlockTags.INFESTED))
                .map(BlockPos::immutable) // important! stream positions are mutable
                .toList();
    }

    private BlockState getUninfestedState(Level level, BlockPos pos) {
        BlockState infestedState = level.getBlockState(pos);
        Block infestedBlock = infestedState.getBlock();
        Block uninfestedBlock = uninfestedCache.get(infestedBlock);
        if (uninfestedBlock != null)
            return uninfestedBlock.defaultBlockState();
        return infestedState;
    }
}
