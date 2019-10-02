package com.snow.tweaked.script.tokens;

import com.snow.tweaked.api.script.IToken;

public class TokenFunction implements IToken
{
    private String var;

    public TokenFunction(String s)
    {
        this.var = s;
    }

    public String getVar()
    {
        return var;
    }

    @Override
    public String print()
    {
        return "";
    }
}
