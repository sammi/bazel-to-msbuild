package com.tuware.msbuild.adapter.composer;

import com.tuware.msbuild.contract.adapter.Composer;
import com.tuware.msbuild.contract.msbuild.project.Project;

public class ProjectUserComposer implements Composer<Project, Object> {

    @Override
    public Project compose(Object data) {
        return  Project.builder().toolsVersion("Current").build();
    }

}
