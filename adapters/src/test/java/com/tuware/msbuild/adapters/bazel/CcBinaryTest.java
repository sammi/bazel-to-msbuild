package com.tuware.msbuild.adapters.bazel;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryTest {

    @Test
    void query() throws IOException {
        CcBinary ccBinary = new CcBinary();
        File file = new ClassPathResource("stage1").getFile();
        List<String> sourceFiles = ccBinary.query(file.getAbsolutePath(), "...");
        sourceFiles.forEach(System.out::println);
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException {
        CcBinary ccBinary = new CcBinary();
        List<String> sourceFiles = ccBinary.cquery(new ClassPathResource("stage3").getFile().getAbsolutePath(), "...");
        sourceFiles.forEach(System.out::println);
        assertEquals(3, sourceFiles.size());
    }
}
