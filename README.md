# AjiraNet Web API

[Spring Boot](http://projects.spring.io/spring-boot/) web API to simulate a local network for communication-related purposes.

## Requirements

For building and running the application you need:

* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
* 	[Maven](https://maven.apache.org/) - Dependency Management

### Running the application with IDE

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.ajithsolomon.ajiranet.AjiranetApplication` class from your IDE.

* 	Download the zip or clone the Git repository.
* 	Unzip the zip file (if you downloaded one)
* 	Open Command Prompt and Change directory (cd) to folder containing pom.xml
* 	Open Eclipse
	* File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
	* Select the project
* 	Choose the Spring Boot Application file (search for @SpringBootApplication)
* 	Right Click on the file and Run as Java Application

* 	URL to access application UI: **http://localhost:8080/ajiranet/process**

### Running the application with Maven

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
$ git clone https://github.com/yourfriendajith/ajiranet.git
$ cd ajiranet
$ mvn spring-boot:run

```shell
mvn spring-boot:run
```
### Accessing Data in H2 Database

#### H2 Console

URL to access H2 console: **http://localhost:8080/h2-console/login.jsp**

Fill the login form as follows and click on Connect:

* 	Saved Settings: **Generic H2 (Embedded)**
* 	Setting Name: **Generic H2 (Embedded)**
* 	Driver class: **org.h2.Driver**
* 	JDBC URL: **jdbc:h2:mem:sbat;MODE=MySQL**
* 	User Name: **sa**
* 	Password:

<img src="images\h2-console-login.PNG"/>

<img src="images\h2-console-main-view.PNG"/>

## Testing API
### Testing with cURL


