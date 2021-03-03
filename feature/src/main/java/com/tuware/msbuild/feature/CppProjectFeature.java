package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import com.tuware.msbuild.contract.template.ProjectTemplateData;
import com.tuware.msbuild.feature.service.*;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.UUID;

@Component
public class CppProjectFeature implements Feature {

    private QueryService queryService;
    private ComposerService composerService;
    private GeneratorService generatorService;
    private ExtractorService extractorService;
    private RepositoryService repositoryService;

    public CppProjectFeature(
            QueryService queryService,
            ComposerService composerService,
            ExtractorService extractorService,
            GeneratorService generatorService,
            RepositoryService repositoryService
    ) {
        this.queryService = queryService;
        this.composerService = composerService;
        this.extractorService = extractorService;
        this.generatorService = generatorService;
        this.repositoryService = repositoryService;
    }

    @Override
    public void buildMsbuildSolutionFromBazelWorkspace(Path bazelWorkspaceFolder, Path msbuildSolutionFolder, String projectName) throws FeatureException {
        try {

            Build.QueryResult queryResult = queryService.query(bazelWorkspaceFolder);

            ProjectSeed projectSeed = extractorService.extractProject(queryResult);

            buildCppProject(msbuildSolutionFolder, projectSeed, projectName);

            buildSolution(msbuildSolutionFolder, projectName, UUID.randomUUID(), projectSeed.getProjectGuid());

            buildCppProjectFilter(msbuildSolutionFolder, projectSeed, projectName);
            buildCppProjectUser(msbuildSolutionFolder, projectName);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

    private void buildCppProject(Path msbuildSolutionFolder, ProjectSeed projectSeed, String projectName) throws AdapterException {
        ProjectTemplateData projectTemplateData = composerService.composeProjectTemplateData(projectSeed);
        String xml = generatorService.generateProjectXml(projectTemplateData);
        repositoryService.saveProject(msbuildSolutionFolder, projectName, xml);
    }

    private void buildCppProjectFilter(Path msbuildSolutionFolder, ProjectSeed projectSeed, String projectName) throws AdapterException {
        ProjectFilerSeed projectFilerSeed = extractorService.extractProjectFilter();
        projectFilerSeed.setSourceFile(projectSeed.getCppFileName());
        Project project = composerService.composeCppProjectFilterTemplateData(projectFilerSeed);
        String xml = generatorService.generateCppProjectFilterXml(project);
        repositoryService.saveProjectFilter(msbuildSolutionFolder, projectName, xml);
    }

    private void buildCppProjectUser(Path msbuildSolutionFolder, String projectName) throws AdapterException {
        Project project = composerService.composeCppProjectUserTemplateData(new Object());
        String xml = generatorService.generateProjectUserXml(project);
        repositoryService.saveProjectUser(msbuildSolutionFolder, projectName, xml);
    }

    private void buildSolution(Path msbuildSolutionFolder, String projectName, UUID solutionUUID, UUID projectUUID) throws AdapterException {
        SolutionSeed solutionSeed = extractorService.buildSolutionSeed(
                msbuildSolutionFolder,
                projectName,
                solutionUUID,
                projectUUID
        );
        Solution solution = composerService.composeSolutionTemplateData(solutionSeed);
        String xml = generatorService.generateSolution(solution);
        repositoryService.saveSolution(msbuildSolutionFolder, projectName, xml);
    }

}
