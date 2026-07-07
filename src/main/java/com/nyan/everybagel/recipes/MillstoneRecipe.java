package com.nyan.everybagel.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class MillstoneRecipe implements Recipe<MillstoneRecipeInput> {
    private final Ingredient itemInput;
    private final ItemStack itemOutput;

    public MillstoneRecipe(Ingredient itemInput, ItemStack itemOutput) {
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
    }

    @Override
    public boolean matches(MillstoneRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return itemInput.test(input.input());
    }

    @Override
    public ItemStack assemble(MillstoneRecipeInput input, HolderLookup.Provider registries) {
        return itemOutput.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return itemOutput;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MILLSTONE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MILLSTONE_TYPE.get();
    }

    public Ingredient getItemInput() { return itemInput; }
    public ItemStack getItemOutput() { return itemOutput; }

    public static class Serializer implements RecipeSerializer<MillstoneRecipe> {
        public static final MapCodec<MillstoneRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.CODEC.fieldOf("item_input").forGetter(MillstoneRecipe::getItemInput),
                        ItemStack.CODEC.fieldOf("item_output").forGetter(MillstoneRecipe::getItemOutput)
                ).apply(instance, MillstoneRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MillstoneRecipe> STREAM_CODEC = StreamCodec.of(
            MillstoneRecipe.Serializer::toNetwork, MillstoneRecipe.Serializer::fromNetwork
        );

        private static MillstoneRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new MillstoneRecipe(input, output);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, MillstoneRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.getItemInput());
            ItemStack.STREAM_CODEC.encode(buf, recipe.getItemOutput());
        }

        @Override
        public MapCodec<MillstoneRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MillstoneRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
