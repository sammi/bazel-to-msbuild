msbuild project file service
----------------------------

Ingest bazel event payload and generate visual studio project file

## Build and start server
```
mvn clean package

#Start with default port number 50051
java -jar api\target\api.jar

#Start with different port number and timesout value
java -jar api\target\api.jar -p 50052 -t 10

#Start with error log level and hide sprinboot logo
java -jar -Dlogging.level.root=warn -Dspring.main.banner-mode=off api\target\api.jar

```

## Test gRPC service

Open Terminal as administrator
```
choco install bloomrpc
```
Windows Start find BloomRPC app, start it by importing proto file, it will generate sample request for you.


