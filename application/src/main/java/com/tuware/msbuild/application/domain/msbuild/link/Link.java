package com.tuware.msbuild.application.domain.msbuild.link;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Link {
    private GenerateWindowsMetadata generateWindowsMetadata;
    private EnableCOMDATFolding enableCOMDATFolding;
    private OptimizeReferences optimizeReferences;
}