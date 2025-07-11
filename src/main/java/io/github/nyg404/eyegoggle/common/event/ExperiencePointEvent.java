package io.github.nyg404.eyegoggle.common.event;

import io.github.nyg404.eyegoggle.api.point.ExperiencePoint;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public class ExperiencePointEvent extends Event {
    private final Player player;
    private final ExperiencePoint experiencePoint;
    private final int amount;

    public ExperiencePointEvent(Player player, ExperiencePoint experiencePoint, int amount) {
        this.player = player;
        this.experiencePoint = experiencePoint;
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public ExperiencePoint getExperiencePoint() {
        return experiencePoint;
    }

    public int getAmount() {
        return amount;
    }

    public static class NewLevels extends Event {
        private final Player player;
        private final ExperiencePoint experiencePoint;
        private final int level;

        public Player getPlayer() {
            return player;
        }

        public int getLevel() {
            return level;
        }

        public ExperiencePoint getExperiencePoint() {
            return experiencePoint;
        }

        public NewLevels(Player player, ExperiencePoint experiencePoint, int level) {
            this.player = player;
            this.experiencePoint = experiencePoint;
            this.level = level;
        }
    }
}
