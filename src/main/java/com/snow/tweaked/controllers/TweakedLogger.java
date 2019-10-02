package com.snow.tweaked.controllers;

import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class TweakedLogger
{
    public static final String NEWLINE = System.lineSeparator();
    public static final String TAB = "\t";
    public static final String DOUBLE_TAB = "\t\t";

    public static final int LEVEL_TRACE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    public static final int LEVEL_ERROR = 4;

    private Logger logger;
    private int logLevel;

    private Writer logWriter;
    private boolean logToSystem;
    private boolean logToFile;

    private Writer dumpWriter;

    public TweakedLogger(Logger logger)
    {
        this.logger = logger;
        this.logLevel = TweakedConfig.logLevel;
        this.logToSystem = TweakedConfig.logToSystem;
        this.logToFile = TweakedConfig.logToFile;

        if (logToFile)
        {
            //setup logging to file
            File logFile = new File(TweakedConfig.tweakedDir + File.separator + "tweaked.log");
            try
            {
                logWriter = new OutputStreamWriter(new FileOutputStream(logFile), StandardCharsets.UTF_8);
            }
            catch (FileNotFoundException ex)
            {
                warn("Warning : Unable to find logging file");
            }
        }

        //setup dumps
        File dumpFile = new File(TweakedConfig.tweakedDir + File.separator + "tweaked.dump");
        try
        {
            dumpWriter = new OutputStreamWriter(new FileOutputStream(dumpFile), StandardCharsets.UTF_8);
        }
        catch (FileNotFoundException ex)
        {
            warn("Warning : Unable to find dump file");
        }
    }

    public void fileWrite(String msg)
    {
        try
        {
            logWriter.write(msg + "\n");
            logWriter.flush();
        }
        catch (IOException ex)
        {
            warn("Warning : Unable to write logs");
        }
    }

    public void dump(String msg)
    {
        try
        {
            dumpWriter.write(msg + "\n");
            dumpWriter.flush();
        }
        catch (IOException ex)
        {
            warn("Warning : Unable to write dump");
        }
    }

    public void print(String msg)
    {
        logger.info(msg);
        fileWrite(msg);
    }

    public void trace(String msg)
    {
        if (logger != null && logLevel <= LEVEL_TRACE)
        {
            if (logToSystem) logger.info(msg);
            if (logToFile) fileWrite(msg);
        }
    }

    public void trace(String msg, Throwable t)
    {
        if (logger != null && logLevel <= LEVEL_TRACE)
        {
            if (logToSystem) logger.info(msg, t);
            if (logToFile) fileWrite(msg);
        }
    }

    public void debug(String msg)
    {
        if (logger != null && logLevel <= LEVEL_DEBUG)
        {
            if (logToSystem) logger.info(msg);
            if (logToFile) fileWrite(msg);
        }
    }

    public void debug(String msg, Throwable t)
    {
        if (logger != null && logLevel <= LEVEL_DEBUG)
        {
            if (logToSystem) logger.info(msg, t);
            if (logToFile) fileWrite(msg);
        }
    }

    public void info(String msg)
    {
        if (logger != null && logLevel <= LEVEL_INFO)
        {
            if (logToSystem) logger.info(msg);
            if (logToFile) fileWrite(msg);
        }
    }

    public void info(String msg, Throwable t)
    {
        if (logger != null && logLevel <= LEVEL_INFO)
        {
            if (logToSystem) logger.info(msg, t);
            if (logToFile) fileWrite(msg);
        }
    }

    public void warn(String msg)
    {
        if (logger != null && logLevel <= LEVEL_WARN)
        {
            if (logToSystem) logger.warn(msg);
            if (logToFile) fileWrite(msg);
        }
    }

    public void warn(String msg, Throwable t)
    {
        if (logger != null && logLevel <= LEVEL_WARN)
        {
            if (logToSystem) logger.warn(msg, t);
            if (logToFile) fileWrite(msg);
        }
    }

    public void error(String msg)
    {
        if (logger != null && logLevel <= LEVEL_ERROR)
        {
            if (logToSystem) logger.error(msg);
            if (logToFile) fileWrite(msg);
        }
    }

    public void error(String msg, Throwable t)
    {
        if (logger != null && logLevel <= LEVEL_ERROR)
        {
            if (logToSystem) logger.error(msg, t);
            if (logToFile) fileWrite(msg);
        }
    }
}
