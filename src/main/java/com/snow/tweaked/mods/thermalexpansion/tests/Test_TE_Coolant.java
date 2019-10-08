package com.snow.tweaked.mods.thermalexpansion.tests;

import cofh.thermalexpansion.util.managers.device.CoolantManager;
import cofh.thermalexpansion.util.managers.device.FactorizerManager;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import static cofh.thermalexpansion.util.managers.device.FactorizerManager.FactorizerRecipe;

public class Test_TE_Coolant
{
    //**************************************************************************************//
    //										coolant.add										//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Coolant_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.coolant.add";
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
                    "te.coolant.add : fluid(lava), 1000000, 40;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return CoolantManager.isValidCoolant(FluidRegistry.LAVA);
        }
    }


    //**************************************************************************************//
    //										coolant.remove									//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Coolant_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "coolant.remove";
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
                    "te.coolant.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return CoolantManager.getCoolantFluids().size() == 1;
        }
    }
}
