package com.nyan.everybagel.recipes;

import com.nyan.everybagel.blocks.MillstoneBlock;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MillstoneRecipeBuilder implements RecipeBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private Ingredient itemInput;
    private ItemStack itemOutput;

    public MillstoneRecipeBuilder(Ingredient itemInput, ItemStack itemOutput) {
        this.itemInput = itemInput;
        this.itemOutput = itemOutput;
    }

    public static MillstoneRecipeBuilder of(Ingredient itemInput, ItemStack itemOutput) {
        return new MillstoneRecipeBuilder(itemInput, itemOutput);
    }


    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return itemOutput.getItem();
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder builder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        criteria.forEach(builder::addCriterion);
        MillstoneRecipe recipe = new MillstoneRecipe(itemInput, itemOutput);
        output.accept(id, recipe, builder.build(id.withPrefix("recipes/")));
    }
}
