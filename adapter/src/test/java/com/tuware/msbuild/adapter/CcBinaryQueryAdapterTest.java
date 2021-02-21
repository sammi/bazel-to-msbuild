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

class CcBinaryQueryAdapterTest {

    private BazelQueryAdapter<Build.QueryResult> packageQuery;
    private BazelQueryAdapter<AnalysisProtosV2.CqueryResult> actionQuery;
    private CcBinaryQueryAdapter ccBinaryQueryAdapter;

    @BeforeEach
    void setup() {
        BazelProcessBuilder bazelProcessBuilder = new BazelProcessBuilder();
        this.packageQuery = new PackageQueryAdapter(bazelProcessBuilder);
        this.actionQuery = new ActionQueryAdapter(bazelProcessBuilder);
        ccBinaryQueryAdapter = new CcBinaryQueryAdapter();
    }

    @Test
    void query() throws IOException, AdapterException {
        File file = new ClassPathResource("stage1").getFile();
        Build.QueryResult queryResult = packageQuery.query(file.getAbsolutePath(), "...");
        List<String> sourceFiles = ccBinaryQueryAdapter.getCcBinarySourceFromPackage(queryResult);
        assertEquals(1, sourceFiles.size());
    }

    @Test
    void cquery() throws IOException, AdapterException {
        File file = new ClassPathResource("stage3").getFile();
        AnalysisProtosV2.CqueryResult cqueryResult = actionQuery.query(file.getAbsolutePath(), "...");
        List<String> sourceFiles = ccBinaryQueryAdapter.getCcBinarySourceFromAction(cqueryResult);
        assertEquals(3, sourceFiles.size());
    }
}
