package com.snow.tweaked.mods.vanilla.helpers;

import net.minecraft.item.ItemStack;

public class Helper_Vanilla_Furnace
{
    public static class TweakedFurnaceRecipe
    {
        private ItemStack output;
        private ItemStack input;
        private float experience;

        public TweakedFurnaceRecipe(ItemStack output, ItemStack input, float experience)
        {
            this.output = output;
            this.input = input;
            this.experience = experience;
        }

        public ItemStack getOutput()
        {
            return output;
        }

        public ItemStack getInput()
        {
            return input;
        }

        public float getExperience()
        {
            return experience;
        }
    }

}
