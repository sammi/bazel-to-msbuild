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

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Component
public class CppProjectFeature implements Feature {

    private final QueryService queryService;
    private final ComposerService composerService;
    private final GeneratorService generatorService;
    private final ExtractorService extractorService;
    private final RepositoryService repositoryService;

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
    public void buildSolution(Path bazelWorkspaceFolder, Path msbuildSolutionFolder, String projectName, UUID solutionUuid, UUID projectUuid) throws FeatureException {
        try {
            Build.QueryResult queryResult = queryService.query(bazelWorkspaceFolder);
            List<ProjectSeed> projectSeedList = extractorService.extractProjectSeedList(queryResult);
            buildProjects(msbuildSolutionFolder, projectSeedList);
            buildSolution(msbuildSolutionFolder, projectName, solutionUuid, projectSeedList);
            buildProjectFilters(msbuildSolutionFolder, projectSeedList);
            buildProjectUser(msbuildSolutionFolder, projectName);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

    private void buildProjects(Path msbuildSolutionFolder, List<ProjectSeed> projectSeedList) throws AdapterException {
        for (ProjectSeed projectSeed : projectSeedList) {
            ProjectTemplateData projectTemplateData = composerService.composeProjectTemplateData(projectSeed);
            String xml = generatorService.generateProjectXml(projectTemplateData);
            repositoryService.saveProject(msbuildSolutionFolder, projectSeed.getPath(), projectSeed.getName(), xml);
        }
    }

    private void buildProjectFilters(Path msbuildSolutionFolder, List<ProjectSeed> projectSeedList) throws AdapterException {
        for (ProjectSeed projectSeed : projectSeedList) {
            ProjectFilerSeed projectFilerSeed = extractorService.extractProjectFilter();
            projectFilerSeed.setSourceFileList(projectSeed.getSourceFileList());
            Project project = composerService.composeCppProjectFilterTemplateData(projectFilerSeed);
            String xml = generatorService.generateCppProjectFilterXml(project);
            repositoryService.saveProjectFilter(msbuildSolutionFolder, projectSeed.getPath() + File.separator + projectSeed.getName(), xml);
        }
    }

    private void buildProjectUser(Path msbuildSolutionFolder, String projectName) throws AdapterException {
        Project project = composerService.composeCppProjectUserTemplateData(new Object());
        String xml = generatorService.generateProjectUserXml(project);
        repositoryService.saveProjectUser(msbuildSolutionFolder, projectName, xml);
    }

    private void buildSolution(Path msbuildSolutionFolder, String projectName, UUID solutionUuid, List<ProjectSeed> projectSeedList) throws AdapterException {

        SolutionSeed solutionSeed = extractorService.buildSolutionSeed(
                solutionUuid,
                projectName,
                msbuildSolutionFolder,
                projectSeedList
        );
        Solution solution = composerService.composeSolutionTemplateData(solutionSeed);

        String xml = generatorService.generateSolution(solution);
        repositoryService.saveSolution(msbuildSolutionFolder, projectName, xml);
    }

}
