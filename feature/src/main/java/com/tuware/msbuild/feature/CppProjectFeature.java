package com.tuware.msbuild.feature;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.*;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.template.CppProjectTemplateData;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class CppProjectFeature implements Feature {

    private Provider provider;
    private Query<Build.QueryResult> query;
    private Extractor<Build.QueryResult, ProjectSeed> extractor;
    private Composer<CppProjectTemplateData, ProjectSeed> composer;
    private Generator<CppProjectTemplateData> generator;
    private Repository<Path, String> repository;

    public CppProjectFeature(
            Provider provider,
            Query<Build.QueryResult> query,
            Extractor<Build.QueryResult, ProjectSeed> extractor,
            Composer<CppProjectTemplateData, ProjectSeed> composer,
            Generator<CppProjectTemplateData> generator,
            Repository<Path, String> repository
    ) {
        this.provider = provider;
        this.query = query;
        this.extractor = extractor;
        this.composer = composer;
        this.generator = generator;
        this.repository = repository;
    }

    @Override
    public void buildMsbuildSolutionFromBazelWorkspace(Path bazelWorkspaceFolder, Path msbuildProjectFile) throws FeatureException {

        List<String> bazelCommands = provider.provide();

        Build.QueryResult queryResult;
        try {
            queryResult = query.query(bazelWorkspaceFolder, bazelCommands);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }

        ProjectSeed projectSeed = extractor.extract(queryResult);

        CppProjectTemplateData cppProjectTemplateData = composer.compose(projectSeed);
        try {
            String xml = generator.generate(cppProjectTemplateData);
            repository.save(msbuildProjectFile, xml);
        } catch (AdapterException e) {
            throw new FeatureException(e);
        }
    }

}
