package com.tuware.msbuild.contract.msbuild.project;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Target {
    private String name;
    private String beforeTargets;
    private List<PropertyGroup> propertyGroupList;
    private List<Error> errorList;
}
