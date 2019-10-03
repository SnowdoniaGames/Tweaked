package com.snow.tweaked.mods.vanilla.tests;

import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import java.util.Map;

@SuppressWarnings("unused")
public class Test_Vanilla_Furnace
{
    //**************************************************************************************//
    //											add											//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Furnace_ADD implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "furnace";
        }

        @Override
        public String getTestDescription()
        {
            return "furnace.add";
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
                    "furnace.add : item(minecraft:emerald), item(minecraft:diamond), 1.0;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
            {
                if (entry.getKey().getItem().equals(Items.DIAMOND))
                {
                    if (entry.getValue().getItem().equals(Items.EMERALD))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }


    //**************************************************************************************//
    //											remove										//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Furnace_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "furnace";
        }

        @Override
        public String getTestDescription()
        {
            return "furnace.remove";
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
                    "furnace.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return FurnaceRecipes.instance().getSmeltingList().size() == 1;
        }
    }


    //**************************************************************************************//
    //										fuel.add										//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Furnace_AddFuel implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "furnace";
        }

        @Override
        public String getTestDescription()
        {
            return "furnace.fuel.add";
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
                    "furnace.fuel.add : item(minecraft:diamond), 8000;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return TileEntityFurnace.getItemBurnTime(new ItemStack(Items.DIAMOND)) == 1000;
        }
    }


    //**************************************************************************************//
    //										fuel.remove										//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Furnace_RemoveFuel implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "furnace";
        }

        @Override
        public String getTestDescription()
        {
            return "furnace.fuel.remove";
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
                    "furnace.fuel.remove : *;"
            };
        }

        @Override
        public boolean runTest(World world)
        {
            return TileEntityFurnace.getItemBurnTime(new ItemStack(Items.COAL)) == 0;
        }
    }
}
