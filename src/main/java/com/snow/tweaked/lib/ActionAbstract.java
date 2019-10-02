package com.snow.tweaked.lib;

import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.api.ITweakedAction;
import com.snow.tweaked.controllers.TweakedFunctions;
import com.snow.tweaked.script.Executor;
import com.snow.tweaked.script.functions.FunctionAction;

@SuppressWarnings("unused")
public abstract class ActionAbstract implements ITweakedAction
{
    protected abstract void run();

    public void apply()
    {
        //get the classname from the annotation
        String actionName = getClass().getAnnotation(TweakedAction.class).value();

        //apply actions in the action loader, which should cause this action to build
        for (FunctionAction action : TweakedFunctions.getFunctions(actionName))
        {
            Executor.execute(action);
        }

        //run the action, handled by the action itself
        run();

        //cleanup the action
        TweakedFunctions.clearActions(actionName);
    }
}
