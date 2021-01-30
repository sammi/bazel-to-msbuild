package com.tuware.msbuild.cppwinrt;

import com.tuware.msbuild.domain.Solution;

public class MSBuild {
    public Solution buildConsoleApp(String name, String location, String solutionName) {
        return Solution.builder().name(solutionName).build();
    }
}
