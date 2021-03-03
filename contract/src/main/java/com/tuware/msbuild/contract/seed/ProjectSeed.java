package com.tuware.msbuild.contract.seed;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectSeed {
    private String cppFileName;
    private UUID projectGuid;
}
