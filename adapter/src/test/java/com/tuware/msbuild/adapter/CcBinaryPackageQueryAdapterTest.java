package com.tuware.msbuild.adapter;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.BazelQueryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcBinaryPackageQueryAdapterTest {

    private BazelQueryAdapter<Build.QueryResult> packageQuery;
    private BazelQueryAdapter<AnalysisProtosV2.CqueryResult> actionQuery;
    private CcBinaryPackageQueryAdapter ccBinaryPackageQueryAdapter;
    private CcBinaryActionQueryAdapter ccBinaryActionQueryAdapter;

    @BeforeEach
    void setup() {
        BazelProcessBuilder bazelProcessBuilder = new BazelProcessBuilder();
        this.packageQuery = new PackageQueryAdapter(bazelProcessBuilder);
        this.actionQuery = new ActionQueryAdapter(bazelProcessBuilder);
        this.ccBinaryPackageQueryAdapter = new CcBinaryPackageQueryAdapter();
        this.ccBinaryActionQueryAdapter = new CcBinaryActionQueryAdapter();

    }

    @Test
    void query() throws IOException, AdapterException {
        File file = new ClassPathResource("stage1").getFile();
        Build.QueryResult queryResult = packageQuery.query(file.getAbsolutePath(), "...");
        List<String> sourceFiles = ccBinaryPackageQueryAdapter.getCppSourceFiles(queryResult);
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException, AdapterException {
        File file = new ClassPathResource("stage3").getFile();
        AnalysisProtosV2.CqueryResult cqueryResult = actionQuery.query(file.getAbsolutePath(), "...");
        List<String> sourceFiles = ccBinaryActionQueryAdapter.getCppSourceFiles(cqueryResult);
        assertEquals(3, sourceFiles.size());
    }
}
