package com.github.conditioner.mod.util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InputHelper {

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (event.dx != 0 || event.dy != 0) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

            if (player != null) {
                player.prevRenderYawOffset = player.renderYawOffset;
                player.prevRotationYawHead = player.rotationYawHead;
                player.prevRotationYaw = player.rotationYaw;
                player.prevRotationPitch = player.rotationPitch;
            }
        }
    }
}
