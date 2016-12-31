
/*Customer Class that goes inside our cashier queue
 * Each object inside the Queue is a customer
 */

public class Customer {
	int customerID;
	int cartItems;
	double totalPrice;
	
	//all our customers will be random so we only need the default constructor
	public Customer(){
		customerID = (int)(Math.random() * 20) + 1;
		cartItems = (int)(Math.random() * 20) + 1;
		totalPrice = cartItems * 10;
	}

	//explicitly set ID, items, or total if needed, but for our APP we won't be using them
	public void setID(int customerID){  
		this.customerID = customerID;
	}
	//getters
	public int getID(){ 
		return customerID;
	}
	public int getNumItems(){
		return customerID;
	}
	public double getTotal(){
		return totalPrice;
	}
	
	

	@Override public String toString(){ //our toString method that we will use inside the cashier class
		return "User ID " + customerID + "\n"+
			   "Number of Cart items: " + cartItems + "\n" +
				"Total Price: " + totalPrice+ "\n";
	}


}
