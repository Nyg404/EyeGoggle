package io.github.nyg404.eyegoggle.common.packet;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;


public interface PacketTest extends CustomPacketPayload {
    static <T extends CustomPacketPayload> Type<T> createType(String name) {
        return new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, name));
    }
}
