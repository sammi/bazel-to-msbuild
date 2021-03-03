bazel-to-msbuild
----------------

Generate msbuild project from bazel project

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
1. [Install lombok plugin](https://stackoverflow.com/questions/41161076/adding-lombok-plugin-to-intellij-project ) in
   intellij to support lombok annotations.
1. Run unit tests and install

```
cd bazel-to-msbuild
mvn clean install
```
1. Run integration tests, note: you could not run ```mvn -P it clean install```, because you could not install integration test module.

```
cd bazel-to-msbuild
mvn -P it clean test
```

