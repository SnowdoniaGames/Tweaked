package com.snow.tweaked.mods.vanilla.proxy;

import com.snow.tweaked.mods.vanilla.events.Events_Vanilla_Recipes;
import net.minecraftforge.common.MinecraftForge;

public class Proxy_Vanilla_Common
{
    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new Events_Vanilla_Recipes());
    }

    public void registerEventsLate()
    {

    }
}
