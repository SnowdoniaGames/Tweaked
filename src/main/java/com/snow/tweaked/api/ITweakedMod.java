package com.snow.tweaked.api;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@SuppressWarnings("unused")
public interface ITweakedMod
{
    int getPriority();

    void pre(FMLPreInitializationEvent event);
    void init(FMLInitializationEvent event);
    void post(FMLPostInitializationEvent event);
    void serverStarting(FMLServerStartingEvent event);
}
