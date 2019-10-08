package com.snow.tweaked.mods.thermalexpansion.tests;

import cofh.thermalexpansion.util.managers.device.FactorizerManager;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static cofh.thermalexpansion.util.managers.device.FactorizerManager.*;

public class Test_TE_Factorizer
{
    //**************************************************************************************//
    //										split.add										//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Factorizer_AddSplit implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.factorizer.split.add";
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
                    "te.factorizer.split.add : item(minecraft:diamond).count(9), item(minecraft:emerald);"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            FactorizerRecipe recipe = FactorizerManager.getRecipe(new ItemStack(Items.EMERALD), true);
            return recipe != null;
        }
    }


    //**************************************************************************************//
    //										split.remove									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Factorizer_RemoveSplit implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.factorizer.split.remove";
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
                    "te.factorizer.split.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return FactorizerManager.getRecipeList(true).length == 1;
        }
    }


    //**************************************************************************************//
    //										combine.add										//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Factorizer_AddCombine implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.factorizer.combine.add";
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
                    "te.factorizer.combine.add : item(minecraft:emerald), item(minecraft:diamond).count(9);"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            FactorizerRecipe recipe = FactorizerManager.getRecipe(new ItemStack(Items.DIAMOND, 9), false);
            return recipe != null;
        }
    }


    //**************************************************************************************//
    //										combine.remove									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Factorizer_RemoveCombine implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.factorizer.combine.remove";
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
                    "te.factorizer.combine.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return FactorizerManager.getRecipeList(false).length == 1;
        }
    }
}
