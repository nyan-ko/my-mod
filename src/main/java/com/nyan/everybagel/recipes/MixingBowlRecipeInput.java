package com.nyan.everybagel.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.List;

public record MixingBowlRecipeInput(List<ItemStack> items, FluidStack fluid) implements RecipeInput {
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public int size() {
        return items.size();
    }
}
