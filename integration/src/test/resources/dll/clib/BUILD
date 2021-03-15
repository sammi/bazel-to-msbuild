load("//build:windows_dll_library.bzl", "windows_dll_library")

windows_dll_library(
    name = "clib",
    srcs = ["clib.cpp"],
    hdrs = ["clib.h"],
    copts = ["/DCOMPILING_DLL", "/D_CRT_SECURE_NO_WARNINGS"],
)
