package io.github.nyg404.eyegoggle.common.point;

import io.github.nyg404.eyegoggle.api.point.ExperiencePoint;
import io.github.nyg404.eyegoggle.common.event.ExperiencePointEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

public class ContainerPointsWrapper {
    public final Player player;
    public final ContainerExperiencePoint containerExperiencePoint;

    public ContainerPointsWrapper(Player player, ContainerExperiencePoint containerExperiencePoint) {
        this.player = player;
        this.containerExperiencePoint = containerExperiencePoint;
    }

    public void addExperiencePoint(ExperiencePoint experiencePoint, int amount) {
        int oldLevel = containerExperiencePoint.getLevel(experiencePoint);
        containerExperiencePoint.add(experiencePoint, amount);
        int newLevel = containerExperiencePoint.getLevel(experiencePoint);

        NeoForge.EVENT_BUS.post(new ExperiencePointEvent(player, experiencePoint, amount));

        for (int level = oldLevel + 1; level <= newLevel; level++) {
            NeoForge.EVENT_BUS.post(new ExperiencePointEvent.NewLevels(player, experiencePoint, level));
        }
    }


}
