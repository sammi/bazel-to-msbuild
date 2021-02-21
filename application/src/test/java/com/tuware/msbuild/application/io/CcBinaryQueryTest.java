package com.tuware.msbuild.application.io;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.application.adapter.DefaultApplicationAdapter;
import com.tuware.msbuild.contract.io.BazelQueryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryQueryTest {

    private CcBinaryQuery ccBinaryQuery;

    @BeforeEach
    void setup() {
        DefaultApplicationAdapter defaultApplicationAdapter = new DefaultApplicationAdapter(new Handlebars());
        ccBinaryQuery = new CcBinaryQuery(new PackageQuery(defaultApplicationAdapter), new ActionQuery(defaultApplicationAdapter));
    }

    @Test
    void query() throws IOException, BazelQueryException {
        File file = new ClassPathResource("stage1").getFile();
        List<String> sourceFiles = ccBinaryQuery.query(file.getAbsolutePath(), "...");
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException, BazelQueryException {
        List<String> sourceFiles = ccBinaryQuery.cquery(new ClassPathResource("stage3").getFile().getAbsolutePath(), "...");
        assertEquals(3, sourceFiles.size());
    }
}
