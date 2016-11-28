# gradle-applyscript-plugin
Gradle plugin that applies Gradle build scripts in jars.
So that you can package multi Gradle build scripts into a jar, and distribute it to any repo as a standard artifact.

## Install
All Gradle versions:
```groovy
buildscript {
	repositories {
		maven {
			url "https://plugins.gradle.org/m2/"
		}
	}
	dependencies {
		classpath "com.orctom.gradle:gradle-applyscript-plugin:1.2-SNAPSHOT"
	}
}

apply plugin: "com.orctom.applyscript"
```

Gradle 2.1 or newer:
```groovy
plugins {
	id "com.orctom.applyscript" version "1.2-SNAPSHOT"
}
```

# Usage
Option 1:
```groovy
dependencies {
	scripts '{{groupA}}:{{nameA}}:{{versionA}}'
	scripts '{{groupA}}:{{nameA}}:{{versionB}}'
	scripts '{{groupC}}:{{nameD}}:{{versionE}}'
}

applyscript '{{nameA}}-{{versionA}}/{{path-of-fileA.gradle}}'
applyscript '{{nameA}}-{{versionA}}/{{path-of-fileB.gradle}}'
applyscript '{{nameA}}-{{versionB}}/{{path-of-fileC.gradle}}'
applyscript '{{nameD}}-{{versionE}}/{{path-of-fileX.gradle}}'
```

Option 2:
```groovy
applyscript '{{groupA}}:{{nameA}}:{{versionA}}/{{path-of-fileA.gradle}}'
applyscript '{{groupA}}:{{nameA}}:{{versionA}}/{{path-of-fileB.gradle}}'
applyscript '{{groupA}}:{{nameA}}:{{versionB}}/{{path-of-fileC.gradle}}'
applyscript '{{groupC}}:{{nameD}}:{{versionE}}/{{path-of-fileX.gradle}}'
```

# Changes

### 1.1
* Fixed the issue that the plugin was not working at all, due to a typo.