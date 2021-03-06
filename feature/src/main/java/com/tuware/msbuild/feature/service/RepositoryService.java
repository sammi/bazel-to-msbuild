package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Repository;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class RepositoryService {
    private static final String S_S_VCXPROJ = "%s/%s/%s.vcxproj";
    private static final String S_S_VCXPROJ_FILTERS = "%s/%s/%s.vcxproj.filters";
    private static final String S_VCXPROJ_USER = "%s/%s/%s.vcxproj.user";
    private static final String S_S_SLN = "%s/%s.sln";

    private final Repository<Path, String> repository;

    public RepositoryService(Repository<Path, String> repository) {
        this.repository = repository;
    }

    public void saveProject(Path msbuildSolutionFolder, Path projectFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_S_VCXPROJ, msbuildSolutionFolder.toAbsolutePath(), projectFolder.toFile().getPath(), projectName);
        repository.save(Paths.get(path), xml);
    }

    public void saveProjectUser(Path msbuildSolutionFolder, Path projectFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_VCXPROJ_USER, msbuildSolutionFolder.toAbsolutePath(), projectFolder.toFile().getPath(), projectName);
        repository.save(Paths.get(path), xml);
    }

    public void saveProjectFilter(Path msbuildSolutionFolder, Path projectFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_S_VCXPROJ_FILTERS, msbuildSolutionFolder.toAbsolutePath(), projectFolder.toFile().getPath(), projectName);
        repository.save(Paths.get(path), xml);
    }

    public void saveSolution(Path msbuildSolutionFolder, String solutionName, String xml) throws AdapterException {
        String path = String.format(S_S_SLN, msbuildSolutionFolder.toAbsolutePath(), solutionName);
        repository.save(Paths.get(path), xml);
    }
}
