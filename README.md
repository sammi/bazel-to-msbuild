bazel-to-msbuild
----------------

Generate msbuild project from bazel project

* The project requires running on windows10 and bazel should be installed and be available in %PATH%.

* Installing Bazel on Windows
https://docs.bazel.build/versions/master/install-windows.html

* Setup jdk 11 toolchain file for maven
Config toolchains.xml and save it under your %USER_HOME%/.m2 folder, change the JDK path to your local file absolute path.
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
