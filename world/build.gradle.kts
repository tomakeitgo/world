plugins {
    id("java")
}

group = "com.tomakeitgo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.register<Zip>("buildZip") {
    archiveFileName.set("world_1.zip")
    into("lib") {
        from(tasks.jar)
        from(configurations.runtimeClasspath)
    }
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.30.30"))
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-events:3.15.0")
}