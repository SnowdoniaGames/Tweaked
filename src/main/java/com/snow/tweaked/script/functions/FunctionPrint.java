package com.snow.tweaked.script.functions;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IFunction;
import com.snow.tweaked.script.arguments.ArgIdentifier;
import com.snow.tweaked.script.arguments.ArgLogicVar;
import com.snow.tweaked.script.arguments.ArgVar;
import com.snow.tweaked.script.tokens.TokenColon;

import java.util.List;

import static com.snow.tweaked.Tweaked.LOG;

public class FunctionPrint implements IFunction
{
    private IArgument value;
    private boolean commit = false;

    public FunctionPrint() { }

    public FunctionPrint(FunctionPrint copy)
    {
        this.value = copy.value;
        this.commit = copy.commit;
    }

    @Override
    public boolean match(String id)
    {
        return id.equals("print");
    }

    @Override
    public IFunction build(String id)
    {
        return new FunctionPrint();
    }

    @Override
    public IFunction build(IFunction copy)
    {
        return copy instanceof FunctionPrint ? new FunctionPrint((FunctionPrint) copy) : null;
    }

    @Override
    public boolean addArg(Object arg)
    {
        if (!commit)
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
        return value != null && commit;
    }

    @Override
    public boolean applyDefines(List<FunctionDefine> defines)
    {
        if (value instanceof ArgVar)
        {
            FunctionDefine define = defines.stream().filter(d -> d.getName().equals(((ArgVar) value).getId())).findFirst().orElse(null);
            if (define != null)
            {
                value = define.getValue();
                return true;
            }
        }
        else
        {
            return value instanceof ArgLogicVar;
        }
        return false;
    }

    @Override
    public boolean apply(IArgument logicVar)
    {
        if (value != null)
        {
            if (value instanceof ArgLogicVar)
            {
                if (logicVar == null) return false;

                LOG.print(logicVar.print());
                return true;
            }
            else
            {
                LOG.print(value.print());
                return true;
            }
        }
        return false;
    }

    @Override
    public String print()
    {
        return value != null ? "print(" + value.print() + ")" : "";
    }
}
