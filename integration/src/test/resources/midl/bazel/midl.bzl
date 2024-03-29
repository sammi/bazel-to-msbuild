"""
MIDL compiler rule, generate c++ header only code and WinRT winmd binary meta file
"""

def _impl(ctx):
    idl = ctx.file.idl

    midl_out_dir = ctx.configuration.genfiles_dir.path

    winmd = ctx.actions.declare_file(ctx.label.name + ".winmd")
    header = ctx.actions.declare_file(ctx.label.name + ".h")

    ctx.actions.run(
        use_default_shell_env = True,
        executable = ctx.executable._code_gen_tool,
        arguments = [midl_out_dir, idl.path],
        inputs = [idl],
        outputs = [winmd, header],
        progress_message = "Generating %s and %s from %s" % (header.path, winmd.path, idl.path),
    )

    return [
        DefaultInfo(files = depset([winmd, header])),
    ]

midl = rule(
    doc = "Generate winmd binary file and c++ header only library from IDL file",
    implementation = _impl,
    output_to_genfiles = True,
    attrs = {
        "idl": attr.label(
            allow_single_file = True,
            mandatory = True,
            doc = "IDL file path, only support 1 file",
        ),
        "_code_gen_tool": attr.label(
            executable = True,
            cfg = "exec",
            allow_files = True,
            default = Label("@midl//:midl.bat"),
            doc = "Wrapper bat file to call midl compiler",
        ),
    },
)
