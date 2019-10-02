package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

public class ArgVar implements IArgument
{
    private String id;

    public ArgVar() { }

    public ArgVar(String s)
    {
        this.id = s;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type != null && type.equals("var") && arg != null;
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgVar(s);
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

    public String getId()
    {
        return id;
    }

    @Override
    public String print()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return print();
    }
}
