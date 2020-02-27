[![Build Status](https://travis-ci.org/moley/adonai.svg?branch=master)](https://travis-ci.org/moley/adonai)
[![CodeCoverage](https://codecov.io/gh/moley/adonai/branch/master/graph/badge.svg)](https://codecov.io/gh/moley/adonai)

distZip -> create distribution
install -> installieren


#Development

## Start the application in IDEA 
You have to configure a gradle call in rootproject with jar task in your launch configuration. 
Use org.adonai.app.AdonaiBootstrap as main class and the root project's dir as working directory. 

## Start the application in gradle 
*Does not work currently*

##Build a fat jar
If you want to build a jar with bootstrapping, api and all plugins call gradlew :app:uberjar 
