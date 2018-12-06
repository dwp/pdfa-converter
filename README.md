# PDF Conversion Utility
## About the project
The project deals with conversion of TEXT and HTML documents to PDF/A documents. It leverages the AGPL version on iText utility.
It is built for a specific scenario being used with-in the department and is used for bulk conversions. The utility is triggered by creation of meta file which is in xml format. 

## How to package it ?
Before packaging ensure to update the `applicationpaths.properties` inside the resources folder. Currently the paths are set to sample values.
As the target file is a PDF/A, it required embedded fonts. The font files are not supplied with this source code but any licensed or open source fonts can be used by supplying the ttf file for it.
Run the below maven command which will generate a packaged jar in the target folder.

`mvn clean package`

## How to install it ?
Run the below command to build the target jar file which is packaged with all the required dependencies. Place the the jar file at a suitable location.

`mvn install`

## How to run it ?

Go to the folder where the jar is placed and just run this command to start the program with 20 threads:

`java -jar ArchivalPdfConversion.jar 20`

If no parameter is provided the program starts with default thread pool of 10. Ensure that JAVA_HOME is pointing to a valid JAVA runtime with version 1.7+.

## META

Distributed under the AGPL 3.0 license. See [License](LICENSE) for more information.

## Contributing
[Contribution Guidelines](CONTRIBUTING.md)
