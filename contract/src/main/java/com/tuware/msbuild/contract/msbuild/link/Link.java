package com.tuware.msbuild.contract.msbuild.link;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Link {
    private String subSystem;
    private Boolean generateDebugInformation;
    private Boolean enableCOMDATFolding;
    private Boolean optimizeReferences;
    private Boolean generateWindowsMetadata;
}
