
public class Cashier {
	private int stackSize; //used to hold size of stack
	private Customer[] customerQue; //array of Objects , each customer is an object
	private int frontOfQue; //Queues are first in first out, this would be first inserted
	private int rearOfQue; //insert items into the rear ,
	private int customers; //used to keep track of number if items
	
	public Cashier() { //basic cashier, you should only need a constructor to change the stack size of queue
		stackSize = 10;
		customerQue = new Customer[stackSize];
		rearOfQue = -1;
		frontOfQue = 0;
		customers = 0;
	}
	
	public Cashier(int lineLength) { //should be the only one you need because you should only need to change the stack size
		stackSize = lineLength;
		customerQue = new Customer[stackSize];
		rearOfQue = -1;
		frontOfQue = 0;
		customers = 0;
	}

	
	//create an array of objects, each customer is an object
	//used to add customers to queue, takes in the customerID and cart items(both random integers)	
	void addToQueue(Customer c){ //add to rear
	
		if (rearOfQue == stackSize-1) //to make sure we're not at the end of the queue and prevent wrap around
			rearOfQue = -1;
		
		customerQue[++rearOfQue] = c; //needs to insert a customer object into the customer Queue from rear 
		customers++;
	}
	
	public int getLength() {
		return customers; //returns number of items in Queue
	}
	public int getFrontOfQue(){
		return frontOfQue;
	}
	
	public void setFrontOfQue(int newFront) {
		frontOfQue = newFront;
	}
	
	public void setRearOfQue(int newRear){ // this was used in my end service from middle method, left it to show you my train of thought
		rearOfQue = newRear;
	}
	
	public int getRearOfQue(){ //this was used in my wrongly implemented end service from middle method
		return rearOfQue;
	}
	
	public Customer[] getCustomer() {
		return customerQue;
	}
	
	public Customer endService() { //remove from front
	
		Customer temp = customerQue[frontOfQue++]; //increments the front and holds the customer
		if (frontOfQue == stackSize) //deal with front wrap around
			frontOfQue = 0;
		customers--;
		return temp; //increments front , so the customer can be replaced
		
	}
	
	/* My end service from middle was not working correctly. I was trying to implement a confusing algorithm,
	 * So I adjusted my plan to add a remove from rear method, and I would adjust the queues by removing 
	 * Elements from the rear until the queues are the same length, I would store those customers in
	 * a temporary queue, and then remove those elements from the rear back into the line that needed size adjustment,
	 * this method preserves the line order
	 */
	public Customer endServiceFromRear() {
		Customer temp = customerQue[rearOfQue--];
		if (rearOfQue == -1){
			rearOfQue = stackSize-1;
		}
		customers--;
		return temp;
	}
	
	/*I made this function to attempt to adjust queue length the correct way
	 * However, I could not get it to work 100% of the time, specifically when the
	 * rear was larger than the front. it would occasionally print out the wrong value
	 * So I wrote a new algorithm that would involve using the remove from rear method above.
	 * I left this method here to show you my thinking while trying to fix my method.
	 */
	public void endServiceFromMiddle(Cashier Line, Cashier LineToAdd) {//remove from middle for adjust queue method
		
		int newRear = ((Line.getLength()/2) - 1);
		int tempFront = Line.getFrontOfQue();
		Line.setFrontOfQue(newRear+1);
		int i = frontOfQue-1;
	
		while (i <= customers){ 
			LineToAdd.addToQueue(Line.endService());
			i++;
		}
		
		Line.setFrontOfQue(tempFront); //set back to the value you held
		Line.setRearOfQue(newRear); //store that old front
			}
	
	
	public boolean isFull(){ //make sure your customer queue is not full when trying to add it
		return (customers == stackSize);
	}
	
	public boolean isEmpty(){ //used to make sure you don't over empty your queue if it is already empty
		return (customers == 0);
	}
	
	
	@Override public String toString(){ 
		String str = "";
		System.out.println("Number of customers in Line: " + customers);
		System.out.println("---------Front of Line-----------");
		
		if (frontOfQue <= rearOfQue){ //front is less than the rear
			for(int i = frontOfQue; i <= rearOfQue; i++){
				if(frontOfQue == stackSize)
					frontOfQue = 0;
				
				str += customerQue[i] + "\n";
			}
		}
		else //rear is greater than front so we have to adjust our logic
		{
			while(true){ //front is greater than rear, so we must
				for(int i = frontOfQue; i < stackSize;i++){  //print the front until the end
					str += customerQue[i] + "\n";
				}
				for(int i = 0; i <= rearOfQue;i++){ //now we're here, the front is 0, so we have to print the front until the rear
					str += customerQue[i] + "\n";
				}
				break; //if we make it here, we're done printing, we can exit the loop and return the string
			}
		}
			
		return str;
	}
	

}
