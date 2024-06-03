pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "My specialty"
include(":app")
include(":core")
include(":domain")
include(":data")
include(":features")
include(":features:sign-in")
include(":features:university")
include(":features:university_info")
