package com.tuware.msbuild.feature.service;

import com.tuware.msbuild.contract.adapter.Composer;
import com.tuware.msbuild.contract.msbuild.project.Project;
import com.tuware.msbuild.contract.msbuild.solution.Solution;
import com.tuware.msbuild.contract.seed.ProjectFilerSeed;
import com.tuware.msbuild.contract.seed.ProjectSeed;
import com.tuware.msbuild.contract.seed.SolutionSeed;
import com.tuware.msbuild.contract.template.ProjectTemplate;
import org.springframework.stereotype.Component;

@Component
public class ComposerService {
    private final Composer<ProjectTemplate, ProjectSeed> projectComposer;
    private final Composer<Project, ProjectFilerSeed> projectFilterComposer;
    private final Composer<Project, Object> projectUserComposer;
    private final Composer<Solution, SolutionSeed> solutionComposer;

    public ComposerService(
            Composer<ProjectTemplate, ProjectSeed> projectComposer,
            Composer<Project, ProjectFilerSeed> projectFilterComposer,
            Composer<Project, Object> projectUserComposer,
            Composer<Solution, SolutionSeed> solutionComposer
    ) {
        this.projectComposer = projectComposer;
        this.projectFilterComposer = projectFilterComposer;
        this.projectUserComposer = projectUserComposer;
        this.solutionComposer = solutionComposer;
    }

    public ProjectTemplate composeProjectTemplate(ProjectSeed projectSeed) {
        return projectComposer.compose(projectSeed);
    }

    public Project composeProjectUserTemplate() {
        return projectUserComposer.compose(new Object());
    }

    public Project composeProjectFilterTemplate(ProjectFilerSeed projectFilerSeed) {
        return projectFilterComposer.compose(projectFilerSeed);
    }

    public Solution composeSolutionTemplateData(SolutionSeed solutionSeed) {
        return solutionComposer.compose(solutionSeed);
    }

}
