load("@rules_cc//cc:defs.bzl", "cc_library", "cc_test", "cc_binary")

cc_library(
    name = "pch",
    srcs = ["pch.cpp"],
    hdrs = ["pch.h", "catch.hpp"],
)

cc_library(
    name = "cache_test",
    srcs = ["cache.cpp"],
    deps = [
        ":pch",
        "//src:winmd_reader"
    ],
    copts = ["-Isrc"],
)

cc_library(
    name = "database_test",
    srcs = ["database.cpp"],
    deps = [
        ":pch",
        "//src:winmd_reader"
    ],
    copts = ["-Isrc"],
)

cc_library(
    name = "filter_test",
    srcs = ["filter.cpp"],
    deps = [
        ":pch",
        "//src:winmd_reader"
    ],
    copts = ["-Isrc"],
)

cc_test(
    name = "main",
    srcs = ["main.cpp"],
    deps = [
        ":cache_test",
        ":database_test",
        ":filter_test",
    ],
    copts = ["-Isrc"],
)

