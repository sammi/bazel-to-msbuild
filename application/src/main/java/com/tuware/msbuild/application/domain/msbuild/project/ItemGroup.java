package com.tuware.msbuild.application.domain.msbuild.project;

import com.tuware.msbuild.application.domain.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.application.domain.msbuild.midl.Midl;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ItemGroup {
    private String label;
    private List<Midl> midlList;
    private List<ProjectConfiguration> projectConfigurationList;
    private List<ClInclude> clIncludeList;
    private List<ApplicationDefinition> applicationDefinitionList;
    private List<None> noneList;
    private List<Filter> filterList;
    private List<ClCompile> clCompileList;
}
