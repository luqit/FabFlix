import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BrowseServlet", urlPatterns = "/api/browse")
public class BrowseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.xml
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json"); // Response mime type
        PrintWriter out = response.getWriter();
   
		//String genre = request.getParameter("genreid");		

        try {
			Connection database = dataSource.getConnection();
			PrintWriter out1 = response.getWriter();
			
			System.out.println("CONNECTED");
			
			String method = request.getParameter("by");
			String initial = request.getParameter("init");
			String title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String name = request.getParameter("starName");
			String genre1 = request.getParameter("genreid");
			String query = "";
			System.out.println("initial:" + initial);
			System.out.println("genre:" + genre1);
			System.out.println("method:" + method);
			
			if (method.equals("genre"))
			{
			System.out.println("GENREEEE");
			query += "select movies.id, movies.title, movies.year, movies.director, ";
			query += "group_concat(distinct(stars.name)) as starNames, group_concat(distinct(genres.name)) as genreNames, ratings.rating from movies ";
			query += "join stars_in_movies on movies.id = stars_in_movies.movieId ";
			query += "join stars on stars.id = starId ";
			query += "join ratings on movies.id = ratings.movieId ";
			query += "join genres_in_movies on movies.id = genres_in_movies.movieId ";
			query += "join genres on genres_in_movies.genreId = genres.id ";
			query += "group by movies.id having genreNames LIKE '%" + genre1 + "%'";
			}
			
			if (method.equals("title"))
			{
			System.out.println("TITLEEEE");
			query += "select movies.id, movies.title, movies.year, movies.director, ";
			query += "group_concat(distinct(stars.name)) as starNames, group_concat(distinct(genres.name)) as genreNames, ratings.rating from movies ";
			query += "join stars_in_movies on movies.id = stars_in_movies.movieId ";
			query += "join stars on stars.id = starId ";
			query += "join ratings on movies.id = ratings.movieId ";
			query += "join genres_in_movies on movies.id = genres_in_movies.movieId ";
			query += "join genres on genres_in_movies.genreId = genres.id ";
			query += "group by movies.id having movies.tile LIKE" + initial + "%'";
			}
			
			else
			{
			List<String> queryList = new ArrayList<String>();
			
			query += "select movies.id, movies.title, movies.year, movies.director, ";
			query += "group_concat(distinct(stars.name)) as starNames, group_concat(distinct(genres.name)) as genreNames, ratings.rating from movies ";
			query += "join stars_in_movies on movies.id = stars_in_movies.movieId ";
			query += "join stars on stars.id = starId ";
			query += "join ratings on movies.id = ratings.movieId ";
			query += "join genres_in_movies on movies.id = genres_in_movies.movieId ";
			query += "join genres on genres_in_movies.genreId = genres.id where ";
			
			if(title != null || !title.isEmpty())
			{
				queryList.add("movies.title LIKE '%" + title + "%'");
				System.out.println(queryList);
			}
			
			if(year != null || !year.isEmpty())
			{
				queryList.add("movies.year LIKE '%" + year + "%'");
				System.out.println(queryList);
			}
			
			if(director != null || !director.isEmpty())
			{
				queryList.add("movies.director LIKE '%" + director + "%'");
				System.out.println(queryList);
			}
			/*
			if(name != null || ! name.isEmpty())
			{
				queryList.add("star.names LIKE '%" + name + "%'");
				System.out.println(queryList);
			}
			*/
			String queryAdd = String.join(" AND ", queryList);
			
			System.out.println(queryAdd);
			
			query += queryAdd;
			query += " group by movies.id having starNames LIKE '%" + name + "%'";
			
			System.out.println(query);
			}
			
			Statement statement = database.createStatement();
			
			ResultSet rs = statement.executeQuery(query);	

			JsonArray jsonArray = new JsonArray();

			// Iterate through each row of rs
			while (rs.next()) {

				String movieId = rs.getString("id");
				String movieTitle = rs.getString("title");
				String movieYear = rs.getString("year");
				String movieDirector = rs.getString("director");
				String genreNames = rs.getString("genreNames");
				String starNames = rs.getString("starNames");
				String rating = rs.getString("rating");

				// Create a JsonObject based on the data we retrieve from rs

				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("movieId", movieId);
				jsonObject.addProperty("movieTitle", movieTitle);
				jsonObject.addProperty("movieYear", movieYear);
				jsonObject.addProperty("movieDirector", movieDirector);
				jsonObject.addProperty("genreNames", genreNames);
				jsonObject.addProperty("starNames", starNames);
				jsonObject.addProperty("rating", rating);
				
				jsonArray.add(jsonObject);
				}
						
			    // write JSON string to output
			out1.write(jsonArray.toString());
			System.out.println(jsonArray.toString());
			    // set response status to 200 (OK)
			response.setStatus(200);

			rs.close();
			statement.close();
			database.close();
			
			
        } catch (Exception e) {
        	
			// write error message JSON object to output
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("errorMessage", e.getMessage());
			out.write(jsonObject.toString());

			// set reponse status to 500 (Internal Server Error)
			response.setStatus(500);

        }
        out.close();

    }
}
