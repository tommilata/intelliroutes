buildscript {
    repositories {
        maven("https://jitpack.io")
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.16.1"
    id("com.palantir.git-version") version "3.0.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect", version = "1.7.22"))
    // explicit dependency to avoid version conflicts
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

intellij {
    version.set("2024.1") // IntelliJ Platform IDE version used to build the plugin
    plugins.set(listOf("com.intellij.java", "org.intellij.scala:2024.1.15"))
}

tasks {
    patchPluginXml {
        // sinceBuild defined by `intellij.version`

        /** should be e.g. 203.* if sinceBuild is 202 (or 2020.2)
         * to give compatibility with a next minor a shot */
        untilBuild.set("241.*")

        changeNotes.set(
            """
        <h1>2020.2.4</h1>
        <ul>
            <li>Added code completion and "go to definition" for other routers</li>
            <li>Improved controller code completion performance</li>
            <li>Added icons next to code completion suggestions</li>
        </ul>
        """
        )
    }

    publishPlugin {
        token.set(System.getenv("INTELLIJ_PUBLISH_TOKEN"))
        channels.set(listOf(System.getenv("INTELLIJ_PUBLISH_CHANNEL") ?: "alpha"))
    }

    generateParser {
        sourceFile.set(file("src/main/grammar/Routes.bnf"))
        pathToParser.set("/com/github/tomasmilata/intelliroutes/parser/RoutesParser.java")
        pathToPsiRoot.set("/com/github/tomasmilata/intelliroutes/psi")
        targetRootOutputDir.set(file( "src/generated"))
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