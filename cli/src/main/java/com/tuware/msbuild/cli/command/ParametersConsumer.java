package com.tuware.msbuild.cli.command;

import picocli.CommandLine;

import java.util.Stack;

public class ParametersConsumer implements CommandLine.IParameterConsumer {

    @Override
    public void consumeParameters(Stack<String> args, CommandLine.Model.ArgSpec argSpec, CommandLine.Model.CommandSpec commandSpec) {
        if (args.size() != 2) {
            throw new CommandLine.ParameterException(commandSpec.commandLine(), "2 and only 2 parameters should be provided");
        }
        String bazelWorkspaceFolder = args.pop();
        String solutionName = args.pop();
        argSpec.setValue(new Parameters(bazelWorkspaceFolder, solutionName));
    }
}
