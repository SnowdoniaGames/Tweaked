package com.snow.tweaked.script.tokens;

import com.snow.tweaked.api.script.IToken;

public class TokenVar implements IToken
{
    private String var;

    public TokenVar(String s)
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
