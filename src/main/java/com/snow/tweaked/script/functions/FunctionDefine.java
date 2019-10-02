package com.snow.tweaked.script.functions;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IFunction;
import com.snow.tweaked.script.arguments.ArgIdentifier;
import com.snow.tweaked.script.tokens.TokenColon;

import java.util.List;

public class FunctionDefine implements IFunction
{
    private String name;
    private IArgument value;
    private boolean commit = false;

    public FunctionDefine() { }

    public FunctionDefine(FunctionDefine copy)
    {
        this.name = copy.name;
        this.value = copy.value;
        this.commit = copy.commit;
    }

    public String getName()
    {
        return name;
    }

    public IArgument getValue()
    {
        return value;
    }

    @Override
    public boolean match(String id)
    {
        return id.equals("define");
    }

    @Override
    public IFunction build(String id)
    {
        return new FunctionDefine();
    }

    @Override
    public IFunction build(IFunction copy)
    {
        return copy instanceof FunctionDefine ? new FunctionDefine((FunctionDefine) copy) : null;
    }

    @Override
    public boolean addArg(Object arg)
    {
        if (name == null)
        {
            if (arg instanceof ArgIdentifier)
            {
                name = ((ArgIdentifier) arg).getId();
                return true;
            }
        }
        else if (!commit)
        {
            if (arg instanceof TokenColon)
            {
                commit = true;
                return true;
            }
        }
        else if (value == null)
        {
            if (arg instanceof IArgument && !(arg instanceof ArgIdentifier))
            {
                value = (IArgument) arg;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validate()
    {
        return name != null && value != null && commit;
    }

    @Override
    public boolean applyDefines(List<FunctionDefine> defines)
    {
        return false;
    }

    @Override
    public boolean apply(IArgument logicVar)
    {
        return false;
    }

    @Override
    public String print()
    {
        return "";
    }
}
