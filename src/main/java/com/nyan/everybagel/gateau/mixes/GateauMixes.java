package com.nyan.everybagel.gateau.mixes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.nyan.everybagel.gateau.Gateau;
import net.minecraft.resources.ResourceKey;

import java.util.*;
import java.util.stream.Collectors;

public class GateauMixes {
    private static Map<GateauMix.Input, GateauMix.Output> toMap(List<Pair<GateauMix.Input, GateauMix.Output>> list) {
        return list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    private static List<Pair<GateauMix.Input, GateauMix.Output>> toList(Map<GateauMix.Input, GateauMix.Output> map) {
        List<Pair<GateauMix.Input, GateauMix.Output>> list = new ArrayList<>();
        map.forEach((a, b) -> list.add(Pair.of(a, b)));
        return list;
    }

    public static final Codec<Map<GateauMix.Input, GateauMix.Output>> CODEC = Codec.pair(
            GateauMix.Input.CODEC,
            GateauMix.Output.CODEC
    ).listOf().xmap(GateauMixes::toMap, GateauMixes::toList);

    public static final GateauMixLoader MIXES = GateauMixLoader.INSTANCE;

    public static List<ResourceKey<Gateau>> calculateMixOutputs(List<ResourceKey<Gateau>> inputs) {
        var map = MIXES.getMixes();
        var result = new HashSet<ResourceKey<Gateau>>();
        int n = inputs.size();

        var cache = Map.of(
                1, 0,
                2, 1,
                4, 2,
                8, 3,
                16, 4,
                32, 5,
                64, 6,
                128, 7,
                256, 8
        );
        var cur = new HashSet<ResourceKey<Gateau>>();
        int prev = 0;
        for (int i = 1; i < (1 << n); i++) {
            int grey = i ^ (i >> 1);
            int diff = grey ^ prev;

            if ((grey & diff) == 1) {
                cur.add(inputs.get(cache.get(diff)));
            }
            else {
                cur.remove(inputs.get(cache.get(diff)));
            }

            prev = grey;

            var check = new GateauMix.Input(cur.stream().toList());
            var value = map.getOrDefault(check, null);
            if (value != null) {
                result.addAll(value.outputs());
            }
        }

        return result.stream().toList();
    }
}
