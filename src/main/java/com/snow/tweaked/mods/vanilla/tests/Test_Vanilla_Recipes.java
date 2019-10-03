package com.snow.tweaked.mods.vanilla.tests;

import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedTest;
import com.snow.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Map;

public class Test_Vanilla_Recipes
{

    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Vanilla_Recipes_Remove implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "crafting";
        }

        @Override
        public String getTestDescription()
        {
            return "recipes.remove";
        }

        @Override
        public String[] getVariables()
        {
            return new String[] { "define varTorch : item(minecraft:torch);" };
        }

        @Override
        public String[] getActions()
        {
            return new String[] { "recipes.remove : var(varTorch);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.TORCH);

            //recipe search
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (ItemStack.areItemsEqual(stack, recipe.getValue().getRecipeOutput()))
                {
                    //recipes still exist, failed
                    return false;
                }
            }

            //no recipes exist, passed
            return true;
        }
    }


    //**************************************************************************************//
    //										shaped											//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Vanilla_Recipes_Shaped implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "crafting";
        }

        @Override
        public String getTestDescription()
        {
            return "recipes.shaped";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
            return new String[] { "recipes.shaped : \"tweaked_shaped\", item(minecraft:command_block), item(diamond), ore(ingotIron), item(coal);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);

            //count recipes
            int count = 0;
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (ItemStack.areItemsEqual(stack, recipe.getValue().getRecipeOutput()))
                {
                    count++;
                }
            }

            return count == 2;
        }
    }


    //**************************************************************************************//
    //										shapeless										//
    //**************************************************************************************//

    @TweakedTest()
    public static class Test_Vanilla_Recipes_Shapeless implements ITweakedTest
    {
        @Override
        public String getFilename()
        {
            return "crafting";
        }

        @Override
        public String getTestDescription()
        {
            return "recipes.shapeless";
        }

        @Override
        public String[] getVariables()
        {
            return new String[0];
        }

        @Override
        public String[] getActions()
        {
            return new String[] { "recipes.shapeless : \"tweaked_shapeless\", item(minecraft:command_block), item(minecraft:diamond), item(minecraft:coal).m(1);" };
        }

        @Override
        public boolean runTest(World world)
        {
            //create an itemstack
            ItemStack stack = new ItemStack(Blocks.COMMAND_BLOCK);

            //count recipes
            int count = 0;
            for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
            {
                if (ItemStack.areItemsEqual(stack, recipe.getValue().getRecipeOutput()))
                {
                    count++;
                }
            }

            return count == 2;
        }
    }
}
