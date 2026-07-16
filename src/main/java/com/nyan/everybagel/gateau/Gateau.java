package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.FastColor;

import java.util.ArrayList;
import java.util.List;

public class Gateau {
    private final String id;
    private final Visual look;
    private final List<ResourceKey<GateauPower>> powers;

    public Gateau(String id, Visual look, List<ResourceKey<GateauPower>> powers) {
        this.id = id;
        this.look  = look;
        this.powers = powers;
    }

    public String getId() { return id; }
    public Visual getLook() { return look; }
    public List<ResourceKey<GateauPower>> getPowers() { return powers; }

    public record Visual(int color, String variation) {
        public static final Codec<Visual> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("color").forGetter(Visual::color),
                Codec.STRING.fieldOf("variation").forGetter(Visual::variation)
        ).apply(instance, Visual::new));

        public static final Visual PLACEHOLDER = new Visual(FastColor.ARGB32.color(0, 0, 0), "base");

        public static Visual of(int r, int g, int b, String variation) {
            return new Visual(FastColor.ARGB32.color(r, g, b), variation);
        }
    }

    public static class Key {
        private final ResourceKey<Gateau> key;

        public Key(ResourceKey<Gateau> key) {
            this.key = key;
        }
    }

    public static class KeyList {
        private final List<Key> keys;

        private KeyList(List<Gateau.Key> keys) {
            this.keys = new ArrayList<>(keys);
        }

        public static KeyList of(List<Gateau.Key> keys) {
            return new KeyList(keys);
        }

        public void add(Gateau.Key key) {
            keys.add(key);
        }
    }
}
