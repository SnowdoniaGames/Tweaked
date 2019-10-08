package com.snow.tweaked.mods.thermalexpansion.actions;

import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.BlockWrapper;
import cofh.core.util.ItemWrapper;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import com.google.common.collect.SetMultimap;
import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.lib.ActionAbstract;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.TweakedTapperRecipe;
import com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Reflection;
import com.snow.tweaked.script.ScriptHelper;
import com.snow.tweaked.script.arguments.ArgAll;
import com.snow.tweaked.script.arguments.ArgFluid;
import com.snow.tweaked.script.arguments.ArgInteger;
import com.snow.tweaked.script.arguments.ArgItem;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.snow.tweaked.Tweaked.LOG;
import static com.snow.tweaked.mods.thermalexpansion.helpers.Helper_TE_Recipes.*;

public class Action_TE_Tapper
{
    public static Action_Tapper_Add ADD = null;
    public static Action_Tapper_Remove REMOVE = null;
    public static Action_Tapper_Leaf_Add ADD_LEAF = null;
    public static Action_Tapper_Fertilizer_Add ADD_FERTILIZER = null;
    public static Action_Tapper_Fertilizer_Remove REMOVE_FERTILIZER = null;


    //**************************************************************************************//
    //								        add									    		//
    //**************************************************************************************//

    @TweakedAction("te.tapper.add")
    public static class Action_Tapper_Add extends ActionAbstract
    {
        private List<TweakedTapperRecipe> RECIPES = new ArrayList<>();

        public Action_Tapper_Add()
        {
            ADD = this;
        }

        public void build(ArgItem log, ArgFluid fluid)
        {
            RECIPES.add(new TweakedTapperRecipe(log.stack, fluid.stack));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedTapperRecipe recipe : RECIPES)
                {
                    TapperManager.addStandardMapping(recipe.getLog(), recipe.getFluid());

                    //debug
                    LOG.debug("te.tapper.add : " + ScriptHelper.stackToScript(recipe.getLog()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //										remove											//
    //**************************************************************************************//

    @TweakedAction("te.tapper.remove")
    public static class Action_Tapper_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;

        public Action_Tapper_Remove()
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
                Map<BlockWrapper, FluidStack> blocks = Helper_TE_Reflection.getTreeBlockList();
                Map<ItemWrapper, FluidStack> items = Helper_TE_Reflection.getTreeItemList();
                if (blocks == null || items == null) return;

                blocks.clear();
                items.clear();

                //debug
                LOG.debug("te.tapper.remove : clear");
            }
        }
    }


    //**************************************************************************************//
    //								       leaf.add								    		//
    //**************************************************************************************//

    @TweakedAction("te.tapper.leaf.add")
    public static class Action_Tapper_Leaf_Add extends ActionAbstract
    {
        private List<TweakedLeafRecipe> RECIPES = new ArrayList<>();

        public Action_Tapper_Leaf_Add()
        {
            ADD_LEAF = this;
        }

        public void build(ArgItem log, ArgItem leaf)
        {
            RECIPES.add(new TweakedLeafRecipe(log.stack, leaf.stack));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedLeafRecipe recipe : RECIPES)
                {
                    SetMultimap<BlockWrapper, BlockWrapper> recipes = Helper_TE_Reflection.getTreeLeafList();
                    if (recipes == null) return;

                    recipes.put(new BlockWrapper(((ItemBlock) recipe.getLog().getItem()).getBlock(), recipe.getLog().getMetadata()), new BlockWrapper(Block.getBlockFromItem(recipe.getLog().getItem()), recipe.getLeaf().getMetadata()));

                    //debug
                    LOG.debug("te.tapper.leaf.add : " + ScriptHelper.stackToScript(recipe.getLog()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //							       fertilizer.add							    		//
    //**************************************************************************************//

    @TweakedAction("te.tapper.fertilizer.add")
    public static class Action_Tapper_Fertilizer_Add extends ActionAbstract
    {
        private List<TweakedFertilizerRecipe> RECIPES = new ArrayList<>();

        public Action_Tapper_Fertilizer_Add()
        {
            ADD_FERTILIZER = this;
        }

        public void build(ArgItem fertilizer, ArgInteger multiplier)
        {
            RECIPES.add(new TweakedFertilizerRecipe(fertilizer.stack, multiplier.value));
        }

        @Override
        protected void run()
        {
            if (!RECIPES.isEmpty())
            {
                for (TweakedFertilizerRecipe recipe : RECIPES)
                {
                    TObjectIntHashMap<ComparableItemStack> fertilizers = Helper_TE_Reflection.getTreeFertilizerList();
                    if (fertilizers == null) return;

                    fertilizers.put(new ComparableItemStack(recipe.getInput()), recipe.getMultiplier());

                    //debug
                    LOG.debug("te.tapper.fertilizer.add : " + ScriptHelper.stackToScript(recipe.getInput()));
                }
            }

            //cleanup
            RECIPES = null;
        }
    }


    //**************************************************************************************//
    //							       fertilizer.remove							    		//
    //**************************************************************************************//

    @TweakedAction("te.tapper.fertilizer.remove")
    public static class Action_Tapper_Fertilizer_Remove extends ActionAbstract
    {
        private boolean CLEAR = false;

        public Action_Tapper_Fertilizer_Remove()
        {
            REMOVE_FERTILIZER = this;
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
                TObjectIntHashMap<ComparableItemStack> fertilizers = Helper_TE_Reflection.getTreeFertilizerList();
                if (fertilizers == null) return;

                fertilizers.clear();

                //debug
                LOG.debug("te.tapper.fertilizer.remove : clear");
            }
        }
    }
}
