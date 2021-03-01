package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import org.springframework.stereotype.Component;

@Component
public class GeneratorService {

    private Generator<CppProjectTemplateData> cppProjectGenerator;
    private Generator<Project> cppProjectFilterGenerator;
    private Generator<Solution> solutionGenerator;
    private Generator<Object>  cppProjectUserGenerator;

    public GeneratorService(
            Generator<CppProjectTemplateData> cppProjectGenerator,
            Generator<Project> cppProjectFilterGenerator,
            Generator<Solution> solutionGenerator,
            Generator<Object> cppProjectUserGenerator
    ) {
        this.cppProjectGenerator = cppProjectGenerator;
        this.cppProjectFilterGenerator = cppProjectFilterGenerator;
        this.solutionGenerator = solutionGenerator;
        this.cppProjectUserGenerator = cppProjectUserGenerator;
    }

    public String generateCppProjectXml(CppProjectTemplateData cppProjectTemplateData) throws AdapterException {
        return cppProjectGenerator.generate(cppProjectTemplateData);
    }

    public String generateCppProjectFilterXml(Project project) throws AdapterException {
        return cppProjectFilterGenerator.generate(project);
    }

    public String generateSolution(Solution solution) throws AdapterException {
        return solutionGenerator.generate(solution);
    }

    public String generateProjectUserXml(Project project) throws AdapterException {
        return cppProjectUserGenerator.generate(project);
    }
}
