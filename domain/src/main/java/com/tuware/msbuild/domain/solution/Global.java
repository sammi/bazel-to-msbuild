package com.tuware.msbuild.domain.solution;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Global {
    private List<GlobalSection> globalSectionList;
}
