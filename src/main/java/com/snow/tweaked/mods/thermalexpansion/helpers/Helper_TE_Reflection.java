package com.snow.tweaked.mods.thermalexpansion.helpers;

import cofh.core.inventory.ComparableItemStack;
import cofh.core.util.BlockWrapper;
import cofh.core.util.ItemWrapper;
import cofh.thermalexpansion.util.managers.device.DiffuserManager;
import cofh.thermalexpansion.util.managers.device.FisherManager;
import cofh.thermalexpansion.util.managers.device.TapperManager;
import cofh.thermalexpansion.util.managers.device.XpCollectorManager;
import com.google.common.collect.SetMultimap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.snow.tweaked.Tweaked.LOG;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.findField;

@SuppressWarnings({"unchecked", "deprecation"})
public class Helper_TE_Reflection
{
    private static Field FISHER_FISH_LIST = null;
    private static Field FISHER_FISH_WEIGHT = null;
    private static Field FISHER_TOTAL_WEIGHT = null;
    private static Field FISHER_BAIT = null;

    private static Field TAPPER_BLOCK = null;
    private static Field TAPPER_ITEM = null;
    private static Field TAPPER_LEAF = null;
    private static Field TAPPER_FERTILIZER = null;

    private static Field DIFFUSER_AMP = null;
    private static Field DIFFUSER_DUR = null;

    private static Field COLLECTOR_XP = null;
    private static Field COLLECTOR_FACTOR = null;

    public static List<ItemStack> getFishList()
    {
        try
        {
            if (FISHER_FISH_LIST == null)
            {
                FISHER_FISH_LIST = findField(FisherManager.class, "fishList");
            }

            Object ret = FISHER_FISH_LIST.get(null);
            if (ret instanceof List)
            {
                return  (List<ItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access FISH_LIST");
            return null;
        }
    }

    public static List<Integer> getFishWeight()
    {
        try
        {
            if (FISHER_FISH_WEIGHT == null)
            {
                FISHER_FISH_WEIGHT = findField(FisherManager.class, "weightList");
            }

            Object ret = FISHER_FISH_WEIGHT.get(null);
            if (ret instanceof List)
            {
                return  (List<Integer>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access FISH_WEIGHT");
            return null;
        }
    }

    public static void setTotalWeight(int value)
    {
        try
        {
            if (FISHER_TOTAL_WEIGHT == null)
            {
                FISHER_TOTAL_WEIGHT = findField(FisherManager.class, "totalWeight");
            }

            FISHER_TOTAL_WEIGHT.set(null, value);
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access FISH_TOTAL_WEIGHT");
        }
    }

    public static TObjectIntHashMap<ComparableItemStack> getBait()
    {
        try
        {
            if (FISHER_BAIT == null)
            {
                FISHER_BAIT = findField(FisherManager.class, "baitMap");
            }

            Object ret = FISHER_BAIT.get(null);
            if (ret instanceof TObjectIntHashMap)
            {
                return  (TObjectIntHashMap<ComparableItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access FISH_BAIT");
            return null;
        }
    }

    public static Map<BlockWrapper, FluidStack> getTreeBlockList()
    {
        try
        {
            if (TAPPER_BLOCK == null)
            {
                TAPPER_BLOCK = findField(TapperManager.class, "blockMap");
            }

            Object ret = TAPPER_BLOCK.get(null);
            if (ret instanceof Map)
            {
                return  (Map<BlockWrapper, FluidStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access TREE_BLOCKS");
            return null;
        }
    }

    public static Map<ItemWrapper, FluidStack> getTreeItemList()
    {
        try
        {
            if (TAPPER_ITEM == null)
            {
                TAPPER_ITEM = findField(TapperManager.class, "itemMap");
            }

            Object ret = TAPPER_ITEM.get(null);
            if (ret instanceof Map)
            {
                return  (Map<ItemWrapper, FluidStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access TREE_ITEMS");
            return null;
        }
    }

    public static SetMultimap<BlockWrapper, BlockWrapper> getTreeLeafList()
    {
        try
        {
            if (TAPPER_LEAF == null)
            {
                TAPPER_LEAF = findField(TapperManager.class, "leafMap");
            }

            Object ret = TAPPER_LEAF.get(null);
            if (ret instanceof List)
            {
                return  (SetMultimap<BlockWrapper, BlockWrapper>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access TREE_LEAVES");
            return null;
        }
    }

    public static TObjectIntHashMap<ComparableItemStack> getTreeFertilizerList()
    {
        try
        {
            if (TAPPER_FERTILIZER == null)
            {
                TAPPER_FERTILIZER = findField(TapperManager.class, "fertilizerMap");
            }

            Object ret = TAPPER_FERTILIZER.get(null);
            if (ret instanceof TObjectIntHashMap)
            {
                return  (TObjectIntHashMap<ComparableItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access TREE_FERTILIZER");
            return null;
        }
    }

    public static TObjectIntHashMap<ComparableItemStack> getDiffuserAmp()
    {
        try
        {
            if (DIFFUSER_AMP == null)
            {
                DIFFUSER_AMP = findField(DiffuserManager.class, "reagentAmpMap");
            }

            Object ret = DIFFUSER_AMP.get(null);
            if (ret instanceof TObjectIntHashMap)
            {
                return  (TObjectIntHashMap<ComparableItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access DIFFUSER_AMP");
            return null;
        }
    }

    public static TObjectIntHashMap<ComparableItemStack> getDiffuserDur()
    {
        try
        {
            if (DIFFUSER_DUR == null)
            {
                DIFFUSER_DUR = findField(DiffuserManager.class, "reagentDurMap");
            }

            Object ret = DIFFUSER_DUR.get(null);
            if (ret instanceof TObjectIntHashMap)
            {
                return  (TObjectIntHashMap<ComparableItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access DIFFUSER_DUR");
            return null;
        }
    }

    public static TObjectIntHashMap<ComparableItemStack> getCatalystXp()
    {
        try
        {
            if (COLLECTOR_XP == null)
            {
                COLLECTOR_XP = findField(XpCollectorManager.class, "catalystMap");
            }

            Object ret = COLLECTOR_XP.get(null);
            if (ret instanceof TObjectIntHashMap)
            {
                return  (TObjectIntHashMap<ComparableItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access CATALYST_XP");
            return null;
        }
    }

    public static TObjectIntHashMap<ComparableItemStack> getCatalystFactor()
    {
        try
        {
            if (COLLECTOR_FACTOR == null)
            {
                COLLECTOR_FACTOR = findField(XpCollectorManager.class, "catalystFactorMap");
            }

            Object ret = COLLECTOR_FACTOR.get(null);
            if (ret instanceof TObjectIntHashMap)
            {
                return  (TObjectIntHashMap<ComparableItemStack>) ret;
            }
            return null;
        }
        catch (ReflectionHelper.UnableToFindFieldException | IllegalAccessException e)
        {
            LOG.error("Unable to access CATALYST_FACTOR");
            return null;
        }
    }
}
