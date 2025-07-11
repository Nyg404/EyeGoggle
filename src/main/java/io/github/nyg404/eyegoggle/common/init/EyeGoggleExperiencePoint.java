package io.github.nyg404.eyegoggle.common.init;

import io.github.nyg404.eyegoggle.api.point.ExperiencePoint;
import io.github.nyg404.eyegoggle.common.init.key.EyeKeyRegister;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.github.nyg404.eyegoggle.EyeGoggle.LOGGER;
import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;

public class EyeGoggleExperiencePoint {
    public static final DeferredRegister<ExperiencePoint> EXPERIENCE_POINT_REGISTER = DeferredRegister.create(EyeKeyRegister.EXPERIENCE_POINT_KEY, MODID);

    public static final Supplier<ExperiencePoint> COMPAT = EXPERIENCE_POINT_REGISTER.register("combat", () -> new ExperiencePoint(ResourceLocation.fromNamespaceAndPath(MODID, "combat"), ResourceLocation.fromNamespaceAndPath(MODID, "213")));
    public static final Supplier<ExperiencePoint> NONE = EXPERIENCE_POINT_REGISTER.register("none", () ->
            new ExperiencePoint(ResourceLocation.fromNamespaceAndPath(MODID, "none"), ResourceLocation.fromNamespaceAndPath(MODID, "none_icon")));

    public static ExperiencePoint getPoint(ResourceLocation name) {
        ExperiencePoint point = EyeKeyRegister.EXPERIENCE_POINT_REGISTRY.get(name);
        if (point == null) {
            LOGGER.warn("ExperiencePoint '{}' not found, returning NONE", name);
            point = EyeKeyRegister.EXPERIENCE_POINT_REGISTRY.get(ResourceLocation.fromNamespaceAndPath(MODID, "none"));
        }
        return point;
    }
    public static void init(IEventBus eventBus){
        EXPERIENCE_POINT_REGISTER.register(eventBus);
    }
}
