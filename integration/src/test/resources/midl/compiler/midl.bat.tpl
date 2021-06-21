@echo OFF

set Platform=Stable_Platform
set WindowsSDKVersion=Stable_WindowsSDKVersion
set VCToolsVersion=Stable_VCToolsVersion
set VSINSTALLDIR=Stable_VSINSTALLDIR
set WindowsSdkDir=Stable_WindowsSdkDir
set FoundationContractVersion=Stabel_FoundationContractVersion
set UniversalApiContractVersion=Stable_UniversalApiContractVersion

REM The path which includes cl.exe
set Path=%VSINSTALLDIR%\VC\Tools\MSVC\%VCToolsVersion%\bin\Host%Platform%\%Platform%;

REM The header files is required by midl.exe
set INCLUDE=%WindowsSdkDir%\include\%WindowsSDKVersion%\winrt;
set ReferenceDir=%WindowsSdkDir%\References\%WindowsSDKVersion%
set FoundationContractReference=%ReferenceDir%\Windows.Foundation.FoundationContract\%FoundationContractVersion%\Windows.Foundation.FoundationContract.winmd
set UniversalApiContractReference=%ReferenceDir%\Windows.Foundation.UniversalApiContract\%UniversalApiContractVersion%\Windows.Foundation.UniversalApiContract.winmd

"%WindowsSdkDir%\bin\%WindowsSDKVersion%\%Platform%\midl.exe" ^
/metadata_dir "%ReferenceDir%\Windows.Foundation.FoundationContract\%FoundationContractVersion%" ^
/reference "%FoundationContractReference%" ^
/reference "%UniversalApiContractReference%" ^
/nomidl ^
/notlb ^
/winrt ^
/nologo ^
/enum_class ^
/ns_prefix ^
/client none ^
/server none ^
/out %1 ^
%2
