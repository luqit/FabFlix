public class Billing
{
    public String creditCardNumber;
    public String firstName;
    public String lastName;
    public String creditCardExpiration;

    public Billing()
    {
    }

    public String getNameOnCard() { return firstName; }

 
    public String getCreditCardNumber() { return creditCardNumber; }

    public String getCreditCardExpiration(){ return creditCardExpiration; }
   
}