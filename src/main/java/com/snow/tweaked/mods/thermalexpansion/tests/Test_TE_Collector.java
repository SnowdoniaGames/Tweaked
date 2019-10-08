package com.snow.tweaked.mods.thermalexpansion.tests;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.managers.device.XpCollectorManager;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Reflection;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Test_TE_Collector
{
    //**************************************************************************************//
    //										collector.add									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Collector_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.collector.add";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
            return new String[] {
                    "te.collector.add : item(diamond), 500, 300;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            ItemStack stack = new ItemStack(Items.DIAMOND);
            return XpCollectorManager.getCatalystXp(stack) == 500 && XpCollectorManager.getCatalystFactor(stack) == 300;
        }
    }


    //**************************************************************************************//
    //										collector.remove								//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Collector_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.collector.remove";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
            return new String[]{
                    "te.collector.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            TObjectIntHashMap<ComparableItemStack> catalysts = Helper_TE_Reflection.getCatalystXp();
            return catalysts != null && catalysts.size() == 1;
        }
    }
}
