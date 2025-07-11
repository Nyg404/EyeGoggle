package io.github.nyg404.eyegoggle.common.datagen;

import io.github.nyg404.eyegoggle.api.point.ListExperiencePoint;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import static com.mojang.text2speech.Narrator.LOGGER;
import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;

public class EyeGoggleDataMaps {
    public static final DataMapType<EntityType<?>, ListExperiencePoint> EXPERIENCE_POINT_DATA_MAP_TYPE =
            DataMapType.builder(
                    ResourceLocation.fromNamespaceAndPath(MODID, "experience_point"),
                    Registries.ENTITY_TYPE,
                    ListExperiencePoint.CODEC
            ).build();

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(EXPERIENCE_POINT_DATA_MAP_TYPE);
        LOGGER.info("Registering EXPERIENCE_POINT_DATA_MAP_TYPE");

    }
}
