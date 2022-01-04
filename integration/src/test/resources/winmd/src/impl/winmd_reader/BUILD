load("@rules_cc//cc:defs.bzl", "cc_library")

package(default_visibility = ["//visibility:public"])

cc_library(
    name = "winmd_reader_lib",
    srcs = glob(["*.h"]),
    defines = ["_WIN32_WINNT=0x0A00"],
)
