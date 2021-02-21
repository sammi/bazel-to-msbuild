package com.tuware.msbuild.adapter;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.contract.adapter.AdapterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryQueryAdapterTest {

    private CcBinaryQueryAdapter ccBinaryQueryAdapter;

    @BeforeEach
    void setup() {
        DefaultApplicationAdapter defaultApplicationAdapter = new DefaultApplicationAdapter(new Handlebars());
        ccBinaryQueryAdapter = new CcBinaryQueryAdapter(new PackageQueryAdapter(defaultApplicationAdapter), new ActionQueryAdapter(defaultApplicationAdapter));
    }

    @Test
    void query() throws IOException, AdapterException {
        File file = new ClassPathResource("stage1").getFile();
        List<String> sourceFiles = ccBinaryQueryAdapter.query(file.getAbsolutePath(), "...");
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException, AdapterException {
        List<String> sourceFiles = ccBinaryQueryAdapter.cquery(new ClassPathResource("stage3").getFile().getAbsolutePath(), "...");
        assertEquals(3, sourceFiles.size());
    }
}
