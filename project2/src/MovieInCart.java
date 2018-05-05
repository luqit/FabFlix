public class MovieInCart
{
    public String movieId;
    public String movieTitle;
    public int quantity;
    

    public MovieInCart()
    {
    }

    public MovieInCart(String aProductCode, String aTitle, int aQuantity)
    {
        movieId = aProductCode;        
        movieTitle = aTitle;
        quantity = aQuantity;
    }

// Make get/set methods so the attributes will appear
// as bean attributes.

    public String getMovie() { return movieId; }

    public String getTitle() { return movieTitle; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int aQuantity) { quantity = aQuantity; }
}