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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        
        JsonObject responseJsonObject = new JsonObject();
        // verify the recaptcha response
		try {      	
            RecaptchaVerifyUtils.verify(gRecaptchaResponse);             
            responseJsonObject.addProperty("gstatus", "success");
            //response.getWriter().write(responseJsonObject.toString());
        }
        catch (Exception e) {
            responseJsonObject.addProperty("gstatus", "fail");
            responseJsonObject.addProperty("gmessage", e.getMessage());
            response.getWriter().write(responseJsonObject.toString());
            return;
        }  
		try {
	     	Connection connection = dataSource.getConnection();
	     	String query = "select * from customers where email=? and password=?";
	     	PreparedStatement statement = connection.prepareStatement(query);
	     	statement.setString(1, username);
	     	statement.setString(2, password);
	     	System.out.println("Login Servlet:" + username);	     	
	     	ResultSet resultSet = statement.executeQuery();
	
	        if (resultSet.next()) {
	            request.getSession().setAttribute("user", new User(username));
	            String userId = resultSet.getString("id");
	            request.getSession().setAttribute("userId", userId);
	            //JsonObject responseJsonObject = new JsonObject();
	            responseJsonObject.addProperty("status", "success");
	            responseJsonObject.addProperty("message", "success");	
	            response.getWriter().write(responseJsonObject.toString());
	        } else {
	            // Login fail
	            //JsonObject responseJsonObject = new JsonObject();
	            responseJsonObject.addProperty("status", "fail");
	            responseJsonObject.addProperty("message", "invalid user information!");
	            System.out.println("all error info:" + responseJsonObject.toString());
	            response.getWriter().write(responseJsonObject.toString());
	        }
	    }
        catch(Exception e) {
			e.printStackTrace();
			return;
		}
    }
}

