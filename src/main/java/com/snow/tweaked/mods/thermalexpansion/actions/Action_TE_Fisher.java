package com.snow.tweaked.mods.thermalexpansion.actions;

import cofh.core.inventory.ComparableItemStack;
import cofh.thermalexpansion.util.managers.device.FisherManager;
import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Reflection;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.script.arguments.ArgAll;
import com.snow.tweaked.script.arguments.ArgInteger;
import com.snow.tweaked.script.arguments.ArgItem;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;
import static com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedBaitRecipe;
import static com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedFishRecipe;

public class Action_TE_Fisher
{
    public static Action_Fisher_Add ADD = null;
    public static Action_Fisher_Remove REMOVE = null;
    public static Action_Fisher_Add_Bait ADD_BAIT = null;
    public static Action_Fisher_Remove_Bait REMOVE_BAIT = null;


    //**************************************************************************************//
    //								        add									    		//
    //**************************************************************************************//

    @TweakedAction("te.fisher.add")
    public static class Action_Fisher_Add extends ActionAbstract
    {
        private List<TweakedFishRecipe> RECIPES = new ArrayList<>();

        public Action_Fisher_Add()
        {
            ADD = this;
        }

        public void build(ArgItem fish, ArgInteger weight)
        {
            RECIPES.add(new TweakedFishRecipe(fish.stack, weight.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedFishRecipe recipe : RECIPES)
                {
                    FisherManager.addFish(recipe.getInput(), recipe.getWeight());

                    //debug
                    LOG.debug("te.fisher.add : " + ScriptHelper.stackToScript(recipe.getInput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.fisher.remove")
    public static class Action_Fisher_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;

        public Action_Fisher_Remove()
        {
            REMOVE = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                List<ItemStack> recipes = Helper_TE_Reflection.getFishList();
                List<Integer> weights = Helper_TE_Reflection.getFishWeight();
                if (recipes == null || weights == null) return;

                recipes.clear();
                weights.clear();
                Helper_TE_Reflection.setTotalWeight(0);

                //debug
                LOG.debug("te.fisher.remove : clear");
            }
        }
    }


    //**************************************************************************************//
    //								        add									    		//
    //**************************************************************************************//

    @TweakedAction("te.fisher.bait.add")
    public static class Action_Fisher_Add_Bait extends ActionAbstract
    {
        private List<TweakedBaitRecipe> RECIPES = new ArrayList<>();

        public Action_Fisher_Add_Bait()
        {
            ADD_BAIT = this;
        }

        public void build(ArgItem bait, ArgInteger multiplier)
        {
            RECIPES.add(new TweakedBaitRecipe(bait.stack, multiplier.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedBaitRecipe recipe : RECIPES)
                {
                    FisherManager.addBait(recipe.getInput(), recipe.getMultiplier());

                    //debug
                    LOG.debug("te.fisher.bait.add : " + ScriptHelper.stackToScript(recipe.getInput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.fisher.bait.remove")
    public static class Action_Fisher_Remove_Bait extends ActionAbstract
    {
        private boolean CLEAR = false;

        public Action_Fisher_Remove_Bait()
        {
            REMOVE_BAIT = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                TObjectIntHashMap<ComparableItemStack> recipes = Helper_TE_Reflection.getBait();
                if (recipes == null) return;

                recipes.clear();

                //debug
                LOG.debug("te.fisher.bait.remove : clear");
            }
        }
    }
}
