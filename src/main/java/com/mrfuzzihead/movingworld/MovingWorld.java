package com.mrfuzzihead.movingworld;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import com.mrfuzzihead.movingworld.common.CommonProxy;
import com.mrfuzzihead.movingworld.common.config.MainConfig;
import com.mrfuzzihead.movingworld.common.mrot.MetaRotations;
import com.mrfuzzihead.movingworld.common.network.MovingWorldMessageToMessageCodec;
import com.mrfuzzihead.movingworld.common.network.MovingWorldPacketHandler;
import com.mrfuzzihead.movingworld.common.network.NetworkUtil;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(
    modid = MovingWorld.MODID,
    name = MovingWorld.NAME,
    version = MovingWorld.VERSION,
    guiFactory = MovingWorld.GUIFACTORY)
public class MovingWorld {

    public static final String MODID = "movingworld";
    public static final String VERSION = Tags.VERSION;
    public static final String NAME = "MovingWorld";
    public static final String GUIFACTORY = "com.mrfuzzihead.movingworld.client.gui.MovingWorldGUIFactory";

    @Mod.Instance(value = MODID)
    public static MovingWorld instance;

    @SidedProxy(
        clientSide = "com.mrfuzzihead.movingworld.client.ClientProxy",
        serverSide = "com.mrfuzzihead.movingworld.common.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;

    public MetaRotations metaRotations;
    public MainConfig mConfig;
    public NetworkUtil network;

    public MovingWorld() {
        metaRotations = new MetaRotations();
        network = new NetworkUtil();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        File configFolder = new File(e.getModConfigurationDirectory(), MODID);
        File mConfigFile = new File(configFolder, MODID + ".cfg");
        mConfig = new MainConfig(new Configuration(mConfigFile));
        mConfig.loadAndSave();

        mConfig.postLoad();
        metaRotations.setConfigDirectory(e.getModConfigurationDirectory());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        network.channels = NetworkRegistry.INSTANCE
            .newChannel(MODID, new MovingWorldMessageToMessageCodec(), new MovingWorldPacketHandler());
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        metaRotations.readMetaRotationFiles();
    }

}
