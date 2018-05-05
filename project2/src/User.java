import java.util.HashMap;

/**
 * This User class only has the username field in this example.
 * <p>
 * However, in the real project, this User class can contain many more things,
 * for example, the user's shopping cart items.
 */
public class User {

    private final String username;
    private HashMap<String, Integer> cart = new HashMap<String, Integer>();
    
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
    
    public HashMap<String, Integer> getCart(){
    	return this.cart;
    }
    
    public void addMovie(String title) {
    	if(cart.containsKey(title)) {
    		cart.put(title, cart.get(title) + 1);
    	}
    	else {
    		cart.put(title, 1);
    	}
    }
    
    public void removeMovie(String title) {
    	if(cart.get(title) != null || cart.get(title) != 0)
    		cart.put(title, cart.get(title) - 1);
    }
    

}
