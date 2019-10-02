package com.snow.tweaked.api.script;

public interface IArgument extends IToken
{
    boolean match(String type, String arg);
    IArgument build(String arg);

    IArgument addModifier(String name, String val);

    boolean generate();
}
