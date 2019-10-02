package com.snow.tweaked.mods.vanilla.helpers;

import com.snow.tweaked.mods.vanilla.Tweaked_Vanilla;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.snow.tweaked.Tweaked.LOG;

public class Helper_Vanilla_Recipes
{
    public static final List<ResourceLocation> REMOVED_RECIPES = new ArrayList<>();

    public static ModContainer TWEAKED_CONTAINER;
    private static ModContainer LAST_CONTAINER;
    private static Map<String, ModContainer> MOD_CONTAINERS;

    public static void setTweakedContainer()
    {
        LAST_CONTAINER = Loader.instance().activeModContainer();
        Loader.instance().setActiveModContainer(TWEAKED_CONTAINER);
    }

    public static void setModContainer(String mod)
    {
        //build map if we haven't already
        if (MOD_CONTAINERS == null)
        {
            MOD_CONTAINERS = new HashMap<>();
            for (ModContainer modContainer : Loader.instance().getModList())
            {
                MOD_CONTAINERS.put(modContainer.getModId(), modContainer);
            }
        }

        if (MOD_CONTAINERS.containsKey(mod))
        {
            LAST_CONTAINER = Loader.instance().activeModContainer();
            Loader.instance().setActiveModContainer(MOD_CONTAINERS.get(mod));
        }
    }

    public static void restoreContainer()
    {
        Loader.instance().setActiveModContainer(LAST_CONTAINER);
    }

    public static void createDummyRecipes()
    {
        if (REMOVED_RECIPES.isEmpty()) return;

        for (ResourceLocation recipe : REMOVED_RECIPES)
        {
            if (!Tweaked_Vanilla.RECIPE_REGISTRY.containsKey(recipe))
            {
                //swap to correct container
                setModContainer(recipe.getResourceDomain());

                //recipe hasn't been replaced, we need to add a dummy
                IRecipe dummy = new DummyRecipe();
                dummy.setRegistryName(recipe);

                Tweaked_Vanilla.RECIPE_REGISTRY.register(dummy);

                //restore our container
                restoreContainer();
            }
        }
    }

    /**
     * A no-op implementation of {@link IRecipe} designed to override vanilla recipes.
     * Credit to Choonster
     */
    public static class DummyRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
    {

        @Override
        public boolean matches(final InventoryCrafting inv, final World worldIn)
        {
            return false;
        }

        @Override
        public ItemStack getCraftingResult(final InventoryCrafting inv)
        {
            return ItemStack.EMPTY;
        }

        @Override
        public boolean canFit(final int width, final int height)
        {
            return false;
        }

        @Override
        public ItemStack getRecipeOutput()
        {
            return ItemStack.EMPTY;
        }
    }

    public static Object[] createShapedArray(Object A, Object B, Object C, Object D, Object E, Object F, Object G, Object H, Object I)
    {
        //create list to help build
        List<Object> buildList = new ArrayList<>();

        //create shape strings
        String rowA = "";
        String rowB = "";
        String rowC = "";

        if (A != null) rowA += "A";
        else rowA += " ";

        if (B != null) rowA += "B";
        else rowA += " ";

        if (C != null) rowA += "C";
        else rowA += " ";

        if (D != null) rowB += "D";
        else rowB += " ";

        if (E != null) rowB += "E";
        else rowB += " ";

        if (F != null) rowB += "F";
        else rowB += " ";

        if (G != null) rowC += "G";
        else rowC += " ";

        if (H != null) rowC += "H";
        else rowC += " ";

        if (I != null) rowC += "I";
        else rowC += " ";

        //trim cols
        while (rowA.endsWith(" ") && rowB.endsWith(" ") && rowC.endsWith(" "))
        {
            rowA = rowA.substring(0, rowA.length() - 1);
            rowB = rowB.substring(0, rowB.length() - 1);
            rowC = rowC.substring(0, rowC.length() - 1);
        }

        //trim rows
        if (rowC.trim().isEmpty())
        {
            rowC = null;

            if (rowB.trim().isEmpty())
            {
                rowB = null;

                if (rowA.trim().isEmpty())
                {
                    LOG.debug("Warning : Recipe with no items");
                    return null;
                }
            }
        }

        //output
        buildList.add(rowA);

        if (rowB != null)
        {
            buildList.add(rowB);
        }

        if (rowC != null)
        {
            buildList.add(rowC);
        }

        if (A != null)
        {
            buildList.add('A');
            buildList.add(A);
        }

        if (B != null)
        {
            buildList.add('B');
            buildList.add(B);
        }

        if (C != null)
        {
            buildList.add('C');
            buildList.add(C);
        }

        if (D != null)
        {
            buildList.add('D');
            buildList.add(D);
        }

        if (E != null)
        {
            buildList.add('E');
            buildList.add(E);
        }

        if (F != null)
        {
            buildList.add('F');
            buildList.add(F);
        }

        if (G != null)
        {
            buildList.add('G');
            buildList.add(G);
        }

        if (H != null)
        {
            buildList.add('H');
            buildList.add(H);
        }

        if (I != null)
        {
            buildList.add('I');
            buildList.add(I);
        }

        return buildList.toArray();
    }

    public static Object[] createShapelessArray(Object A, Object B, Object C, Object D, Object E, Object F, Object G, Object H, Object I)
    {
        //create list to help build
        List<Object> buildList = new ArrayList<>();

        if (A != null)
        {
            buildList.add(A);
        }

        if (B != null)
        {
            buildList.add(B);
        }

        if (C != null)
        {
            buildList.add(C);
        }

        if (D != null)
        {
            buildList.add(D);
        }

        if (E != null)
        {
            buildList.add(E);
        }

        if (F != null)
        {
            buildList.add(F);
        }

        if (G != null)
        {
            buildList.add(G);
        }

        if (H != null)
        {
            buildList.add(H);
        }

        if (I != null)
        {
            buildList.add(I);
        }

        return buildList.toArray();
    }
}
