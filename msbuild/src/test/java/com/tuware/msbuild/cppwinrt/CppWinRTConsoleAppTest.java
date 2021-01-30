package com.tuware.msbuild.cppwinrt;

import com.tuware.msbuild.domain.Solution;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CppWinRTConsoleAppTest {

    @Test
    void buildConsoleApp() {
        MSBuild msbuild = new MSBuild();
        String name = "TestCppWinRTConsoleApp";
        String location = "projectFolderPath";
        String solutionName = "TestCppWinRTConsoleAppSolution";
        Solution solution = msbuild.buildConsoleApp(name, location, solutionName);
        assertEquals(solutionName, solution.getName());
    }

}
