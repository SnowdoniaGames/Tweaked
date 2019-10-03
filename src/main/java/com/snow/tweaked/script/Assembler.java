package com.snow.tweaked.script;

import com.snow.tweaked.api.script.IArgument;
import com.snow.tweaked.api.script.IFunction;
import com.snow.tweaked.api.script.IToken;
import com.snow.tweaked.script.functions.FunctionDefine;
import com.snow.tweaked.script.arguments.*;
import com.snow.tweaked.script.tokens.*;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Assembler
{
    private static StringBuilder tokenBuilder;

    public List<IToken> assemble(List<IToken> compileList)
    {
        ScriptHelper.initMaps();
        if (compileList == null) return null;

        //start by parsing all variables
        compileList = parseVariables(compileList);
        if (compileList == null) return null;

        //then parse all remaining tokens
        compileList = parseTokens(compileList);
        if (compileList == null) return null;

        //finally apply commands
        List<FunctionDefine> defines = new ArrayList<>();
        for (IToken token : compileList)
        {
            if (token instanceof FunctionDefine)
            {
                defines.add((FunctionDefine) token);
            }
            else if (token instanceof IFunction)
            {
                if (!((IFunction) token).applyDefines(defines))
                {
                    ScriptHelper.throwScriptError(null, null,"Unable to determine defined variables for '" + token.print() + "'");
                    return null;
                }
                if (!((IFunction) token).apply(null))
                {
                    ScriptHelper.throwScriptError(null, null,"Unable to apply function '" + token.print() + "'");
                    return null;
                }
            }
        }

        return compileList;
    }

    private static IArgument parseVariable(TokenVar token)
    {
        ScriptHelper.lineCount = 0;

        String in = token.getVar().trim();
        tokenBuilder = new StringBuilder();

        boolean varOpen = false;
        String varType = null;
        String varArg = null;

        boolean modOpen = false;
        String modifier = null;
        List<Tuple<String, String>> modifiers = new ArrayList<>();

        if (in.contains("(") && in.endsWith(")"))
        {
            for (byte b : in.getBytes())
            {
                char c = (char) b;

                ScriptHelper.TokenType type = ScriptHelper.tokenMap.get(c);
                if (type == null)
                {
                    tokenBuilder.append(c);
                }
                else
                {
                    switch (type)
                    {
                        case IGNORE:
                            break;
                        case COMMA:
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Variable cannot contain ','");
                            return null;
                        case VAR_OPEN:
                            varOpen = true;

                            if (modOpen)
                            {
                                if (varType == null)
                                {
                                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Modifiers must follow a valid arguments");
                                    return null;
                                }
                                if (modifier != null)
                                {
                                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Previous modifier was not closed");
                                    return null;
                                }
                                modifier = read();
                            }
                            else
                            {
                                varType = read();
                            }
                            clear();
                            break;
                        case VAR_CLOSE:
                            if (!varOpen)
                            {
                                ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Attempting to close unopened arguments");
                                return null;
                            }
                            varOpen = false;

                            if (modOpen)
                            {
                                if (modifier == null)
                                {
                                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Attempting to close a missing modifier");
                                    return null;
                                }
                                modifiers.add(new Tuple<>(modifier, read()));

                                modOpen = false;
                                modifier = null;
                            }
                            else
                            {
                                varArg = read();
                            }
                            clear();
                            break;
                        case PERIOD:
                            if (modOpen)
                            {
                                ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Previous modifier was not closed");
                                return null;
                            }

                            modOpen = true;
                            break;
                        default:
                            tokenBuilder.append(c);
                    }
                }
            }
        }
        else
        {
            varArg = in;
            clear();
        }

        for (IArgument var : ScriptHelper.argMap)
        {
            if (var.match(varType, varArg))
            {
                var = var.build(varArg);
                if (var == null)
                {
                    return null;
                }
                for (Tuple<String, String> mod : modifiers)
                {
                    var = var.addModifier(mod.getFirst(), mod.getSecond());
                    if (var == null)
                    {
                        ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Unable to add modifer");
                        return null;
                    }
                }
                return var;
            }
        }

        return null;
    }

    private static IArgument parseList(List<IArgument> in)
    {
        if (in.isEmpty())
        {
            ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Lists cannot be empty");
            return null;
        }
        if (in.stream().map(IArgument::getClass).distinct().count() > 1)
        {
            ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Lists cannot contain variables of different types");
            return null;
        }

        IArgument firstItem = in.get(0);
        if (firstItem instanceof ArgFloat)
        {
            return new ArgFloatList(in.stream().map(var -> (ArgFloat) var).collect(Collectors.toList()));
        }
        else if (firstItem instanceof ArgInteger)
        {
            return new ArgIntegerList(in.stream().map(var -> (ArgInteger) var).collect(Collectors.toList()));
        }
        else if (firstItem instanceof ArgString)
        {
            return new ArgStringList(in.stream().map(var -> (ArgString) var).collect(Collectors.toList()));
        }
        else if (firstItem instanceof ArgItem)
        {
            return new ArgItemList(in.stream().map(var -> (ArgItem) var).collect(Collectors.toList()));
        }
        else if (firstItem instanceof ArgFluid)
        {
            return new ArgFluidList(in.stream().map(var -> (ArgFluid) var).collect(Collectors.toList()));
        }
        ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Unknown list type");
        return null;
    }

    private static String read()
    {
        return tokenBuilder != null ? tokenBuilder.toString().trim() : "";
    }

    private static void clear()
    {
        if (tokenBuilder != null) tokenBuilder.setLength(0);
    }

    private List<IToken> parseVariables(List<IToken> compileList)
    {
        ScriptHelper.lineCount = 0;

        List<IToken> tmpList = new ArrayList<>();
        for (Object token : compileList)
        {
            if (token instanceof TokenLineNumber)
            {
                ScriptHelper.lineCount = ((TokenLineNumber) token).getVar();
                tmpList.add((IToken) token);
            }
            else if (token instanceof TokenVar)
            {
                IArgument var = parseVariable((TokenVar) token);
                if (var == null) return null;

                tmpList.add(var);
            }
            else if (token instanceof TokenList)
            {
                List<IArgument> varList = new ArrayList<>();
                for (Object listToken : ((TokenList) token).getVars())
                {
                    if (listToken instanceof TokenLineNumber)
                    {
                        ScriptHelper.lineCount = ((TokenLineNumber) listToken).getVar();
                    }
                    else if (listToken instanceof TokenVar)
                    {
                        IArgument var = parseVariable((TokenVar) listToken);
                        if (var == null) return null;

                        varList.add(var);
                    }
                    else if (listToken instanceof TokenList)
                    {
                        ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "List cannot contain another list");
                        return null;
                    }
                }
                if (varList.isEmpty())
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "List cannot be empty");
                    return null;
                }
                IArgument list = parseList(varList);
                if (list == null)
                {
                    return null;
                }
                tmpList.add(list);
            }
            else if (token instanceof TokenLogic)
            {
                List<IToken> logicList = parseVariables(((TokenLogic) token).getLogicList());
                if (logicList == null)
                {
                    return null;
                }
                ((TokenLogic) token).setLogicList(logicList);
                tmpList.add((IToken) token);
            }
            else
            {
                tmpList.add((IToken) token);
            }
        }
        return tmpList;
    }

    private List<IToken> parseTokens(List<IToken> compileList)
    {
        //now parse tokens
        List<IToken> tmpList = new ArrayList<>();
        IFunction functionHolder = null;
        for (Object token : compileList)
        {
            if (token instanceof TokenLineNumber)
            {
                ScriptHelper.lineCount = ((TokenLineNumber) token).getVar();
            }
            else if (token instanceof TokenFunction)
            {
                if (functionHolder != null)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Attempting to parse command before ending the previous one");
                    return null;
                }

                String functionName = ((TokenFunction) token).getVar();
                for (IFunction command : ScriptHelper.functionMap)
                {
                    if (command.match(functionName))
                    {
                        functionHolder = command.build(functionName.toLowerCase());
                        break;
                    }
                }

                if (functionHolder == null)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Failed to find command '" + functionName + "'");
                    return null;
                }
            }
            else if (token instanceof TokenLineBreak)
            {
                if (functionHolder == null)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Failed to parse command");
                    return null;
                }
                if (!functionHolder.validate())
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Failed to validate command");
                    return null;
                }

                tmpList.add(functionHolder);
                functionHolder = null;
            }
            else if (token instanceof TokenLogic)
            {
                if (functionHolder == null)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Attempting to parse logic block before a command has been defined");
                    return null;
                }

                List<IToken> logicList = parseTokens(((TokenLogic) token).getLogicList());
                if (logicList == null)
                {
                    return null;
                }

                if (!functionHolder.addArg(logicList))
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Failed to parse arguments into command");
                    return null;
                }

                if (!functionHolder.validate())
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Failed to validate command");
                    return null;
                }

                tmpList.add(functionHolder);
                functionHolder = null;
            }
            else
            {
                if (functionHolder == null)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Attempting to parse arguments before a command has been defined");
                    return null;
                }

                if (!functionHolder.addArg(token))
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, ScriptHelper.lineCount, "Failed to parse arguments into command");
                    return null;
                }
            }
        }
        return tmpList;
    }
}
