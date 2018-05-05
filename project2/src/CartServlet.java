

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/moviedb")
    private DataSource dataSource;

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String decrementTitle = request.getParameter("decrement");
		String incrementTitle = request.getParameter("increment");
		
		PrintWriter out = response.getWriter();
		JsonArray jsonArray = new JsonArray();
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		
		user.addMovie("Inception");
		
		if(!decrementTitle.equals("null") && !decrementTitle.isEmpty())
			user.removeMovie(decrementTitle);
		if(!incrementTitle.equals("null") && !incrementTitle.isEmpty())
			user.addMovie(decrementTitle);
		HashMap<String, Integer> cart = user.getCart();
			
		
		for(Map.Entry<String, Integer> entry: cart.entrySet()) {
		JsonObject jsonObject = new JsonObject();	
		String title = entry.getKey();
		Integer quantity = entry.getValue();
		jsonObject.addProperty("title", title);
		jsonObject.addProperty("quantity", quantity);
		jsonArray.add(jsonObject);
		}
		
		out.write(jsonArray.toString());
	}

}
