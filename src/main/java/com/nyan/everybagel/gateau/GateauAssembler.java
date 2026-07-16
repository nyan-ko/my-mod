package com.nyan.everybagel.gateau;

import com.nyan.everybagel.ModComponents;
import com.nyan.everybagel.gateau.mixes.GateauMix;
import com.nyan.everybagel.gateau.mixes.GateauMixes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GateauAssembler {


//    public static List<ResourceKey<Gateau>> calculateOutputGateaux(List<ResourceKey<Gateau>> inputs) {
//
//    }

    public static List<ResourceKey<Gateau>> calculateMixOutputs(List<ResourceKey<Gateau>> inputs) {
        var map = GateauMixes.MIXES.getMixes();
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

    public static List<ResourceKey<Gateau>> sumInputGateaux(IItemHandler itemHandler) {
        var result = new HashSet<ResourceKey<Gateau>>();

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            var gateau = stack.get(ModComponents.GATEAU);
            if (gateau != null) {
                result.addAll(gateau);
            }
        }

        return result.stream().toList();
    }
}
