package com.snow.tweaked.mods.thermalexpansion.tests;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.managers.device.FisherManager;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Reflection;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class Test_TE_Fisher
{
    //**************************************************************************************//
    //										fisher.add										//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Fisher_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.fisher.add";
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
                    "te.fisher.add : item(minecraft:emerald), 1;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            //simulate catching 100 fish
            for (int i = 0; i < 100; i++)
            {
                ItemStack fish = FisherManager.getFish();
                if (fish == null || fish.getItem() != Items.EMERALD)
                {
                    return false;
                }
            }
            return true;
        }
    }


    //**************************************************************************************//
    //										fisher.remove									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Fisher_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.fisher.remove";
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
                    "te.fisher.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            List<ItemStack> fish = Helper_TE_Reflection.getFishList();
            return fish != null && fish.size() == 1;
        }
    }


    //**************************************************************************************//
    //									fisher.bait.add										//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Fisher_Bait_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.fisher.bait.add";
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
                    "te.fisher.bait.add : item(minecraft:diamond), 5;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return FisherManager.getBaitMultiplier(new ItemStack(Items.DIAMOND)) == 5;
        }
    }


    //**************************************************************************************//
    //									fisher.bait.remove									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Fisher_Bait_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.fisher.bait.remove";
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
                    "te.fisher.bait.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            TObjectIntHashMap<ComparableItemStack> bait = Helper_TE_Reflection.getBait();
            return bait != null && bait.size() == 1;
        }
    }
}
