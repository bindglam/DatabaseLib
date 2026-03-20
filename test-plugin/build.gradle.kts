plugins {
    id("xyz.jpenilla.run-paper") version "3.0.2"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
    id("com.gradleup.shadow") version "9.3.1"
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    implementation(rootProject)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

paperPluginYaml {
    name = "${rootProject.name}-TestPlugin"
    main = "io.github.bindglam.database.test.TestPlugin"
    apiVersion = "1.21"
}

tasks {
    runServer {
        version("1.21.11")
    }
}