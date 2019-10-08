package com.snow.tweaked.mods.vanilla.actions;

import com.google.common.collect.Maps;
import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Fuel;
import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Furnace.TweakedFurnaceRecipe;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.script.arguments.ArgAll;
import com.snow.tweaked.script.arguments.ArgFloat;
import com.snow.tweaked.script.arguments.ArgInteger;
import com.snow.tweaked.script.arguments.ArgItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.snow.tweaked.Tweaked.LOG;

public class Action_Vanilla_Furnace
{
    public static Action_Furnace_Add ADD = null;
    public static Action_Furnace_Remove REMOVE = null;
    public static Action_Furnace_AddFuel ADD_FUEL = null;
    public static Action_Furnace_RemoveFuel REMOVE_FUEL = null;


    //**************************************************************************************//
    //										add												//
    //**************************************************************************************//

    @TweakedAction("furnace.add")
    public static class Action_Furnace_Add extends ActionAbstract
    {
        private List<TweakedFurnaceRecipe> RECIPES = new ArrayList<>();

        public Action_Furnace_Add()
        {
            ADD = this;
        }

        public void build(ArgItem output, ArgItem input)
        {
            RECIPES.add(new TweakedFurnaceRecipe(output.stack, input.stack, 0.0f));
        }

        public void build(ArgItem output, ArgItem input, ArgFloat experience)
        {
            RECIPES.add(new TweakedFurnaceRecipe(output.stack, input.stack, experience.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedFurnaceRecipe recipe : RECIPES)
                {
                    FurnaceRecipes.instance().addSmeltingRecipe(recipe.getInput(), recipe.getOutput(), recipe.getExperience());

                    //debug
                    LOG.debug("furnace.add : " + ScriptHelper.stackToScript(recipe.getOutput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("furnace.remove")
    public static class Action_Furnace_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;
        private List<ItemStack> OUTPUTS = new ArrayList<>();

        public Action_Furnace_Remove()
        {
            REMOVE = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        public void build(ArgItem output)
        {
            OUTPUTS.add(output.stack);
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                FurnaceRecipes.instance().getSmeltingList().clear();

                //debug
                LOG.debug("furnace.remove : clear");
            }
            else
            {
                if (!OUTPUTS.isEmpty())
                {
                    Map<ItemStack, ItemStack> newRecipes = Maps.newHashMap();
                    for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
                    {
                        for (ItemStack stack : OUTPUTS)
                        {
                            if (!ScriptHelper.areScriptItemsEqual(stack, entry.getValue()))
                            {
                                newRecipes.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }

                    //debug
                    LOG.debug("furnace.remove : " + (FurnaceRecipes.instance().getSmeltingList().size() - newRecipes.size()) + " recipes");

                    FurnaceRecipes.instance().getSmeltingList().clear();
                    for (Map.Entry<ItemStack, ItemStack> entry : newRecipes.entrySet())
                    {
                        FurnaceRecipes.instance().getSmeltingList().put(entry.getKey(), entry.getValue());
                    }
                }
            }

            //cleanup
            OUTPUTS = null;
        }
    }


    //**************************************************************************************//
    //										add fuel										//
    //**************************************************************************************//

    @TweakedAction(value="furnace.fuel.add")
    public static class Action_Furnace_AddFuel extends ActionAbstract
    {
        public Action_Furnace_AddFuel()
        {
            ADD_FUEL = this;
        }

        public void build(ArgItem fuel, ArgInteger burnTime)
        {
            Helper_Vanilla_Fuel.addAddFuel(fuel.stack, burnTime.value);
        }

        @Override
        protected void run()
        {
            //handled by event
        }
    }


    //**************************************************************************************//
    //										remove fuel										//
    //**************************************************************************************//

    @TweakedAction(value="furnace.fuel.remove")
    public static class Action_Furnace_RemoveFuel extends ActionAbstract
    {
        public Action_Furnace_RemoveFuel()
        {
            REMOVE_FUEL = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            Helper_Vanilla_Fuel.setClear(true);
        }

        public void build(ArgItem fuel)
        {
            Helper_Vanilla_Fuel.addRemoveFuel(fuel.stack);
        }

        @Override
        protected void run()
        {
            //handled by event
        }
    }

}
