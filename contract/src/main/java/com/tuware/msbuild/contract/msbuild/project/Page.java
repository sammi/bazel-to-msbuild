package com.tuware.msbuild.contract.msbuild.project;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Page {
    private String include;
    private String subType;
    private String filter;
    private boolean showAllFiles;
}
