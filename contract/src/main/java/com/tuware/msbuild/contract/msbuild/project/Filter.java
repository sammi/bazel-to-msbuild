package com.tuware.msbuild.contract.msbuild.project;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Filter {
    private String include;
    private UUID uniqueIdentifier;
    private String extensions;
}
