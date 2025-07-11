package io.github.nyg404.eyegoggle.common.init.key;

import io.github.nyg404.eyegoggle.api.point.ExperiencePoint;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;
@EventBusSubscriber(modid = MODID)
public class EyeKeyRegister {
    public static final ResourceKey<Registry<ExperiencePoint>> EXPERIENCE_POINT_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MODID, "experience_points"));

    public static final Registry<ExperiencePoint> EXPERIENCE_POINT_REGISTRY = new RegistryBuilder<>(EXPERIENCE_POINT_KEY)
            .sync(true)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(MODID, "none"))
            .maxId(256)
            .create();

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        event.register(EXPERIENCE_POINT_REGISTRY);
    }
}
