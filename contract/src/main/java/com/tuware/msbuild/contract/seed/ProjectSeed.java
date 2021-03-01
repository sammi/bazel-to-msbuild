package com.tuware.msbuild.contract.seed;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectSeed {
    private String cppFileName;
    private String projectGuild;
}
