# about

* eureka server http://localhost:1111
* service01 http://localhost:8081
* service02 http://localhost:8082

service02 is using service discovery loadbalancer to connect to service01

# usage
in each directory:
```
../gradlew bootRun
