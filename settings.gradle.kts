enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "kotatsu-java-stub"
include("kotatsu-kotlin")
include("kotatsu-java")
include("example")
