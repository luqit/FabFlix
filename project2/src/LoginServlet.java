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
import java.sql.Statement;

import org.jasypt.util.password.StrongPasswordEncryptor;

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
	     	Statement statement = connection.createStatement();
			String query = String.format("SELECT * from customers where email='%s'", username);
			//ResultSet rs = statement.executeQuery(query);
	     	System.out.println("Login Servlet:" + username);	     	
	     	ResultSet resultSet = statement.executeQuery(query);
	     	boolean success = false;
	        if (resultSet.next()) {
	            
	            String encryptedPassword = resultSet.getString("password");
				success = new StrongPasswordEncryptor().checkPassword(password, encryptedPassword);
				if(success == true) {
					request.getSession().setAttribute("user", new User(username));
		            String userId = resultSet.getString("id");
		            request.getSession().setAttribute("userId", userId);
		            //JsonObject responseJsonObject = new JsonObject();
		            responseJsonObject.addProperty("status", "success");
		            responseJsonObject.addProperty("message", "success");	
		            response.getWriter().write(responseJsonObject.toString());
				}
				else {
					 responseJsonObject.addProperty("status", "fail");
			         responseJsonObject.addProperty("message", "invalid password!");	
			         response.getWriter().write(responseJsonObject.toString());
				}
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

