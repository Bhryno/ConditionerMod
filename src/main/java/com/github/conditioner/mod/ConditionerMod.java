package com.github.conditioner.mod;

import com.github.conditioner.mod.util.InputHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ConditionerMod.MOD_ID, name = ConditionerMod.NAME, version = ConditionerMod.VERSION, acceptedMinecraftVersions = "1.8.9")
public class ConditionerMod {

    public static final String MOD_ID = "conditioned-mod";

    public static final String NAME = "ConditionerMod";

    public static final String VERSION = "1.0-SNAPSHOT";

    public static final Logger LOGGER = LogManager.getLogger(ConditionerMod.NAME);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info(NAME + " | " + VERSION + " has initialised on " + event.getSide());
        MinecraftForge.EVENT_BUS.register(new InputHelper());
    }
}
