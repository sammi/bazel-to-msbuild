package com.tuware.msbuild.model.msbuild.link;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class OptimizeReferences {
    private boolean value;
}
