package com.snow.tweaked.script.functions;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IArgumentList;
import com.snow.tweaked.api.script.IFunction;
import com.snow.tweaked.api.script.IToken;
import com.snow.tweaked.script.arguments.ArgVar;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionFor implements IFunction
{
    private IArgument value;
    private List<IToken> logic;

    public FunctionFor() { }

    public FunctionFor(FunctionFor copy)
    {
        this.value = copy.value;
        this.logic = copy.logic;
    }

    @Override
    public boolean match(String id)
    {
        return id.equals("for");
    }

    @Override
    public IFunction build(String id)
    {
        return new FunctionFor();
    }

    @Override
    public IFunction build(IFunction copy)
    {
        return copy instanceof FunctionFor ? new FunctionFor((FunctionFor) copy) : null;
    }

    @Override
    public boolean addArg(Object arg)
    {
        if (value == null)
        {
            if (arg instanceof IArgumentList)
            {
                value = (IArgument) arg;
                return true;
            }
            else if (arg instanceof ArgVar)
            {
                value = (IArgument) arg;
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (logic == null)
        {
            if (arg instanceof List<?>)
            {
                logic = ((List<?>) arg).stream().map(e -> (IToken) e).collect(Collectors.toList());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validate()
    {
        return value != null && logic != null;
    }

    @Override
    public boolean applyDefines(List<FunctionDefine> defines)
    {
        if (value instanceof ArgVar)
        {
            FunctionDefine define = defines.stream().filter(d -> d.getName().equals(((ArgVar) value).getId())).findFirst().orElse(null);
            if (define == null) return false;
            if (!(define.getValue() instanceof IArgumentList)) return false;

            value = define.getValue();
        }

        for (IToken token : logic)
        {
            if (token instanceof FunctionDefine)
            {
                defines.add((FunctionDefine) token);
            }
            else if (token instanceof IFunction)
            {
                if (!((IFunction) token).applyDefines(defines))
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(IArgument logicVar)
    {
        if (value instanceof IArgumentList)
        {
            for (IArgument s : ((IArgumentList) value).getValues())
            {
                for (IToken token : logic)
                {
                    if (token instanceof IFunction)
                    {
                        IFunction newFunction = ((IFunction) token).build((IFunction) token);
                        if (newFunction == null) return false;

                        if (!(newFunction.apply(s)))
                        {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String print()
    {
        return value != null ? "for " + value.print() : "";
    }
}
