bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* It is using ```bazel query ... output=proto``` to gather all cc_librarr, cc_binary, and cc_test packages;
* It generates msbuild project for each package;
* It generates one solution for one bazel workspace;
* It detect package dependencies and manage it as project references in msbuild project as well;

# Quick start

Unzip the tool and put it in your PATH
```
wget https://github.com/sammi/bazel-to-msbuild/releases/download/0.2.0/b2m-0.2.0.zip
unzip b2m-0.2.0.zip
```

Run the command:

```
b2m {your_bazel_workspace_dir} {your_msbuild_solution_dir} {your_solution_file_name}
```

Then Open {your_solution_file_name}.sln by visual studio.

# Project Layout

The project modules are organized
by [hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) style.

## bazel-to-msbuild

It defines project shared dependencies, which includes springboot dependencies, spock test framework, and groovy plugin.

## contract

* It defines interfaces which are required by feature, being implemented by adapter;
* It defines shared msbuild model classes, bazel protobuf metadata, and related gRPC dependencies, which are being used
  by feature and adapter;

## feature

It defines features to be used by end user, it depends on contract model at compile time, calls adapter model
implementations at run time.

## adapter

It implements interfaces defined in contract model, it only depends on contract model, not feature module.

## integration

Integrate adapter and feature together to run end to end integration tests.

# Set up development environment

1. [Install Bazel on Windows](https://docs.bazel.build/versions/master/install-windows.html), make sure it is available
   in your PATH
2. [Install lombok plugin](https://stackoverflow.com/questions/41161076/adding-lombok-plugin-to-intellij-project ) in
   intellij to support lombok annotations.
3. Run unit tests and install
```
cd bazel-to-msbuild
mvn clean install
```
4. Run integration tests, note: you could not run ```mvn -P it clean install```, because you could not install integration test module.

```
cd bazel-to-msbuild
mvn -P it clean test
```
