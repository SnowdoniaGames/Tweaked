package com.snow.tweaked.controllers;

import com.snow.tweaked.script.functions.FunctionAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweakedFunctions
{
    private static final Map<String, List<FunctionAction>> FUNCTION_MAP = new HashMap<>();

    public static void storeFunction(String actionName, FunctionAction action)
    {
        if (!FUNCTION_MAP.containsKey(actionName))
        {
            FUNCTION_MAP.put(actionName, new ArrayList<>());
        }
        FUNCTION_MAP.get(actionName).add(action);
    }

    public static List<FunctionAction> getFunctions(String actionName)
    {
        if (FUNCTION_MAP.containsKey(actionName))
        {
            return FUNCTION_MAP.get(actionName);
        }
        return new ArrayList<>();
    }

    public static void clearActions(String actionName)
    {
        FUNCTION_MAP.remove(actionName);
    }

    public static void clearAllActions()
    {
        FUNCTION_MAP.clear();
    }

}
