package io.github.nyg404.eyegoggle.common.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.nyg404.eyegoggle.common.point.ContainerExperiencePoint;
import net.neoforged.neoforge.attachment.AttachmentType;


import java.util.HashMap;

public record ContainerPoints(ContainerExperiencePoint container) {
    public static Codec<ContainerPoints> CODEC = RecordCodecBuilder.create(aspectContainerPlayerAttachmentInstance -> aspectContainerPlayerAttachmentInstance.group(
            ContainerExperiencePoint.CODEC.fieldOf("aspect_container_player").forGetter(ContainerPoints::container)
    ).apply(aspectContainerPlayerAttachmentInstance, ContainerPoints::new));

    public static final AttachmentType<ContainerPoints> TYPE = AttachmentType
            .builder(a -> new ContainerPoints(new ContainerExperiencePoint(new HashMap<>())))
            .serialize(CODEC)
            .copyOnDeath()
            .build();
}
