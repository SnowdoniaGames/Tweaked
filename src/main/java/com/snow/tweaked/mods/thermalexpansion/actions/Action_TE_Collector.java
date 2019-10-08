package com.snow.tweaked.mods.thermalexpansion.actions;

import cofh.core.inventory.ComparableItemStack;
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
import static com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedCatalystRecipe;

public class Action_TE_Collector
{
    public static Action_Collector_Add ADD = null;
    public static Action_Collector_Remove REMOVE = null;


    //**************************************************************************************//
    //								        add									    		//
    //**************************************************************************************//

    @TweakedAction("te.collector.add")
    public static class Action_Collector_Add extends ActionAbstract
    {
        private List<TweakedCatalystRecipe> RECIPES = new ArrayList<>();

        public Action_Collector_Add()
        {
            ADD = this;
        }

        public void build(ArgItem catalyst, ArgInteger xp, ArgInteger factor)
        {
            RECIPES.add(new TweakedCatalystRecipe(catalyst.stack, xp.value, factor.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedCatalystRecipe recipe : RECIPES)
                {
                    TObjectIntHashMap<ComparableItemStack> xpMap = Helper_TE_Reflection.getCatalystXp();
                    TObjectIntHashMap<ComparableItemStack> factorMap = Helper_TE_Reflection.getCatalystFactor();
                    if (xpMap == null || factorMap == null) return;

                    xpMap.put(new ComparableItemStack(recipe.getInput()), recipe.getXp());
                    factorMap.put(new ComparableItemStack(recipe.getInput()), recipe.getFactor());

                    //debug
                    LOG.debug("te.collector.add : " + ScriptHelper.stackToScript(recipe.getInput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.collector.remove")
    public static class Action_Collector_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;
        private List<ItemStack> RECIPES = new ArrayList<>();

        public Action_Collector_Remove()
        {
            REMOVE = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        public void build(ArgItem catalyst)
        {
            RECIPES.add(catalyst.stack);
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                TObjectIntHashMap<ComparableItemStack> xpMap = Helper_TE_Reflection.getCatalystXp();
                TObjectIntHashMap<ComparableItemStack> factorMap = Helper_TE_Reflection.getCatalystFactor();
                if (xpMap == null || factorMap == null) return;

                xpMap.clear();
                factorMap.clear();

                //debug
                LOG.debug("te.collector.remove : clear");
            }
            else
            {
                if (!RECIPES.isEmpty())
                {
                    TObjectIntHashMap<ComparableItemStack> xpMap = Helper_TE_Reflection.getCatalystXp();
                    TObjectIntHashMap<ComparableItemStack> factorMap = Helper_TE_Reflection.getCatalystFactor();
                    if (xpMap == null || factorMap == null) return;

                    int count = 0;
                    for (ItemStack recipe : RECIPES)
                    {
                        xpMap.remove(new ComparableItemStack(recipe));
                        factorMap.remove(new ComparableItemStack(recipe));
                        count++;
                    }

                    //debug
                    LOG.debug("te.collector.remove : " + count + " recipes");
                }
            }

            //cleanup
            RECIPES = null;
        }
    }
}
