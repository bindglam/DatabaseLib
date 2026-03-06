plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.3.2"
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

tasks {
    jar {
        finalizedBy(shadowJar)
    }

    shadowJar {
        archiveClassifier = ""

        dependencies {
        }
    }
}
