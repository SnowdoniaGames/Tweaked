package com.snow.tweaked.mods.vanilla;

import com.snow.tweaked.Tweaked;
import com.snow.tweaked.annotation.TweakedMod;
import com.snow.tweaked.api.ITweakedMod;
import com.snow.tweaked.controllers.TweakedConfig;
import com.snow.tweaked.mods.vanilla.actions.Action_Vanilla_Recipes;
import com.snow.tweaked.mods.vanilla.helpers.Helper_Vanilla_Recipes;
import com.snow.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Common;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import static net.minecraftforge.fml.common.Mod.*;

@SuppressWarnings("WeakerAccess")
@Mod(modid = Tweaked_Vanilla.MODID, name = Tweaked_Vanilla.NAME, version = Tweaked.VERSION, dependencies=Tweaked_Vanilla.DEPENDENCIES)
public class Tweaked_Vanilla
{
    public static final String MODID = "tweaked_vanilla";
    public static final String NAME = "Tweaked_Vanilla";
    public static final String DEPENDENCIES = "required-after:tweaked;";

    //flags
    public static boolean LOADED = false;
    public static boolean ITEMS_REGISTERED = false;
    public static boolean RECIPEBOOK_FIXED = false;

    @SidedProxy(clientSide = "com.snow.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Client", serverSide = "com.snow.tweaked.mods.vanilla.proxy.Proxy_Vanilla_Common")
    public static Proxy_Vanilla_Common proxy;

    //storage
    public static IForgeRegistryModifiable<IRecipe> RECIPE_REGISTRY = null;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        LOADED = TweakedConfig.modsEnableJEI;

        //register events
        if (LOADED)
        {
            proxy.registerEvents();
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (LOADED)
        {
            //recipes
            Action_Vanilla_Recipes.REMOVE.apply();
            Action_Vanilla_Recipes.SHAPED.apply();
            Action_Vanilla_Recipes.SHAPELESS.apply();

            //create dummy recipes
            Helper_Vanilla_Recipes.createDummyRecipes();
        }
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        //register late events
        if (LOADED)
        {
            proxy.registerEventsLate();
        }
    }
}