package com.nyan.everybagel.gateau;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nyan.everybagel.EverythingBagel;
import com.nyan.everybagel.gateau.powers.GateauPower;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Map;

@EventBusSubscriber(modid = EverythingBagel.MOD_ID)
public class Gateaux {
    public static final ResourceKey<Registry<Gateau>> GATEAU_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau"));

    public static final ResourceKey<Registry<GateauPower>> GATEAU_POWER_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, "gateau/powers"));

    public static final ResourceKey<Gateau> DEFAULT = createGateauResourceKey("default");
    public static final ResourceKey<Gateau> DEFAULT2 = createGateauResourceKey("default2");

    public static final ResourceKey<GateauPower> POWER11 = createPowerResourceKey("power11");
    public static final ResourceKey<GateauPower> POWER12 = createPowerResourceKey("power12");
    public static final ResourceKey<GateauPower> POWER13 = createPowerResourceKey("power13");
    public static final ResourceKey<GateauPower> POWER2 = createPowerResourceKey("power2");

    private static ResourceKey<Gateau> createGateauResourceKey(String key) {
        return ResourceKey.create(GATEAU_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, key));
    }

    private static ResourceKey<GateauPower> createPowerResourceKey(String key) {
        return ResourceKey.create(GATEAU_POWER_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(EverythingBagel.MOD_ID, key));
    }

    public static final Codec<Gateau> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Gateau::getId),
                    ResourceKey.codec(GATEAU_POWER_REGISTRY_KEY).listOf().fieldOf("powers").forGetter(Gateau::getPowers)
            ).apply(instance, Gateau::new)
    );

    public static final Map<ResourceLocation, MapCodec<? extends GateauPower>> POWER_CODECS_BY_RESOURCE = Map.of(
            GateauPower.Power1.ID, GateauPower.Power1.CODEC,
            GateauPower.Power2.ID, GateauPower.Power2.CODEC
    );

    public static final Codec<GateauPower> POWER_CODEC = ResourceLocation.CODEC.dispatch(
            "type",
            GateauPower::base,
            POWER_CODECS_BY_RESOURCE::get
    );


    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                GATEAU_REGISTRY_KEY,
                CODEC,
                CODEC
        );
        event.dataPackRegistry(
                GATEAU_POWER_REGISTRY_KEY,
                POWER_CODEC,
                POWER_CODEC
        );
    }
}
