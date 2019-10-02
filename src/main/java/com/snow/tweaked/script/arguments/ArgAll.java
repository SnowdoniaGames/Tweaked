package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

public class ArgAll implements IArgument
{
    @Override
    public boolean match(String type, String arg)
    {
        return type == null && arg.equals("*");
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgAll();
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
        return "*";
    }

    @Override
    public String toString()
    {
        return print();
    }
}
