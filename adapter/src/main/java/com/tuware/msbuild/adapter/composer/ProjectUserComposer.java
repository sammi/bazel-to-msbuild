package com.tuware.msbuild.adapter.composer;

import com.tuware.msbuild.contract.adapter.ComposerAdapter;
import com.tuware.msbuild.contract.msbuild.project.Project;

public class ProjectUserComposer implements ComposerAdapter<Project> {

    @Override
    public Project compose(String cppFileName, String projectGuild) {
        return  Project.builder().toolsVersion("Current").build();
    }

}
