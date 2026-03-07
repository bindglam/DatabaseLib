plugins {
    id("java-library")
}

group = "com.bindglam.database"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("com.zaxxer:HikariCP:7.0.2")
    api("redis.clients:jedis:7.1.0")
}