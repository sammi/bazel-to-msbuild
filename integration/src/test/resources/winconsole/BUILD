load("@rules_cc//cc:defs.bzl", "cc_binary", "cc_library")


cc_import(
  name = "windowsapp",
  hdrs = ["winrt/Windows.Foundation.h", "winrt/Windows.Foundation.Collections.h"],
  static_library = "windowsapp.lib",
  system_provided = 1,
)

cc_library(
    name = "pre_compiled_lib",
    srcs = ["pch.cpp"],
    hdrs = ["pch.h"],
    deps = [":windowsapp"],
)

cc_binary(
    name = "winconsole",
    srcs = ["main.cpp"],
    deps = [
        ":pre_compiled_lib",
    ],
)