package com.nyan.everybagel.blocks.entities.dispatchers;

import com.nyan.everybagel.ModItemTags;
import com.nyan.everybagel.blocks.entities.MillstoneBlockEntity;
import com.nyan.everybagel.blocks.entities.MixingBowlBlockEntity;
import com.nyan.everybagel.blocks.entities.actions.MillstoneActions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MillstoneUseItemOn {

    public static ItemInteractionResult use(MillstoneBlockEntity be, ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) {
            if (player.isShiftKeyDown()) {

            }
            else {

            }
        }
        else {
            if (stack.is(ModItemTags.MILLSTONE_INPUT)) {

            }
            else if (stack.is(ModItemTags.MOD_DEBUG)) {
                MillstoneActions.debug(be, player);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
