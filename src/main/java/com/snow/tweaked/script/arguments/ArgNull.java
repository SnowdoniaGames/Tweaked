package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IIngredient;

public class ArgNull implements IArgument, IIngredient
{
    @Override
    public boolean match(String type, String arg)
    {
        return type == null && arg.equals("null");
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgNull();
    }

    @Override
    public IArgument addModifier(String name, String val)
    {
        return null;
    }

    @Override
    public boolean generate()
    {
        return true;
    }

    @Override
    public String print()
    {
        return "null";
    }

    @Override
    public String toString()
    {
        return print();
    }

    @Override
    public Object getItem()
    {
        return null;
    }
}
