package com.snow.tweaked.script.arguments;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IIngredient;
import net.minecraftforge.oredict.OreDictionary;

public class ArgOre implements IArgument, IIngredient
{
    public String oreName;

    public ArgOre() { }

    public ArgOre(String s)
    {
        this.oreName = s;
    }

    @Override
    public boolean match(String type, String arg)
    {
        return type != null && type.equals("ore") && arg != null;
    }

    @Override
    public IArgument build(String s)
    {
        return new ArgOre(s);
    }

    @Override
    public IArgument addModifier(String name, String val)
    {
        return null;
    }

    @Override
    public boolean generate()
    {
        return OreDictionary.doesOreNameExist(oreName);
    }

    @Override
    public String print()
    {
        return oreName;
    }

    @Override
    public String toString()
    {
        return print();
    }


    @Override
    public Object getItem()
    {
        return oreName;
    }
}
