import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//
@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 5L;
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
        	// create database connection
	     	Connection connection = dataSource.getConnection();
	     	// prepare query
	     	String query = "select * from customers where email=? and password=?";
	     	// prepare statement
	     	PreparedStatement statement = connection.prepareStatement(query);
	     	statement.setString(1, username);
	     	statement.setString(2, password);
	     	System.out.println("Login Servlet:" + username);
	     	// execute query
	     	ResultSet resultSet = statement.executeQuery();
	
	        /* This example only allows username/password to be test/test
	        /  in the real project, you should talk to the database to verify username/password
	        */
	        if (resultSet.next()) {
	            // Login success:
	
	            // set this user into the session
	            request.getSession().setAttribute("user", new User(username));
	
	            JsonObject responseJsonObject = new JsonObject();
	            responseJsonObject.addProperty("status", "success");
	            responseJsonObject.addProperty("message", "success");	
	            response.getWriter().write(responseJsonObject.toString());
	        } else {
	            // Login fail
	            JsonObject responseJsonObject = new JsonObject();
	            responseJsonObject.addProperty("status", "fail");
	            responseJsonObject.addProperty("message", "invalid user information!");
	            response.getWriter().write(responseJsonObject.toString());
	        }
	    }
        catch(Exception e) {
			e.printStackTrace();
		}
    }
}
