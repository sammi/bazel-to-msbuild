package com.tuware.msbuild.contract.msbuild.solution;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class Global {
    private List<GlobalSection> globalSectionList;
}
