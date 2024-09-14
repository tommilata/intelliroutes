buildscript {
    repositories {
        maven("https://jitpack.io")
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("com.palantir.git-version") version "3.0.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
        intellijDependencies()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.2.1")

        plugins(listOf("org.intellij.scala:2024.2.25"))
        bundledPlugins(listOf("com.intellij.java"))

        instrumentationTools()


    }
    implementation(kotlin("reflect", version = "1.7.22"))
    // explicit dependency to avoid version conflicts


}

kotlin {
    jvmToolchain(17)
}

group = "com.github.tomas-milata"

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion() // uses git describe

sourceSets {
    main {
        java.srcDirs("src/main", "src/generated")
        resources.srcDir("resources")
    }
}

intellijPlatform {

    pluginConfiguration {
        ideaVersion {
            /** should be e.g. 203.* if sinceBuild is 202 (or 2020.2)
             * to give compatibility with a next minor a shot */
            untilBuild = "242.*"
        }

        changeNotes = """
            <h1>2020.2.4</h1>
            <ul>
                <li>Added code completion and "go to definition" for other routers</li>
                <li>Improved controller code completion performance</li>
                <li>Added icons next to code completion suggestions</li>
            </ul>
            """.trimIndent()
    }

    publishing {
        token = System.getenv("INTELLIJ_PUBLISH_TOKEN")
        channels = listOf(System.getenv("INTELLIJ_PUBLISH_CHANNEL") ?: "alpha")
    }
}

tasks {

    generateParser {
        sourceFile.set(file("src/main/grammar/Routes.bnf"))
        pathToParser.set("/com/github/tomasmilata/intelliroutes/parser/RoutesParser.java")
        pathToPsiRoot.set("/com/github/tomasmilata/intelliroutes/psi")
        targetRootOutputDir.set(file("src/generated"))
    }

    generateLexer {
        dependsOn(generateParser)
        sourceFile.set(file("src/main/jflex/Routes.flex"))
        targetOutputDir.set(file("src/generated/com/github/tomasmilata/intelliroutes"))
    }

    compileKotlin {
        dependsOn(generateLexer)
    }
}

intellijPlatformTesting {
    runIde
    testIde
    testIdeUi
    testIdePerformance
}