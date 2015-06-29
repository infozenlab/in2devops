<b>InfoZen Prototype URL: http://fda.identrix.com/</b>

<b>InfoZen GitHub URL: https://github.com/infozenlab/fdademo</b>

openFDA - Infozen Open Source Prototype Setup
============================================================

Summary
-------
The project is a web application visualizing openFDA Data as per required by GSA 18F Agile Delivery Services RFQ.

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

Deployed, Live, application can be accessed anytime at http://fda.identrix.com

Installation
------------

To install the application in your local repository execute following commands:

```bash
    git clone https://github.com/infozenlab/fdademo.git
    cd fdademo
    mvn clean install
```

<b>Run the project</b>


```bash
	mvn test tomcat7:run
```

<b>Test on the browser</b>


	http://localhost:8080/fdademo

Note: No additional services are required in order to start the application. No DB configuration is required.

Creating a manual deployment
----------------------------
```bash
    git clone https://github.com/infozenlab/fdademo.git
    cd fdademo/target
```    
Copy fdademo.war file
```bash
    cd $TOMCAT_HOME/webapps
```
Paste fdademo.war
Start tomcat server

<b>Test Manual Deployment on the browser</b>

	http://localhost:8080/fdademo

Note: No additional services are required in order to start the application. No DB configuration is required.

Creating a new project in Eclipse
----------------------------------

* Import project URI by `Import ... > Projects from Git > Clone URI`
* Paste https://github.com/infozenlab/fdademo.git into `URI`
* Select `dev` branch
* Confirm directory and remote name `origin`
* Import as general project
* Leave the default `fdademo` as project name
* Click Finish

* Right-Click on project and `Configure > Convert to Maven Project`
* Right-Click on project `Run as > Run on Server` follow wizard till server is up and running

<b>Test on the browser</b>

	http://localhost:8080/fdademo

Note: No additional services are required in order to start the application. No DB configuration is required.

Running Selenium Test on Mozilla Firefox
----------------------------------------
<b>Pre-requisites</b>
* Mozilla Firefox 37+

```bash
    cd fdademo
    mvn clean install -P integration-tests
```

750 Word Brief Narrative
========================

InfoZen leveraged its Agile methods and DevOps principles that have been honed at DHS and NASA. InfoZen’s CEO assigned Jeff Highman as the leader/delivery manager who’s accountable for the delivery of the solution, process, and the product within one week<b><sup>1</sup></b>.  Jeff assembled a multidisciplinary team including a technical architect, front-end developer, DevOps engineer, and Agile coach. A lean plan was formed to align to the U.S Digital Services Playbook – these correlations are noted throughout this narrative with “Play #” citations.

We set out to support the business hypothesis: <b>users will see trends when the openFDA data is aggregated and visualized effectively</b>.  

The team used personas to project users who would benefit from an enriched experience that would build upon openFDA<b><sup>2</sup></b>. The team examined the whole API to discover any limitations that could be overcome for system stakeholders<b><sup>3</sup></b>. We built upon the USPTO style guide and aligned our data visualizations accordingly<b><sup>4</sup></b>.   We used a Kanban method, Lean UX, Continuous Integration, and DevOps practices<b><sup>5</sup></b>. We emulated the experience of a contract for five people with the requirement of shipping a production application deployed, tested, and documented to enable smooth handover<b><sup>6</sup></b>. Each team member was experienced with coding, continuous integration, and DevOps<b><sup>7</sup></b>. The team chose a modern, lightweight, technology stack based on proven open source<b><sup>8</sup></b> to enable rich functionality with deep security<b><sup>9</sup></b>. The application was deployed and tested on both private cloud (VMWare) and public cloud (AWS)<b><sup>10</sup></b>, was tested inside/out with automated tools/techniques,<b><sup>11</sup></b> and system performance was monitored in real-time<b><sup>12</sup></b>.

The team used the Kanban method to track user stories, technical features, and documentation tasks over the lifecycle.  They held daily stand-ups, user reviews/demos and used Lean UX methods that included personas, wireframes, hypothesis statement, and user stories to rapidly assimilate the development.  The developers tested the API for variations of the hypothesis against usability criteria.  The team collectively decided on application architecture that enabled data to be aggregated and visualized. The team defined continuous integration tooling and target deployment containers.  The selected user interface included Bootstrap, D3.js, and JQuery.  The back-end API layer was Java-based and used Spring MVC, Spring Security, Thymeleaf Template engine, and SLF4J.  The continuous integration was based on Maven, Jenkins, JUnit, and Selenium.   The code was tracked on version control with Git with libraries managed by Nexus.  The target build environments were CentOS hosting Tomcat built with Vagrant.  The developers used the Eclipse IDE.  The team produced low-fidelity diagrams to seed development which began on day one. Features were developed as fully functional vertical slices of the application that were verified by the product owner.  The team took an API-first approach that made these options addressable through a Restful-API. A minimum viable product was defined to include an aggregated visualization of drug-related data that plots the number of events for a given country.

The functionality was tested with several layers that included unit testing with JUnit, UI testing with Selenium, test isolation with Mockito.  Tests were run within the continuous integration pipeline with every commit of code to the Git repository.  SonarQube was used to evaluate the code quality with each build.  The application was deployed to end users to enable feedback, focus groups, and user acceptance testing. The prototype is cloud portable and was tested for public (AWS) and private (VMWare). The user interface was also tested for browser compatibility on major browsers.

The application architecture was conceived to support the hypothesis that users could see trends if the data was aggregated and visualized.  The service layer encapsulated the aggregation so this function could be embedded into other applications.  The Spring framework provided a Model View Controller that allowed the application to be extended for advanced security, persistence, or caching should these needs arise.  The UI Layer adopted a similar library to the openFDA architecture to allow these solutions to easily merge for a broader solution.  The team extended the USPTO style guide, based on Bootstrap and JQuery, to provide a type-ahead feature so input criteria was easier for the user. In addition, 18F could now engage another agency by beginning with reuse.

The team held a retrospective on the last day of the sprint.  The velocity of the team was generally within the planned capacity and quality.  The team concluded that this data set could be further enriched for many variations of the business hypothesis.

<b><sup>1</sup></b><href>https://playbook.cio.gov/#play6</href>
<b><sup>2</sup></b><href>https://playbook.cio.gov/#play1</href>
<b><sup>3</sup></b><href>https://playbook.cio.gov/#play2</href>
<b><sup>4</sup></b><href>https://playbook.cio.gov/#play3</href>
<b><sup>5</sup></b><href>https://playbook.cio.gov/#play4</href>
<b><sup>6</sup></b><href>https://playbook.cio.gov/#play5</href>
<b><sup>7</sup></b><href>https://playbook.cio.gov/#play7</href>
<b><sup>8</sup></b><href>https://playbook.cio.gov/#play13</href>
<b><sup>9</sup></b><href>https://playbook.cio.gov/#play8</href>
<b><sup>10</sup></b><href>https://playbook.cio.gov/#play9</href>
<b><sup>11</sup></b><href>https://playbook.cio.gov/#play10</href>
<b><sup>12</sup></b><href>https://playbook.cio.gov/#play12</href>
