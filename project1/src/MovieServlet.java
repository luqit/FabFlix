import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class MovieServlet
 */
@WebServlet("/MovieServlet")
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUser = "root";
        String loginPasswd = "Cookiesncream123!";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
        // set response mime type
        response.setContentType("text/html"); 

        // get the printwriter for writing response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><link rel=\"stylesheet\" href=\"style.css\"><title>Fabflix</title></head>");
        
        
        try {
        		Class.forName("com.mysql.jdbc.Driver").newInstance();
        		// create database connection
        		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        		// declare statement
        		Statement statement1 = connection.createStatement();
        		// prepare query
        		String query1 = "SELECT * FROM movies, (SELECT * from ratings order by rating DESC limit 20) AS top20 WHERE movies.id = top20.movieId";
        		// execute query
        		ResultSet resultSet1 = statement1.executeQuery(query1);
        		

        		out.println("<body>");
        		out.println("<h1>MovieDB Top20</h1>");
        		
        		out.println("<table border>");
        		
        		// add table header row
        		out.println("<tr>");
        		out.println("<td class = \"h1\">Title</td>");
        		out.println("<td class = \"h1\">Rating</td>");
        		out.println("<td class = \"h1\">Year</td>");
        		out.println("<td class = \"h1\">Director</td>");
        		out.println("<td class = \"h1\">Genres</td>");
        		out.println("<td class = \"h1\">Stars</td>");
        		out.println("</tr>");
        		
        		// add a row for every star result
        		while (resultSet1.next()) {
        			// get a star from result set
        			String mID = resultSet1.getString("movieId");
        			String mTitle = resultSet1.getString("title");
        			String mRating = resultSet1.getString("rating");
        			String mYear = resultSet1.getString("year");
        			String mDirector = resultSet1.getString("director");
        			Statement statement2 = connection.createStatement();
        			String query2 = "SELECT name FROM genres, (SELECT genreId FROM genres_in_movies WHERE movieId=\"" + mID + "\") AS g WHERE (genres.id = g.genreId)";
        			ResultSet resultSet2 = statement2.executeQuery(query2);
        			Statement statement3 = connection.createStatement();
        			String query3 = "SELECT name FROM stars, (SELECT starId FROM stars_in_movies WHERE movieId=\"" + mID + "\") AS s WHERE (stars.id = s.starId)";
        			ResultSet resultSet3 = statement3.executeQuery(query3);
        			
        			out.println("<tr>");
        			out.println("<td class = \"h2\">" + mTitle + "</td>");
        			out.println("<td class = \"h2\">" + mRating + "</td>");
        			out.println("<td class = \"h2\">" + mYear + "</td>");
        			out.println("<td class = \"h2\">" + mDirector + "</td>");
        			out.println("<td class = \"h2\">");
        			while(resultSet2.next())
        			{
        				if(resultSet2.isLast())
        				{
        					out.println(resultSet2.getString("name"));
        					break;
        				}
        				out.println(resultSet2.getString("name") + ", ");		
        			}
        			out.println("</td>");
        			out.println("<td class = \"h2\">");
        			while(resultSet3.next())
        			{
        				if(resultSet3.isLast())
        				{
        					out.println(resultSet3.getString("name"));
        					break;
        				}
        				out.println(resultSet3.getString("name") + ", ");
        			}
        			out.println("</td>");
        			out.println("</tr>");
        		}
        		
        		out.println("</table>"); 		
        		out.println("</body>");
        		
        		resultSet1.close();
        		statement1.close();
        		connection.close();
        		
        } catch (Exception e) {
        		/*
        		 * After you deploy the WAR file through tomcat manager webpage,
        		 *   there's no console to see the print messages.
        		 * Tomcat append all the print messages to the file: tomcat_directory/logs/catalina.out
        		 * 
        		 * To view the last n lines (for example, 100 lines) of messages you can use:
        		 *   tail -100 catalina.out
        		 * This can help you debug your program after deploying it on AWS.
        		 */
        		e.printStackTrace();
        		
        		out.println("<body>");
        		out.println("<p>");
        		out.println("Exception in doGet: " + e.getMessage());
        		out.println("</p>");
        		out.print("</body>");
        }
        
        out.println("</html>");
        out.close();
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
