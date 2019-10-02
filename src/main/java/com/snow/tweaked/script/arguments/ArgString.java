package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

public class ArgString implements IArgument
{
    public String value;

    public ArgString() { }

    public ArgString(String value)
    {
        this.value = value;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type == null && arg.chars().filter(num -> num == '"').count() == 2 && arg.startsWith("\"") && arg.endsWith("\"");
    }

    @Override
    public IArgument build(String arg)
    {
        return new ArgString(arg.replace("\"", ""));
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
        return value;
    }

    @Override
    public String toString()
    {
        return print();
    }
}
