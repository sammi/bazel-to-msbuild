package com.tuware.msbuild.contract.adapter;

import java.io.IOException;

public interface ApplicationAdapter {

    String buildXml(String resourcePath, Object data) throws AdapterException;

    Process startBazelQueryProcess(String bazelProjectRootPath, String command, String query) throws IOException, AdapterException;

}
