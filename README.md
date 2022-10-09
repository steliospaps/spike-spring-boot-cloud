# about

A eureka server and two service service01 and service02 registering to it.
Service02 is using service discovery loadbalancer to connect to service01

# usage

## direct run

* eureka server http://localhost:1111
* service01 (1) http://localhost:8081
* service01 (2) http://localhost:8083
* service02 http://localhost:8082

run in separate windows
```
./gradlew eureka-server:bootRun
SERVER_PORT=8083 ./gradlew service01:bootRun
./gradlew service01:bootRun
./gradlew service02:bootRun

```

## docker compose

* eureka server on http://localhost:1111
* service02 http://localhost:8082

```
./gradlew composeUp
```