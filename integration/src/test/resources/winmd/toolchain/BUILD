load("@rules_cc//cc/private/toolchain:windows_cc_toolchain_config.bzl", "cc_toolchain_config")
load("@rules_cc//cc:defs.bzl", "cc_toolchain")

package(default_visibility = ["//visibility:public"])

filegroup(name = "empty")

filegroup(
    name = "msvc_compiler_files",
    srcs = [":builtin_include_directory_paths_msvc"],
)

cc_toolchain_config(
    name = "msvc_x64",
    abi_libc_version = "local",
    abi_version = "local",
    compiler = "msvc-cl",
    cpu = "x64_windows",
    cxx_builtin_include_directories = [
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\atlmfc\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Auxiliary\\VS\\include",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\ucrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\um",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\shared",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\winrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\cppwinrt",
    ],
    dbg_mode_debug_flag = "/DEBUG:FULL",
    default_link_flags = ["/MACHINE:X64"],
    fastbuild_mode_debug_flag = "/DEBUG:FASTLINK",
    host_system_name = "local",
    msvc_cl_path = "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\cl.exe",
    msvc_env_include = ";".join([
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\atlmfc\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Auxiliary\\VS\\include",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\ucrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\um",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\shared",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\winrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.20348.0\\cppwinrt",
    ]),
    msvc_env_lib = ";".join([
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\lib\\x64",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\atlmfc\\lib\\x64",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Auxiliary\\VS\\lib\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\lib\\10.0.20348.0\\ucrt\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\lib\\10.0.20348.0\\um\\x64",
    ]),
    msvc_env_path = ";".join([
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\HostX64\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\bin\\10.0.20348.0\\x64",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\Common7\\tools",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\Common7\\ide",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\MSBuild\\Current\\Bin",
        "C:\\WINDOWS\\system32",
    ]),
    msvc_env_tmp = "C:\\Windows\\Temp",
    msvc_lib_path = "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\lib.exe",
    msvc_link_path = "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\link.exe",
    msvc_ml_path = "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\ml64.exe",
    target_libc = "msvcrt",
    target_system_name = "local",
#    tool_bin_path = "wrapper\\bin\\msvc_nop.bat",
    tool_paths = {
        "ar": "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\lib.exe",
        "ml": "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\ml64.exe",
        "cpp": "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\cl.exe",
        "gcc": "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\cl.exe",
        "gcov": "wrapper\\bin\\msvc_nop.bat",
        "ld": "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30919\\bin\\Hostx64\\x64\\link.exe",
        "nm": "wrapper\\bin\\msvc_nop.bat",
        "objcopy": "wrapper\\bin\\msvc_nop.bat",
        "objdump": "wrapper\\bin\\msvc_nop.bat",
        "strip": "wrapper\\bin\\msvc_nop.bat",
    },
    toolchain_identifier = "msvc_x64",
#    archiver_flags = ["/MACHINE:X64"],
)

cc_toolchain(
    name = "cc-compiler-x64_windows",
    toolchain_identifier = "msvc_x64",
    toolchain_config = ":msvc_x64",
    all_files = ":empty",
    ar_files = ":empty",
    as_files = ":msvc_compiler_files",
    compiler_files = ":msvc_compiler_files",
    dwp_files = ":empty",
    linker_files = ":empty",
    objcopy_files = ":empty",
    strip_files = ":empty",
    supports_param_files = 1,
)

toolchain(
    name = "windows_msvc_toolchain",
    exec_compatible_with = [
        "@platforms//cpu:x86_64",
        "@platforms//os:windows",
    ],
    target_compatible_with = [
        "@platforms//cpu:x86_64",
        "@platforms//os:windows",
    ],
    toolchain = ":cc-compiler-x64_windows",
    toolchain_type = "@bazel_tools//tools/cpp:toolchain_type",
)
