package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IIngredient;
import com.snow.tweaked.script.ScriptHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class ArgItem implements IArgument, IIngredient
{
    public String itemName;
    public ItemStack stack;

    //modifiers
    private Integer meta = null;
    private Integer count = null;
    private NBTTagCompound nbt = null;
    private List<Tuple<Enchantment, Integer>> enchants = null;

    public ArgItem() { }

    public ArgItem(String s)
    {
        this.itemName = s;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type != null && type.equals("item") && arg != null;
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgItem(s);
    }

    @Override
    public IArgument addModifier(String name, String val)
    {
        switch (name)
        {
            case "meta":
            case "m":
                try
                {
                    this.meta = Integer.parseInt(val);
                    return this;
                }
                catch (NumberFormatException ignored)
                {
                }
                break;
            case "count":
            case "c":
                try
                {
                    this.count = Integer.parseInt(val);
                    return this;
                }
                catch (NumberFormatException ignored)
                {
                }
                break;
            case "nbt":
            case "n":
                try
                {
                    this.nbt = JsonToNBT.getTagFromJson(val);
                    return this;
                }
                catch (NBTException e)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.lineCount, "Warning : Failed to parse NBT \"" + val + "\"");
                }
                break;
            case "enchant":
            case "e":
                return parseEnchantments(val) ? this : null;
        }
        return null;
    }

    @Override
    public boolean generate()
    {
        //basic item or block, returns a RecipeItem or RecipeBlock
        Block block = Block.REGISTRY.getObject(new ResourceLocation(itemName));
        if (block != Blocks.AIR)
        {
            stack = new ItemStack(block);
            if (meta != null) stack.setItemDamage(meta);
            if (count != null) stack.setCount(count);
            if (nbt != null) stack.setTagCompound(nbt);
            if (enchants != null) enchants.forEach(ench -> stack.addEnchantment(ench.getFirst(), ench.getSecond()));

            return true;
        }

        Item item = Item.REGISTRY.getObject(new ResourceLocation(itemName));
        if (item != null)
        {
            stack = new ItemStack(item);
            if (meta != null) stack.setItemDamage(meta);
            if (count != null) stack.setCount(count);
            if (nbt != null) stack.setTagCompound(nbt);
            if (enchants != null) enchants.forEach(ench -> stack.addEnchantment(ench.getFirst(), ench.getSecond()));

            return true;
        }

        return false;
    }

    public boolean matches(ItemStack match)
    {
        if (stack.hasTagCompound())
        {
            return ItemStack.areItemStacksEqual(stack, match);
        }
        else
        {
            return ItemStack.areItemsEqual(stack, match);
        }
    }

    @Override
    public String print()
    {
        return itemName;
    }

    @Override
    public String toString()
    {
        return print();
    }


    @Override
    public Object getItem()
    {
        return stack;
    }

    private boolean parseEnchantments(String in)
    {
        if (enchants == null) enchants = new ArrayList<>();

        //split the input
        String[] args = in.split(":");

        if (args.length == 2)
        {
            try
            {
                String enchantName = args[0];
                int level = Integer.parseInt(args[1]);

                Enchantment enchant = Enchantment.getEnchantmentByLocation(enchantName);
                if (enchant != null)
                {
                    //check level is valid
                    if (level < enchant.getMinLevel() || level > enchant.getMaxLevel())
                    {
                        ScriptHelper.throwScriptError(ScriptHelper.lineCount, "Enchantment Modifier \"" + in + "\" has a too low/high specified level");
                        return false;
                    }

                    enchants.add(new Tuple<>(enchant, level));
                }
            }
            catch (NumberFormatException e)
            {
                ScriptHelper.throwScriptError(ScriptHelper.lineCount, "Enchantment Modifier \"" + in + "\" must have an integer as the second argument");
                return false;
            }
        }
        else if (args.length == 3)
        {
            try
            {
                String enchantName = args[0] + ":" + args[1];
                int level = Integer.parseInt(args[2]);

                Enchantment enchant = Enchantment.getEnchantmentByLocation(enchantName);
                if (enchant != null)
                {
                    //check level is valid
                    if (level < enchant.getMinLevel() || level > enchant.getMaxLevel())
                    {
                        ScriptHelper.throwScriptError(ScriptHelper.lineCount, "Enchantment Modifier \"" + in + "\" has a too low/high specified level");
                        return false;
                    }

                    enchants.add(new Tuple<>(enchant, level));
                }
            }
            catch (NumberFormatException e)
            {
                ScriptHelper.throwScriptError(ScriptHelper.lineCount, "Enchantment Modifier \"" + in + "\" must have an integer as the second argument");
                return false;
            }
        }
        else
        {
            ScriptHelper.throwScriptError(ScriptHelper.lineCount, "Enchantment Modifier \"" + in + "\" can only have 2 arguments");
            return false;
        }

        return true;
    }
}
