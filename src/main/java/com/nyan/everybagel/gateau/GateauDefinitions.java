package com.nyan.everybagel.gateau;

import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;

public enum GateauDefinitions {
    WOOD("wood"),
    STONE("stone"),
    GRAVEL("gravel"),
    SAND("sand"),
    DIRT("dirt"),

    FLINT("flint"),
    BONE("bone"),
    SCULK("sculk"),

    COPPER("copper"),
    IRON("iron"),
    GOLD("gold"),
    NETHERITE("netherite"),
    
    COAL("coal"),
    AMETHYST("amethyst"),
    REDSTONE("redstone"),
    GLOWSTONE("glowstone"),
    NETHER_QUARTZ("nether_quartz"),
    PRISMARINE("prismarine"),
    LAPIS_LAZULI("lapis_lazuli"),
    DIAMOND("diamond"),
    EMERALD("emerald");

    private final TagKey<Item> tag;
    private final ResourceKey<Gateau> gateau;
    private final List<ResourceKey<GateauPower>> powers;

    GateauDefinitions(String id) {
        this(id, List.of());
    }

    GateauDefinitions(String id, List<ResourceKey<GateauPower>> powers) {
        this.tag = createTagKey(id);
        this.gateau = createGateauResourceKey(id);
        this.powers = powers;
    }

    public TagKey<Item> getTag() { return tag; }
    public ResourceKey<Gateau> getGateau() { return gateau; }
    public List<ResourceKey<GateauPower>> getPowers() { return powers; }

    private static ResourceKey<Gateau> createGateauResourceKey(String id) {
        return ResourceKey.create(Gateaux.GATEAU_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, id));
    }

    private static TagKey<Item> createTagKey(String id) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau/" + id));
    }
}
