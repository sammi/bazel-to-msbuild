package com.tuware.msbuild.adapter.extractor;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.adapter.query.BazelProcessBuilder;
import com.tuware.msbuild.adapter.query.PackageQuery;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.Query;
import com.tuware.msbuild.contract.input.ProjectInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CCBinaryExtractorTest {

    private Query<Build.QueryResult> packageQuery;
    private CCBinaryExtractor ccBinaryPackageQueryAdapter;

    @BeforeEach
    void setup() {
        BazelProcessBuilder bazelProcessBuilder = new BazelProcessBuilder();
        this.packageQuery = new PackageQuery(bazelProcessBuilder);
        this.ccBinaryPackageQueryAdapter = new CCBinaryExtractor();
    }

    @Test
    void query() throws IOException, AdapterException {
        File file = new ClassPathResource("stage1").getFile();
        List<String> commands = Arrays.asList(
                "cmd",
                "/c",
                "bazel",
                "--batch",
                "query",
                "...",
                "--output=proto");
        Build.QueryResult queryResult = packageQuery.query(file.getAbsolutePath(), commands);
        ProjectInput projectInput = ccBinaryPackageQueryAdapter.extract(queryResult);
        assertNotNull(projectInput.getProjectGuild());
    }

}
