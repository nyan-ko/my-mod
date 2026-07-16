package com.nyan.everybagel.gateau.mixes;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

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
}
