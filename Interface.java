import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
public class Interface {

	private static ObjectInputStream read,readReview;
	private static ObjectOutputStream output, outputReview;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, Review> Reviews= new HashMap<String, Review>(); // map for reviews, quick insertion of a new one.
		Scanner input= new Scanner(System.in);						// reviews for the bank
		Map <Integer,Account> Accounts = new HashMap<Integer, Account> (); // hash map to hold the account and their numbers
		boolean exit= false; // flag to terminate the program
		char choice;
		
		
		// Reading the master file of reviews in the bank
		 
				/* try
				 {
					 /// reading the Accounts that were stored in the file and put them in the map
					 read = new ObjectInputStream(Files.newInputStream(Paths.get("Reviews.ser")));
					 while(true)
					 {
						 Review review= (Review) readReview.readObject();
						 String Author= (review.getAuthor());
						 Reviews.put(Author, review);
						 
					 }
				 }
				catch(EOFException e)
				 {
					// whatever
				 }
				 catch(ClassNotFoundException e)
				 {
					 System.err.println("Review Object Not Found");
				 }
				 
				 catch(IOException e)
				 {
					 System.err.println("File Not Found");
				 }
				
				// close the file
				
				try
				{
					if(readReview!=null)
						read.close();
				}
				catch(IOException e)
				{
					//nothing to display
				}
		*/
		
		// Reading the master file of accounts in the bank
		 
		 try
		 {
			 /// reading the Accounts that were stored in the file and put them in the map
			 read = new ObjectInputStream(Files.newInputStream(Paths.get("Accounts.ser")));
			 while(true)
			 {
				 Account readAccount= (Account) read.readObject();
				 Integer AccountNumber= (readAccount.getAccountNo());
				 Accounts.put(AccountNumber, readAccount);
				 
			 }
		 }
		catch(EOFException e)
		 {
			// whatever
		 }
		 catch(ClassNotFoundException e)
		 {
			 System.err.println("Account Object Not Found");
		 }
		 
		 catch(IOException e)
		 {
			 System.err.println("File Not Found");
		 }
		System.out.println("Welcome to Java Bank");
		
		// close the file
		
		try
		{
			if(read!=null)
				read.close();
		}
		catch(IOException e)
		{
			//nothing to display
		}
		
		//Main menu of the application
		do
		{
			System.out.println("c- Create a new account with us\n"
					+ "l- Log into your account\n"
					+ "x- Exit the program\n"
					+ "r- Review our services\n"
					+ "v- View users reviews\n");
			choice=input.nextLine().charAt(0); // I do not care if the user input a word. I just parse the first character
			choice= Character.toLowerCase(choice);
			switch(choice)
			{
				case 'l': LogIn(Accounts);
				break;
				case 'c': CreateAccount(Accounts);
				break;
				case 'r': MakeReviews(Reviews);
				break;
				case 'v': ViewReviews(Reviews);
				break;
				case 'x': System.out.println("Thank You four chosing our services\nGoodbye!");
							exit=true;
				break;
				default: exit=false;
			}
			
		}
		while(!exit);
		// End of the main menu
	}
	
	// creating a log in function
	public static void LogIn(Map<Integer, Account> a)
	{
		Scanner input= new Scanner(System.in);
		System.out.println("Enter your Account Number");
		Integer id= Integer.parseInt(input.nextLine()); // parse the input as an integer
		String password;
		if(a.containsKey(id)) // if the account number is in the database
		{
			String correctPassword= a.get(id).getPassword();
			System.out.println(correctPassword);
			do {
				System.out.print("Enter your password\n");
				password= input.next().trim();
			if(password.equals(correctPassword))
				AccountMenu(a.get(id));
			
				System.out.println("Worng Password, try again\n");
				password= input.next().trim();
			}
			while(password.equals(correctPassword));
		}
		else
			System.out.println("No such account, start over\n");
		
	}
	
	// creating a new account
	public static void CreateAccount(Map<Integer, Account> a)
	{	
		Account newAccount= new Account();
		Scanner input= new Scanner(System.in);
		System.out.println("Pease enter your first name: ");
		String fn= input.nextLine().trim();
		newAccount.setFname(fn);
		System.out.println("Pease enter your middle name: ");
		String mn= input.nextLine().trim();
		newAccount.setMname(mn);
		System.out.println("Pease enter your last name: ");
		String ln= input.nextLine().trim();
		newAccount.setLname(ln);
		System.out.println("Please enter your Government ID number: ");
		String ID= input.nextLine().trim(); // I can do a while loop to make sure that a correct ID was entered (format, length...etc)
		newAccount.setPersonID(ID); // for the person ID insertion, I will need a map conataining all the person ID and their names to verify missmatches
		System.out.println("Create your own security question: ");
		String sq= input.nextLine().trim();
		newAccount.setSecurityQuestion(sq);
		System.out.println("Your Security question is saved as \""+sq+"\". You can change it anytime later on");
		System.out.println("Create an answer for your security question: ");
		String sqAnswer= input.nextLine().trim();
		newAccount.setSecurityQuestion(sqAnswer);
		System.out.println("Your Security question is answered as \""+sqAnswer+"\". You can change it anytime later on");
		
		System.out.println("Pease enter the desired amount to open the account: ");
		double amt= input.nextDouble();
		newAccount.Deposit(amt);
		System.out.println("Your Account have been succesfully created! Now you need to set up a password to access it.");
		int accNo=1000001+a.size(); // the account number is by choice that it starts from 1000001 and adds the size of the map
		boolean ok=false;					// just so that it contains 8 digits
		newAccount.setAccountNo(accNo); //inserting this account number to the account object
		do 
		{
			System.out.println("Please enter a password of at least 8 characters: ");
			String psw= input.next().trim();
			System.out.println("Confirm your password: ");
			String psw2= input.next().trim();
			String pswd1= new String(psw);
			String pswd2= new String(psw2);
			
			ok=true; // Assuming everything is good
			
			//check length
			if(pswd2.length()<8)
			{
				System.out.println("Password to short! Try again");
				ok=false;
			}
			
			
			// check matching password
			if(!pswd1.equals(pswd2))
			{
				System.out.println("Passwords do not match! Try again.");
				ok=false;
			}
			
			// check if password is weak 
			if(weakPassword(newAccount, pswd1))
			{
				System.out.println("Your chosen password is too weak. Here is a suggestion: "+suggestedPassword());
				ok=false;
			}
			
			if(ok)
				newAccount.setPassword(pswd1); // setting the password for the newly created account
		}
		while(!ok);
		a.put(accNo, newAccount);
		
		// adding all the accounts in the map to the file
	    Collection <Account> AccountSet= a.values(); // put all the accounts that were in the set to that collection
	    Set<Account> setAccount= new HashSet<>(AccountSet); // turn the Collection of accounts into a non duplicate set
		try
		{
			output = new ObjectOutputStream(Files.newOutputStream(Paths.get("Accounts.ser")));
			for(Account acc: setAccount)
			{
				output.writeObject(acc); // write each account object to the file in a serialized way
			}
		}
		catch(NoSuchElementException e)
		{
			// I could not care less
		}
		catch(IOException e)
		{
			e.printStackTrace(); // print the type of exception occurred
		}
		
		// close the file again
		try
		{
			if(read!=null)
				read.close();
		}
		catch(IOException e)
		{
			//nothing to display
		}
		System.out.printf("Here is your account number %d, you will need it with your password to log in next time%n", accNo);
	}
	
	
	/// Make reviews function starts
	public static void MakeReviews(Map<String, Review> a)
	{
		Scanner input= new Scanner(System.in);
		String author, review;
		System.out.println("Please provide us with your name before you type in your review: ");
		author=input.nextLine();
		System.out.println("You can write your review now: ");
		review= input.nextLine();
		Date date = new Date();  // get the date from the system
		Review newReview= new Review(author, review, date.toString());
		// put the review in the file
		
		Set<Review> revw= new HashSet<Review>(a.values());
		try
		{
			output = new ObjectOutputStream(Files.newOutputStream(Paths.get("Reviews.ser")));
			for(Review acc: revw)
			{
				outputReview.writeObject(acc); // write each account object to the file in a serialized way
			}
		}
		catch(NoSuchElementException e)
		{
			// I could not care less
		}
		catch(IOException e)
		{
			e.printStackTrace(); // print the type of exception occured
		}
		
		// close the file again
		try
		{
			if(read!=null)
				read.close();
		}
		catch(IOException e)
		{
			//nothing to display
		}
		a.put(author, newReview);
		
	} /// make review function ends
	
	
	/// veiw reviews funtion starts here
	public static void ViewReviews(Map<String, Review> a)
	{
		System.out.println("Here is a list of reviews from our customers: ");
		Set<Review> reviews= new HashSet<Review>(a.values());
		for(Review r: reviews)
			r.printReview();
	}
	// print reviews function ends

	
	// Menu for when the user log into his/her account
	public static void AccountMenu(Account a)
	{
		int choice;
		boolean stay=false;
		Scanner input = new Scanner(System.in);
		do {
		System.out.println("1- View Balance\n"
				+ "2- View Account Number\n"
				+ "3- View Summary\n"
				+ "0- Log Out\n");
		
			choice= input.nextInt();
			switch(choice)
			{
				case 1: System.out.println("Your balance is $"+a.getBalance());
				break;
				case 2: System.out.println("Your Account Number is"+a.getAccountNo());
				break;
				case 3: a.toString();
				break;
				case 0: System.out.println("Logged out"); 
						stay=true;
				break;
				default: stay=false;
			}
		}
		while(!stay);
		
		
	} // user Account menu ends here
	
	// weak password function
	private static boolean  weakPassword(Account a, String p)
	{
		// I could also use a hash map from a known list of weak password
		String passwd[]= {"1234","qwer","1qaz","abcd","1111","0000","aaaa"}; // de facto unwanted sequence of character in a password
		if(p.contains(a.getFname())|| p.contains(a.getLname())| p.contains(a.getMname()))
			return true;
		for(int i=0; i<7; i++)
		{
			if(p.contains(passwd[i])) // if the passed password contains one of the array's sequence
				return true;  // it is a weak password
		}
		
		return false; // finally if it passes these previous tests then it is not weak
	} // end of weak password function
	
	// this function makes a password of randomly chosen 12 characters
	private static String suggestedPassword()
	{
		String password="";
		Random r= new Random();
		char c;
		// suggesting 12 random characters for the  password
		for(int i=0; i<12; i++)
		{
			c= (char)(r.nextInt(93)+'!'); // this gives any character from the ascii code 33-126
			password+=c;
		}
		return password;
	} // end of the suggested password function
	
}
