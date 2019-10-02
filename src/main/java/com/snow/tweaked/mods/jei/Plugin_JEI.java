package com.snow.tweaked.mods.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientRegistry;

@mezz.jei.api.JEIPlugin
public class Plugin_JEI implements IModPlugin
{
    public static IIngredientRegistry itemRegistry;

    @Override
    public void register(IModRegistry registry)
    {
        Plugin_JEI.itemRegistry = registry.getIngredientRegistry();
    }
}
