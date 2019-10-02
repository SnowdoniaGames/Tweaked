package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;

public class ArgFluid implements IArgument
{
    private String fluidName;

    private Integer count = null;

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
        if (name.equals("count"))
        {
            try
            {
                this.count = Integer.parseInt(val);
                return this;
            }
            catch (NumberFormatException ignored) { }
        }
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
        return fluidName;
    }

    @Override
    public String toString()
    {
        return print();
    }
}
