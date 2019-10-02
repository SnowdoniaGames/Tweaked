package com.snow.tweaked.mods.vanilla.actions;

import com.snow.tweaked.Tweaked;
import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.api.script.IIngredient;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.mods.vanilla.Tweaked_Vanilla;
import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Recipes;
import com.snow.tweaked.script.arguments.ArgItem;
import com.snow.tweaked.script.arguments.ArgNull;
import com.snow.tweaked.script.arguments.ArgString;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.snow.tweaked.Tweaked.LOG;

public class Action_Vanilla_Recipes
{
    public static Action_Vanilla_Recipes_Remove REMOVE = null;
    public static Action_Vanilla_Recipes_Shaped SHAPED = null;
    public static Action_Vanilla_Recipes_Shapeless SHAPELESS = null;


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction(value = "recipes.remove", modid = "minecraft")
    public static class Action_Vanilla_Recipes_Remove extends ActionAbstract
    {
        public List<ArgItem> STACKS =  new ArrayList<>();
        public List<ResourceLocation> NAMES = new ArrayList<>();

        public Action_Vanilla_Recipes_Remove()
        {
            REMOVE = this;
        }

        public void build(String recipeName)
        {
            NAMES.add(new ResourceLocation(recipeName));
        }

        public void build(ArgItem ingredient)
        {
            STACKS.add(ingredient);
        }

        @Override
        protected void run()
        {
            //search registry to match recipes
            if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
            {
                LOG.warn("Warning : Recipe Registry Missing");
                return;
            }

            if (!STACKS.isEmpty())
            {
                //convert stacks into recipe names
                for (Map.Entry<ResourceLocation, IRecipe> recipe : Tweaked_Vanilla.RECIPE_REGISTRY.getEntries())
                {
                    for (ArgItem item : STACKS)
                    {
                        if (item.matches(recipe.getValue().getRecipeOutput()))
                        {
                            NAMES.add(recipe.getValue().getRegistryName());
                            break;
                        }
                    }
                }
            }

            if (!NAMES.isEmpty())
            {
                //remove recipes
                for (ResourceLocation recipe : NAMES)
                {
                    if (Tweaked_Vanilla.RECIPE_REGISTRY.containsKey(recipe))
                    {
                        //remove the recipes and create restore point
                        Tweaked_Vanilla.RECIPE_REGISTRY.remove(recipe);

                        //schedule the recipe to be replaced by a dummy if required
                        Helper_Vanilla_Recipes.REMOVED_RECIPES.add(recipe);

                        //debug
                        LOG.debug("recipes.remove : " + recipe);
                    }
                }
            }

            //cleanup
            STACKS = new ArrayList<>();
            NAMES = new ArrayList<>();
        }
    }


    //**************************************************************************************//
    //										shaped											//
    //**************************************************************************************//

    @TweakedAction(value = "recipes.shaped", modid = "minecraft")
    public static class Action_Vanilla_Recipes_Shaped extends ActionAbstract
    {
        public List<IRecipe> RECIPES = new ArrayList<>();

        public Action_Vanilla_Recipes_Shaped()
        {
            SHAPED = this;
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA)
        {
            build(recipeName, output, inputA, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB)
        {
            build(recipeName, output, inputA, inputB, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC)
        {
            build(recipeName, output, inputA, inputB, inputC, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, inputF, new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF, IIngredient inputG)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, inputF, inputG, new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF, IIngredient inputG, IIngredient inputH)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, inputF, inputG, inputH, new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF, IIngredient inputG, IIngredient inputH, IIngredient inputI)
        {
            //create the recipe args
            Object[] craftingArray = Helper_Vanilla_Recipes.createShapedArray(inputA.getItem(), inputB.getItem(), inputC.getItem(), inputD.getItem(), inputE.getItem(), inputF.getItem(), inputG.getItem(), inputH.getItem(), inputI.getItem());
            if (craftingArray == null) return;

            //create a new shaped
            IRecipe recipe = new ShapedOreRecipe(null, output.stack, craftingArray);

            //set container to Tweaked so that recipes are registered to it rather than Tweaked_Vanilla
            Helper_Vanilla_Recipes.setTweakedContainer();

            //add recipe
            recipe.setRegistryName(new ResourceLocation(Tweaked.MODID, recipeName.value));
            RECIPES.add(recipe);

            //restore container
            Helper_Vanilla_Recipes.restoreContainer();
        }

        @Override
        protected void run()
        {
            //search registry to match recipes
            if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
            {
                LOG.warn("Warning : Recipe Registry Missing");
                return;
            }

            if (!RECIPES.isEmpty())
            {
                for (IRecipe recipe : RECIPES)
                {
                    Tweaked_Vanilla.RECIPE_REGISTRY.register(recipe);

                    //debug
                    LOG.debug("recipes.shaped : " + recipe.getRecipeOutput());
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										shapeless										//
    //**************************************************************************************//

    @TweakedAction(value = "recipes.shapeless", modid = "minecraft")
    public static class Action_Vanilla_Recipes_Shapeless extends ActionAbstract
    {
        public List<IRecipe> RECIPES = new ArrayList<>();

        public Action_Vanilla_Recipes_Shapeless()
        {
            SHAPELESS = this;
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA)
        {
            build(recipeName, output, inputA, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB)
        {
            build(recipeName, output, inputA, inputB, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC)
        {
            build(recipeName, output, inputA, inputB, inputC, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, new ArgNull(), new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, inputF, new ArgNull(), new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF, IIngredient inputG)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, inputF, inputG, new ArgNull(), new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF, IIngredient inputG, IIngredient inputH)
        {
            build(recipeName, output, inputA, inputB, inputC, inputD, inputE, inputF, inputG, inputH, new ArgNull());
        }

        public void build(ArgString recipeName, ArgItem output, IIngredient inputA, IIngredient inputB, IIngredient inputC, IIngredient inputD, IIngredient inputE, IIngredient inputF, IIngredient inputG, IIngredient inputH, IIngredient inputI)
        {
            //create the recipe args
            Object[] craftingArray = Helper_Vanilla_Recipes.createShapelessArray(inputA.getItem(), inputB.getItem(), inputC.getItem(), inputD.getItem(), inputE.getItem(), inputF.getItem(), inputG.getItem(), inputH.getItem(), inputI.getItem());

            //create a new shapeless recipe
            IRecipe recipe = new ShapelessOreRecipe(null, output.stack, craftingArray);

            //set container to Tweaked so that recipes are registered to it rather than Tweaked_Vanilla
            Helper_Vanilla_Recipes.setTweakedContainer();

            //add recipe
            recipe.setRegistryName(new ResourceLocation(Tweaked.MODID, recipeName.value));
            RECIPES.add(recipe);

            //restore container
            Helper_Vanilla_Recipes.restoreContainer();
        }

        @Override
        protected void run()
        {
            //search registry to match recipes
            if (Tweaked_Vanilla.RECIPE_REGISTRY == null)
            {
                LOG.warn("Warning : Recipe Registry Missing");
                return;
            }

            if (!RECIPES.isEmpty())
            {
                for (IRecipe recipe : RECIPES)
                {
                    Tweaked_Vanilla.RECIPE_REGISTRY.register(recipe);

                    //debug
                    LOG.debug("recipes.shapeless : " + recipe.getRecipeOutput());
                }
            }

            //cleanup
            RECIPES = null;
        }
    }
}
