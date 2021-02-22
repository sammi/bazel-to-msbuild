package com.tuware.msbuild.adapter.mapper;

import com.google.devtools.build.lib.query2.proto.proto2api.Build;
import com.tuware.msbuild.adapter.query.BazelWindowsProcessBuilder;
import com.tuware.msbuild.adapter.query.PackageQueryAdapter;
import com.tuware.msbuild.contract.adapter.AdapterException;
import com.tuware.msbuild.contract.adapter.QueryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtractPackageMapperTest {

    private QueryAdapter<Build.QueryResult> packageQuery;
    private ExtractPackageMapper ccBinaryPackageQueryAdapter;

    @BeforeEach
    void setup() {
        BazelWindowsProcessBuilder bazelWindowsProcessBuilder = new BazelWindowsProcessBuilder();
        this.packageQuery = new PackageQueryAdapter(bazelWindowsProcessBuilder);
        this.ccBinaryPackageQueryAdapter = new ExtractPackageMapper();
    }

    @Test
    void query() throws IOException, AdapterException {
        File file = new ClassPathResource("stage1").getFile();
        Build.QueryResult queryResult = packageQuery.query(file.getAbsolutePath(), "...");
        List<String> sourceFiles = ccBinaryPackageQueryAdapter.extract(queryResult);
        assertEquals(1, sourceFiles.size());
    }

}
