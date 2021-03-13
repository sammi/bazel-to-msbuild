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
3. Run tests, the integration test requires install bazel on your develop machine.
```
cd bazel-to-msbuild
mvn clean test
```

# Release process
1. When code pushed to release branch, it auto deploy maven packages and publish b2m.zip files to a new prerelease;
2. Manually check content, and do a real release;
3. Manually create a PR to merge code from release back to main;