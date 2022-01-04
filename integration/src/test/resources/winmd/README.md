We choose [winmd](https://github.com/microsoft/winmd) as the sample bazel project.

1. Build and test winmd library by bazel
   ```
   bazel test //test:all
   ```
2. Figurate out how to convert winmd project into msbuild solution and project.
   In progress...
   
*Notes*:
   we're using c++ toolchain to build the project on windows10 and visual studio 2002.
* The toolchain is defined in toolchain folder;
* In WORKSPACE, we register the toolchain;
* In .bazelrc, we enable the c++ toolchain resolution process;
    ```
    build   --incompatible_enable_cc_toolchain_resolution
    ```
