bazel-to-msbuild
----------------

Generate msbuild project from bazel project

# Project Layout
The project modules are organized by [hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) style.
## base
  root folder, setting up spring boot depdencies, and also groovy and spock framework
## contract
    common interfaces and also shared msbuild model data, bazel protobuf, and related gRPC dependencies
## feature
    Defines the high level feature, it only depends on contract model in compile time;
    It will call adapter implementations in run time;
## adapter
    It implements interfaces defined in contract model, it depends on conract model, has no dependency on features.

# Set up development environment
  
* Install Bazel on Windows, make sure it is available in your PATH

   https://docs.bazel.build/versions/master/install-windows.html

* Install lombok plugin in intellij to support lombok annotations.

  https://stackoverflow.com/questions/41161076/adding-lombok-plugin-to-intellij-project 
