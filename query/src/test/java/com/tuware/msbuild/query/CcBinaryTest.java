package com.tuware.msbuild.query;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryTest {

    @Test
    void query() throws IOException {
        CcBinary ccBinary = new CcBinary();
        List<String> sourceFiles = ccBinary.query(
                "C:\\Users\\songy\\source\\repos\\blackfin\\bazel\\stage1", "..."
        );
        sourceFiles.forEach(System.out::println);
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException {
        CcBinary ccBinary = new CcBinary();
        List<String> sourceFiles = ccBinary.cquery(
                "C:\\Users\\songy\\source\\repos\\blackfin\\bazel\\stage3", "..."
        );
        sourceFiles.forEach(System.out::println);
        assertEquals(3, sourceFiles.size());
    }
}
