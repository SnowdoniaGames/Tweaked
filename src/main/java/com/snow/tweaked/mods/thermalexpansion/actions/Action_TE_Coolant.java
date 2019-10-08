package com.snow.tweaked.mods.thermalexpansion.actions;

import cofh.thermalexpansion.util.managers.device.CoolantManager;
import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.script.arguments.ArgAll;
import com.snow.tweaked.script.arguments.ArgFluid;
import com.snow.tweaked.script.arguments.ArgInteger;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.snow.tweaked.Tweaked.LOG;
import static com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedCoolantRecipe;

public class Action_TE_Coolant
{
    public static Action_Coolant_Add ADD = null;
    public static Action_Coolant_Remove REMOVE = null;


    //**************************************************************************************//
    //									        add											//
    //**************************************************************************************//

    @TweakedAction("te.coolant.add")
    public static class Action_Coolant_Add extends ActionAbstract
    {
        private List<TweakedCoolantRecipe> RECIPES = new ArrayList<>();

        public Action_Coolant_Add()
        {
            ADD = this;
        }

        public void build(ArgFluid coolant, ArgInteger rf, ArgInteger factor)
        {
            RECIPES.add(new TweakedCoolantRecipe(coolant.stack, rf.value, factor.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedCoolantRecipe recipe : RECIPES)
                {
                    CoolantManager.addCoolant(recipe.getFluid().getFluid().getName(), recipe.getRf(), recipe.getFactor());

                    //debug
                    LOG.debug("te.coolant.add : " + ScriptHelper.fluidToScript(recipe.getFluid()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.coolant.remove")
    public static class Action_Coolant_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;
        private List<FluidStack> FLUIDS = new ArrayList<>();

        public Action_Coolant_Remove()
        {
            REMOVE = this;
        }

        @SuppressWarnings("unused")
        public void build(ArgAll all)
        {
            CLEAR = true;
        }

        public void build(ArgFluid fluid)
        {
            FLUIDS.add(fluid.stack);
        }

        @Override
        protected void run()
        {
            if (CLEAR)
            {
                Set<String> coolants = CoolantManager.getCoolantFluids();
                for (String coolant : coolants)
                {
                    CoolantManager.removeCoolant(coolant);
                }

                //debug
                LOG.debug("te.coolant.remove : clear");
            }
            else
            {
                if (!FLUIDS.isEmpty())
                {
                    int count = 0;
                    for (FluidStack fluid : FLUIDS)
                    {
                        CoolantManager.removeCoolant(fluid.getFluid().getName());
                        count++;
                    }

                    //debug
                    LOG.debug("te.coolant.remove : " + count + " recipes");
                }
            }

            //cleanup
            FLUIDS = null;
        }
    }
}
