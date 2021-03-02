package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Repository;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import com.tuware.msbuild.contract.template.ProjectTemplateData;
import com.tuware.msbuild.feature.service.ComposerService;
import com.tuware.msbuild.feature.service.ExtractorService;
import com.tuware.msbuild.feature.service.GeneratorService;
import com.tuware.msbuild.feature.service.QueryService;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class CppProjectFeature implements Feature {

    public static final String S_S_VCXPROJ = "%s/%s.vcxproj";
    private QueryService queryService;
    private ComposerService composerService;
    private GeneratorService generatorService;
    private ExtractorService extractorService;
    private Repository<Path, String> repository;

    public CppProjectFeature(
            QueryService queryService,
            ComposerService composerService,
            ExtractorService extractorService,
            GeneratorService generatorService,
            Repository<Path, String> repository
    ) {
        this.queryService = queryService;
        this.composerService = composerService;
        this.extractorService = extractorService;
        this.generatorService = generatorService;
        this.repository = repository;
    }

    @Override
    public void buildMsbuildSolutionFromBazelWorkspace(Path bazelWorkspaceFolder, Path msbuildSolutionFolder) throws FeatureException {
        Build.QueryResult queryResult = queryService.query(bazelWorkspaceFolder);
        try {
            buildCppProject(msbuildSolutionFolder, queryResult);
            buildCppProjectFilter(msbuildSolutionFolder, queryResult);
            buildCppProjectUser(msbuildSolutionFolder);
            buildSolution(msbuildSolutionFolder, queryResult);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

    private void buildCppProject(Path msbuildSolutionFolder, Build.QueryResult queryResult) throws AdapterException {
        ProjectSeed projectSeed = extractorService.extractProject(queryResult);
        ProjectTemplateData projectTemplateData = composerService.composeProjectTemplateData(projectSeed);
        String xml = generatorService.generateProjectXml(projectTemplateData);
        String path = String.format(S_S_VCXPROJ, msbuildSolutionFolder.toAbsolutePath(), "project");
        repository.save(Paths.get(path), xml);
    }

    private void buildCppProjectFilter(Path msbuildSolutionFolder, Build.QueryResult queryResult) throws AdapterException {
        ProjectFilerSeed projectFilerSeed = extractorService.extractProjectFilter(queryResult);
        Project project = composerService.composeCppProjectFilterTemplateData(projectFilerSeed);
        String path = String.format(S_S_VCXPROJ, msbuildSolutionFolder.toAbsolutePath(), "project.filter");
        String xml = generatorService.generateCppProjectFilterXml(project);
        repository.save(Paths.get(path), xml);
    }

    private void buildCppProjectUser(Path msbuildSolutionFolder) throws AdapterException {
        Project project = composerService.composeCppProjectUserTemplateData(new Object());
        String path = String.format(S_S_VCXPROJ, msbuildSolutionFolder.toAbsolutePath(), "project.user");
        String xml = generatorService.generateProjectUserXml(project);
        repository.save(Paths.get(path), xml);
    }

    private void buildSolution(Path msbuildSolutionFolder, Build.QueryResult queryResult) throws AdapterException {
        SolutionSeed solutionSeed = extractorService.extractSolution(queryResult);
        Solution solution = composerService.composeSolutionTemplateData(solutionSeed);
        String path = String.format("%s/%s.sln", msbuildSolutionFolder.toAbsolutePath(), "solution");
        String xml = generatorService.generateSolution(solution);
        repository.save(Paths.get(path), xml);
    }


}
