package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import com.tuware.msbuild.contract.template.ProjectTemplate;
import com.tuware.msbuild.feature.service.*;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

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
    public void buildSolution(Path bazelWorkspaceFolder, Path msbuildSolutionFolder, String solutionName) throws FeatureException {
        try {
            Build.QueryResult queryResult = queryService.query(bazelWorkspaceFolder);
            List<ProjectSeed> projectSeedList = extractorService.extractProjectSeedList(queryResult);
            buildProjects(msbuildSolutionFolder, projectSeedList);
            buildProjectFilters(msbuildSolutionFolder, projectSeedList);
            buildProjectUsers(msbuildSolutionFolder, projectSeedList);
            buildSolution(msbuildSolutionFolder, solutionName, projectSeedList);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

    private void buildProjects(Path msbuildSolutionFolder, List<ProjectSeed> projectSeedList) throws AdapterException {
        for (ProjectSeed projectSeed : projectSeedList) {
            ProjectTemplate projectTemplate = composerService.composeProjectTemplateData(projectSeed);
            String xml = generatorService.generateProjectXml(projectTemplate);
            repositoryService.saveProject(msbuildSolutionFolder, projectSeed.getFolder(), projectSeed.getName(), xml);
        }
    }

    private void buildProjectFilters(Path msbuildSolutionFolder, List<ProjectSeed> projectSeedList) throws AdapterException {
        for (ProjectSeed projectSeed : projectSeedList) {
            ProjectFilerSeed projectFilerSeed = extractorService.extractProjectFilter();
            projectFilerSeed.setSourceFileList(projectSeed.getSourceFileList());
            Project project = composerService.composeCppProjectFilterTemplateData(projectFilerSeed);
            String xml = generatorService.generateCppProjectFilterXml(project);
            repositoryService.saveProjectFilter(msbuildSolutionFolder, projectSeed.getFolder(), projectSeed.getName(), xml);
        }
    }

    private void buildProjectUsers(Path msbuildSolutionFolder, List<ProjectSeed> projectSeedList) throws AdapterException {
        for(ProjectSeed projectSeed: projectSeedList) {
            Project project = composerService.composeCppProjectUserTemplateData(new Object());
            String xml = generatorService.generateProjectUserXml(project);
            repositoryService.saveProjectUser(msbuildSolutionFolder, projectSeed.getFolder(), projectSeed.getName(), xml);
        }
    }

    private void buildSolution(Path msbuildSolutionFolder, String solutionName, List<ProjectSeed> projectSeedList) throws AdapterException {

        SolutionSeed solutionSeed = extractorService.buildSolutionSeed(
                solutionName,
                msbuildSolutionFolder,
                projectSeedList
        );
        Solution solution = composerService.composeSolutionTemplateData(solutionSeed);

        String xml = generatorService.generateSolution(solution);
        repositoryService.saveSolution(msbuildSolutionFolder, solutionName, xml);
    }

}
