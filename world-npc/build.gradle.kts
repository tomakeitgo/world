plugins {
    id("java")
}

group = "com.tomakeitgo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.register<Zip>("buildZip") {
    archiveFileName.set("world-npc.zip")
    into("lib") {
        from(tasks.jar)
        from(configurations.runtimeClasspath)
    }
}

dependencies {
    implementation(platform("software.amazon.awssdk:bom:2.30.30"))
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-events:3.15.0")
    implementation("software.amazon.awssdk:dynamodb")
    implementation("com.google.code.gson:gson:2.12.1")

    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("software.amazon.awssdk:cloudformation")
    testImplementation("software.amazon.awssdk:sso")
    testImplementation("software.amazon.awssdk:ssooidc")
}
