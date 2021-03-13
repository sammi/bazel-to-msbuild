bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* It is using ```bazel query ... output=proto``` to gather all cc_library, cc_binary, and cc_test packages;
* It generates msbuild project for each package;
* It generates one solution for one bazel workspace;
* It detect package dependencies and manage it as project references in msbuild project;


# Install and Usage

Unzip the tool and put it in your PATH
```
wget https://github.com/sammi/bazel-to-msbuild/releases/download/v0.3.21/b2m-0.3.29.zip
unzip b2m-0.3.29.zip
```

Run the command:

```
b2m {your_bazel_workspace_dir} {your_msbuild_solution_dir} {your_solution_file_name}
```

Then Open {your_solution_file_name}.sln by visual studio.

  
