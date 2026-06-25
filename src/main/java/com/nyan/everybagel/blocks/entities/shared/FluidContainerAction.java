package com.nyan.everybagel.blocks.entities.shared;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

@FunctionalInterface
public interface FluidContainerAction {
    FluidActionResult apply(ItemStack container, IFluidHandler fluidHandler, IItemHandler inventory, int maxAmount, Player player, boolean doAction);
}
