package com.snow.tweaked.script.tokens;

import com.snow.tweaked.api.script.IToken;

public class TokenLineNumber implements IToken
{
    private int var;

    public TokenLineNumber(int i)
    {
        this.var = i;
    }

    public int getVar()
    {
        return var;
    }

    @Override
    public String print()
    {
        return "";
    }
}
