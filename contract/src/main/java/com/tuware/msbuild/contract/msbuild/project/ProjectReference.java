package com.tuware.msbuild.contract.msbuild.project;

import com.tuware.msbuild.contract.msbuild.solution.UniqueProjectGuid;
import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectReference {
    private String include;
    private UniqueProjectGuid project;
}
