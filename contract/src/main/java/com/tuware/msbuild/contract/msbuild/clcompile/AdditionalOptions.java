package com.tuware.msbuild.contract.msbuild.clcompile;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class AdditionalOptions {
    private List<String> additionalOptionList;
}
