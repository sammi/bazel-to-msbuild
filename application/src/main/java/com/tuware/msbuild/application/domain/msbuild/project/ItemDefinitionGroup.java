package com.tuware.msbuild.application.domain.msbuild.project;

import com.tuware.msbuild.application.domain.msbuild.link.Link;
import com.tuware.msbuild.application.domain.msbuild.clcompile.ClCompile;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ItemDefinitionGroup {
    private String condition;
    private List<ClCompile> clCompileList;
    private List<Link> linkList;
}
