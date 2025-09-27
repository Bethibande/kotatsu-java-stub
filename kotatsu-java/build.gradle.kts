plugins {
    `java-library`
    `maven-publish`
    signing
}

group = "com.bethibande"
version = "21.9"

repositories {
    mavenCentral()
    google()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    api("com.github.KotatsuApp:kotatsu-parsers:5962efb7df")
    api("com.squareup.okhttp3:okhttp:4.12.0")

    implementation(projects.kotatsuKotlin)

    implementation("com.google.auto.service:auto-service:1.1.1")
    annotationProcessor("com.google.auto.service:auto-service:1.1.1")
}

tasks.test {
    useJUnitPlatform()
}

java {
    withSourcesJar()
    withJavadocJar()
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