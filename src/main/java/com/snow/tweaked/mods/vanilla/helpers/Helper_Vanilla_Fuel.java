package com.snow.tweaked.mods.vanilla.helpers;

import com.snow.tweaked.script.ScriptHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

public class Helper_Vanilla_Fuel
{
    private static boolean clear = false;

    private static List<Tuple<ItemStack, Integer>> addFuels;
    private static List<ItemStack> removeFuels;

    /**
     * Used to determine whether to register the loadFuel event
     * @return true if it needs registering
     */
    public static boolean hasChanges()
    {
        return clear || addFuels != null || removeFuels != null;
    }

    public static void setClear(boolean b)
    {
        clear = b;

        LOG.debug("furnace.fuel.remove : clear");
    }

    public static boolean isClear()
    {
        return clear;
    }

    public static void addAddFuel(ItemStack fuel, int burnTime)
    {
        if (addFuels == null) addFuels = new ArrayList<>();

        addFuels.add(new Tuple<>(fuel.copy(), burnTime));

        LOG.debug("furnace.fuel.add : " + ScriptHelper.stackToScript(fuel) + " = " + burnTime);
    }

    public static List<Tuple<ItemStack, Integer>> getAddFuels()
    {
        return addFuels;
    }

    public static void addRemoveFuel(ItemStack fuel)
    {
        if (removeFuels == null) removeFuels = new ArrayList<>();

        removeFuels.add(fuel.copy());

        LOG.debug("furnace.fuel.remove : " + ScriptHelper.stackToScript(fuel));
    }

    public static List<ItemStack> getRemoveFuels()
    {
        return removeFuels;
    }
}
