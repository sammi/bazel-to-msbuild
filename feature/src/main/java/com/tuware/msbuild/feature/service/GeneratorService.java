package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Generator;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.template.ProjectTemplate;
import org.springframework.stereotype.Component;

@Component
public class GeneratorService {

    private Generator<ProjectTemplate> cppProjectGenerator;
    private Generator<Project> projectFilterGenerator;
    private Generator<Solution> solutionGenerator;
    private Generator<Object> projectUserGenerator;

    public GeneratorService(
            Generator<ProjectTemplate> cppProjectGenerator,
            Generator<Project> projectFilterGenerator,
            Generator<Solution> solutionGenerator,
            Generator<Object> projectUserGenerator
    ) {
        this.cppProjectGenerator = cppProjectGenerator;
        this.projectFilterGenerator = projectFilterGenerator;
        this.solutionGenerator = solutionGenerator;
        this.projectUserGenerator = projectUserGenerator;
    }

    public String generateProjectXml(ProjectTemplate projectTemplate) throws AdapterException {
        return cppProjectGenerator.generate(projectTemplate);
    }

    public String generateProjectFilterXml(Project project) throws AdapterException {
        return projectFilterGenerator.generate(project);
    }

    public String generateSolution(Solution solution) throws AdapterException {
        return solutionGenerator.generate(solution);
    }

    public String generateProjectUserXml(Project project) throws AdapterException {
        return projectUserGenerator.generate(project);
    }
}
