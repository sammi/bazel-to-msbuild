package com.tuware.msbuild.query;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryTest {

    @Test
    void query() throws IOException {
        CcBinary ccBinary = new CcBinary();
        List<String> sourceFiles = ccBinary.query(new ClassPathResource("stage1").getFile().getAbsolutePath(), "...");
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
