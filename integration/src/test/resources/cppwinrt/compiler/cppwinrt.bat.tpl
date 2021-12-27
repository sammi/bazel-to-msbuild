@echo OFF

set Platform=Stable_Platform
set WindowsSDKVersion=Stable_WindowsSDKVersion
set VCToolsVersion=Stable_VCToolsVersion
set VSINSTALLDIR=Stable_VSINSTALLDIR
set WindowsSdkDir=Stable_WindowsSdkDir
set FoundationContractVersion=Stabel_FoundationContractVersion
set UniversalApiContractVersion=Stable_UniversalApiContractVersion
set WwanContractContractVersion=Stable_WwanContractContractVersion

REM The path which includes cl.exe
set Path=%VSINSTALLDIR%\VC\Tools\MSVC\%VCToolsVersion%\bin\Host%Platform%\%Platform%;

set ReferenceDir=%WindowsSdkDir%\References\%WindowsSDKVersion%
set FoundationContractReference=%ReferenceDir%\Windows.Foundation.FoundationContract\%FoundationContractVersion%\Windows.Foundation.FoundationContract.winmd
set UniversalApiContractReference=%ReferenceDir%\Windows.Foundation.UniversalApiContract\%UniversalApiContractVersion%\Windows.Foundation.UniversalApiContract.winmd
set WwanContractReference=%ReferenceDir%\Windows.Networking.Connectivity.WwanContract\%WwanContractContractVersion%\Windows.Networking.Connectivity.WwanContract.winmd

"%WindowsSdkDir%\bin\%WindowsSDKVersion%\%Platform%\cppwinrt.exe" ^
-reference "%FoundationContractReference%" ^
-reference "%UniversalApiContractReference%" ^
-reference "%WwanContractReference%" ^
-output %1 ^
-input %2
