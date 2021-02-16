package com.tuware.msbuild.domain.link;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class EnableCOMDATFolding {
    private boolean value;
}
