package com.snow.tweaked.script.functions;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IFunction;
import com.snow.tweaked.controllers.TweakedAnnotations;
import com.snow.tweaked.controllers.TweakedFunctions;
import com.snow.tweaked.script.arguments.ArgIdentifier;
import com.snow.tweaked.script.arguments.ArgLogicVar;
import com.snow.tweaked.script.arguments.ArgVar;
import com.snow.tweaked.script.tokens.TokenColon;

import java.util.ArrayList;
import java.util.List;

public class FunctionAction implements IFunction
{
    public String actionName;
    public Object actionClass;
    public List<IArgument> args = new ArrayList<>();
    public List<Class<?>> classes = new ArrayList<>();
    private boolean commit = false;

    public FunctionAction() { }

    public FunctionAction(String action)
    {
        this.actionName = action;
    }

    public FunctionAction(FunctionAction copy)
    {
        this.actionName = copy.actionName;
        this.actionClass = copy.actionClass;
        this.args = copy.args;
        this.commit = copy.commit;
    }

    @Override
    public boolean match(String id)
    {
        return id.contains(".") && !id.contains("\"") && !id.contains(",") && !id.contains("(") && !id.contains("[") && !id.contains("<");
    }

    @Override
    public IFunction build(String id)
    {
        return new FunctionAction(id);
    }

    @Override
    public IFunction build(IFunction copy)
    {
        return copy instanceof FunctionAction ? new FunctionAction((FunctionAction) copy) : null;
    }

    @Override
    public boolean addArg(Object arg)
    {
        if (actionName == null)
        {
            return false;
        }
        else if (!commit)
        {
            if (arg instanceof TokenColon)
            {
                commit = true;
                return true;
            }
        }
        else if (arg instanceof IArgument && !(arg instanceof ArgIdentifier))
        {
            args.add((IArgument) arg);
            return true;
        }
        return false;
    }

    @Override
    public boolean validate()
    {
        return actionName != null && commit && !args.isEmpty();
    }

    @Override
    public boolean applyDefines(List<FunctionDefine> defines)
    {
        List<IArgument> newList = new ArrayList<>();
        for (IArgument arg : args)
        {
            if (arg instanceof ArgVar)
            {
                FunctionDefine define = defines.stream().filter(d -> d.getName().equals(((ArgVar) arg).getId())).findFirst().orElse(null);
                if (define == null) return false;

                newList.add(define.getValue());
            }
            else
            {
                newList.add(arg);
            }
        }
        args = newList;
        return true;
    }

    @Override
    public boolean apply(IArgument logicVar)
    {
        //check this function is registed
        if (!TweakedAnnotations.FUNCTIONS.containsKey(actionName))
        {
            return false;
        }

        List<IArgument> newList = new ArrayList<>();
        for (IArgument arg : args)
        {
            if (arg instanceof ArgLogicVar)
            {
                if (logicVar == null) return false;

                newList.add(logicVar);
            }
            else
            {
                newList.add(arg);
            }
        }
        args = newList;

        //build classes
        for (IArgument arg : args)
        {
            classes.add(arg.getClass());
        }

        //add to active function list
        TweakedFunctions.storeFunction(actionName, this);

        return true;
    }

    public boolean generateVars()
    {
        for (IArgument arg : args)
        {
            if (!arg.generate()) return false;
        }
        return true;
    }

    @Override
    public String print()
    {
        return actionName + "{" + args + "}";
    }
}
