package com.tuware.msbuild.contract.msbuild.solution;

import com.tuware.msbuild.contract.msbuild.Pair;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class GlobalSection {
    List<Pair<String, String>> configList;
    private VsPackage vsPackage;
    private Phase phase;
}
