package com.snow.tweaked.api.script;

import com.snow.tweaked.script.functions.FunctionDefine;

import java.util.List;

public interface IFunction extends IToken
{
    boolean match(String id);
    IFunction build(String id);
    IFunction build(IFunction copy);

    boolean addArg(Object arg);
    boolean validate();

    boolean applyDefines(List<FunctionDefine> defines);
    boolean apply(IArgument logicVar);
}
