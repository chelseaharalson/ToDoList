# ToDoList

**Domain:** To-Do List

**Interface:** Web UI (Server-generated)

**Platform:** Java

**Language:** JavaScript

You can see it in action here: http://todolist-env.nakins5fmz.us-east-2.elasticbeanstalk.com/#

Users are able to add a task, delete a task, edit a task, complete a task, complete all tasks, delete all tasks, and view details about the task. There are two viewing modes. One to show the not completed list and another to show all the tasks (complete or incomplete). If a task is overdue, the text color will turn red. A user can also choose to mark a task as incomplete by simply unchecking the checkbox and then pressing the complete button.

There are JUNIT test cases to test the functionality of the ToDoList object.

### Source Code:
**ToDo/src/controller/DataTableServlet.java:** servlet for interaction between server and client

**ToDo/src/model/ToDo.java:** ToDo object

**ToDo/src/model/ToDoList.java:** ToDoList functions

**ToDo/src/model/ToDoListTest.java:** JUNIT test cases

**ToDo/WebContent/scripts/ToDoListLogic.js:** JavaScript file where ajax is used for callbacks (interaction between front end and back end)

**ToDo/WebContent/css/*:** css files for page and datatable

**ToDo/WebContent/scripts/*:** jQuery files for datatable

**ToDo/WebContent/images/*:** image files for page

**ToDo/WebContent/fonts/*:** fonts for designs on buttons

**ToDo/WebContent/index.jsp:** main JSP file

### Running the Code:

**AWS:**

I've uploaded the code on AWS (http://todolist-env.nakins5fmz.us-east-2.elasticbeanstalk.com/#) so that you can see what it looks like in the browser. 

**Tomcat Instructions:**

* I have also included the WAR file. You can deploy it on the Tomcat v8.5 Server.

1. Download Tomcat here: https://tomcat.apache.org/download-80.cgi

2. Setup environment variables for CATALINA_HOME and JAVA_HOME

3. Run the startup script in the tomcat directory. It is in the bin folder. It will either be catalina.sh or catalina.bat.

* Example:
```
sh catalina.sh start
```

4. Go to http://localhost:8080 and use the Manager app to deploy the WAR file.

5. You may also need to change the conf/tomcat-users.xml file to:

```
<role rolename="manager-gui"/>
<user password="cam" roles="manager-gui,manager-script,admin" username="chelsea"/>
```

6. Go to http://localhost:8080/ToDo to see the app

**Eclipse Instructions:**

I coded the project in Eclipse and included the full Eclipse folder, so you can also import the project in Eclipse. Be sure to add the servlet-api.jar in the referenced libraries and the json-20170516.jar as an external library if they don't load automatically.

The filepath to the json jar is: ToDo/WebContent/WEB-INF/lib/json-20170516.jar