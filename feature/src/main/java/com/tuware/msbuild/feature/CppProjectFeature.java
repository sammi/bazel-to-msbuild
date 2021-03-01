package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Repository;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import com.tuware.msbuild.feature.service.ComposerService;
import com.tuware.msbuild.feature.service.ExtractorService;
import com.tuware.msbuild.feature.service.GeneratorService;
import com.tuware.msbuild.feature.service.QueryService;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class CppProjectFeature implements Feature {

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
    public void buildMsbuildSolutionFromBazelWorkspace(Path bazelWorkspaceFolder, Path msbuildProjectFile) throws FeatureException {
        Build.QueryResult queryResult = queryService.query(bazelWorkspaceFolder);
        try {
            buildCppProject(msbuildProjectFile, queryResult);
            buildCppProjectFilter(msbuildProjectFile, queryResult);
            buildCppProjectUser(msbuildProjectFile);
            buildSolution(msbuildProjectFile, queryResult);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

    private void buildSolution(Path msbuildProjectFile, Build.QueryResult queryResult) throws AdapterException {
        SolutionSeed solutionSeed = extractorService.extractSolution(queryResult);
        Solution solution = composerService.composeSolutionTemplateData(solutionSeed);
        String xml = generatorService.generateSolution(solution);
        repository.save(msbuildProjectFile, xml);
    }

    private void buildCppProjectUser(Path msbuildProjectFile) throws AdapterException {
        Project project = composerService.composeCppProjectUserTemplateData(new Object());
        String xml = generatorService.generateProjectUserXml(project);
        repository.save(msbuildProjectFile, xml);
    }

    private void buildCppProject(Path msbuildProjectFile, Build.QueryResult queryResult) throws AdapterException {
        ProjectSeed projectSeed = extractorService.extractProject(queryResult);
        CppProjectTemplateData cppProjectTemplateData = composerService.composeCppProjectTemplateData(projectSeed);
        String xml = generatorService.generateCppProjectXml(cppProjectTemplateData);
        repository.save(msbuildProjectFile, xml);
    }

    private void buildCppProjectFilter(Path msbuildProjectFile, Build.QueryResult queryResult) throws AdapterException {
        ProjectFilerSeed projectFilerSeed = extractorService.extractProjectFilter(queryResult);
        Project project = composerService.composeCppProjectFilterTemplateData(projectFilerSeed);
        String xml = generatorService.generateCppProjectFilterXml(project);
        repository.save(msbuildProjectFile, xml);
    }

}
