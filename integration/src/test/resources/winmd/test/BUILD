load("@rules_cc//cc:defs.bzl", "cc_library", "cc_test", "cc_binary")

cc_library(
    name = "pch",
    srcs = ["pch.cpp"],
    hdrs = ["pch.h", "catch.hpp"],
    testonly = True,
)

cc_test(
    name = "cache_test",
    srcs = ["cache.cpp"],
    deps = [
        ":pch",
        "//src:winmd_reader"
    ],
    copts = ["-Isrc"],
    testonly = True,
)

cc_test(
    name = "database_test",
    srcs = ["database.cpp"],
    deps = [
        ":pch",
        "//src:winmd_reader"
    ],
    copts = ["-Isrc"],
    testonly = True,
)

cc_test(
    name = "filter_test",
    srcs = ["filter.cpp"],
    deps = [
        ":pch",
        "//src:winmd_reader"
    ],
    copts = ["-Isrc"],
    testonly = True,
)
