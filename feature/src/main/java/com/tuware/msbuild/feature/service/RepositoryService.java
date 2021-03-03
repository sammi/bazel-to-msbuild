package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Repository;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class RepositoryService {
    private static final String S_S_VCXPROJ = "%s/%s.vcxproj";
    private static final String S_S_VCXPROJ_FILTERS = "%s/%s.vcxproj.filters";
    private static final String S_VCXPROJ_USER = "%s/%s.vcxproj.user";
    private static final String S_S_SLN = "%s/%s.sln";

    private Repository<Path, String> repository;

    public RepositoryService(Repository<Path, String> repository) {
        this.repository = repository;
    }

    public void saveProject(Path msbuildSolutionFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_S_VCXPROJ, msbuildSolutionFolder.toAbsolutePath(), projectName);
        repository.save(Paths.get(path), xml);
    }

    public void saveProjectUser(Path msbuildSolutionFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_VCXPROJ_USER, msbuildSolutionFolder.toAbsolutePath(), projectName);
        repository.save(Paths.get(path), xml);
    }

    public void saveProjectFilter(Path msbuildSolutionFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_S_VCXPROJ_FILTERS, msbuildSolutionFolder.toAbsolutePath(), projectName);
        repository.save(Paths.get(path), xml);
    }

    public void saveSolution(Path msbuildSolutionFolder, String projectName, String xml) throws AdapterException {
        String path = String.format(S_S_SLN, msbuildSolutionFolder.toAbsolutePath(), projectName);
        repository.save(Paths.get(path), xml);
    }
}
