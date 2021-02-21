package com.tuware.msbuild.model.msbuild.link;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class GenerateWindowsMetadata {
    private boolean value;
}
