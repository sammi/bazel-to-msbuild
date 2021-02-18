bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* The project requires running on windows10 and bazel should be installed and be available in %PATH%.

* Installing Bazel on Windows
https://docs.bazel.build/versions/master/install-windows.html

* Setup jdk 11 toolchain file for maven
copy toolchains.xml to your .m2 folder, change the JDK path to your local file absolute path.

* Install lombok plugin in intellij to support lombok annotations.
