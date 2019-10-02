package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

import java.util.regex.Pattern;

public class ArgFloat implements IArgument
{
    private static final Pattern pattern = Pattern.compile("^-?[0-9]*\\.?[0-9]+$");

    private float value;

    public ArgFloat() { }

    public ArgFloat(float value)
    {
        this.value = value;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type == null && pattern.matcher(arg).find();
    }

    @Override
    public IArgument build(String arg)
    {
        return new ArgFloat(Float.parseFloat(arg));
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
