package com.orctom.gradle.apply

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.Dependency

/**
 * Plugin to apply script plugin in build classpath
 * Created by hao on 11/13/15.
 */
class ApplyScriptPlugin implements Plugin<Project> {

    private static final String CONFIGURATION_SCRIPTS = 'scripts'

    @Override
    void apply(Project project) {
        Set<String> scriptSet = new HashSet<>()

        Configuration scriptsDependencies = project.configurations.create(CONFIGURATION_SCRIPTS)
        scriptsDependencies.transitive = false

        project.extensions.applyScript = { String scriptId ->
            if (scriptId.contains(':')) {
                def scriptIds = scriptId.split('/')
                def dependency = scriptIds[0]
                def script = scriptIds[1]
                Dependency dep = project.getDependencies().add(CONFIGURATION_SCRIPTS, dependency)
                scriptSet.add(getScriptPath(project, dep, script))
            } else {
                scriptSet.add("${project.buildDir}/scripts/${scriptId}")
            }
        }

        project.afterEvaluate {
            scriptsDependencies.resolve().each { jarFile ->
                def jar = project.zipTree(jarFile)
                def scriptFolder = project.file(getScriptFolderPath(project, jarFile))
                project.copy {
                    from jar
                    into scriptFolder
                    exclude '**/WEB-INF/*'
                    exclude '**/META-INF/*'
                    includeEmptyDirs = false
                }
            }

            scriptSet.each {
                project.apply { from: it }
            }
        }
    }

    static String getScriptPath(Project project, Dependency dep, String script) {
        "${project.buildDir}/scripts/${dep.name}-${dep.version}/${script}"
    }

    static String getScriptFolderPath(Project project, File jarFile) {
        def scriptFolderName = removeExtension(jarFile.name)
        "${project.buildDir}/scripts/${scriptFolderName}/"
    }

    static String removeExtension(String filename) {
        int index = filename.lastIndexOf('.')
        return index == -1 ? filename : filename.substring(0, index);
    }
}
