package com.tuware.msbuild.contract.template;

import com.tuware.msbuild.contract.msbuild.project.ItemDefinitionGroup;
import com.tuware.msbuild.contract.msbuild.project.ItemGroup;
import com.tuware.msbuild.contract.msbuild.project.PropertyGroup;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectTemplate {
    private String defaultTargets;
    private ItemGroup projectConfigurations;
    private PropertyGroup globals;
    private String cppDefaultPropsImport;
    private List<PropertyGroup> configurationPropertyGroupList;
    private String cppPropsImport;
    private ItemGroup clCompileItemGroup;
    private ItemGroup projectReferenceItemGroup;
    private List<ItemDefinitionGroup> itemDefinitionGroupList;
}
