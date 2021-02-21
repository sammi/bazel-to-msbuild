package com.tuware.msbuild.contract.msbuild.link;

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
