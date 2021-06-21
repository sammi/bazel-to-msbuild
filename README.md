bazel-to-msbuild
----------------

Generate msbuild project from bazel project

- [x] It is using ```bazel query ... output=proto``` to gather all cc_library, cc_binary, and cc_test packages;
- [x] It generates one msbuild project for one target;
- [x] It generates one solution for one bazel workspace;
- [x] It detects target dependencies and manage them as project references in msbuild project;
- [x] It detects windows dll library and mange dll libary as the application project reference;
- [ ] It detects midl file and manage it by Midl task in project;

# Install and Usage

Unzip the tool and put it in your PATH
```
wget https://github.com/sammi/bazel-to-msbuild/releases/download/v0.2.7/b2m-0.2.7.zip
unzip b2m-0.2.7.zip
```

Run the command:

```
b2m {your_bazel_workspace_dir} {your_solution_file_name}
```

Generate project with static library
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
Generate project with static library
```
cd bazel-to-msbuild\integration\src\test\resources\dll
b2m . app
```
Then you should be able to find msbuild solution and project files under dll folder:
```
app.sln
clib\clib.vcxproj
clib\clib.filters
clib\clib.user
main\main.vcxproj
main\main.filters
main\main.user
```

Then Open app.sln by Visualstudio, set main as the startup project, you should be able to debug the application and its dll library clib.

  
