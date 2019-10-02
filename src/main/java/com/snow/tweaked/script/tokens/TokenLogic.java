package com.snow.tweaked.script.tokens;

import com.snow.tweaked.api.script.IToken;

import java.util.List;

public class TokenLogic implements IToken
{
    private List<IToken> logicList;

    public TokenLogic(List<IToken> logicList)
    {
        this.logicList = logicList;
    }

    public void setLogicList(List<IToken> logicList)
    {
        this.logicList = logicList;
    }

    public List<IToken> getLogicList()
    {
        return logicList;
    }

    @Override
    public String print()
    {
        return "";
    }
}
