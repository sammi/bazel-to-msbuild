package com.tuware.msbuild.contract.input;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ProjectInput {
    private String cppFileName;
    private String projectGuild;
}
