package com.tuware.msbuild.contract.input;

import lombok.*;

import java.nio.file.Path;
import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class SolutionInput {
    private Path solutionPath;
    private String name;
    private String location;
    private UUID projectGuid;
    private UUID solutionGuid;
}
