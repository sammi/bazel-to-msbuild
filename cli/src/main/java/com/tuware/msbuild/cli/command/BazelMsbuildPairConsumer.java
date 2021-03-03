package com.tuware.msbuild.cli.command;

import picocli.CommandLine;

import java.util.Stack;

public class BazelMsbuildPairConsumer implements CommandLine.IParameterConsumer {

    @Override
    public void consumeParameters(Stack<String> args, CommandLine.Model.ArgSpec argSpec, CommandLine.Model.CommandSpec commandSpec) {
        if (args.size() != 3) {
            throw new CommandLine.ParameterException(commandSpec.commandLine(), "3 and only 3 parameters should be provided");
        }
        String bazelWorkspaceFolder = args.pop();
        String msbuildSolutionFolder = args.pop();
        String projectName = args.pop();
        argSpec.setValue(new Parameters(bazelWorkspaceFolder, msbuildSolutionFolder, projectName));
    }
}
