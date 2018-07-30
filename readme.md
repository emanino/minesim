# Mine Simulation

## Overview

Simulation of a mining environment.

## Startup

### Project Setup

1. Setup Eclipse IDE with Maven and the Java EE plugins.
2. Install Apache Tomcat (tested Tomcat 8).
3. Import the project in Eclipse from GitHub.
4. Add the Tomcat server to the Eclipse project. You might have to give Eclipse enough permissions to access the Tomcat files. In case of ClassNotFoundException, go to properties, Deployment Assembly, and add Java Build Path Entries. You might have add the Tomcat jars in Maven: ```<groupId>org.apache.tomcat</groupId>
    <artifactId>tomcat-servlet-api</artifactId>
    <version>8.5.32</version>```

### Running the Simulation

Start the server by selecting the project and then Run on Server. If everything is setup correctly you will be able to access an graphical interface to the simulation from your Tomcat server, e.g. from: `http://localhost:8080/minesim/index.jsp?`.
You can now generate mine, and perform a number of updates to see how the mine evolves.
