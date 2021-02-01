package com.tuware.msbuild.domain.midl;


import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Midl {
    private String include;
    private String condition;
    private String dependentUpon;
    private String filter;
    private boolean enableWindowsRuntime;
    private String metadataFileName;
    private GenerateClientFiles generateClientFiles;
    private GenerateStublessProxies generateStublessProxies;
    private GenerateTypeLibrary generateTypeLibrary;
    private HeaderFileName headerFileName;
    private DllDataFileName dllDataFileName;
    private InterfaceIdentifierFileName interfaceIdentifierFileName;
    private ProxyFileName proxyFileName;
    private TypeLibraryName typeLibraryName;
}
