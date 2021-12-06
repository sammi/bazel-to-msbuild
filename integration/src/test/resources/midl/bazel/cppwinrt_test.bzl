load("@bazel_skylib//lib:unittest.bzl", "asserts", "analysistest")
load("cppwinrt.bzl", "cppwinrt")

def _test_impl(ctx):
    env = analysistest.begin(ctx)
    target_under_test = analysistest.target_under_test(env)
    actions = analysistest.target_actions(env)
    header_output = actions[0].outputs.to_list()[0]
    asserts.equals(env, target_under_test.label.name + ".h", header_output.basename)
    return analysistest.end(env)

cppwinrt_test = analysistest.make(_test_impl)

def _test():
    # Rule under test. Be sure to tag 'manual', as this target should not be built using `:all` except as a dependency of the test.
    cppwinrt(
        name="cppwinrt_test_rule",
        winmd="cppwinrt_test.winmd",
        tags = ["manual"],
    )
    cppwinrt_test(name="cppwinrt_test", target_under_test="cppwinrt_test_rule")

def cppwinrt_test_suite(name):
    _test()
    native.test_suite(
        name = name,
        tests = [
            ":cppwinrt_test"
        ]
    )