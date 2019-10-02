package com.snow.tweaked.script;

import com.snow.tweaked.controllers.TweakedAnnotations;
import com.snow.tweaked.script.functions.FunctionAction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.snow.tweaked.Tweaked.LOG;

public class Executor
{
    public static void execute(FunctionAction action)
    {
        //attempt to find action class
        Object actionClass = TweakedAnnotations.FUNCTIONS.get(action.actionName.toLowerCase());
        if (actionClass == null)
        {
            //ScriptHelper.reportScriptError(start, "TweakedAction \"" + actionName + "\" doesn't exist");
            return;
        }
        action.actionClass = actionClass;

        //generate vars at runtime (items, fluids, etc)
        if (!action.generateVars())
        {
            return;
        }

        //attempt to find correct method
        try
        {
            Method method = findMethod(action.actionClass, "build", action.classes.toArray(new Class<?>[0]));
            method.invoke(action.actionClass, action.args.toArray());
        }
        catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            LOG.info("Build doesn't exist or has invalid arguments");
        }
    }

    public static Method findMethod(Object clazz, String methodName, Class[] methodArgs) throws NoSuchMethodException
    {
        //attempt basic method first
        try
        {
            Method method = clazz.getClass().getMethod("build", methodArgs);
            if (method != null) return method;
        }
        catch (NoSuchMethodException e)
        {
            Method[] methods = clazz.getClass().getDeclaredMethods();
            for (Method m : methods)
            {
                if (m.getName().equals(methodName))
                {
                    if (m.getParameterTypes().length == methodArgs.length)
                    {
                        Class<?>[] params = m.getParameterTypes();
                        boolean match = true;
                        for (int i = 0; i < params.length && match; i++)
                        {
                            if (params[i].equals(methodArgs[i]))
                            {
                                continue;
                            }
                            else if (params[i].isInterface() && Arrays.asList(methodArgs[i].getInterfaces()).contains(params[i]))
                            {
                                continue;
                            }
                            match = false;
                        }
                        if (match)
                        {
                            return m;
                        }

                    }
                }
            }
        }
        throw new NoSuchMethodException();
    }
}
