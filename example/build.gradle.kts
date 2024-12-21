plugins {
    id("java")
}

group = "com.bethibande.kotatsu"
version = "unspecified"

repositories {
    mavenCentral()
    google()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(projects.kotatsuJava)
}

tasks.test {
    useJUnitPlatform()
}