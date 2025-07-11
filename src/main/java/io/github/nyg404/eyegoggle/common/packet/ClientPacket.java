package io.github.nyg404.eyegoggle.common.packet;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ClientPacket extends PacketTest {

    default void handleOnClient(IPayloadContext context){
        context.enqueueWork(() ->{
            handleOnClient(context.player());
        });
    }
    default void handleOnClient(Player player) {
        throw new AbstractMethodError("Unimplemented method on " + getClass());
    }
}
