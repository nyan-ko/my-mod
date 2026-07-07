package com.nyan.everybagel.blocks.entities;

import com.nyan.everybagel.blocks.entities.shared.SimpleItemInventory;
import com.nyan.everybagel.recipes.MillstoneRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class MillstoneBlockEntity extends BlockEntity {
    public static final int ITEM_SLOTS = 1;
    public static final int RECIPE_COMPLETE = 120;

    private final ItemStackHandler inventory;
    private MillstoneRecipe currentRecipe;
    private int recipeProgress = 0;

    public MillstoneBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MILLSTONE_BE.get(), pos, blockState);
        this.inventory = new SimpleItemInventory(this, ITEM_SLOTS);
    }

    public boolean mill(int increment) {
        if (currentRecipe == null) {
            return false;
        }
        recipeProgress = recipeProgress + increment;
        return recipeProgress >= RECIPE_COMPLETE;
    }

    public void setRecipe(MillstoneRecipe recipe) {
        if (currentRecipe != recipe) {
            currentRecipe = recipe;
            recipeProgress = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public String toString() {
        return "MillstoneBlockEntity{" +
                "inventory=" + inventory +
                ", currentRecipe=" + currentRecipe +
                ", recipeProgress=" + recipeProgress +
                '}';
    }

    public final ItemStackHandler getInventory() { return inventory; }
}
