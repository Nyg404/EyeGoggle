package io.github.nyg404.eyegoggle.common.packet.packetigi;

import io.github.nyg404.eyegoggle.common.point.ContainerExperiencePoint;
import io.github.nyg404.eyegoggle.common.attachments.ContainerPoints;
import io.github.nyg404.eyegoggle.common.init.EyeGoggleAttachment;
import io.github.nyg404.eyegoggle.common.packet.ClientPacket;
import io.github.nyg404.eyegoggle.common.packet.PacketTest;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ContainerPointToClient(ContainerExperiencePoint containerPoints) implements ClientPacket {
    public static final Type<ContainerPointToClient> TYPE =
            PacketTest.createType("container_point_to_client");


    public static final StreamCodec<RegistryFriendlyByteBuf, ContainerPointToClient> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public ContainerPointToClient decode(RegistryFriendlyByteBuf buf) {
                    return new ContainerPointToClient(
                            new ContainerExperiencePoint(ContainerExperiencePoint.STREAM_CODEC.decode(buf))
                    );
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buf, ContainerPointToClient pkt) {
                    ContainerExperiencePoint.STREAM_CODEC.encode(buf, pkt.containerPoints.getPoints());
                }
            };


    @Override
    public void handleOnClient(IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = Minecraft.getInstance().player;
            if (player != null) {
                player.setData(EyeGoggleAttachment.CONTAINER_POINTS.get(), new ContainerPoints(containerPoints));
            }
        });
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
