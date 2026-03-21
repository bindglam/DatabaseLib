plugins {
    id("java-library")
}

allprojects {
    apply(plugin = "java-library")

    group = "com.bindglam.database"
    version = "2.1.1"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
    }
}

dependencies {
    api("com.zaxxer:HikariCP:7.0.2")
    api("redis.clients:jedis:7.1.0")
    api("com.h2database:h2:2.4.240")
}