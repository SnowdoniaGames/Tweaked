package com.snow.tweaked.mods.jei;

import com.snow.tweaked.Tweaked;
import com.snow.tweaked.controllers.TweakedConfig;
import com.snow.tweaked.mods.jei.actions.Action_JEI;
import com.snow.tweaked.mods.jei.proxy.Proxy_JEI_Common;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.minecraftforge.fml.common.Mod.EventHandler;

@Mod(modid = Tweaked_JEI.MODID, name = Tweaked_JEI.NAME, version = Tweaked.VERSION, dependencies = Tweaked_JEI.DEPENDENCIES)
public class Tweaked_JEI
{
    public static final String MODID = "tweaked_jei";
    public static final String NAME = "Tweaked_JEI";
    public static final String DEPENDENCIES = "required-after:tweaked;after:jei;";

    //flags
    public static boolean LOADED = false;

    @SidedProxy(clientSide = "com.snow.tweaked.mods.jei.proxy.Proxy_JEI_Client", serverSide = "com.snow.tweaked.mods.jei.proxy.Proxy_JEI_Common")
    public static Proxy_JEI_Common proxy;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        LOADED = TweakedConfig.modsEnableJEI;
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        if (LOADED)
        {
            //hide items
            Action_JEI.HIDE.apply();
        }
    }

}