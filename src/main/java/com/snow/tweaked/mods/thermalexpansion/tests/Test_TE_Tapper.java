package com.snow.tweaked.mods.thermalexpansion.tests;

import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.BlockWrapper;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Reflection;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class Test_TE_Tapper
{
    //**************************************************************************************//
    //						    				add		    								//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Tapper_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.tapper.add";
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
                    "te.tapper.add : item(minecraft:log), fluid(lava);"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return TapperManager.getFluid(new ItemStack(Blocks.LOG)).getFluid() == FluidRegistry.LAVA;
        }
    }


    //**************************************************************************************//
    //								    		remove		    							//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Tapper_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.tapper.remove";
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
                    "te.tapper.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            Map<BlockWrapper, FluidStack> blocks = Helper_TE_Reflection.getTreeBlockList();

            return blocks != null && blocks.size() == 1;
        }
    }


    //**************************************************************************************//
    //										leaf.add										//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Tapper_Leaf_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.tapper.leaf.add";
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
                    "te.tapper.leaf.add : item(minecraft:log), item(stone);"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            //experimental
            return true;
        }
    }


    //**************************************************************************************//
    //						    		fertilizer.add	    								//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Tapper_Fertilizer_Add implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.tapper.fertilizer.add";
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
                    "te.tapper.fertilizer.add : item(minecraft:diamond), 5;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return TapperManager.getFertilizerMultiplier(new ItemStack(Items.DIAMOND)) == 5;
        }
    }


    //**************************************************************************************//
    //								    fertilizer.remove	    							//
    //**************************************************************************************//

    @TweakedTest(modid = "thermalexpansion")
    public static class Test_Tapper_Fertilizer_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "thermalexpansion";
        }

        @Override
        public String getTestDescription()
        {
            return "te.tapper.fertilizer.remove";
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
                    "te.tapper.fertilizer.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            TObjectIntHashMap<ComparableItemStack> fertilizers = Helper_TE_Reflection.getTreeFertilizerList();
            return fertilizers != null && fertilizers.size() == 1;
        }
    }
}
