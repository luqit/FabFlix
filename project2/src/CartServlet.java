<<<<<<< HEAD
import javax.annotation.Resource;
=======


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
>>>>>>> eaefac934d6890f68d9005415343dc37cd65bfad
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import javax.sql.DataSource;

import java.io.IOException;

//
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 5L;
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.out.println("got it!");
        String id = request.getParameter("movieid");
        String title = request.getParameter("movientitle");
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("ShoppingCart");
        if (cart == null){
            cart = new ShoppingCart();
            request.getSession().setAttribute("ShoppingCart", cart);
        }
        
        MovieInCart itemToAdd = new MovieInCart(id,title,0);
        cart.addItem(itemToAdd);
        System.out.println(cart);
        
    }
}

=======
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
>>>>>>> eaefac934d6890f68d9005415343dc37cd65bfad
