package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

import java.util.regex.Pattern;

public class ArgBool implements IArgument
{
    private boolean value;

    public ArgBool() { }

    public ArgBool(boolean value)
    {
        this.value = value;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type == null && arg.equals("true") || arg.equals("false");
    }

    @Override
    public IArgument build(String arg)
    {
        return new ArgBool(Boolean.parseBoolean(arg));
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
        return String.valueOf(value);
    }

    @Override
    public String toString()
    {
        return print();
    }
}
