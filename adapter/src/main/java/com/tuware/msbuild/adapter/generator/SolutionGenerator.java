package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import org.springframework.stereotype.Component;

@Component
public class SolutionGenerator implements Generator<Solution> {
    @Override
    public String generate(Solution templateData) {
        return "";
    }
}
