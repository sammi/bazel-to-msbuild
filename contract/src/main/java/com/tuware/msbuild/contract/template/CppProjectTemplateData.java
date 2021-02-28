package com.tuware.msbuild.contract.template;

import com.tuware.msbuild.contract.msbuild.project.ItemGroup;
import com.tuware.msbuild.contract.msbuild.project.PropertyGroup;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class CppProjectTemplateData {
    private String defaultTargets;
    private ItemGroup projectConfigurations;
    private PropertyGroup globals;
    private String cppDefaultPropsImport;
    private List<PropertyGroup> configurationPropertyGroupList;
    private String cppPropsImport;
    private ItemGroup clCompileItemGroup;
}
