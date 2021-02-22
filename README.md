bazel-to-msbuild
----------------

Generate msbuild project from bazel project

# Project Layout
The project modules are organized by [hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) style.
## base
It defines project shared dependencies, which includes springboot depdencies, spock test framework, and groovy plugin.
## contract
It defines interfaces and shared model classes, bazel protobuf meta data, and related gRPC dependencies.
## feature
It defines features to be used for end user, it depends on contract model at compile time, and it calls adapter implementations at run time.
## adapter
It implements interfaces defined in contract model, it only depends on conract model, not on feature module.

# Set up development environment
  
1. Install Bazel on Windows, make sure it is available in your PATH

   https://docs.bazel.build/versions/master/install-windows.html

1. Install lombok plugin in intellij to support lombok annotations.

  https://stackoverflow.com/questions/41161076/adding-lombok-plugin-to-intellij-project 
  
1. Then start up the tests and enjoy coding!
```
cd bazel-to-msbuild
mvn clean test
```
