plugins {
    id("java")
}

group = "com.bindglam.database"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.zaxxer:HikariCP:7.0.2")
    compileOnly("redis.clients:jedis:7.1.0")
}
