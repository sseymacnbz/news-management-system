# NEWS MANAGEMENT SYSTEM
## Info About Project
* The news management system is a management application where the news is shown to normal users and 
subscribers, news content entries are made by the publisher editors, and the main editor makes 
arrangements about news, contents and users.![img_swagger.png](img%2Fimg_swagger.png)


## Used Technologies
* Java 17
* Spring Boot 3.0.6
* PostgreSql 14.1.1
* IntelliJ IDEA Community Edition 2023.1.2 


## How Is The Project Ready To Work?
1) Click on the 'Download ZIP' under the Code button and download.
![img_download.png](img%2Fimg_download.png)
2) Extract the ZIP file.
3) Open IntelliJ IDEA Community Edition.
4) Go to File>Open
![img_open.png](img%2Fimg_open.png)
5) Find the project, select it and click the OK button
![img_chooseProject.PNG](img%2Fimg_chooseProject.PNG)
6) Follow the progress bar at the bottom to understand the project getting ready.
7) Open the pgAdmin 4 application that comes with PostgreSql.
8) Right-click on Databases and Create Database.
![img_db_create.png](img%2Fimg_db_create.png)
9) Give the database name news-management-system in the General section and save.
10) In the Intellij IDEA go to news-management-system-master>src>main>resources>application.properties and write your username and password for database connection
11) Right-click on the NewsManagementSystemApplication under the news-management-system-master>src>main>java>com>newsmanagementsystem and select "Run 'NewsManagement...main()' "
12) The application is running now. To open Swagger UI go to http://localhost:8080/swagger-ui/index.html#/ in a web browser.
13) Database tables are created automatically while the application is running. 
14) To see the tables right-click on the news-management-system database and refresh it. Then you can see tables under Schemas


## Application Scenario
* The main editor will be created automatically when the application is started.
* Create publisher editor with main editor id in main-editor-controller.
* Create 3 user in user-controller.
* Assign one of the user to subscriber in main-editor-controller. 
* Assign one of the user to publisher editor in main-editor-controller.
* Create different contents with publisher editor ids in publisher-editor-controller.
* Create different news with content id and main editor id in main-editor-controller. Do not use all contents to create news.
* Update news with main editor id and news id in main-editor-controller
* In the getNewsContent endpoint in the main-editor-controller, set false to see unused news content, or true to see used news content.
  You can also set pageability here.
* To see normal users news, use 'news' endpoint with user id in user-controller.
* To see subscribers news, use 'news' endpoint with user id in subscriber-controller.
* Delete subscriber with main editor id and user id in main-editor-controller.
* Delete news with  main editor id and news id in main-editor-controller.
* Delete content with main editor id and content id in main-editor-controller. This will also delete news connected with deleted content.
* Delete publisher editor with main editor id and publisher editor id in main-editor-controller.
This will also delete contents and news connected with deleted publisher editor.
