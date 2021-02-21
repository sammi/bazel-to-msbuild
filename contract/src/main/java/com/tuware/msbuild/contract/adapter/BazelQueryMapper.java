package com.tuware.msbuild.contract.adapter;

import com.google.devtools.build.lib.analysis.AnalysisProtosV2;
import com.google.devtools.build.lib.query2.proto.proto2api.Build;

import java.util.List;

public interface BazelQueryMapper {
    List<String> getCcBinarySourceFromPackage(Build.QueryResult queryResult);
    List<String> getCcBinarySourceFromAction(AnalysisProtosV2.CqueryResult cqueryResult);
}
