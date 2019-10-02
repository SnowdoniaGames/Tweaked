package com.snow.tweaked.mods.vanilla.proxy;

import com.snow.tweaked.mods.vanilla.events.Events_Vanilla_Client;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class Proxy_Vanilla_Client extends Proxy_Vanilla_Common
{
    @Override
    public void registerEvents()
    {
        super.registerEvents();

        MinecraftForge.EVENT_BUS.register(new Events_Vanilla_Client());
    }

    @Override
    public void registerEventsLate()
    {
        super.registerEventsLate();
    }
}
