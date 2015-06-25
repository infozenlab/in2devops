InfoZen Prototype URL: https://fda.identrix.com/
------------------------------------------------
InfoZen GitHub URL: https://github.com/infozenlab/fdademo
---------------------------------------------------------

Open FDA - Spring MVC, Bootstrap Prototype Application Setup
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

Running Selenium Test on Mozilla Firefox
----------------------------------------
```bash
    cd fdademo
    mvn clean install -P integration-tests
```

750 Word Brief Narrative
========================

InfoZen leveraged its Agile methods and DevOps principles that have been honed at DHS and NASA. InfoZen’s CEO assigned Jeff Highman as the leader/delivery manager who’s accountable for the delivery of the solution, process, and the product within <a href="https://playbook.cio.gov/#play6">one week</a>.  Jeff assembled a multidisciplinary team including a technical architect, front-end developer, DevOps engineer, and Agile coach. A lean plan was formed to align to the U.S Digital Services Playbook – these correlations are noted throughout this narrative with “Play #” citations.

We set out to support the business hypothesis: <b>users will see trends when the openFDA data is aggregated and visualized effectively</b>.  

The team used personas to project users who would benefit from an enriched experience that would build upon openFDA<sup>2</sup>. The team examined the whole API to discover any limitations that could be overcome for system stakeholders<sup>3</sup>. We built upon the USPTO style guide and aligned our data visualizations accordingly<sup>4</sup>.   We used a Kanban method, Lean UX, Continuous Integration, and DevOps practices<sup>5</sup>. We emulated the experience of a contract for five people with the requirement of shipping a production application deployed, tested, and documented to enable smooth handover<sup>6</sup>. Each team member was experienced with coding, continuous integration, and DevOps<sup>7</sup>. The team chose a modern, lightweight, technology stack based on proven open source<sup>8</sup> to enable rich functionality with deep security<sup>9</sup>. The application was deployed and tested on both private cloud (VMWare) and public cloud (AWS)<sup>10</sup>, was tested inside/out with automated tools/techniques,<sup>11</sup> and system performance was monitored in real-time<sup>12</sup>.

The team used the Kanban method to track user stories, technical features, and documentation tasks over the lifecycle.  They held daily stand-ups, user reviews/demos and used Lean UX methods that included personas, wireframes, hypothesis statement, and user stories to rapidly assimilate the development.  The developers tested the API for variations of the hypothesis against usability criteria.  The team collectively decided on application architecture that enabled data to be aggregated and visualized. The team defined continuous integration tooling and target deployment containers.  The selected user interface included Bootstrap, D3.js, and JQuery.  The back-end API layer was Java-based and used Spring MVC, Spring Security, Thymeleaf Template engine, and SLF4J.  The continuous integration was based on Maven, Jenkins, JUnit, and Selenium.   The code was tracked on version control with Git with libraries managed by Nexus.  The target build environments were CentOS hosting Tomcat built with Vagrant.  The developers used the Eclipse IDE.  The team produced low-fidelity diagrams to seed development which began on day one. Features were developed as fully functional vertical slices of the application that were verified by the product owner.  The team took an API-first approach that made these options addressable through a Restful-API. A minimum viable product was defined to include an aggregated visualization of drug-related data that plots the number of events for a given country.

The functionality was tested with several layers that included unit testing with JUnit, UI testing with Selenium, test isolation with Mockito.  Tests were run within the continuous integration pipeline with every commit of code to the Git repository.  SonarQube was used to evaluate the code quality with each build.  The application was deployed to end users to enable feedback, focus groups, and user acceptance testing. The prototype is cloud portable and was tested for public (AWS) and private (VMWare). The user interface was also tested for browser compatibility on major browsers.

The application architecture was conceived to support the hypothesis that users could see trends if the data was aggregated and visualized.  The service layer encapsulated the aggregation so this function could be embedded into other applications.  The Spring framework provided a Model View Controller that allowed the application to be extended for advanced security, persistence, or caching should these needs arise.  The UI Layer adopted a similar library to the openFDA architecture to allow these solutions to easily merge for a broader solution.  The team extended the USPTO style guide, based on Bootstrap and JQuery, to provide a type-ahead feature so input criteria was easier for the user. In addition, 18F could now engage another agency by beginning with reuse.

The team held a retrospective on the last day of the sprint.  The velocity of the team was generally within the planned capacity and quality.  The team concluded that this data set could be further enriched for many variations of the business hypothesis.

<sup>1</sup><href>https://playbook.cio.gov/#play6</href>
<sup>2</sup><href>https://playbook.cio.gov/#play1</href>
<sup>3</sup><href>https://playbook.cio.gov/#play2</href>
<sup>4</sup><href>https://playbook.cio.gov/#play3</href>
<sup>5</sup><href>https://playbook.cio.gov/#play4</href>
<sup>6</sup><href>https://playbook.cio.gov/#play5</href>
<sup>7</sup><href>https://playbook.cio.gov/#play7</href>
<sup>8</sup><href>https://playbook.cio.gov/#play13</href>
<sup>9</sup><href>https://playbook.cio.gov/#play8</href>
<sup>10</sup><href>https://playbook.cio.gov/#play9</href>
<sup>11</sup><href>https://playbook.cio.gov/#play10</href>
<sup>12</sup><href>https://playbook.cio.gov/#play12</href>
