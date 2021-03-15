package com.tuware.msbuild.contract.msbuild.project;

import com.tuware.msbuild.contract.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.contract.msbuild.link.Link;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ItemDefinitionGroup {
    private String condition;
    private ClCompile clCompile;
    private Link link;
}
