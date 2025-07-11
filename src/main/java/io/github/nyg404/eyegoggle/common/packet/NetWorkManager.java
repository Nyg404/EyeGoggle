package io.github.nyg404.eyegoggle.common.packet;


import io.github.nyg404.eyegoggle.common.packet.packetigi.ContainerPointToClient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;

@EventBusSubscriber(modid = MODID)
public class NetWorkManager {

    @SubscribeEvent
    public static void init(RegisterPayloadHandlersEvent event){
        var registry = event.registrar(MODID);
        clientbound(registry, ContainerPointToClient.TYPE, ContainerPointToClient.STREAM_CODEC);
    }

    private static <T extends ClientPacket> void clientbound(PayloadRegistrar registrar, CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToClient(type, codec, ClientPacket::handleOnClient);
    }

    private static <T extends ServerPacket> void serverbound(PayloadRegistrar registrar, CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToServer(type, codec, ServerPacket::handleOnServer);
    }

    private  static <T extends ServerPacket & ClientPacket> void bidirectional(PayloadRegistrar registrar, CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playBidirectional(type, codec, (payload, context) -> {
            if (context.flow().isClientbound()) {
                payload.handleOnClient(context);
            } else {
                payload.handleOnServer(context);
            }
        });
    }
}
