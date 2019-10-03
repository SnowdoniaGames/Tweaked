package com.snow.tweaked.mods.vanilla.proxy;

import com.snow.tweaked.mods.vanilla.events.Events_Vanilla_Fuel;
import com.snow.tweaked.mods.vanilla.events.Events_Vanilla_Recipes;
import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Fuel;
import net.minecraftforge.common.MinecraftForge;

public class Proxy_Vanilla_Common
{
    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new Events_Vanilla_Recipes());
    }

    public void registerEventsLate()
    {
        //only register fuel event if we have custom fuels added/removed
        if (Helper_Vanilla_Fuel.hasChanges())
        {
            MinecraftForge.EVENT_BUS.register(new Events_Vanilla_Fuel());
        }
    }
}
