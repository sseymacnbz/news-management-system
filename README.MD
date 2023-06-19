# NEWS MANAGEMENT SYSTEM
## Info About Project
* The news management system is a management application where the news is shown to normal users and
  subscribers, news content entries are made by the publisher editors, and the main editor makes
  arrangements about news, contents and users. <br /> ![img_swagger.png](img%2Fimg_swagger.png)
![db_diagram.PNG](img%2Fdb_diagram.PNG)

## Used Technologies
* Java 17
* Spring Boot 3.0.6
* Spring Data Jpa
* PostgreSql 14.1.1
* IntelliJ IDEA Community Edition 2023.1.2


## How Is The Project Ready To Work?
1) Clone the project to Intellij IDEA.
2) Follow the progress bar at the bottom to understand the project getting ready.
3) Open the pgAdmin 4 application that comes with PostgreSql.
4) Right-click on Databases and Create Database.
   <br /> ![img_db_create.png](img%2Fimg_db_create.png)
5) Give the database name news-management-system in the General section and save.
6) In the Intellij IDEA go to news-management-system-master>src>main>resources>application.properties and write your username and password for database connection
7) Right-click on the NewsManagementSystemApplication under the news-management-system-master>src>main>java>com>newsmanagementsystem and select "Run 'NewsManagement...main()' "
8) The application is running now. To open Swagger UI go to http://localhost:8080/swagger-ui/index.html#/ in a web browser.
9) Database tables are created automatically while the application is running.
10) To see the tables right-click on the news-management-system database and refresh it. Then you can see tables under Schemas

## Roles
* subscriber :Subscribers are people who are members of the system. Subscriber can;
  * access free news and paid news
* publisher editor : Publisher editors are users who deal with contents. Publisher editor can:
  * create contents
  * view its own contents
* main editor :The main editor is the user who deals with the news. Takes the role of admin in the application. Main editor can;
  * update a news
  * assign users to subscriber
  * assign users to publisher editor
  * create publisher editor
  * create news
  * view whether the contents have been used as news or not.
  * delete subscriber
  * delete publisher editor
  * delete news
  * delete content

## Application Scenario
* The main editor will be created automatically when the application is started.
* Create publisher editor by main editor id in 'createPublisherEditor' endpoint in main-editor-controller.
* Create 3 user in 'create' endpoint in user-controller.
* Assign one of the user to subscriber in 'assignSubscriber' in main-editor-controller.
* Assign one of the user to publisher editor in 'assignPublisherEditor' endpoint in main-editor-controller.
* Create different contents by publisher editor ids in 'createContent' endpoint in publisher-editor-controller.
* Create different news by content id and main editor id in 'createNews' endpoint in main-editor-controller. Do not use all contents to create news.
* Update news by main editor id and news id in 'updateNews' endpoint in main-editor-controller
* In the getNewsContent endpoint in the main-editor-controller, set false to see unused news content, or true to see used news content.
  You can also set pageability here.
* To see normal users news, use 'news' endpoint by user id in user-controller.
* To see subscribers news, use 'news' endpoint by user id in subscriber-controller.
* Delete subscriber by main editor id and user id in 'deleteSubscriber' in main-editor-controller.
* Delete news by  main editor id and news id in 'deleteNews' endpoint in main-editor-controller.
* Delete content by main editor id and content id in 'deleteContent' endpoint in main-editor-controller. This will also delete news connected with deleted content.
* Delete publisher editor by main editor id and publisher editor id in 'deletePublisherEditor' endpoint in main-editor-controller.
  This will also delete contents and news connected with deleted publisher editor.