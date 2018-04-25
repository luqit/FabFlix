

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		String password = request.getParameter("pass");
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		
		try {
		// create database connection
		Connection connection = dataSource.getConnection();
		// prepare query
		String query = "select * from customers where email=? and password=?";
		// prepare statement
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		statement.setString(2, password);
		// execute query
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next())
		{
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			response.sendRedirect("mainPage");
		}
			
	}
		catch(Exception e) {
		e.printStackTrace();
		}

}
}
