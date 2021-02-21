package com.tuware.msbuild.adapters.bazel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryTest {

    private CcBinary ccBinary;

    @BeforeEach
    void setup() {
        ccBinary = new CcBinary(new PackageQuery(), new ActionQuery());
    }

    @Test
    void query() throws IOException, InterruptedException {
        File file = new ClassPathResource("stage1").getFile();
        List<String> sourceFiles = ccBinary.query(file.getAbsolutePath(), "...");
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException, InterruptedException {
        List<String> sourceFiles = ccBinary.cquery(new ClassPathResource("stage3").getFile().getAbsolutePath(), "...");
        assertEquals(3, sourceFiles.size());
    }
}
