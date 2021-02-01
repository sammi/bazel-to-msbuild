package com.tuware.msbuild.domain.property;

import lombok.*;

import javax.xml.bind.annotation.XmlValue;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class ApplicationTypeRevision {
    @XmlValue
    private boolean value;
}
