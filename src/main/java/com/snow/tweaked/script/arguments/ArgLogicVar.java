package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

public class ArgLogicVar implements IArgument
{
    private String id;

    public ArgLogicVar() { }

    public ArgLogicVar(String s)
    {
        this.id = s;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type == null && arg.equals("var");
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgLogicVar(s);
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
