package com.snow.tweaked;

import com.snow.tweaked.controllers.TweakedConfig;
import com.snow.tweaked.controllers.TweakedAnnotations;
import com.snow.tweaked.controllers.TweakedCommands;
import com.snow.tweaked.controllers.TweakedTests;
import com.snow.tweaked.controllers.TweakedLogger;
import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Recipes;
import com.snow.tweaked.script.ScriptHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;

@Mod(modid = Tweaked.MODID, name = Tweaked.NAME, version = Tweaked.VERSION)
public class Tweaked
{
    public static final String MODID = "tweaked";
    public static final String NAME = "Tweaked";
    public static final String VERSION = "1.0.1";

    public static TweakedLogger LOG;

    @EventHandler
    public void construction(FMLConstructionEvent event)
    {
        //set mod container, for use later
        Helper_Vanilla_Recipes.TWEAKED_CONTAINER = Loader.instance().activeModContainer();

        //load configuration
        TweakedConfig.loadConfig(TweakedConfig.getMcDir());

        //create logger
        LOG = new TweakedLogger(LogManager.getLogger(MODID));

        //load annotations
        TweakedAnnotations.build(event.getASMHarvestedData());

        //load scripts
        if (TweakedConfig.testMode) ScriptHelper.loadTests();
        else ScriptHelper.loadScripts();

        LOG.debug("Finished Construction");
    }

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        LOG.debug("Started PreInit Event");

        //preInit mods
        TweakedAnnotations.MODS.forEach(mod -> mod.pre(event));

        LOG.debug("Finished PreInit Event");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOG.debug("Started Init Event");

        //init mods
        TweakedAnnotations.MODS.forEach(mod -> mod.init(event));

        LOG.debug("Finished Init Event");
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        LOG.debug("Started PostInit Event");

        //postInit mods
        TweakedAnnotations.MODS.forEach(mod -> mod.post(event));

        //save configuration
        TweakedConfig.saveConfig();

        LOG.debug("Finished PostInit Event");
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        LOG.debug("Started ServerStarting Event");

        //register commands
        event.registerServerCommand(new TweakedCommands());

        //serverStart mods
        TweakedAnnotations.MODS.forEach(mod -> mod.serverStarting(event));

        //run tests now that everything should be loaded
        if (TweakedConfig.testMode)
        {
            //get world for testing
            World world = event.getServer().getWorld(0);

            TweakedTests.run(world);
        }

        LOG.debug("Finished ServerStarting Event");
    }
}