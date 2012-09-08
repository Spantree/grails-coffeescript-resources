includeTargets << grailsScript("_GrailsPlugins")

rhinoInputPackage = "org.mozilla"
rhinoOutputPackage = "org.grails.plugins.coffeescript.mozilla"

target(defineJarJarTask: "Adds a task definition for jarjar") {
	jarjarJar = grailsSettings.buildDependencies.find { File jar ->
		jar.name =~ /jarjar-.*\.jar$/
	}
	println "Found jarjar jar: ${jarjarJar}"
	Ant.taskdef(
		name: 'jarjar',
		classname: 'com.tonicsystems.jarjar.JarJarTask',
		classpath: jarjarJar.path
	)
}

target(buildRhinoJar: "Uses jarjar to package a rhino jar with renamed packages") {
	depends(defineJarJarTask)
	
	rhinoJar = grailsSettings.buildDependencies.find { File jar ->
		jar.name =~ /rhino-.*\.jar$/
	}
	println "Found rhino jar: ${rhinoJar}"

	println "Renaming '${rhinoInputPackage}' classes to '${rhinoOutputPackage}'"
	
	Ant.jarjar(jarFile: "${basedir}/lib/coffeescript-resources-${rhinoJar.name}") {
		zipfileset(src: rhinoJar.path)
		rule(
			pattern: "${rhinoInputPackage}.**",
			result: "${rhinoOutputPackage}.@1"
		)
	}
}
