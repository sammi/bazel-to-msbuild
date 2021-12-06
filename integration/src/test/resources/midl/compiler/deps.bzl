def _midl_impl(repository_ctx):
    environ = repository_ctx.os.environ
    repository_ctx.template(
        "midl.bat",
        repository_ctx.attr._build_tpl,
        substitutions = {
            "Stable_Platform": environ.get("Stable_Platform"),
            "Stable_WindowsSDKVersion": environ.get("Stable_WindowsSDKVersion"),
            "Stable_VCToolsVersion": environ.get("Stable_VCToolsVersion"),
            "Stable_VSINSTALLDIR": environ.get("Stable_VSINSTALLDIR"),
            "Stable_WindowsSdkDir": environ.get("Stable_WindowsSdkDir"),
            "Stabel_FoundationContractVersion": environ.get("Stabel_FoundationContractVersion"),
            "Stable_UniversalApiContractVersion": environ.get("Stable_UniversalApiContractVersion"),
        },
    )
    repository_ctx.file("BUILD", 'exports_files(["midl.bat"])')


midl_bat_repo = repository_rule(
    implementation = _midl_impl,
    local = True,
    attrs = {
        "_build_tpl": attr.label(
            default = ":midl.bat.tpl",
        ),
    }
)

def _cppwinrt_impl(repository_ctx):
    environ = repository_ctx.os.environ
    repository_ctx.template(
        "cppwinrt.bat",
        repository_ctx.attr._build_tpl,
        substitutions = {
            "Stable_Platform": environ.get("Stable_Platform"),
            "Stable_WindowsSDKVersion": environ.get("Stable_WindowsSDKVersion"),
            "Stable_VCToolsVersion": environ.get("Stable_VCToolsVersion"),
            "Stable_VSINSTALLDIR": environ.get("Stable_VSINSTALLDIR"),
            "Stable_WindowsSdkDir": environ.get("Stable_WindowsSdkDir"),
            "Stabel_FoundationContractVersion": environ.get("Stabel_FoundationContractVersion"),
            "Stable_UniversalApiContractVersion": environ.get("Stable_UniversalApiContractVersion"),
        },
    )
    repository_ctx.file("BUILD", 'exports_files(["cppwinrt.bat"])')


cppwinrt_bat_repo = repository_rule(
    implementation = _cppwinrt_impl,
    local = True,
    attrs = {
        "_build_tpl": attr.label(
            default = ":cppwinrt.bat.tpl",
        ),
    }
)