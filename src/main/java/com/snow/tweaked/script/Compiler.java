package com.snow.tweaked.script;

import com.snow.tweaked.api.script.IToken;
import com.snow.tweaked.script.tokens.*;

import java.util.ArrayList;
import java.util.List;

public class Compiler
{
    private List<IToken> lexHolder;
    private List<IToken> listHolder;
    private List<IToken> logicHolder;
    private StringBuilder tokenBuilder;

    private Character lineSep = null;
    private int lineCount = 1;

    private boolean lineStart = true;
    private int varOpen = 0;
    private boolean listOpen = false;
    private boolean logicOpen = false;
    private boolean quoteOpen = false;
    private boolean comment = false;

    public List<IToken> compile(byte[] byteIn)
    {
        ScriptHelper.initMaps();

        lexHolder = new ArrayList<>();
        tokenBuilder = new StringBuilder();

        commit(new TokenLineNumber(lineCount));
        for(byte b : byteIn)
        {
            char c = (char) b;

            ScriptHelper.TokenType type = ScriptHelper.tokenMap.get(c);
            if (type == null)
            {
                if (!comment)
                {
                    append(c);
                }
            }
            else
            {
                switch (type)
                {
                    case IGNORE:
                        break;
                    case WHITESPACE:
                        if (comment) break;
                        if (varOpen > 0 || listOpen || quoteOpen)
                        {
                            append(c);
                            break;
                        }
                        if (!commit(read())) return null;

                        clear();
                        break;
                    case COMMENT:
                        comment = true;
                        break;
                    case LINE_SEPARATOR:
                        if (lineSep == null) lineSep = c;

                        if (c == lineSep)
                        {
                            lineCount++;
                            commit(new TokenLineNumber(lineCount));

                            comment = false;
                        }
                        break;
                    case LINE_BREAK:
                        if (comment) break;
                        if (varOpen > 0)
                        {
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Line ended before closing a arguments");
                            return null;
                        }
                        if (listOpen)
                        {
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Line ended before closing a list");
                            return null;
                        }
                        if (quoteOpen)
                        {
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Line ended before closing a string");
                            return null;
                        }
                        if (!commit(read())) return null;
                        if (!commit(new TokenLineBreak())) return null;
                        lineStart = true;

                        clear();
                        break;
                    case COLON:
                        if (comment) break;
                        if (varOpen > 0)
                        {
                            append(c);
                            break;
                        }

                        //commit command
                        if (!commit(read())) return null;
                        if (!commit(new TokenColon())) return null;

                        clear();
                        break;
                    case VAR_OPEN:
                        if (comment) break;
                        varOpen++;

                        append(c);
                        break;
                    case VAR_CLOSE:
                        if (comment) break;
                        if (varOpen == 0)
                        {
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Attempting to close unopened arguments");
                            return null;
                        }
                        varOpen--;

                        append(c);
                        break;
                    case LIST_OPEN:
                        if (comment) break;
                        listOpen = true;
                        listHolder = new ArrayList<>();
                        break;
                    case LIST_CLOSE:
                        if (comment) break;
                        if (!listOpen || listHolder == null)
                        {
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Attempting to close unopened list");
                            return null;
                        }
                        if(!commit(read())) return null;
                        listOpen = false;

                        if(!commit(new TokenList(listHolder))) return null;
                        listHolder = null;

                        clear();
                        break;
                    case LOGIC_OPEN:
                        if (comment) break;

                        if (varOpen > 0)
                        {
                            append(c);
                            break;
                        }

                        logicOpen = true;
                        logicHolder = new ArrayList<>();
                        lineStart = true;

                        clear();
                        break;
                    case LOGIC_CLOSE:
                        if (comment) break;
                        if (varOpen > 0)
                        {
                            append(c);
                            break;
                        }

                        if (!logicOpen || logicHolder == null)
                        {
                            ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Attempting to close unopened logic");
                            return null;
                        }
                        logicOpen = false;

                        if(!commit(new TokenLogic(logicHolder))) return null;

                        logicHolder = null;
                        clear();
                        break;
                    case QUOTE:
                        if (comment) break;
                        quoteOpen = !quoteOpen;
                        append(c);
                        break;
                    case COMMA:
                        if (comment) break;
                        if (!commit(read())) return null;
                        clear();
                        break;
                    default:
                        if (comment) break;
                        append(c);
                }
            }
        }

        return lexHolder;
    }

    private boolean commit(Object commit)
    {
        if (lexHolder != null)
        {
            if (lineStart)
            {
                //handle line numbers
                if (commit instanceof TokenLineNumber)
                {
                    if (logicOpen && logicHolder != null)
                    {
                        logicHolder.add((IToken) commit);
                        return true;
                    }
                    else
                    {
                        lexHolder.add((IToken) commit);
                        return true;
                    }
                }

                //handle logic blocks
                if (commit instanceof TokenLogic)
                {
                    lexHolder.add((IToken) commit);
                    return true;
                }

                lineStart = false;

                //line must start with commands, therefore must be a string id
                if (!(commit instanceof String))
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Line must start with a command");
                    return false;
                }

                if (listOpen && listHolder != null)
                {
                    ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "List cannot contain a command");
                    return false;
                }
                else if (logicOpen && logicHolder != null)
                {
                    logicHolder.add(new TokenFunction((String) commit));
                    return true;
                }
                else
                {
                    lexHolder.add(new TokenFunction((String) commit));
                    return true;
                }
            }
            else
            {
                if (listOpen && listHolder != null)
                {
                    if (commit instanceof String)
                    {
                        if (commit.toString().isEmpty()) return true;

                        listHolder.add(new TokenVar((String) commit));
                        return true;
                    }
                    else
                    {
                        listHolder.add((IToken) commit);
                        return true;
                    }
                }
                else if (logicOpen && logicHolder != null)
                {
                    if (commit instanceof String)
                    {
                        if (commit.toString().isEmpty()) return true;

                        logicHolder.add(new TokenVar((String) commit));
                        return true;
                    }
                    else
                    {
                        logicHolder.add((IToken) commit);
                        return true;
                    }
                }
                else
                {
                    if (commit instanceof String)
                    {
                        if (commit.toString().isEmpty()) return true;

                        lexHolder.add(new TokenVar((String) commit));
                        return true;
                    }
                    else
                    {
                        lexHolder.add((IToken) commit);
                        return true;
                    }
                }
            }
        }
        ScriptHelper.throwScriptError(ScriptHelper.fileName, lineCount, "Missing lex list");
        return false;
    }

    private String read()
    {
        return tokenBuilder != null ? tokenBuilder.toString().trim() : "";
    }

    private void append(char c)
    {
        if (tokenBuilder != null) tokenBuilder.append(c);
    }

    private void clear()
    {
        if (tokenBuilder != null) tokenBuilder.setLength(0);
    }
}
