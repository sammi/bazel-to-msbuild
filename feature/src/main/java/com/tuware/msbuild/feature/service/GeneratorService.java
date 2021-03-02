package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.template.ProjectTemplateData;
import org.springframework.stereotype.Component;

@Component
public class GeneratorService {

    private Generator<ProjectTemplateData> cppProjectGenerator;
    private Generator<Project> projectFilterGenerator;
    private Generator<Solution> solutionGenerator;
    private Generator<Object> projectUserGenerator;

    public GeneratorService(
            Generator<ProjectTemplateData> cppProjectGenerator,
            Generator<Project> projectFilterGenerator,
            Generator<Solution> solutionGenerator,
            Generator<Object> projectUserGenerator
    ) {
        this.cppProjectGenerator = cppProjectGenerator;
        this.projectFilterGenerator = projectFilterGenerator;
        this.solutionGenerator = solutionGenerator;
        this.projectUserGenerator = projectUserGenerator;
    }

    public String generateProjectXml(ProjectTemplateData projectTemplateData) throws AdapterException {
        return cppProjectGenerator.generate(projectTemplateData);
    }

    public String generateCppProjectFilterXml(Project project) throws AdapterException {
        return projectFilterGenerator.generate(project);
    }

    public String generateSolution(Solution solution) throws AdapterException {
        return solutionGenerator.generate(solution);
    }

    public String generateProjectUserXml(Project project) throws AdapterException {
        return projectUserGenerator.generate(project);
    }
}
