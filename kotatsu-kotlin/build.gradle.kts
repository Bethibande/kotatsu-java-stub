plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.bethibande.kotatsu"
version = "1.0"

repositories {
    mavenCentral()
    google()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.9.0")
    implementation("com.github.KotatsuApp:kotatsu-parsers:f3d14e101c")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okio:okio:3.9.0")
    implementation("io.webfolder:quickjs:1.1.0")
    implementation("org.json:json:20240303")
    implementation("androidx.collection:collection:1.4.4")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}