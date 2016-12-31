/*Author: Ben Xerri
 * This is the main app class.  We will create 2 cashiers to work with.
 * No clone method, all exceptions are accounted for , so the user will not be able to cause an error
 * 
 * Version 2: The only change I made was in the logic of the adjust queues method. To solve this, I added a remove from rear method
 * 
 */
import java.util.Scanner;

public class CustomerApp {
	

	static Cashier Line1; //2 static line cashier objects
	static Cashier Line2;
	static int cashierLength; //used to hold cashier length
	static double totalStoreSales; //used to hold total of store sales
	static String userValue;
	static int userNumber;
	
	public static void main(String [] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the most organized store in the entire world!!");
		System.out.println("Enter length of Cashiers"); //prompt for length of cashier line
		
		String cashierLengthOne = sc.next(); //take in the user input as a string
		while(true){ //used to make sure our user enters the appropriate length
			try{
				
		cashierLength = Integer.parseInt(cashierLengthOne);	 //if we can't convert it, it's not an integer, and we go to the catch block
		if (cashierLength > 0) //if we can convert it and it's larger than 0, we continue with the program
			break;
		else  //other wise they entered a negative number
			System.out.println("You need to enter a positive value for cashiers"); //and we can prompt them to enter a positive value
			cashierLengthOne = sc.next(); //take in that next input
		}catch(Exception e){ //catches conversions that are letter related
			System.out.println("You entered a letter, you need to enter a number greater than 0.");
			cashierLengthOne = sc.next();
			}	
		}	
		
		Line1 = new Cashier(cashierLength); //create the two cashiers with the length the user inputed
		Line2 = new Cashier(cashierLength);
		
		System.out.println("Cashiers created with a length of: " + cashierLength);
		totalStoreSales = 0; //we havn't done anything yet so our total store sales are 0

		System.out.println("-----------------------------------------------------");
		System.out.println("Add a customer to a line by choosing 1"); //these our the choices we will print out for each round
		System.out.println("Enter 2 to serve line 1, or enter 3 to serve line 2");
		System.out.println("Enter 4 to see that status of your lines.");
		System.out.println("Enter 5 to even out the length of your lines.");
		System.out.println("Enter 6 to close line 2 and add the customers to line 1.");
		System.out.println("Enter 7 to close the store and get the total of your sales for the day.");
		System.out.println("-----------------------------------------------------");
		
		userValue = sc.next();
		int userNumber = 0; //by default which will re-prompt the user for the correct key if this value is not changed
		
		try {
			userNumber = Integer.parseInt(userValue);  //used to catch any values that are not integers, if we can't parse the string into an integer, than its not an integer
		}catch (Exception e){ //then we can catch this error
			System.out.println("You entered a letter, that is not valid"); //and print out the user input
		}
	
		
		while(true ){ //keeps loop running, only ends by break statement when user enters 7
			
			if (userNumber == 1){ //the user wants to add a customer to the line
				System.out.println("Which line would you like to go to?"); //now the user needs to pick line 1 or line 2
				String lineChoiceString = sc.next();
				int lineChoice = 0;
				
				try {
					lineChoice = Integer.parseInt(lineChoiceString);
				}catch(Exception e){
					System.out.println("You entered an invalid value");
				}
				if (lineChoice == 1){
					if (!(Line1.isFull())){ //make sure the line isn't already full before adding a customer
						Line1.addToQueue(new Customer()); //as long as its not full, we can add a customer, and we'll use the default new constructor to generate a random ID and number of items
						System.out.println("Customer added to line 1."); //Success message
						}
					else
						System.out.println("Line 1 is full, try adjusting your lines,or checking out customers at the front"); //if the line is full, you'll get an error message and nothing will happen
					}
				
				else if (lineChoice ==2){
					if(!(Line2.isFull())){ //same as line 1,except this is if the user enters 2 for line 2
						Line2.addToQueue(new Customer());
						System.out.println("Customer added to line 2.");
					}
					else
						System.out.println("Line 2 is full, try adjusting your lines,or checking out customers at the front");

				}
				else
					System.out.println("That is not a valid line, please select choice 1, then select choice 1 or 2.");
			} //end of choice one
			if (userNumber == 2){
				finishCustomer(Line1); //choice number 2 and 3 uses a custom method for totaling out the customer and ensuring that their values arn't lost
			}							//logic is down below
			if (userNumber == 3){
				finishCustomer(Line2);
			}
			if (userNumber == 4){  //prints out the status of the queues 
				if (!(Line1.isEmpty())){ //as long as they are not empty
					System.out.println(":::::::::::  Line 1  :::::::::::");
					System.out.println(Line1);
					}
				if (!(Line2.isEmpty())){
					System.out.println(":::::::::::  Line 2  :::::::::::");
					System.out.println(Line2);
				}
			}
			
			if (userNumber == 5){ //events out the two lanes
				System.out.println("::::::::MERGING LANES:::::::");
				adjustQues();
			}
			
			if (userNumber == 6){ //closes line two and adds it to the back of line one
				System.out.println(":::::::::Line 2 CLOSING::::::");
				boolean isMerged = mergeQueues(Line1, Line2);
				if (isMerged){ //success message if the method work
					System.out.println("All customers to the back of Line 1");
					System.out.println("Merge was sucessful");
				}
				else { //fail method if it did not work
					System.out.println("Unable to merge lanes, number of customers exceeds line length");
				}
				
			}
			
			if (userNumber == 7){ //close the store, empty both lines, and return the total
				System.out.println(":::::::::::: STORE CLOSING :::::::::");
				while (!(Line1.isEmpty())){ //finish line 1
					finishCustomer(Line1);
				}
				while (!(Line2.isEmpty())){ //finish line 2
					finishCustomer(Line2);
				}
				
				
				System.out.println("Total Store Sales is:: $" + totalStoreSales); //print out the total
				break; //our program is done and we may break out of the main loop
			}
			
			if(userNumber <= 0 || userNumber > 7)	 //to assure we enter a correct number
				System.out.println("That input is not a valid option for this program.  Please enter (1-7)");
			
			System.out.println("-----------------------------------------------------"); //we return to the prompt for choices 1-6
			System.out.println("Add a customer to a line by choosing 1");
			System.out.println("Enter 2 to serve line 1, or enter 3 to serve line 2");
			System.out.println("Enter 4 to print that status of your lines.");
			System.out.println("Enter 5 to even out the length of your lines.");
			System.out.println("Enter 6 to close line 2 and add the customers to line 1.");
			System.out.println("Enter 7 to close the store and get the total of your sales for the day.");
			System.out.println("-----------------------------------------------------");
			
			userValue = sc.next();
			
			try {
				userNumber = Integer.parseInt(userValue); //use a try/catch to look an input other than a number
			}catch (Exception e){ //this will catch any exceptions that are none integers
			 System.out.println("You entered a letter, that is not valid");
			}
		} //end of while
		System.out.println("Thanks for playing!!");
		sc.close();
	}// end of main method
	
	public static void adjustQues(){ 
		Cashier tempCashier = new Cashier(cashierLength);
		if(Line1.getLength() == Line2.getLength()) //checks to make sure they arn't already the same length
			System.out.println("Lines are equal ,no reason to perform a merge.");
		else if(Math.abs(Line1.getLength() - Line2.getLength()) < 2) //the lanes only have a difference of 1, no reason to merge them
			System.out.println("Difference of lane length is less than 1, no reason to merge.");
		else if(Line1.getLength() > Line2.getLength()) {//if line 1 is longer than line 2
			int i = Line2.getLength(); //get the length of line 2
			while(Line1.getLength() > i){ //as long as line1 is longer than line 2
				tempCashier.addToQueue(Line1.endServiceFromRear()); //remove values from the rear and store them in a temp cashier
				i++; //increment i so we keep order
				} //once we are here line1 is no longer greater in length than line 2
			while (!(tempCashier.isEmpty())) { //now we can empty the temp cashier into Line 2 from rear to preserve order!
				Line2.addToQueue(tempCashier.endServiceFromRear());
			}
		}	//if we are here, the only option left is line2 is greater than line 1 in length
		else {
			int i = Line1.getLength();
			while(Line2.getLength() > i){ //same steps as above, except we remove from the rear of line 2
				tempCashier.addToQueue(Line2.endServiceFromRear()); 
				i++;
			}
			while (!(tempCashier.isEmpty())) {
				Line1.addToQueue(tempCashier.endServiceFromRear()); //to store in line 1
			}
		
		}
	}
	
	public static boolean mergeQueues(Cashier Line1,Cashier Line2){ //merge line 2 and line 1
		if ( (Line1.getLength() + Line2.getLength()) > cashierLength) //if the length of the 2 is greater than the total cashier length
			return false; //we can't merge and we'll return false
	
		Cashier tempCashier = new Cashier(cashierLength);
		
		while(true){ //keep looping until we need to break out
			if(!Line1.isEmpty())
				tempCashier.addToQueue(Line1.endService());
			if (!Line2.isEmpty())
				tempCashier.addToQueue(Line2.endService());
			
			if(Line1.isEmpty() && Line2.isEmpty()) //if both lines are empty
				break; //we're done with this loop, and we can break out of it
		}	
		
		while(!(tempCashier.isEmpty())){ //now we can add the temporary queue back into the original Line 1
			Line1.addToQueue(tempCashier.endService());
		}			
		return true;
	}


	/* pre-condition: The user enters a cashier object as a parameter
	 * post-condition: The customer is removed from the front of the line, and the total of items they had is added to the store total
	 */
	static double finishCustomer(Cashier Line){ //used for choice 2 and 3 instead of repeating the same code twice
		
		if (Line.isEmpty()){ //if the line is empty and they try to end the lane, this is to ensure that the total doesn't change
			System.out.println("This line had no customers, try adding a customer to the line first.");
			return totalStoreSales;
		}//if we make it here, our line has customers and we can get their total
		System.out.println("Serving customer from front of line.  Great job team!");
		Customer finishedCustomer = Line.endService(); //we end the service of the customer that is first in line and store the object in a Customer object
		double customerSales = finishedCustomer.getTotal(); //now that we have created an object of the customer we can get their total and store it in a variable
		totalStoreSales += customerSales; //add customer sales to our total
		
		return totalStoreSales; //return the total
	}
	
	
	} //end CustomerApp class


