package com.snow.tweaked.controllers;

import com.snow.tweaked.annotation.TweakedAction;
import com.snow.tweaked.annotation.TweakedCommand;
import com.snow.tweaked.annotation.TweakedMod;
import com.snow.tweaked.annotation.TweakedTest;
import com.snow.tweaked.api.ITweakedAction;
import com.snow.tweaked.api.ITweakedCommand;
import com.snow.tweaked.api.ITweakedMod;
import com.snow.tweaked.api.ITweakedTest;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.util.*;

import static com.snow.tweaked.Tweaked.LOG;
import static net.minecraftforge.fml.common.discovery.ASMDataTable.*;

public class TweakedAnnotations
{
    //a map of mods to be loaded by tweaked, associated with their modid
    public static final List<ITweakedMod> MODS = new ArrayList<>();

    //a map of actions associated with their script name
    public static final Map<String, ITweakedAction> FUNCTIONS = new HashMap<>();

    //a map of commands that can be called by the player when ingame, associated with their command name
    public static final Map<String, ITweakedCommand> COMMANDS = new HashMap<>();

    //a map of tests that are performed when running in test mode
    public static final List<ITweakedTest> TESTS = new ArrayList<>();

    private static ASMDataTable asmData;

    public static void build(ASMDataTable asm)
    {
        try
        {
            asmData = asm;

            buildMods();
            buildFunctions();
            buildCommands();
            if (TweakedConfig.testMode) buildTests();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    private static Set<ASMData> getASMInstances(Class annotationClass)
    {
        return asmData.getAll(annotationClass.getName());
    }

    private static void buildMods() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException
    {
        Set<ASMData> asmSet = getASMInstances(TweakedMod.class);
        for (ASMData data : asmSet)
        {
            String className = data.getClassName();
            if (className == null) continue;

            //invoke the class
            Class<?> clazz = Class.forName(className);
            Object invokedClazz = clazz.newInstance();

            //get the modid
            Object modid = data.getAnnotationInfo().get("modid");
            if (!(modid instanceof String)) return;

            if (Loader.isModLoaded((String) modid))
            {
                if (invokedClazz instanceof ITweakedMod)
                {
                    MODS.add((ITweakedMod) invokedClazz);

                    //debug
                    LOG.debug("Registered Mod : " + (className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className));
                }
            }
        }

        //sort mods based on priority
        Comparator<ITweakedMod> comparator = Comparator.comparingInt(ITweakedMod::getPriority);
        MODS.sort(comparator);
    }

    private static void buildFunctions() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException
    {
        Set<ASMData> asmSet = getASMInstances(TweakedAction.class);
        for (ASMData data : asmSet)
        {
            String className = data.getClassName();
            if (className == null) continue;
            String out = className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className;

            Object modid = data.getAnnotationInfo().get("modid");
            if (modid instanceof String)
            {
                if (!Loader.isModLoaded(((String) modid)))
                {
                    LOG.debug("Disabled Function : " + out + " as '" + data.getAnnotationInfo().get("value") + "'");
                    continue;
                }
            }

            //invoke the class
            Class<?> clazz = Class.forName(className);
            Object invokedClazz = clazz.newInstance();

            //get the input token
            Object value = data.getAnnotationInfo().get("value");

            if (value instanceof String && invokedClazz instanceof ITweakedAction)
            {
                //register action
                FUNCTIONS.put(((String) value).toLowerCase(), (ITweakedAction) invokedClazz);

                //debug
                LOG.debug("Registered Function : " + out + " as '" + value + "'");
            }
        }
    }

    private static void buildCommands() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException
    {
        Set<ASMData> asmSet = getASMInstances(TweakedCommand.class);
        for (ASMData data : asmSet)
        {
            String className = data.getClassName();
            if (className == null) continue;
            String out = className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className;

            Object modid = data.getAnnotationInfo().get("modid");

            if (modid instanceof String)
            {
                if (!Loader.isModLoaded(((String) modid)))
                {
                    LOG.debug("Disabled Command : " + out + " as '" + data.getAnnotationInfo().get("value") + "'");
                    continue;
                }
            }

            //invoke the class
            Class<?> clazz = Class.forName(className);
            Object invokedClazz = clazz.newInstance();

            //get the input token
            Object value = data.getAnnotationInfo().get("value");

            if (value instanceof String && invokedClazz instanceof ITweakedCommand)
            {
                //register action
                COMMANDS.put(((String) value).toLowerCase(), (ITweakedCommand) invokedClazz);

                //debug
                LOG.debug("Registered Command : " + out + " as '" + value + "'");
            }
        }
    }

    private static void buildTests() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException
    {
        Set<ASMData> asmSet = getASMInstances(TweakedTest.class);
        for (ASMData data : asmSet)
        {
            String className = data.getClassName();
            if (className == null) continue;
            String out = className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className;

            Object modid = data.getAnnotationInfo().get("modid");
            if (modid instanceof String)
            {
                if (!Loader.isModLoaded(((String) modid)))
                {
                    LOG.debug("Disabled Test : " + out + " as '" + data.getAnnotationInfo().get("value") + "'");
                    continue;
                }
            }

            //invoke the class
            Class<?> clazz = Class.forName(className);
            Object invokedClazz = clazz.newInstance();

            //get the input token

            if (invokedClazz instanceof ITweakedTest)
            {
                //register action
                TESTS.add((ITweakedTest) invokedClazz);

                //debug
                LOG.debug("Registered Test : " + out);
            }
        }
    }
}
