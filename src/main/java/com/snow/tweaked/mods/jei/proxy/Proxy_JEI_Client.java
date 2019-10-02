package com.snow.tweaked.mods.jei.proxy;

import com.snow.tweaked.mods.jei.Plugin_JEI;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

import java.util.List;

@SuppressWarnings("unused")
public class Proxy_JEI_Client extends Proxy_JEI_Common
{
    @Override
    public void hideIngredients(List<ItemStack> stacks)
    {
        Plugin_JEI.itemRegistry.removeIngredientsAtRuntime(VanillaTypes.ITEM, stacks);
    }
}
