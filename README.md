bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* It is using ```bazel query ... output=proto``` to gather all cc_library, cc_binary, and cc_test packages;
* It generates msbuild project for each package;
* It generates one solution for one bazel workspace;
* It detect package dependencies and manage it as project references in msbuild project;
* 

  
