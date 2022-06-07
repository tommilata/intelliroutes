buildscript {
    repositories {
        maven("https://jitpack.io")
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.intellij") version "1.6.0"
    id("com.palantir.git-version") version "0.15.0"
    id("org.jetbrains.grammarkit") version "2021.2.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect", version = "1.6.21"))
    // explicit dependency to avoid version conflicts

    testImplementation("io.kotlintest:kotlintest:2.0.7")
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
    version.set("2022.1") // IntelliJ Platform IDE version used to build the plugin
    plugins.set(listOf("com.intellij.java", "org.intellij.scala:2022.1.14"))
}

tasks {
    patchPluginXml {
        // sinceBuild defined by `intellij.version`

        /** should be e.g. 203.* if sinceBuild is 202 (or 2020.2)
         * to give compatibility with a next minor a shot */
        untilBuild.set("231.*")

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
        source.set("src/main/grammar/Routes.bnf")
        pathToParser.set("/com/github/tomasmilata/intelliroutes/parser/RoutesParser.java")
        pathToPsiRoot.set("/com/github/tomasmilata/intelliroutes/psi")
        targetRoot.set("src/generated")
        purgeOldFiles.set(true)
    }

    generateLexer {
        dependsOn(generateParser)
        source.set("src/main/jflex/Routes.flex")
        targetDir.set("src/generated/com/github/tomasmilata/intelliroutes")
        targetClass.set("RoutesLexer")
        purgeOldFiles.set(true)
    }

    compileKotlin {
        dependsOn(generateLexer)
    }
}