package com.nyan.everybagel.datagen;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.Gateaux;
import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class GateauPowerProvider extends DatapackBuiltinEntriesProvider {
    public GateauPowerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, getRegistrySetBuilder(), Set.of(EverythingBagel.MOD_ID));
    }

    private static RegistrySetBuilder getRegistrySetBuilder() {
        return new RegistrySetBuilder()
                .add(Gateaux.GATEAU_POWER_REGISTRY_KEY, bootstrap -> {
                    bootstrap.register(Gateaux.POWER11, new GateauPower.Power1(1));
                    bootstrap.register(Gateaux.POWER12, new GateauPower.Power1(4));
                    bootstrap.register(Gateaux.POWER13, new GateauPower.Power1(8));
                    bootstrap.register(Gateaux.POWER2, new GateauPower.Power2(0, Optional.empty()));
                });
    }

    @Override
    public String getName() {
        return "Gateau Powers: " + EverythingBagel.MOD_ID;
    }
}
