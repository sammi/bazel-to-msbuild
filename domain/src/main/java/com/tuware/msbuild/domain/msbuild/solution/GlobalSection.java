package com.tuware.msbuild.domain.msbuild.solution;

import com.tuware.msbuild.domain.Pair;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class GlobalSection {
    private VsPackage vsPackage;
    private Phase phase;
    List<Pair<String, String>> configList;
}
