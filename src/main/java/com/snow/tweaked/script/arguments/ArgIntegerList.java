package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IArgumentList;

import java.util.List;

public class ArgIntegerList implements IArgument, IArgumentList
{
    private List<IArgument> values;

    public ArgIntegerList(List<IArgument> values)
    {
        this.values = values;
    }

    @Override
    public List<IArgument> getValues()
    {
        return values;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return false;
    }

    @Override
    public IArgument build(String arg)
    {
        return null;
    }

    @Override
    public IArgument addModifier(String name, String val)
    {
        return null;
    }

    @Override
    public boolean generate()
    {
        for (IArgument arg : values)
        {
            if (!arg.generate()) return false;
        }
        return true;
    }

    @Override
    public String print()
    {
        return values.toString();
    }

    @Override
    public String toString()
    {
        return print();
    }
}
