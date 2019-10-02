package com.snow.tweaked.controllers;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class TweakedConfig
{
    //the tweaked file directory
    public static File tweakedDir;

    //config holder
    private static Configuration config;

    //categories
    private static final String CATEGORY_MODS = "mods";
    private static final String CATEGORY_LOGGING = "logging";
    private static final String CATEGORY_TESTING = "testing";

    //config mods
    public static boolean modsEnableVanilla = true;
    public static boolean modsEnableJEI = true;

    //config logging
    public static boolean logToSystem = true;
    public static boolean logToFile = true;
    public static int logLevel = 2;

    //config testing
    public static boolean testMode = false;

    public static File getMcDir()
    {
        return new File(".");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void loadConfig(File dataDir)
    {
        //create config directory
        tweakedDir = new File(dataDir + File.separator + "tweaked");
        if (!tweakedDir.exists())
        {
            tweakedDir.mkdirs();
        }

        try
        {
            config = new Configuration(new File(tweakedDir.getPath(), "tweaked.cfg"));
            config.load();

            //mods
            config.addCustomCategoryComment(CATEGORY_MODS, "Enable or disable specific modules");

            modsEnableVanilla = config.getBoolean("vanilla", CATEGORY_MODS, modsEnableVanilla, "Enables Vanilla module.");
            modsEnableJEI = config.getBoolean("jei", CATEGORY_MODS, modsEnableJEI, "Enables Just Enough Items (JEI) module.");

            //logging
            config.addCustomCategoryComment(CATEGORY_LOGGING, "TweakedLogger to files and system output");

            logToSystem = config.getBoolean("logToSystem", CATEGORY_LOGGING, logToSystem, "Whether to send logs to system output (standard log file)");
            logToFile = config.getBoolean("logToFile", CATEGORY_LOGGING, logToFile, "Whether to send logs to the tweaked log file (tweaked.log)");
            logLevel = config.getInt("logLevel", CATEGORY_LOGGING, logLevel, 0, 4, "Sets the level for log outputs, lower settings equals more logs : 0=Trace, 1=Debug, 2=Info, 3=Warn, 4=Errors");

            //testing
            config.addCustomCategoryComment(CATEGORY_TESTING, "Testing mode, no need to touch this unless you know what it's for");

            testMode = config.getBoolean("testMode", CATEGORY_TESTING, testMode, "Enables Testing Mode, disabling normal scripts.");
        }
        catch (Exception e)
        {
            //no logging setup yet
            System.out.println("Tweaked could not load its configuration");
        }
        finally
        {
            saveConfig();
        }
    }

    public static void saveConfig()
    {
        if (config.hasChanged())
        {
            config.save();
        }
    }
}
