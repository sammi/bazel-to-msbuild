package com.tuware.msbuild.application.query;

import com.github.jknack.handlebars.Handlebars;
import com.tuware.msbuild.application.utils.ApplicationUtils;
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
        ApplicationUtils applicationUtils = new ApplicationUtils(new Handlebars());
        ccBinaryQuery = new CcBinaryQuery(new PackageQuery(applicationUtils), new ActionQuery(applicationUtils));
    }

    @Test
    void query() throws IOException, InterruptedException {
        File file = new ClassPathResource("stage1").getFile();
        List<String> sourceFiles = ccBinaryQuery.query(file.getAbsolutePath(), "...");
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException, InterruptedException {
        List<String> sourceFiles = ccBinaryQuery.cquery(new ClassPathResource("stage3").getFile().getAbsolutePath(), "...");
        assertEquals(3, sourceFiles.size());
    }
}
