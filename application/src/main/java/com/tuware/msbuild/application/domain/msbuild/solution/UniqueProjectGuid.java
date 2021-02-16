package com.tuware.msbuild.application.domain.msbuild.solution;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class UniqueProjectGuid {
    private UUID value;
    public String getValue() {
        return String.format("{%s}", value);
    }
}
