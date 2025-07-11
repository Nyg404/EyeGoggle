package io.github.nyg404.eyegoggle.api.point;

import com.mojang.serialization.Codec;
import io.github.nyg404.eyegoggle.common.init.key.EyeKeyRegister;
import net.minecraft.resources.ResourceLocation;

public class ExperiencePoint {
    public static final Codec<ExperiencePoint> CODEC = EyeKeyRegister.EXPERIENCE_POINT_REGISTRY.byNameCodec();

    public ResourceLocation name;
    public ResourceLocation icon;

    public ExperiencePoint(ResourceLocation name, ResourceLocation icon) {
        this.name = name;
        this.icon = icon;
    }

    public ResourceLocation getName() {
        return name;
    }

    public ResourceLocation getIcon() {
        return icon;
    }
}
