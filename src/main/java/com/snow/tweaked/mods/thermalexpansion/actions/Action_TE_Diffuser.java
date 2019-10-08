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
import static com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedReagentRecipe;

public class Action_TE_Diffuser
{
    public static Action_Diffuser_Add ADD = null;
    public static Action_Diffuser_Remove REMOVE = null;


    //**************************************************************************************//
    //								        add									    		//
    //**************************************************************************************//

    @TweakedAction("te.diffuser.add")
    public static class Action_Diffuser_Add extends ActionAbstract
    {
        private List<TweakedReagentRecipe> RECIPES = new ArrayList<>();

        public Action_Diffuser_Add()
        {
            ADD = this;
        }

        public void build(ArgItem reagent, ArgInteger amp, ArgInteger dur)
        {
            RECIPES.add(new TweakedReagentRecipe(reagent.stack, amp.value, dur.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedReagentRecipe recipe : RECIPES)
                {
                    TObjectIntHashMap<ComparableItemStack> reagentAmpMap = Helper_TE_Reflection.getDiffuserAmp();
                    TObjectIntHashMap<ComparableItemStack> reagentDurMap = Helper_TE_Reflection.getDiffuserDur();
                    if (reagentAmpMap == null || reagentDurMap == null) return;

                    reagentAmpMap.put(new ComparableItemStack(recipe.getInput()), recipe.getAmp());
                    reagentDurMap.put(new ComparableItemStack(recipe.getInput()), recipe.getDur());

                    //debug
                    LOG.debug("te.diffuser.add : " + ScriptHelper.stackToScript(recipe.getInput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.diffuser.remove")
    public static class Action_Diffuser_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;
        private List<ItemStack> RECIPES = new ArrayList<>();

        public Action_Diffuser_Remove()
        {
            REMOVE = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        public void build(ArgItem reagent)
        {
            RECIPES.add(reagent.stack);
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                TObjectIntHashMap<ComparableItemStack> reagentAmpMap = Helper_TE_Reflection.getDiffuserAmp();
                TObjectIntHashMap<ComparableItemStack> reagentDurMap = Helper_TE_Reflection.getDiffuserDur();
                if (reagentAmpMap == null || reagentDurMap == null) return;

                reagentAmpMap.clear();
                reagentDurMap.clear();

                //debug
                LOG.debug("te.diffuser.remove : clear");
            }
            else
            {
                if (!RECIPES.isEmpty())
                {
                    TObjectIntHashMap<ComparableItemStack> reagentAmpMap = Helper_TE_Reflection.getDiffuserAmp();
                    TObjectIntHashMap<ComparableItemStack> reagentDurMap = Helper_TE_Reflection.getDiffuserDur();
                    if (reagentAmpMap == null || reagentDurMap == null) return;

                    int count = 0;
                    for (ItemStack recipe : RECIPES)
                    {
                        reagentAmpMap.remove(new ComparableItemStack(recipe));
                        reagentDurMap.remove(new ComparableItemStack(recipe));
                        count++;
                    }

                    //debug
                    LOG.debug("te.diffuser.remove : " + count + " recipes");
                }
            }

            //cleanup
            RECIPES = null;
        }
    }
}
