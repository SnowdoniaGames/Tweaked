package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

public class ArgIdentifier implements IArgument
{
    private String id;

    public ArgIdentifier() { }

    public ArgIdentifier(String s)
    {
        this.id = s;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type == null && !arg.contains("(") && !arg.contains(")") && !arg.contains(",") && !arg.contains("\"");
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgIdentifier(s);
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
