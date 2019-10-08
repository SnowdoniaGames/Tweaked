package com.snow.tweaked.mods.thermalexpansion.tests;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.managers.device.DiffuserManager;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Reflection;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Test_TE_Diffuser
{
    //**************************************************************************************//
    //										diffuser.add									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Diffuser_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.diffuser.add";
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
                    "te.diffuser.add : item(diamond), 5, 5;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            ItemStack stack = new ItemStack(Items.DIAMOND);
            return DiffuserManager.getReagentAmplifier(stack) == 5 && DiffuserManager.getReagentDuration(stack) == 5;
        }
    }


    //**************************************************************************************//
    //										diffuser.remove									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Diffuser_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.diffuser.remove";
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
                    "te.diffuser.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            TObjectIntHashMap<ComparableItemStack> reagents = Helper_TE_Reflection.getDiffuserAmp();
            return reagents != null && reagents.size() == 1;
        }
    }
}
