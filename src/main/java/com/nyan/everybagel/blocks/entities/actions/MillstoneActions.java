package com.nyan.everybagel.blocks.entities.actions;

import com.nyan.everybagel.blocks.MillstoneBlock;
import com.nyan.everybagel.blocks.entities.MillstoneBlockEntity;
import com.nyan.everybagel.blocks.entities.MixingBowlBlockEntity;
import com.nyan.everybagel.recipes.MillstoneRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class MillstoneActions {
    public static void debug(MillstoneBlockEntity be, Player player) {
        player.displayClientMessage(Component.literal(be.toString()), false);
    }

    public static void mill(MillstoneBlockEntity be, RecipeHolder<MillstoneRecipe> recipe, Player player) {
        be.setRecipe(recipe.value());
        var finished = be.mill(MillstoneBlockEntity.RECIPE_COMPLETE / 20);
        if (finished) {
            ItemHandlerHelper.giveItemToPlayer(player, recipe.value().getItemOutput().copy());
            be.setRecipe(null);
        }
    }

    public static void insert(IItemHandler inventory, ItemStack stack, Player player, InteractionHand hand) {
        var result = ItemHandlerHelper.insertItem(inventory, stack.copy(), false);
        player.setItemInHand(hand, result);
    }

    public static void pop(MillstoneBlockEntity be, Player player) {
        var inventory = be.getInventory();
        ItemStack result = inventory.extractItem(0, Item.ABSOLUTE_MAX_STACK_SIZE, false);
        ItemHandlerHelper.giveItemToPlayer(player, result);
    }
}
