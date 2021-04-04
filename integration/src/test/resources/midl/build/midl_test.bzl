load("@bazel_skylib//lib:unittest.bzl", "asserts", "analysistest")
load(":midl.bzl", "midl", "MidlInfo")

def _test_impl(ctx):
    env = analysistest.begin(ctx)
    target_under_test = analysistest.target_under_test(env)
    actions = analysistest.target_actions(env)
    winmd_output = actions[0].outputs.to_list()[0]
    header_output = actions[0].outputs.to_list()[1]
    asserts.equals(env, target_under_test.label.name + ".winmd", winmd_output.basename)
    asserts.equals(env, target_under_test.label.name + ".h", header_output.basename)
    return analysistest.end(env)

midl_test = analysistest.make(_test_impl)

def _test():
    midl(
        name="midl_test_rule", 
        idl="midl_test.idl",
        tags = ["manual"],
    )
    midl_test(name="midl_test", target_under_test="midl_test_rule")

def midl_test_suite(name):
    _test()
    native.test_suite(
        name = name,
        tests = [
            ":midl_test"
        ]
    )