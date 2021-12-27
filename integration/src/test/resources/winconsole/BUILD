load("@rules_cc//cc:defs.bzl", "cc_binary", "cc_library")

cc_library(
    name = "pre_compiled_lib",
    srcs = ["pch.cpp"],
    hdrs = ["pch.h"],
)

cc_binary(
    name = "main",
    srcs = ["main.cpp"],
    deps = [
        ":pre_compiled_lib",
    ],
)
