load("@bazel_skylib//lib:unittest.bzl", "asserts", "analysistest")
load(":midl.bzl", "midl", "MidlInfo")

def _provider_contents_test_impl(ctx):
    env = analysistest.begin(ctx)
    target_under_test = analysistest.target_under_test(env)
    actions = analysistest.target_actions(env)
    winmd_output = actions[0].outputs.to_list()[0]
    header_output = actions[0].outputs.to_list()[1]
    asserts.equals(env, target_under_test.label.name + ".winmd", winmd_output.basename)
    asserts.equals(env, target_under_test.label.name + ".h", header_output.basename)
    return analysistest.end(env)

provider_contents_test = analysistest.make(_provider_contents_test_impl)

def _test_provider_contents():
    midl(
        name="midl_unit_test", 
        idl="midl_test.idl",
        tags = ["manual"],
    )
    provider_contents_test(name="provider_contents_test", target_under_test="midl_unit_test")

def midl_test_suite(name):
    _test_provider_contents()
    native.test_suite(
        name = name,
        tests = [
            ":provider_contents_test"
        ]
    )