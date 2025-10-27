plugins {
    kotlin("jvm") version "2.2.10"
    `java-library`
    `maven-publish`
    signing
}

group = "com.bethibande"
version = "21.12"

description = "Kotlin utilities needed to use the kotatsu-parsers library."

repositories {
    mavenCentral()
    google()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("com.github.KotatsuApp:kotatsu-parsers:8908031")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("com.squareup.okio:okio:3.16.0")
    implementation("org.openjdk.nashorn:nashorn-core:15.7")
    implementation("org.json:json:20250517")
    implementation("androidx.collection:collection:1.5.0")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
}
kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }

            pom {
                name = project.name
                description = project.description

                url = "https://github.com/Bethibande/kotatsu-java-stub"

                licenses {
                    license {
                        name = "GPL-3.0"
                        url = "https://raw.githubusercontent.com/Bethibande/kotatsu-java-stub/refs/heads/master/LICENSE"
                    }
                }

                developers {
                    developer {
                        id = "bethibande"
                        name = "Max Bethmann"
                        email = "bethibande@gmail.com"
                    }
                }

                scm {
                    connection = "scm:git:git://github.com/Bethibande/kotatsu-java-stub.git"
                    developerConnection = "scm:git:ssh://github.com/Bethibande/kotatsu-java-stub.git"
                    url = "https://github.com/Bethibande/kotatsu-java-stub"
                }
            }
        }
    }

    repositories {
        maven {
            name = "Maven-Releases"
            url = uri("https://pckg.bethibande.com/repository/maven-releases/")
            credentials {
                username = providers.gradleProperty("mavenUsername").get()
                password = providers.gradleProperty("mavenPassword").get()
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}
