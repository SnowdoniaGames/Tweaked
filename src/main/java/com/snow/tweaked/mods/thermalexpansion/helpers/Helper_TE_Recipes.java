package com.snow.tweaked.mods.thermalexpansion.helpers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class Helper_TE_Recipes
{
    public static class TweakedFishRecipe
    {
        final ItemStack input;
        final int weight;

        public TweakedFishRecipe(ItemStack input, int weight)
        {
            this.input = input;
            this.weight = weight;
        }

        public ItemStack getInput()
        {
            return input;
        }

        public int getWeight()
        {
            return weight;
        }
    }

    public static class TweakedBaitRecipe
    {
        final ItemStack input;
        final int multiplier;

        public TweakedBaitRecipe(ItemStack input, int multiplier)
        {
            this.input = input;
            this.multiplier = multiplier;
        }

        public ItemStack getInput()
        {
            return input;
        }

        public int getMultiplier()
        {
            return multiplier;
        }
    }

    public static class TweakedCoolantRecipe
    {
        final FluidStack fluid;
        final int rf;
        final int factor;

        public TweakedCoolantRecipe(FluidStack fluid, int rf, int factor)
        {
            this.fluid = fluid;
            this.rf = rf;
            this.factor = factor;
        }

        public FluidStack getFluid()
        {
            return fluid;
        }

        public int getRf()
        {
            return rf;
        }

        public int getFactor()
        {
            return factor;
        }
    }

    public static class TweakedFactorizerRecipe
    {
        final ItemStack input;
        final ItemStack output;

        public TweakedFactorizerRecipe(ItemStack input, ItemStack output)
        {
            this.input = input;
            this.output = output;
        }

        public ItemStack getInput() {
            return this.input;
        }

        public ItemStack getOutput() {
            return this.output;
        }
    }

    public static class TweakedTapperRecipe
    {
        final ItemStack log;
        final FluidStack fluid;

        public TweakedTapperRecipe(ItemStack log, FluidStack fluid)
        {
            this.log = log;
            this.fluid = fluid;
        }

        public ItemStack getLog()
        {
            return log;
        }

        public FluidStack getFluid()
        {
            return fluid;
        }
    }

    public static class TweakedLeafRecipe
    {
        final ItemStack log;
        final ItemStack leaf;

        public TweakedLeafRecipe(ItemStack log, ItemStack leaf) {
            this.log = log;
            this.leaf = leaf;
        }

        public ItemStack getLog() {
            return log;
        }

        public ItemStack getLeaf() {
            return leaf;
        }
    }

    public static class TweakedFertilizerRecipe
    {
        final ItemStack input;
        final int multiplier;

        public TweakedFertilizerRecipe(ItemStack input, int multiplier)
        {
            this.input = input;
            this.multiplier = multiplier;
        }

        public ItemStack getInput()
        {
            return input;
        }

        public int getMultiplier()
        {
            return multiplier;
        }
    }

    public static class TweakedReagentRecipe
    {
        final ItemStack input;
        final int amp;
        final int dur;

        public TweakedReagentRecipe(ItemStack input, int amp, int dur)
        {
            this.input = input;
            this.amp = amp;
            this.dur = dur;
        }

        public ItemStack getInput()
        {
            return input;
        }

        public int getAmp()
        {
            return amp;
        }

        public int getDur()
        {
            return dur;
        }
    }

    public static class TweakedCatalystRecipe
    {
        final ItemStack input;
        final int xp;
        final int factor;

        public TweakedCatalystRecipe(ItemStack input, int xp, int factor)
        {
            this.input = input;
            this.xp = xp;
            this.factor = factor;
        }

        public ItemStack getInput()
        {
            return input;
        }

        public int getXp()
        {
            return xp;
        }

        public int getFactor()
        {
            return factor;
        }
    }
}
