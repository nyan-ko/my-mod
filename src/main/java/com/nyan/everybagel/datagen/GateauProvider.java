package com.nyan.everybagel.datagen;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.Gateaux;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GateauProvider extends DatapackBuiltinEntriesProvider {
    public GateauProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, getRegistrySetBuilder(), Set.of(EverythingBagel.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(Gateaux.GATEAU_REGISTRY_KEY, bootstrap -> {
                    bootstrap.register(Gateaux.DEFAULT, new Gateau("default", List.of(Gateaux.POWER11)));
                    bootstrap.register(Gateaux.DEFAULT2, new Gateau("default2", List.of(Gateaux.POWER2)));
                });
    }

    @Override
    public String getName() {
        return "Gateaux: " + EverythingBagel.MOD_ID;
    }
}
