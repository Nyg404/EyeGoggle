package io.github.nyg404.eyegoggle.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.nyg404.eyegoggle.EyeGoggle.MODID;

@EventBusSubscriber(value = Dist.CLIENT, modid = MODID)
public class RenderLevelIndi {
    private static final Minecraft mc = Minecraft.getInstance();

    // Позиции для рендеринга (5 позиций)
    private static final int[][] POSITIONS = {
            {50, 50},
            {100, 100},
            {150, 150},
            {200, 200},
            {250, 250}
    };

    // Текстуры индикаторов
    private static final ResourceLocation[] TEXTURES = {
            rl("combat.png"),
            rl("pick.png"),
            rl("speed.png"),
            rl("jump.png"),
            rl("health.png")
    };

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/indicator/" + path);
    }

    @SubscribeEvent
    public static void renderGetExperiencePoint(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        List<Integer> activeIndicators = List.of(0, 1, 3);
        Set<Integer> occupiedPositions = new HashSet<>();

        int frameCount = 30;
        int spriteWidth = 16;
        int spriteHeight = 48;

        // Вычисляем текущий кадр анимации (например, с циклом по времени)
        int tick = (int)(System.currentTimeMillis() / 50) % frameCount;

        for (int textureId : activeIndicators) {
            int posIndex = findMinimalFreePosition(occupiedPositions);
            if (posIndex == -1) {
                // Нет свободных позиций
                break;
            }
            occupiedPositions.add(posIndex);

            int x = POSITIONS[posIndex][0];
            int y = POSITIONS[posIndex][1];

            int u = 0;
            int v = tick * spriteHeight;

            guiGraphics.blit(TEXTURES[textureId], x, y, u, v, spriteWidth, spriteHeight);
        }
    }


    private static int findMinimalFreePosition(Set<Integer> occupied) {
        for (int i = 0; i < POSITIONS.length; i++) {
            if (!occupied.contains(i)) {
                return i;
            }
        }
        return -1; // нет свободных позиций
    }

}
