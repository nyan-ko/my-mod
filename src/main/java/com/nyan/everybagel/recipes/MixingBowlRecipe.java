package com.nyan.everybagel.recipes;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;

public record MixingBowlRecipe(NonNullList<Ingredient> ingredients, ItemStack output) implements Recipe<MixingBowlRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(MixingBowlRecipeInput input, Level level) {
        if (level.isClientSide) {
            return false;
        }

        var items = new ArrayList<ItemStack>();
        for (var item : input.items()) {
            if (!item.isEmpty()) {
                items.add(item);
            }
        }
        var matches = RecipeMatcher.findMatches(items, this.ingredients());
        return matches != null; // "condition is always true" ??
    }

    @Override
    public ItemStack assemble(MixingBowlRecipeInput input, HolderLookup.Provider registries) {
        return this.output().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MIXING_BOWL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MIXING_BOWL_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MixingBowlRecipe> {
        public static final MapCodec<MixingBowlRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY
                        .listOf()
                        .fieldOf("ingredients")
                        .flatXmap(
                                ingredient -> {
                                    Ingredient[] arr = ingredient.toArray(Ingredient[]::new);
                                    if (arr.length == 0) {
                                        return DataResult.error(() -> "No ingredients for Mixing Bowl recipe");
                                    }
                                    else {
                                        return DataResult.success(NonNullList.of(Ingredient.EMPTY, arr));
                                    }
                                },
                                DataResult::success
                        )
                        .forGetter(recipe -> recipe.ingredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(MixingBowlRecipe::output)
        ).apply(inst, MixingBowlRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MixingBowlRecipe> STREAM_CODEC = StreamCodec.of(
                MixingBowlRecipe.Serializer::toNetwork, MixingBowlRecipe.Serializer::fromNetwork
        );

        private static MixingBowlRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            int length = buf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(length, Ingredient.EMPTY);
            ingredients.replaceAll(i -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            var result = ItemStack.STREAM_CODEC.decode(buf);
            return new MixingBowlRecipe(ingredients, result);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, MixingBowlRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());
            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public MapCodec<MixingBowlRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MixingBowlRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
