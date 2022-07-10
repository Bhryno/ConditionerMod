package com.github.conditioner.mod.util;

import com.github.conditioner.mod.ConditionerMod;

import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class ResourceHandler {

    private static final int SIZE = 64;

    public static BufferedImage scaleResourcePackImg(BufferedImage img) {
        if (img == null) {
            return null;
        }
        ConditionerMod.LOGGER.info("Scaling resource pack icon from " + img.getWidth() + " to " + SIZE);
        BufferedImage smallImg = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = smallImg.getGraphics();

        graphics.drawImage(img, 0, 0, SIZE, SIZE, null);
        graphics.dispose();
        return smallImg;
    }
}
