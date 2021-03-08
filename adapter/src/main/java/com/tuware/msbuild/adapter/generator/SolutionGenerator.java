package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SolutionGenerator implements Generator<Solution> {

    private TemplateBuilder templateBuilder;

    public SolutionGenerator() {
        this.templateBuilder = new TemplateBuilder();
    }

    @Override
    public String generate(Solution solution) throws AdapterException {
        try {
            return templateBuilder.compileFromTemplateFile(TemplatePaths.solutionTemplate(), solution);
        } catch (IOException e) {
            throw new AdapterException("Failed to compile solution xml content.", e);
        }
    }
}
