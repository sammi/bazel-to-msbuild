package com.tuware.msbuild.domain;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Solution {
    private String name;
}
