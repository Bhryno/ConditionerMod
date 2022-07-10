package com.github.conditioner.mod.startup.hook;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.List;

@SuppressWarnings("unused")
public class GuiUtilsHook {

    public static int scrollX = 0, scrollY = 0;

    public static boolean allowScrolling;

    public static void drawHoveringText(List<String> textLines, int screenHeight, int tooltipY, int tooltipHeight) {
        if (!allowScrolling) {
            scrollX = 0;
            scrollY = 0;
        }
        allowScrolling = tooltipY < 0;
        GlStateManager.pushMatrix();
        if (allowScrolling) {
            int dWheel = Mouse.getDWheel();

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                if (dWheel < 0) {
                    scrollX += 10;
                } else if (dWheel > 0) {
                    scrollX -= 10;
                }
            } else {
                if (dWheel < 0) {
                    scrollY -= 10;
                } else if (dWheel > 0) {
                    scrollY += 10;
                }
            }
            if (scrollY + tooltipY > 6) {
                scrollY -= tooltipY + 6;
            } else if (scrollY + tooltipY + tooltipHeight + 6 < screenHeight) {
                scrollY = screenHeight - 6 - tooltipY - tooltipHeight;
            }
        }
        GlStateManager.translate(scrollX, scrollY, 0);
    }
}
