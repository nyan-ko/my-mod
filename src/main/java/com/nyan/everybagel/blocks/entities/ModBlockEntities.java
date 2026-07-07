package com.nyan.everybagel.blocks.entities;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.blocks.MillstoneBlock;
import com.nyan.everybagel.blocks.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, EverythingBagel.MOD_ID);

    public static final Supplier<BlockEntityType<MixingBowlBlockEntity>> MIXING_BOWL_BE = BLOCK_ENTITIES.register("mixing_bowl_be", () -> BlockEntityType.Builder.of(MixingBowlBlockEntity::new, ModBlocks.MIXING_BOWL_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<MillstoneBlockEntity>> MILLSTONE_BE = BLOCK_ENTITIES.register("millstone_be", () -> BlockEntityType.Builder.of(MillstoneBlockEntity::new, ModBlocks.MILLSTONE_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
