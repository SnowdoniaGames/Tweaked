package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.script.ScriptHelper;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ArgFluid implements IArgument
{
    private String fluidName;
    public FluidStack stack;

    //modifiers
    private Integer count = null;
    private NBTTagCompound nbt = null;

    public ArgFluid() { }

    public ArgFluid(String s)
    {
        this.fluidName = s;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type != null && type.equals("fluid") && arg != null;
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgFluid(s);
    }

    @Override
    public IArgument addModifier(String name, String val)
    {
        switch (name)
        {
            case "count":
            case "c":
                try
                {
                    this.count = Integer.parseInt(val);
                    return this;
                }
                catch (NumberFormatException ignored) { }
            case "nbt":
            case "n":
                try
                {
                    this.nbt = JsonToNBT.getTagFromJson(val);
                    return this;
                }
                catch (NBTException e)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Warning : Failed to parse NBT \"" + val + "\"");
                }
                break;
        }
        return null;
    }

    @Override
    public boolean generate()
    {
        Fluid fluid = FluidRegistry.getFluid(fluidName);
        if (fluid == null) return false;

        stack = new FluidStack(fluid, count == null ? 1000 : count);
        if (nbt != null) stack.tag = nbt;

        return true;
    }

    @Override
    public String print()
    {
        return fluidName;
    }

    @Override
    public String toString()
    {
        return print();
    }
}
