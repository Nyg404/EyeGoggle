package io.github.nyg404.eyegoggle.common.event;

import io.github.nyg404.eyegoggle.api.point.ExperiencePoint;
import io.github.nyg404.eyegoggle.api.point.ListExperiencePoint;
import io.github.nyg404.eyegoggle.common.attachments.ContainerPoints;
import io.github.nyg404.eyegoggle.common.datagen.EyeGoggleDataMaps;
import io.github.nyg404.eyegoggle.common.init.EyeGoggleAttachment;
import io.github.nyg404.eyegoggle.common.init.EyeGoggleExperiencePoint;
import io.github.nyg404.eyegoggle.common.init.key.EyeKeyRegister;
import io.github.nyg404.eyegoggle.common.packet.packetigi.ContainerPointToClient;
import io.github.nyg404.eyegoggle.common.point.ContainerPointsWrapper;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;
import static net.neoforged.neoforgespi.ILaunchContext.LOGGER;

@EventBusSubscriber(modid = MODID)
public class PlayerTEst {
    @SubscribeEvent
    public static void onPostDamage(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity target)) return;
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();
        if (attacker instanceof Player player) {
            EntityType<?> type = target.getType();
            Holder<EntityType<?>> holder = type.builtInRegistryHolder();
            ListExperiencePoint listExperiencePoint = holder.getData(EyeGoggleDataMaps.EXPERIENCE_POINT_DATA_MAP_TYPE);
            ContainerPoints containerExperiencePoint = player.getData(EyeGoggleAttachment.CONTAINER_POINTS);
            ContainerPointsWrapper containerPointsWrapper = new ContainerPointsWrapper(player, containerExperiencePoint.container());
            String name = type.builtInRegistryHolder().getRegisteredName();
            player.sendSystemMessage(Component.literal("Ты ударил моба: " + name));
            player.sendSystemMessage(Component.literal("DEBUG: Holder = " + holder.getRegisteredName()));
            player.sendSystemMessage(Component.literal("DEBUG: Data = " + (listExperiencePoint != null ? "OK" : "NULL")));
            player.sendSystemMessage(Component.literal("DEBUG: Registry Contents = " + EyeKeyRegister.EXPERIENCE_POINT_REGISTRY.keySet()));
            if (listExperiencePoint != null) {
                LOGGER.info("ListExperiencePoint contents: {}", listExperiencePoint.getListPoint());
                for (Map.Entry<ExperiencePoint, Integer> entry : listExperiencePoint.getListPoint().entrySet()) {
                    ExperiencePoint point = entry.getKey();
                    int amount = entry.getValue();
                    player.sendSystemMessage(Component.literal("Тип: " + point.getName().toString() + " | Кол-во: " + amount));
                    if(player instanceof ServerPlayer player1){
                        containerPointsWrapper.addExperiencePoint(EyeGoggleExperiencePoint.COMPAT.get(), 22);
                        PacketDistributor.sendToPlayer(player1, new ContainerPointToClient(containerExperiencePoint.container()));
                    }

                }
            } else {
                player.sendSystemMessage(Component.literal("У него нет очков."));
            }
        }
    }

    @SubscribeEvent
    public static void addOput(ExperiencePointEvent event){
        Player player = event.getPlayer();
        player.sendSystemMessage(Component.literal("Ого ты получил опыт)) " + event.getAmount()));
    }

    @SubscribeEvent
    public static void addLevel(ExperiencePointEvent.NewLevels event){
        Player player = event.getPlayer();
        player.sendSystemMessage(Component.literal("Ого новый левел " + event.getLevel()));
    }

}
