import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShoppingCart 
{
// The shopping cart items are stored in a Vector.
    protected Vector<MovieInCart> items;

    public ShoppingCart(){
        items = new Vector<MovieInCart>();
    }

/** Returns a Vector containing the items in the cart. The Vector
 *  returned is a clone, so modifying the vector won't affect the
 *  contents of the cart.
 */
    public Vector<MovieInCart> getItems(){
        return items;
    }

    public void addItem(MovieInCart newItem){
        items.addElement(newItem);
    }

    public void removeItem(int itemIndex){
        items.removeElementAt(itemIndex);
    }
}
