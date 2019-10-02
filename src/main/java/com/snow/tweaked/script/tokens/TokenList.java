package com.snow.tweaked.script.tokens;

import com.snow.tweaked.api.script.IToken;

import java.util.List;

public class TokenList implements IToken
{
    private List<IToken> vars;

    public TokenList(List<IToken> vars)
    {
        this.vars = vars;
    }

    public List<IToken> getVars()
    {
        return vars;
    }

    @Override
    public String print()
    {
        return "";
    }
}
