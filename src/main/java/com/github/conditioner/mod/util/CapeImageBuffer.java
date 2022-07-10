package com.github.conditioner.mod.util;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

@SuppressWarnings("unused")
public class CapeImageBuffer implements IImageBuffer {

    private final WeakReference<AbstractClientPlayer> playerRef;

    private final ResourceLocation resource;

    public CapeImageBuffer(AbstractClientPlayer entity, ResourceLocation resource) {
        ImageBufferDownload img = new ImageBufferDownload();
        playerRef = new WeakReference<>(entity);
        this.resource = resource;
    }

    @Override
    public BufferedImage parseUserSkin(BufferedImage img) {
        return parseCape(img);
    }

    @Override
    public void skinAvailable() {
        AbstractClientPlayer entity = playerRef.get();

        if (entity != null) {
            setCapeLocation(entity, resource);
        }
    }

    private void setCapeLocation(AbstractClientPlayer entity, ResourceLocation resource) {}

    private BufferedImage parseCape(BufferedImage img) {
        return null;
    }
}
