package com.snow.tweaked.mods.vanilla.events;

import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Fuel;
import com.snow.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class Events_Vanilla_Fuel
{
    @SubscribeEvent
    public void loadFuel(FurnaceFuelBurnTimeEvent event)
    {
        if (Helper_Vanilla_Fuel.getAddFuels() != null)
        {
            for (Tuple<ItemStack, Integer> fuel : Helper_Vanilla_Fuel.getAddFuels())
            {
                if (ScriptHelper.areScriptItemsEqual(fuel.getFirst(), event.getItemStack()))
                {
                    event.setBurnTime(fuel.getSecond());
                    return;
                }
            }
        }

        if (Helper_Vanilla_Fuel.isClear())
        {
            event.setBurnTime(0);
        }
        else
        {
            if (Helper_Vanilla_Fuel.getRemoveFuels() != null)
            {
                for (ItemStack fuel : Helper_Vanilla_Fuel.getRemoveFuels())
                {
                    if (ScriptHelper.areScriptItemsEqual(fuel, event.getItemStack()))
                    {
                        event.setBurnTime(0);
                        return;
                    }
                }
            }
        }
    }
}
