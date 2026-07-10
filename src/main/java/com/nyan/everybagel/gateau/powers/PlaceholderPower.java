package com.nyan.everybagel.gateau.powers;

import com.mojang.serialization.MapCodec;
import com.nyan.everybagel.EverythingBagel;
import net.minecraft.resources.ResourceLocation;

public record PlaceholderPower() implements GateauPower {
    private static final MapCodec<PlaceholderPower> CODEC = MapCodec.unit(PlaceholderPower::new);

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "placeholder");

    @Override
    public MapCodec<?> codec() {
        return CODEC;
    }

    @Override
    public ResourceLocation base() {
        return ID;
    }
}
