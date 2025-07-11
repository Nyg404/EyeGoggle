package io.github.nyg404.eyegoggle.common.init;

import io.github.nyg404.eyegoggle.common.attachments.ContainerPoints;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;

public class EyeGoggleAttachment {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ContainerPoints>> CONTAINER_POINTS =
            ATTACHMENT_TYPES.register("container_points", () -> ContainerPoints.TYPE);

    public static void init(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
