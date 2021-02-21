package com.tuware.msbuild.model.msbuild.solution;

import com.tuware.msbuild.model.Pair;
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
