plugins {
    `java-library`
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

    api("com.github.KotatsuApp:kotatsu-parsers:f3d14e101c")
    api("com.squareup.okhttp3:okhttp:4.12.0")

    implementation(projects.kotatsuKotlin)
}

tasks.test {
    useJUnitPlatform()
}