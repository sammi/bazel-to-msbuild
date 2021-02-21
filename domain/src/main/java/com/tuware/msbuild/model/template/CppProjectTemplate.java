package com.tuware.msbuild.model.template;

import com.tuware.msbuild.model.msbuild.project.ItemGroup;
import com.tuware.msbuild.model.msbuild.project.PropertyGroup;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class CppProjectTemplate {
    private String defaultTargets;
    private ItemGroup projectConfigurations;
    private PropertyGroup globals;
    private String cppDefaultPropsImport;
    private List<PropertyGroup> configurationPropertyGroupList;
    private String cppPropsImport;
    private ItemGroup clCompileItemGroup;
}
