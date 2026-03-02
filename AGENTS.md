## Cursor Cloud specific instructions

### Project overview

Java/Spring Cloud microservices application (seat reservation system) using Maven multi-module layout under `parent/`. Four modules: `eteserver` (Eureka, port 8761), `common` (shared lib), `seat_provider` (core service, port 8002), `ete_getway` (Zuul gateway, port 7001).

### System dependencies

- **JDK 11** is required. The project targets Java 1.8 but uses Spring Boot 2.2.0 + Spring Cloud Hoxton.RC1, which are incompatible with JDK 17+. Set `JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64`.
- **Maven 3.x** (`mvn`), installed via `apt-get install -y maven`.
- **Redis** must be running locally on port 6379. Start with `redis-server --daemonize yes`.
- The **Oracle JDBC driver** (`com.hynnet:oracle-driver-ojdbc:12.1.0.2`) is not available in public Maven repos. The update script installs a compatible Oracle JDBC JAR into `~/.m2/repository` to satisfy this dependency.

### Build

```
cd /workspace/parent && JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64 mvn clean install -Dmaven.test.skip=true
```

Use `-Dmaven.test.skip=true` (not just `-DskipTests`) because `SeatControllerTest.java` imports `javafx.application.Application` (an unused import), which does not exist in OpenJDK 11 and causes test compilation to fail. This is a pre-existing bug in the repository.

### Running services

Start in order: Eureka first, then the provider/gateway.

1. **Eureka**: `java -jar parent/eteserver/target/eteserver-1.0-SNAPSHOT.jar` (port 8761)
2. **seat_provider**: `java -jar parent/seat_provider/target/seat_provider-1.0-SNAPSHOT.jar --spring.redis.host=localhost` (port 8002). Override Redis host since the config points to a remote IP (`172.16.150.156`) that is unreachable.
3. **ete_getway** (Zuul): Requires an Oracle DB at `39.107.123.105:1521/zypw` for authentication. This remote DB is unreachable from the cloud VM, so the gateway cannot start without a local Oracle instance or config override.

### Known limitations

- **Test compilation fails** due to unused `javafx.application.Application` import in `SeatControllerTest.java`.
- **ete_getway** cannot run without Oracle DB access. The datasource is hardcoded in `ete_getway/src/main/resources/application.properties`.
- Redis host/password configs are hardcoded to a remote IP in both `seat_provider` and `ete_getway` properties files; override via command-line args when running locally.
