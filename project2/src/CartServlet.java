import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

