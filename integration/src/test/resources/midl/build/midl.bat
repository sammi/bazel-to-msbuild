@echo OFF

set Platform=x64
set WindowsSDKVersion=10.0.20206.0
set VCToolsVersion=14.28.29910
set VSINSTALLDIR=C:\Program Files (x86)\Microsoft Visual Studio\2019\Community
set WindowsSdkDir=C:\Program Files (x86)\Windows Kits\10

REM The path which includes cl.exe
set Path=%VSINSTALLDIR%\VC\Tools\MSVC\%VCToolsVersion%\bin\Host%Platform%\%Platform%;

REM The header files is required by midl.exe
set INCLUDE=%WindowsSdkDir%\include\%WindowsSDKVersion%\winrt;

set ReferenceDir=%WindowsSdkDir%\References\%WindowsSDKVersion%

set FoundationContractReference=%ReferenceDir%\Windows.Foundation.FoundationContract\4.0.0.0\Windows.Foundation.FoundationContract.winmd
set UniversalApiContractReference=%ReferenceDir%\Windows.Foundation.UniversalApiContract\12.0.0.0\Windows.Foundation.UniversalApiContract.winmd

"%WindowsSdkDir%\bin\%WindowsSDKVersion%\%Platform%\midl.exe" ^
/metadata_dir "%ReferenceDir%\Windows.Foundation.FoundationContract\4.0.0.0" ^
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
