import java.util.InputMismatchException;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

//Enumeration for Course Status, See Class Course
enum Status { TAKEN, NOTTAKEN, PLANNED, TAKING }

//Classes Setup
//Custom Class "course" - for the main catalogue of courses
class course {
	private String courseID;
	private String description;
	private String prereqs;
	private int creditAmt;
	private Status status;

	//getters
	public String getcourseID() { return this.courseID;}
	public String getdescription() {return this.description;}
	public String getprereqs() {return this.prereqs;}
	public int getCreditAmt() {return this.creditAmt;}
	public Status getStatus() {return this.status;}

	//setters
	public void setcourseID(String courseID) {this.courseID = courseID; setCreditAmt();}
	public void setdescription(String description) {this.description = description;}
	public void setprereqs(String prereqs) {this.prereqs = prereqs;}
	public void setStatus(Status status) { this.status = status;}

	//Calculates the CreditHours for the course;
	public int setCreditAmt() {
		String[] tokens = courseID.split(" ");
		creditAmt = Character.getNumericValue(tokens[1].charAt(1));
		return creditAmt;
	}
}

class Group {
	private course Course;

	private String groupName;
	private int totCreditHours;
	private boolean satisfied;
	ArrayList<course> options;
	ArrayList<course> picks;

	public String getGroupName() { return this.groupName;}
	public int getTotCreditHours() { return this.totCreditHours;}
	public boolean getSatisfied() { return this.satisfied;}
	public ArrayList getOptions() { return this.options;}
	public ArrayList getPicks() { return this.picks;}

	public void setGroupName(String groupName) { this.groupName = groupName;}
	public void setTotCreditHours(int totCreditHours) { this.totCreditHours = totCreditHours;}
	public void setSatisified(boolean satisfied) { this.satisfied = satisfied;}
	public void setOptions(ArrayList options) { this.options = options;}
	public void setPicks(ArrayList picks) { this.picks = picks;}

	public boolean satisfiedCheck() {
		for(course c : picks)
		return true;
	}
	{
		
	}
}

class Category{
	private ArrayList<Group> groups;
	private String categoryName;
	private boolean satisfied;
	private int totalCreditHours;

	public Category(ArrayList<Group> groups, String categoryName, boolean satisfied, int totalCreditHours){
		this.groups = groups;
		this.categoryName = categoryName;
		this.satisfied = satisfied;
		this.totalCreditHours = totalCreditHours;
	}

	public ArrayList<Group> getGroups(){
		return this.groups;
	}

	
	public String getCategoryName(){
		return this.categoryName;
	}

	
	public boolean getSatisfied(){
		return this.satisfied;
	}

	
	public int getTotalCreditHours(){
		return this.totalCreditHours;
	}

	public void setGroups(ArrayList<Group> groups){
		this.groups = groups;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public void setGetSatisfied(boolean getSatisfied){
		this.getSatisfied = getSatisfied;
	}

	public void setTotalCreditHours(int totalCreditHours){
		this.totalCreditHours = totalCreditHours;
	}

	public boolean satisfiedCheck(ArrayList<Group> groups){
		boolean status;
		for(Group g : groups){
			if(g.getTotalCreditHours() == this.totalCreditHours){
				status = true;
			}else{
				status = false;
			}
		}
		return status;
	}
}

//This is the Main Class, Name of the Program
public class courseScheduler {
	private static final Scanner scanner = new Scanner(System.in); //Scanner for input use
	//declares Catalogue List, to load all courses available to the program's needs
	private static ArrayList<course> catalogueList = new ArrayList();


//***Methods****/
  //******Calogue Management Methods
	static public void catalogueBuilder()
	{
		try //try catchblock as we read from file
		{
			BufferedReader reader = new BufferedReader(new FileReader("src/catalogue.txt")); //Creates Buffered Reader
			int counter = 0;
			String courseRawData; //Declares the string to hold all data in one line

			while((courseRawData = reader.readLine()) != null) //cycles through each line
			{
				course newCourse = new course(); //prepares temp object to hold read data
				
				String[] parts = courseRawData.split("#"); //tokenizes the string

				//Assigning Values to the temp object
				newCourse.setcourseID(parts[0].trim());
				newCourse.setdescription(parts[1].trim());
				if (parts.length > 2) { newCourse.setprereqs(parts[2].trim()); }
					else { newCourse.setprereqs("None"); }
				newCourse.setStatus(Status.NOTTAKEN); //Assings Not Taken as Default


				catalogueList.add(newCourse); //Adds object data to new item in Catalogue List

				counter++; //increases counter to move to next item in the file
			}

			System.out.printf("%d number of courses read.", counter);
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Error reading the file: " + e.getMessage());
		}
	}
	//Displays the Full Basic Catalogue
	static public void viewCatalogue()
	{
		//int size = catalogueList.size(); int c = 0;
		for(course c : catalogueList)
		{
			System.out.printf("| %13s | %-45s | %d Credit Hours | %13s\n", c.getcourseID(), c.getdescription(), c.getCreditAmt(), c.getStatus());
		}
	}
  //******Calogue Management Methods

  //Menu Navigation Methods	
	static public int validMenuChoice() //Gets User Input for Main Menu and Validates Input
	{
		int choice;
		while(true)
		{
			System.out.print("Your Choice: ");
			try {
					choice = scanner.nextInt();
					scanner.nextLine(); // Consume newline
					return switch (choice) {
						case 0, 1, 2, 3, 4, 5 -> choice;
						default -> {
							System.out.println("Invalid input. Please choose between 1 and 5, or 0 to exit.\n");
							choice = scanner.nextInt(); scanner.nextLine();
							yield choice;
						}
					};
				}
				catch (InputMismatchException e) {
					System.out.println("Invalid input. Please choose between 1 and 5, or 0 to exit.\n");
					scanner.nextLine(); // Consume the invalid input
					//choice = scanner.nextInt(); scanner.nextLine();
				}
		}
	}//Menu Choice and Validation
  //*******Main Method****
	public static void main(String[] args) {
		System.out.print("**********************\n");

		//Used to Build out the Basic Course Database(ArrayList)
		catalogueBuilder();


		//Menu Loop, Main Program Loop, 0 to exit
		int userChoice = 999;
		do {
			System.out.print("\nClass Scheduler. Pick an option: \n");
			System.out.print("1. View Course Catalogue \n");
			System.out.print("2. Find Course By CourseID: \n");
			System.out.print("0. Exit Program.\n");
			System.out.println("***********************");

			userChoice = validMenuChoice();

			switch (userChoice) {
				case 0: {
					System.out.println("0: Exiting Program.\n"); break;
					}
				case 1: {
					System.out.println("1: View Course Catalogue:\n");
					viewCatalogue();
					break;
					}
				case 2: {
					System.out.println("2: Find Course:\n");
					//addRecArrList();
					break;
					}
				default: {
					System.out.println("Something went wrong with input; returning to menu.\n"); break;
					}
				}//End Switch Statement
		}
		while (userChoice != 0);



		scanner.close();
		System.out.print("End of Main Function\n");
	}//End Main Method
  //*******Main Method****
}//End courseScheduler Class