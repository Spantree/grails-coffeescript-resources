includeTargets << new File("scripts/_RhinoJarJar.groovy")

eventCreateWarStart = { name, stagingDir ->
    // TODO: must be done here something with war archive
}

eventSetClasspath = {
	binding.getVariable('buildRhinoJar').call()
	new File('lib').eachFileMatch(~/coffeescript-resources-rhino-.*.jar$/) { file ->
		rootLoader.addURL(new URL("file:${file.path}"))
	}
}