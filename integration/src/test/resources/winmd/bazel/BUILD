load(":windows_msvc_cl_toolchain_config.bzl", "windows_msvc_cl_toolchain_config")
load("@rules_cc//cc:defs.bzl", "cc_toolchain")

package(default_visibility = ["//visibility:public"])


filegroup(
    name = "empty",
    srcs = [],
)

USER_HOME_PATH = "C:\\Users\\songy"

WINDOWS_SYSTEM = "C:\\WINDOWS\\system32"

PROGRAM_FILES_PATH = "C:\\Program Files"

PROGRAM_FILES_X86_PATH = "C:\\Program Files (x86)"

DOT_NET_PATH = "C:\\WINDOWS\\Microsoft.NET\\Framework64"

DOT_NET_VERSION = "v4.0.30319"

WINDOWS_SDK_VERSION = "10.0.22000.0"

MSVC_VERSION = "14.31.30818"

WINDOWS_SDK_PATH = "{}\\Windows Kits".format(PROGRAM_FILES_X86_PATH)

VS_HOME = "{}\\Microsoft Visual Studio".format(PROGRAM_FILES_PATH)

WINDOWS_KITS_BIN_PATH = "{}\\Windows Kits\\10\\bin".format(PROGRAM_FILES_X86_PATH)

VS_PREVIEW_PATH = "{}\\2022\\Preview".format(VS_HOME)

MSVC_PATH = "{}\\VC\\Tools\\MSVC\\{}".format(VS_PREVIEW_PATH, MSVC_VERSION)

TOOL_PATH = "{}\\bin\\HostX64\\x64".format(MSVC_PATH)

SDK_INCLUDE_PATH = "{}\\10\\include\\{}".format(WINDOWS_SDK_PATH, WINDOWS_SDK_VERSION)

SDK_LIB_PATH = "{}\\10\\lib\\{}".format(WINDOWS_SDK_PATH, WINDOWS_SDK_VERSION)

MSVC_IDE_PATH = "{}\\Common7\\IDE".format(VS_PREVIEW_PATH)

windows_msvc_cl_toolchain_config(
    name = "msvc_x64",
    abi_libc_version = "local",
    abi_version = "local",
    compiler = "msvc-cl",
    cpu = "x64_windows",
    cxx_builtin_include_directories = [
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\atlmfc\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Auxiliary\\VS\\include",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\ucrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\um",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\shared",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\winrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\cppwinrt",
        "C:\\Program Files (x86)\\Windows Kits\\NETFXSDK\\4.8\\Include\\um",
    ],
    dbg_mode_debug_flag = "/DEBUG:FULL",
    default_link_flags = ["/MACHINE:X64"],
    fastbuild_mode_debug_flag = "/DEBUG:FASTLINK",
    host_system_name = "local",
    msvc_cl_path = "{}\\cl.exe".format(TOOL_PATH),
    msvc_env_include = ";".join([
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\atlmfc\\include",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Auxiliary\\VS\\include",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\ucrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\um",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\shared",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\winrt",
        "C:\\Program Files (x86)\\Windows Kits\\10\\Include\\10.0.22000.0\\cppwinrt",
        "C:\\Program Files (x86)\\Windows Kits\\NETFXSDK\\4.8\\Include\\um",
    ]),
    msvc_env_lib = ";".join([
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\lib\\x64",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\atlmfc\\lib\\x64",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Auxiliary\\VS\\lib\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\lib\\10.0.22000.0\\ucrt\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\lib\\10.0.22000.0\\um\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\NETFXSDK\\4.8\\lib\\um\\x64",
    ]),
    msvc_env_path = ";".join([
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\VC\\Tools\\MSVC\\14.31.30818\\bin\\HostX64\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\bin\\10.0.22000.0\\x64",
        "C:\\Program Files (x86)\\Microsoft SDKs\\Windows\\v10.0A\\bin\\NETFX 4.8 Tools\\x64",
        "C:\\Program Files (x86)\\Windows Kits\\10\\bin\\10.0.22000.0\\x86",
        "C:\\Program Files (x86)\\Microsoft SDKs\\Windows\\v10.0A\\bin\\NETFX 4.8 Tools",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\Common7\\tools",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\Common7\\ide",
        "C:\\Program Files (x86)\\HTML Help Workshop",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\MSBuild\\Current\\Bin\\amd64",
        "C:\\WINDOWS\\Microsoft.NET\\Framework64\\v4.0.30319\\",
        "C:\\WINDOWS\\Microsoft.NET\\Framework\\v4.0.30319\\",
        "C:\\Program Files\\Microsoft Visual Studio\\2022\\Preview\\MSBuild\\Current\\Bin",
        "C:\\WINDOWS\\system32",
        "C:\\WINDOWS",
        "C:\\WINDOWS\\System32\\WindowsPowerShell\\v1.0\\",
        "C:\\WINDOWS\\System32\\OpenSSH\\",
        "C:\\ProgramData\\chocolatey\\bin",
        "C:\\Program Files\\dotnet\\",
        "C:\\WINDOWS\\System32\\Wbem",
        "C:\\Program Files\\nodejs\\",
        "C:\\Program Files\\Git\\cmd",
        "C:\\Program Files (x86)\\Brackets\\command",
        "C:\\Go\\bin",
        "C:\\Users\\songy\\AppData\\Local\\Programs\\Python\\Python38\\Scripts\\",
        "C:\\Users\\songy\\AppData\\Local\\Programs\\Python\\Python38\\",
        "C:\\Program Files\\Conan\\conan",
        "C:\\Users\\songy\\AppData\\Local\\Programs\\Microsoft VS Code\\bin",
        "C:\\Users\\songy\\tools",
        "C:\\Users\\songy\\tools\\depot_tools",
        "C:\\Users\\songy\\AppData\\Local\\Microsoft\\WindowsApps",
        "C:\\Program Files\\Sublime Text 3",
        "C:\\Program Files\\Sublime Merge",
        "C:\\Program Files\\Vim\\vim82",
        "C:\\Users\\songy\\AppData\\Roaming\\npm",
        "C:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2020.2.4\\bin",
        "C:\\Program Files\\CMake\\bin",
        "C:\\Users\\",
        "C:\\HashiCorp\\Vagrant\\bin",
        "C:\\Users\\songy\\scoop\\shims",
        "C:\\Users\\songy\\Go\\bin",
    ]),
    msvc_env_tmp = "{}\\AppData\\Local\\Temp".format(USER_HOME_PATH),
    msvc_lib_path = "{}\\lib.exe".format(TOOL_PATH),
    msvc_link_path = "{}\\link.exe".format(TOOL_PATH),
    msvc_ml_path = "{}\\ml64.exe".format(TOOL_PATH),
    target_libc = "msvcrt",
    target_system_name = "local",
    tool_paths = {
        "ar": "{}\\lib.exe".format(TOOL_PATH),
        "ml": "{}\\ml64.exe".format(TOOL_PATH),
        "cpp": "{}\\cl.exe".format(TOOL_PATH),
        "gcc": "{}\\cl.exe".format(TOOL_PATH),
        "gcov": "wrapper\\bin\\msvc_nop.bat",
        "ld": "{}\\link.exe".format(TOOL_PATH),
        "nm": "wrapper\\bin\\msvc_nop.bat",
        "objcopy": "wrapper\\bin\\msvc_nop.bat",
        "objdump": "wrapper\\bin\\msvc_nop.bat",
        "strip": "wrapper\\bin\\msvc_nop.bat",
    },
    toolchain_identifier = "msvc_x64",
)

cc_toolchain(
    name = "windows_msvc_cl_toolchain",
    all_files = ":empty",
    ar_files = ":empty",
    as_files = ":msvc_compiler_files",
    compiler_files = ":msvc_compiler_files",
    dwp_files = ":empty",
    linker_files = ":empty",
    objcopy_files = ":empty",
    strip_files = ":empty",
    supports_param_files = 1,
    toolchain_config = ":msvc_x64",
    toolchain_identifier = "msvc_x64",
)

filegroup(
    name = "msvc_compiler_files",
    srcs = [":builtin_include_directory_paths_msvc"],
)

toolchain(
    name = "windows_toolchain",
    exec_compatible_with = [
        "@platforms//cpu:x86_64",
        "@platforms//os:windows",
    ],
    target_compatible_with = [
        "@platforms//cpu:x86_64",
        "@platforms//os:windows",
    ],
    toolchain = ":windows_msvc_cl_toolchain",
    toolchain_type = "@bazel_tools//tools/cpp:toolchain_type",
)
