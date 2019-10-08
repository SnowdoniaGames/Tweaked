package com.snow.tweaked.mods.thermalexpansion.actions;

import cofh.thermalexpansion.util.managers.device.FactorizerManager;
import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedFactorizerRecipe;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.script.arguments.ArgAll;
import com.snow.tweaked.script.arguments.ArgItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cofh.thermalexpansion.util.managers.device.FactorizerManager.*;
import static com.snow.tweaked.Tweaked.LOG;

public class Action_TE_Factorizer
{
    public static Action_Factorizer_Add_Split ADD_SPLIT = null;
    public static Action_Factorizer_Remove_Split REMOVE_SPLIT = null;
    public static Action_Factorizer_Add_Combine ADD_COMBINE = null;
    public static Action_Factorizer_Remove_Combine REMOVE_COMBINE = null;


    //**************************************************************************************//
    //									split.add											//
    //**************************************************************************************//

    @TweakedAction("te.factorizer.split.add")
    public static class Action_Factorizer_Add_Split extends ActionAbstract
    {
        private List<TweakedFactorizerRecipe> RECIPES = new ArrayList<>();

        public Action_Factorizer_Add_Split()
        {
            ADD_SPLIT = this;
        }

        public void build(ArgItem output, ArgItem input)
        {
            RECIPES.add(new TweakedFactorizerRecipe(input.stack, output.stack));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedFactorizerRecipe recipe : RECIPES)
                {
                    FactorizerManager.addRecipe(recipe.getInput(), recipe.getOutput(), true);

                    //debug
                    LOG.debug("te.factorizer.split.add : " + ScriptHelper.stackToScript(recipe.getOutput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.factorizer.split.remove")
    public static class Action_Factorizer_Remove_Split extends ActionAbstract
    {
        private boolean CLEAR = false;
        private List<ItemStack> INPUTS = new ArrayList<>();

        public Action_Factorizer_Remove_Split()
        {
            REMOVE_SPLIT = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        public void build(ArgItem input)
        {
            INPUTS.add(input.stack);
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                FactorizerRecipe[] recipes = FactorizerManager.getRecipeList(true);
                for (FactorizerRecipe recipe : recipes)
                {
                    FactorizerManager.removeRecipe(recipe.getInput(), true);
                }

                //debug
                LOG.debug("te.factorizer.split.remove : clear");
            }
            else
            {
                if (!INPUTS.isEmpty())
                {
                    int count = 0;
                    for (ItemStack input : INPUTS)
                    {
                        FactorizerManager.removeRecipe(input, true);
                        count++;
                    }

                    //debug
                    LOG.debug("te.factorizer.split.remove : " + count + " recipes");
                }
            }

            //cleanup
            INPUTS = null;
        }
    }


    //**************************************************************************************//
    //									combine.add											//
    //**************************************************************************************//

    @TweakedAction("te.factorizer.combine.add")
    public static class Action_Factorizer_Add_Combine extends ActionAbstract
    {
        private List<TweakedFactorizerRecipe> RECIPES = new ArrayList<>();

        public Action_Factorizer_Add_Combine()
        {
            ADD_COMBINE = this;
        }

        public void build(ArgItem output, ArgItem input)
        {
            RECIPES.add(new TweakedFactorizerRecipe(input.stack, output.stack));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedFactorizerRecipe recipe : RECIPES)
                {
                    FactorizerManager.addRecipe(recipe.getInput(), recipe.getOutput(), false);

                    //debug
                    LOG.debug("te.factorizer.combine.add : " + ScriptHelper.stackToScript(recipe.getOutput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.factorizer.combine.remove")
    public static class Action_Factorizer_Remove_Combine extends ActionAbstract
    {
        private boolean CLEAR = false;
        private List<ItemStack> INPUTS = new ArrayList<>();

        public Action_Factorizer_Remove_Combine()
        {
            REMOVE_COMBINE = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        public void build(ArgItem input)
        {
            INPUTS.add(input.stack);
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                FactorizerRecipe[] recipes = FactorizerManager.getRecipeList(false);
                for (FactorizerRecipe recipe : recipes)
                {
                    FactorizerManager.removeRecipe(recipe.getInput(), false);
                }

                //debug
                LOG.debug("te.factorizer.combine.remove : clear");
            }
            else
            {
                if (!INPUTS.isEmpty())
                {
                    int count = 0;
                    for (ItemStack input : INPUTS)
                    {
                        FactorizerManager.removeRecipe(input, false);
                        count++;
                    }

                    //debug
                    LOG.debug("te.factorizer.combine.remove : " + count + " recipes");
                }
            }

            //cleanup
            INPUTS = null;
        }
    }
}
