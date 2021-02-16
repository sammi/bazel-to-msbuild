package com.tuware.msbuild.application.domain.msbuild.project;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Project {
    private String toolsVersion;
    private String defaultTargets;
    private String initialTargets;
    private String sdk;
    private String treatAsLocalProperty;
    private List<PropertyGroup> propertyGroupList;
    private List<Import> importList;
    private List<ImportGroup> importGroupList;
    private List<ItemDefinitionGroup> itemDefinitionGroupList;
    private List<ItemGroup> itemGroupList;
    private List<Target> targetList;
}
