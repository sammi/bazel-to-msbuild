package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.Composer;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import com.tuware.msbuild.contract.template.ProjectTemplateData;
import org.springframework.stereotype.Component;

@Component
public class ComposerService {
    private Composer<ProjectTemplateData, ProjectSeed> projectComposer;
    private Composer<Project, ProjectFilerSeed> projectFilterComposer;
    private Composer<Project, Object> projectUserComposer;
    private Composer<Solution, SolutionSeed> solutionComposer;

    public ComposerService(
            Composer<ProjectTemplateData, ProjectSeed> projectComposer,
            Composer<Project, ProjectFilerSeed> projectFilterComposer,
            Composer<Project, Object> projectUserComposer,
            Composer<Solution, SolutionSeed> solutionComposer
    ) {
        this.projectComposer = projectComposer;
        this.projectFilterComposer = projectFilterComposer;
        this.projectUserComposer = projectUserComposer;
        this.solutionComposer = solutionComposer;
    }

    public ProjectTemplateData composeProjectTemplateData(ProjectSeed projectSeed) {
        return projectComposer.compose(projectSeed);
    }

    public Project composeCppProjectUserTemplateData(Object projectUserSeed) {
        return projectUserComposer.compose(projectUserSeed);
    }

    public Project composeCppProjectFilterTemplateData(ProjectFilerSeed projectFilerSeed) {
        return projectFilterComposer.compose(projectFilerSeed);
    }

    public Solution composeSolutionTemplateData(SolutionSeed solutionSeed) {
        return solutionComposer.compose(solutionSeed);
    }

}
