package com.tuware.msbuild.adapter.generator;

import com.tuware.msbuild.contract.adapter.Generator;
import org.springframework.stereotype.Component;

@Component
public class ProjectUserGenerator implements Generator<Object> {

    @Override
    public String generate(Object templateData) {
        return "";
    }
}
