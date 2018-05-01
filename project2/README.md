## CS 122B Project 2 API example

This example shows how frontend and backend are separated by implementing a star list page and a single star page with movie list.

### To run this example: 
1. clone this repository using `git clone https://github.com/UCI-Chenli-teaching/project2-api-example.git`
2. open Eclipse -> File -> import -> under "Maven" -> "Existing Maven Projects" -> Click "Finish".
3. For "Root Directory", click "Browse" and select this repository's folder. Click "Finish".
4. In `WebContent/META-INF/context.xml`, make sure the mysql username is `mytestuser` and password is `mypassword`.
5. Also make sure you have the `moviedb` database.
6. You can run this project on Tomcat now.

### Brief Explanation
`StarsServlet.java` is a Java servlet that talks to the database and get the stars. It returns a list of stars in the JSON format. 
The name of star is generated as a link to Single Star page.

`index.js` is the main Javascript file that initiates an HTTP GET request to the `StarsServlet`. After the response is returned, `index.js` populates the table using the data it gets.

`index.html` is the main HTML file that imports jQuery, Bootstrap, and `index.js`. It also contains the initial skeleton for the table.

`SingleStarServlet.java` is a Java servlet that talks to the database and get information about one Star and all the movie this Star performed. It returns a list of Movies in the JSON format. 

`single-star.js` is the Javascript file that initiates an HTTP GET request to the `SingleStarServlet`. After the response is returned, `single-star.js` populates the table using the data it gets.

`single-star.html` is the HTML file that imports jQuery, Bootstrap, and `single-star.js`. It also contains the initial skeleton for the movies table.

### Separating frontend and backend
For project 2, you are recommended to separate frontend and backend. Backend Java Servlet only provides API in JSON format. Frontend Javascript code fetches the data through HTTP (ajax) requests and then display the data on the webpage. 

This approach is **not** required, but we recommend it because it's considered a better practice compared to `JSP`, a very outdated technology.

This example uses `jQuery` for making HTTP requests and manipulate DOM. jQuery is relatively easy to learn compared to other frameworks. This example also includes `Bootstrap`, a popular UI framework to let you easily make your webpage look fancy. 

You are free to use any technology/tool/framework you want. The backend language must be Java.


### DataSource
For project 2, you are recommended to use tomcat to manage your DataSource instead of manually define MySQL connection in each of the servlet.

`WebContent/META-INF/context.xml` contains a DataSource, with database information stored in it.
`WEB-INF/web.xml` registers the DataSource to name jdbc/moviedb, which could be referred to anywhere in the project.

In both `SingleStarServlet.java` and `StarsServlet.java`, a private DataSource reference dataSource is created with `@Resource` annotation. It is a reference to the DataSource `jdbc/moviedb` we registered in `web.xml`

To use DataSource, you can create a new connection to it by `dataSource.getConnection()`, and you can use the connection as previous examples.