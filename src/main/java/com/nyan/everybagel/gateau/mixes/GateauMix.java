package com.nyan.everybagel.gateau.mixes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.gateau.Gateau;
import com.nyan.everybagel.gateau.Gateaux;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GateauMix {
    public static class Input {
        private final List<ResourceKey<Gateau>> inputs;
        private final String key;

        public Input(List<ResourceKey<Gateau>> inputs) {
            this.inputs = inputs;
            var temp = new ArrayList<>(this.inputs);
            this.key = temp.stream().sorted().map(ResourceKey::toString).collect(Collectors.joining(", "));
        }

        public static Codec<Input> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).listOf().fieldOf("inputs").forGetter(Input::inputs)
        ).apply(inst, Input::new));

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Input && this.key.equals(((Input) obj).key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        public List<ResourceKey<Gateau>> inputs() { return this.inputs; }

    }
    public record Output(List<ResourceKey<Gateau>> outputs) {
        public static Codec<Output> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceKey.codec(Gateaux.GATEAU_REGISTRY_KEY).listOf().fieldOf("outputs").forGetter(Output::outputs)
        ).apply(inst, Output::new));
    }
}
