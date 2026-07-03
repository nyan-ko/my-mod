package com.nyan.everybagel.gateau;

import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.resources.ResourceKey;

import java.util.List;

public class Gateau {
    public String id;
    public List<ResourceKey<GateauPower>> powers;

    public Gateau(String id, List<ResourceKey<GateauPower>> powers) {
        this.id = id;
        this.powers = powers;
    }

    public String getId() { return id; }
    public List<ResourceKey<GateauPower>> getPowers() { return powers; }
}
