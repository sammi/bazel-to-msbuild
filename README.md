bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* It is using ```bazel query ... output=proto``` to gather all cc_library, cc_binary, and cc_test packages;
* It generates one msbuild project for one target;
* It generates one solution for one bazel workspace;
* It detects target dependencies and manage them as project references in msbuild project;


# Install and Usage

Unzip the tool and put it in your PATH
```
wget https://github.com/sammi/bazel-to-msbuild/releases/download/v0.1.3/b2m-0.1.3.zip
unzip b2m-0.1.3.zip
```

Run the command:

```
b2m {your_bazel_workspace_dir} {your_solution_file_name}
```

For example:
```
cd bazel-to-msbuild\integration\src\test\resources\stage3
b2m . app
```
Then you should be able to find msbuild solution and project files under stage3 folder:
```
app.sln
lib\hello-time.vcxproj
lib\hello-time.filters
lib\hello-time.user

main\hello-greet.vcxproj
main\hello-greet.filters
main\hello-greet.user

main\hello-world.vcxproj
main\hello-world.filters
main\hello-world.user
```

Then Open app.sln by Visualstudio, set hello-world as the startup project, you should be able to debug the application.

  
