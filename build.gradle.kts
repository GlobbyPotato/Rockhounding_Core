import java.time.format.DateTimeFormatter
import java.time.Instant

plugins {
    idea
    java
    id("net.minecraftforge.gradle") version "5.1.+"
    id("wtf.gofancy.fancygradle") version "1.1.+"
}

val versionMC: String by project
val versionForge: String by project
val versionMod: String by project

val versionRF: String by project
val versionJEI: String by project
val versionCT: String by project

group = "com.globbypotato.rockhounding_core"
version = "$versionMC-$versionMod"
setProperty("archivesBaseName", "rockhounding_core")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

minecraft {
    mappings("snapshot", "20171003-1.12")

    runs {
        create("client") {
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            workingDirectory(file("run"))

            mods {
                create("rockhounding_core") {
                    source(sourceSets.getByName("main"))
                }
            }
        }
        create("server") {
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            workingDirectory(file("run-server"))

            mods {
                create("rockhounding_core") {
                    source(sourceSets.getByName("main"))
                }
            }
        }
    }
}

fancyGradle {
    patches {
        resources
        coremods
        codeChickenLib
        asm
    }
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}

repositories {
    mavenCentral()
    maven { // JEI
        name = "Progwml6 maven"
        url = uri("https://dvs1.progwml6.com/files/maven/")
    }
    maven { // JEI Fallback
        name = "ModMaven"
        url = uri("https://modmaven.dev")
    }
    maven { // https://www.cursemaven.com/forge
        name = "CurseMaven"
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "$versionMC-$versionForge")

    compileOnly(fg.deobf("mezz.jei:jei_$versionMC:$versionJEI:api"))
    runtimeOnly("mezz.jei:jei_$versionMC:$versionJEI")

    compileOnly("CraftTweaker2:CraftTweaker2-API:$versionCT")
    implementation("CraftTweaker2:CraftTweaker2-MC1120-Main:1.12-$versionCT")
    compileOnly("CraftTweaker2:ZenScript:$versionCT")
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Specification-Title" to "RockhoundingCore",
            "Specification-Vendor" to "GlobbyPotato",
            "Specification-Version" to "${versionMC}-${versionMod}",
            "Implementation-Title" to project.name,
            "Implementation-Version" to archiveVersion,
            "Implementation-Vendor" to "GlobbyPotato",
            "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        )
    }
}

tasks.processResources {
    inputs.properties(
        "coreModVersion" to project.version,
        "mcVersion" to versionMC
    )

    filesMatching("mcmod.info") {
        expand(
            "coreModVersion" to project.version,
            "mcVersion" to versionMC
        )
    }
}

idea {
    module {
        inheritOutputDirs = true
    }
}