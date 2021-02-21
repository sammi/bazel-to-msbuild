package com.tuware.msbuild.contract.msbuild.project;


import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectConfiguration {
    private String include;
    private String configuration;
    private String platform;
}
