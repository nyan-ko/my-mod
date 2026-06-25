package com.nyan.everybagel.blocks;

import com.nyan.everybagel.blocks.entities.MixingBowlBlockEntity;
import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.blocks.entities.dispatchers.MixingBowlUseItemOn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MixingBowlBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 1, 14);
    public static final MapCodec<MixingBowlBlock> CODEC = simpleCodec(MixingBowlBlock::new);

    protected MixingBowlBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MixingBowlBlockEntity(pos, state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {

        // TODO
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof MixingBowlBlockEntity mixingBowlBlockEntity) {
            return MixingBowlUseItemOn.use(mixingBowlBlockEntity, stack, state, level, pos, player, hand, hitResult);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
