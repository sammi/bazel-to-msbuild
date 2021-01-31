package com.tuware.msbuild.domain.solution;

import javafx.util.Pair;
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
