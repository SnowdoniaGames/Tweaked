package com.snow.tweaked.mods.thermalexpansion;

import com.snow.tweaked.Tweaked;
import com.snow.tweaked.controllers.TweakedConfig;
import com.snow.tweaked.mods.thermalexpansion.actions.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.minecraftforge.fml.common.Mod.EventHandler;

@Mod(modid = Tweaked_TE.MODID, name = Tweaked_TE.NAME, version = Tweaked.VERSION, dependencies=Tweaked_TE.DEPENDENCIES)
public class Tweaked_TE
{
    public static final String MODID = "tweaked_te";
    public static final String NAME = "Tweaked_TE";
    public static final String DEPENDENCIES = "required-after:tweaked;after:thermalexpansion;";

    //flags
    public static boolean LOADED = false;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        LOADED = TweakedConfig.modsEnableThermalExpansion;
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        //register late events
        if (LOADED)
        {
            //coolant (thermal mediator)
            Action_TE_Coolant.REMOVE.apply();
            Action_TE_Coolant.ADD.apply();

            //collector
            Action_TE_Collector.REMOVE.apply();
            Action_TE_Collector.ADD.apply();

            //diffuser
            Action_TE_Diffuser.REMOVE.apply();
            Action_TE_Diffuser.ADD.apply();

            //factorizer
            Action_TE_Factorizer.REMOVE_SPLIT.apply();
            Action_TE_Factorizer.REMOVE_COMBINE.apply();
            Action_TE_Factorizer.ADD_SPLIT.apply();
            Action_TE_Factorizer.ADD_COMBINE.apply();

            //fisher
            Action_TE_Fisher.REMOVE.apply();
            Action_TE_Fisher.REMOVE_BAIT.apply();
            Action_TE_Fisher.ADD.apply();
            Action_TE_Fisher.ADD_BAIT.apply();

            //tapper
            Action_TE_Tapper.REMOVE.apply();
            Action_TE_Tapper.REMOVE_FERTILIZER.apply();
            Action_TE_Tapper.ADD.apply();
            Action_TE_Tapper.ADD_LEAF.apply();
            Action_TE_Tapper.ADD_FERTILIZER.apply();
        }
    }
}