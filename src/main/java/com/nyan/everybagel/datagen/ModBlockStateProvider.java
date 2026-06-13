package com.nyan.everybagel.datagen;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.blocks.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, EverythingBagel.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DOUGH_BLOCK);
    }

    private void blockWithItem(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
