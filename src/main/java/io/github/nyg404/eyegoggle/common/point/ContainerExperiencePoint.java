package io.github.nyg404.eyegoggle.common.point;

import com.mojang.serialization.Codec;
import io.github.nyg404.eyegoggle.api.point.ExperiencePoint;
import io.github.nyg404.eyegoggle.api.point.IContainerExperiencePoint;
import io.github.nyg404.eyegoggle.api.point.PointData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContainerExperiencePoint implements IContainerExperiencePoint {

    public static final Codec<Map<ExperiencePoint, PointData>> MAP_CODEC = Codec.unboundedMap(ExperiencePoint.CODEC, PointData.CODEC);
    public static final Codec<ContainerExperiencePoint> CODEC = MAP_CODEC.xmap(ContainerExperiencePoint::new,
            ContainerExperiencePoint::getPoints);
    public static final StreamCodec<RegistryFriendlyByteBuf, Map<ExperiencePoint, PointData>> STREAM_CODEC =
            ByteBufCodecs.map(
                    LinkedHashMap::new,
                    ByteBufCodecs.fromCodec(ExperiencePoint.CODEC),
                    ByteBufCodecs.fromCodec(PointData.CODEC)
            );

    public final Map<ExperiencePoint, PointData> points;

    public static final int MAX_LEVEL = 200;
    public static final int LEVEL_UP_START = 50;
    public static final double LEVEL_MULTIPLIER = 1.04;
    public static final int TREB_PER_LEVEL = 3;

    public ContainerExperiencePoint(Map<ExperiencePoint, PointData> points) {
        this.points = points != null ? new HashMap<>(points) : new HashMap<>();
    }


    @Override
    public Map<ExperiencePoint, PointData> getPoints() {
        return points;
    }

    @Override
    public void add(ExperiencePoint experiencePoint, int amount) {
        PointData pointData = points.getOrDefault(experiencePoint, new PointData(0, 0, 0));
        int currentAmount = pointData.amount() + amount;
        int currentLevel = pointData.level();

        while (currentLevel < MAX_LEVEL) {
            int requiredExp = getRequiredExperienceForLevel(currentLevel);
            if (currentAmount < requiredExp) break;

            currentAmount -= requiredExp;
            currentLevel++;
        }

        int requiredExpForNextLevel = getRequiredExperienceForLevel(currentLevel);

        points.put(experiencePoint, new PointData(currentAmount, requiredExpForNextLevel, currentLevel));
    }




    @Override
    public void subtract(ExperiencePoint experiencePoint, int amount) {
        PointData pointData = points.get(experiencePoint);
        if (pointData == null) return;

        int currentAmount = Math.max(pointData.amount() - amount, 0);
        int currentLevel = pointData.level();

        // Не понижаем уровень, но treb всё равно пересчитываем по текущему уровню
        int currentTrebAmount = calculateTrebAmount(currentLevel);

        points.put(experiencePoint, new PointData(currentAmount, currentTrebAmount, currentLevel));
    }

    @Override
    public Integer getAmount(ExperiencePoint experiencePoint) {
        PointData pointData = points.get(experiencePoint);
        return pointData != null ? pointData.amount() : 0;
    }

    @Override
    public Integer getLevel(ExperiencePoint experiencePoint) {
        PointData pointData = points.get(experiencePoint);
        return pointData != null ? pointData.level() : 0;
    }

    public Integer getTrebAmount(ExperiencePoint experiencePoint) {
        PointData pointData = points.get(experiencePoint);
        return pointData != null ? pointData.trebamount() : 0;
    }

    private int getRequiredExperienceForLevel(int level) {
        return (int) Math.round(LEVEL_UP_START * Math.pow(LEVEL_MULTIPLIER, level));
    }


    private int calculateTrebAmount(int level) {
        return level * TREB_PER_LEVEL;
    }
}
