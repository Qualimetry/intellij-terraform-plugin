plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.2.1"
}

group = "com.qualimetry.intellij"
version = providers.gradleProperty("pluginVersion").getOrElse("2.2.3")

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    mavenLocal()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.3")
    }

    implementation("com.qualimetry.sonar:terraform-rules:2.2.4")

    implementation("org.sonarsource.api.plugin:sonar-plugin-api:11.1.0.2693")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

intellijPlatform {
    pluginConfiguration {
        id = "com.qualimetry.terraform"
        name = "Qualimetry Terraform Analyzer"
        version = project.version.toString()
        description = """
            <p>Static analysis of Terraform (HCL) files (<code>.tf</code>)
            in IntelliJ IDEA and other JetBrains IDEs. Also runs in JetBrains Qodana.</p>
            <p>Powered by tflint, Trivy, and Checkov with the Qualimetry rule registry.</p>
            <ul>
              <li>400+ rules covering tflint, Trivy, and Checkov (AWS, Azure, GCP)</li>
              <li>Real-time analysis as you edit</li>
              <li>Works in IntelliJ IDEA, Rider, and JetBrains Qodana</li>
            </ul>
        """.trimIndent()
        vendor {
            name = "Qualimetry"
            url = "https://qualimetry.com"
        }
        ideaVersion {
            sinceBuild = "243"
            untilBuild = provider { null }
        }
    }

    publishing {
        token = providers.environmentVariable("JETBRAINS_MARKETPLACE_TOKEN")
    }

    signing {
        certificateChain = providers.environmentVariable("PLUGIN_SIGNING_CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PLUGIN_SIGNING_KEY")
        password = providers.environmentVariable("PLUGIN_SIGNING_KEY_PASSWORD")
    }
}

tasks {
    buildSearchableOptions {
        enabled = false
    }
}
