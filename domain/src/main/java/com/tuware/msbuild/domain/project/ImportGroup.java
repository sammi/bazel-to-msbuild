package com.tuware.msbuild.domain.project;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ImportGroup {
    private String label;
    private List<Import> importList;
}
