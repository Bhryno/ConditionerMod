package com.github.conditioner.mod.startup.hook;

import com.github.conditioner.mod.ConditionerMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class NetHandlerPlayClientHook {

    public static boolean validateResourcePackUrl(NetHandlerPlayClient client, String url, String hash) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            boolean levelProtocol = scheme.equals("level");

            if (!scheme.equals("http") && !scheme.equals("https") && !levelProtocol) {
                client.getNetworkManager().sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                throw new URISyntaxException(url, "Wrong protocol");
            }
            url = URLDecoder.decode(url.substring("level://".length()), StandardCharsets.UTF_8.toString());
            if (levelProtocol && (url.contains("..") || !url.endsWith("/resources.zip"))) {
                ConditionerMod.LOGGER.warn("Malicious server tried to access " + url);
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

                if (player != null) {
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + EnumChatFormatting.BOLD.toString() + "[WARNING] The current server has attempted to be malicious but ConditionedMod has denied their entry."));
                }
                throw new URISyntaxException(url, "Invalid level-storage resource pack path");
            }
            return true;
        } catch (URISyntaxException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            ConditionerMod.LOGGER.error(e.getMessage());
        }
        return false;
    }
}
