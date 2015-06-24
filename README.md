FDA Open Data Spring MVC, Bootstrap Application Setup
=====================================================

Summary
-------
The project is a web application visualizing FDA Open Data as per required by FDA Open Data Challenge.

Generated project characteristics
---------------------------------
* No-xml Spring MVC 4 web application for Servlet 3.0 environment
* Thymeleaf, Bootstrap
* JUnit/Mockito
* Spring Security 4.1

Pre-requisites
--------------
* Tomcat 7+
* JAVA 1.8+
* Maven 3.2+

Access Application from Private Cloud
-------------------------------------

Deployed code and Live application can be accessed anytime at http://fda.identrix.com

Installation
------------

To install the application in your local repository execute following commands:

```bash
    git clone https://github.com/infozenlab/fdademo.git
    cd fdademo
    mvn clean install
```

Run the project
----------------

```bash
	mvn test tomcat7:run
```

Test on the browser
-------------------

	http://localhost:8080/fdademo

Note: No additional services are required in order to start the application. No DB configuration is required.

Creating a manual deployment
```bash
    git clone https://github.com/infozenlab/fdademo.git
    cd fdademo/target
	  cp fdademo.war
    cd $TOMCAT_HOME/webapps
```
  Paste fdademo.war
  Start tomcat server

Test on the browser
-------------------

	http://localhost:8080/fdademo

Note: No additional services are required in order to start the application. No DB configuration is required.

Creating a new project in Eclipse
----------------------------------

* Import project URI by `Import ... > Projects from Git > Clone URI`
* Paste https://github.com/infozenlab/fdademo.git into `URI`
* Select `master` branch
* Confirm directory and remote name `origin`
* Import as general project
* Leave the default `fdademo` as project name
* Click Finish

* Right-Click on project and `Configure > Convert to Maven Project`
* Right-Click on project `Run as > Run on Server` follow wizard till server is up and running

Test on the browser
-------------------

	http://localhost:8080/fdademo

Note: No additional services are required in order to start the application. No DB configuration is required.
