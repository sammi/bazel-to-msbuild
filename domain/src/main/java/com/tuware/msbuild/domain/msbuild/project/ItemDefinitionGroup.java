package com.tuware.msbuild.domain.msbuild.project;

import com.tuware.msbuild.domain.msbuild.clcompile.ClCompile;
import com.tuware.msbuild.domain.msbuild.link.Link;
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
