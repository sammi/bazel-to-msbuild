bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* Install Bazel on Windows, make sure it is available in your PATH
https://docs.bazel.build/versions/master/install-windows.html

* Edit toolchains.xml and save it at %USER_HOME%/.m2.
```xml
<?xml version="1.0" encoding="UTF8"?>
<toolchains>
    <toolchain>
        <type>jdk</type>
        <provides>
            <version>8</version>
        </provides>
        <configuration>
            <!--Replace jdk 8 path in your system, you copy the path from your IDE. File -> Project Structure -> Platform Settings -> SDK -->
            <jdkHome>C:\Program Files\Java\jdk1.8.0_211</jdkHome>
        </configuration>
    </toolchain>
    <toolchain>
        <type>jdk</type>
        <provides>
            <version>11</version>
        </provides>
        <configuration>
            <!--Replace jdk 11 path in your system, you copy the path from your IDE. File -> Project Structure -> Platform Settings -> SDK -->
            <!-- If jdk 11 is not exists on your system, you could download it from IDE as well. File -> Project Structure -> Platform Settings -> SDK -> Click + -->            
            <jdkHome>C:\Users\songy\.jdks\corretto-11.0.10</jdkHome>
        </configuration>
    </toolchain>
</toolchains>
```

* Install lombok plugin in intellij to support lombok annotations.
