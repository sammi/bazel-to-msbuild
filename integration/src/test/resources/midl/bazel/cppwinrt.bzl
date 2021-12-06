"""
cppwinrt compiler rule, generate c++ header only code from winmd binary meta file
"""

def _impl(ctx):
    winmd = ctx.file.winmd

    cppwinrt_out_dir = ctx.configuration.genfiles_dir.path

    header = ctx.actions.declare_file(ctx.label.name + ".h")

    ctx.actions.run(
        use_default_shell_env = True,
        executable = ctx.executable._code_gen_tool,
        arguments = [cppwinrt_out_dir, winmd.path],
        inputs = [winmd],
        outputs = [header],
        progress_message = "Generating %s from %s" % (header.path, winmd.path),
    )

    return [
        DefaultInfo(files = depset([header])),
    ]

cppwinrt = rule(
    doc = "Generate c++ header only library code form windmd file",
    implementation = _impl,
    output_to_genfiles = True,
    attrs = {
        "winmd": attr.label(
            allow_single_file = True,
            mandatory = True,
            doc = "IDL file path, only support 1 file",
        ),
        "_code_gen_tool": attr.label(
            executable = True,
            cfg = "exec",
            allow_files = True,
            default = Label("@cppwinrt//:cppwinrt.bat"),
            doc = "Wrapper bat file to call cppwinrt compiler",
        ),
    },
)
