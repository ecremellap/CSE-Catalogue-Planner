/*
   Author: Zachary Coddington - 1000389288
   Program: Recipe Manager
   Purpose: To Practice Using Classes, Setters and Getters
   Reflection: ArrayList is very powerful for dynamic arrays. But between ArrayList and Arrays; even the fuction calls to the getters
   and setters is very very similar.
*/
import java.util.InputMismatchException;
//import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

class recipe {
	private String recipeName;
	private String ingredients;
	private String prepTime;
	private String servings;

	//getters
	public String getName() { return this.recipeName;}
	public String getIngredients() {return this.ingredients;}
	public String getPrepTime() {return this.prepTime;}
	public String getServings() {return this.servings;}

	//setters
	public void setName(String recipeName) {this.recipeName = recipeName;}
	public void setIngredients(String ingredients) {this.ingredients = ingredients;}
	public void setPrepTime(String prepTime) {this.prepTime = prepTime;}
	public void setServings(String servings) {this.servings = servings;}
}

public class recipesMain {
	static int arraySize = 5; static int arrayCounter = 0; //Trackers for Array
	private static recipe[] recArr = new recipe[arraySize]; //Array

	private static ArrayList <recipe> recArrList = new ArrayList<>(); //ArrayList

	private static final Scanner scanner = new Scanner(System.in); //Scanner for input use



	//**************************Methods
	static void findRecipe()
	{
		int cursor = 0; int selection = -1; //cursor to track progression, selection to select when item matches
		String Name = scanner.nextLine().trim();
		while(cursor < recArrList.size())
		{
			if(recArrList.get(cursor).getName() == Name)
			{
				selection = cursor;
			}
			cursor++;
		}
		//Output the found Recipe
		if(selection != -1)
		{
			System.out.println("Recipe #" + (selection + 1));
			System.out.println("Recipe Name: " + recArrList.get(selection).getName());
			System.out.println("Ingredients: " + recArrList.get(selection).getIngredients());
			System.out.println("Recipe Prep Time: " + recArrList.get(selection).getPrepTime());
			System.out.println("Recipe Servings: " + recArrList.get(selection).getServings());
		}
		else
			System.out.println("Recipe not found. Returing to menu.\n");
	}

	static void displayRecipes(int userChoice)
	{
		if(userChoice == 3)
		{
			//view Array0
			int c = 0;
			if (arrayCounter == 0)
				System.out.println("Array is empty.\n");
			while(c < arrayCounter)
			{
				System.out.println("Recipe #" + (c + 1));
				System.out.println("Recipe Name: " + recArr[c].getName());
				System.out.println("Ingredients: " + recArr[c].getIngredients());
				System.out.println("Recipe Prep Time: " + recArr[c].getPrepTime());
				System.out.println("Recipe Servings: " + recArr[c].getServings());
				c++;
			}
		}
		if(userChoice == 4)
		{
			int c = 0;
			while(c < recArrList.size())
			{
				System.out.println("Recipe #" + (c + 1));
				System.out.println("Recipe Name: " + recArrList.get(c).getName());
				System.out.println("Ingredients: " + recArrList.get(c).getIngredients());
				System.out.println("Recipe Prep Time: " + recArrList.get(c).getPrepTime());
				System.out.println("Recipe Servings: " + recArrList.get(c).getServings());
				c++;
			}
		}
	}

	static void addRecArrList()
	{
		String inputs[] = getRecipeInputs();
		recipe newRecipe = new recipe();
		newRecipe.setName(inputs[0]);
		newRecipe.setIngredients(inputs[1]);
		newRecipe.setPrepTime(inputs[2]);
		newRecipe.setServings(inputs[3]);

		recArrList.add(newRecipe);
		System.out.println("ArrayList has" + recArrList.size() + " recipes.\n");
	}

	static void addRecArr() //Add to the Recipe Array
	{
		String inputs[] = getRecipeInputs();//Assign to temp array
		recipe newRecipe = new recipe(); //create temp object

		//Assign to newRecipe temp object
		newRecipe.setName(inputs[0]);
		newRecipe.setIngredients(inputs[1]);
		newRecipe.setPrepTime(inputs[2]);
		newRecipe.setServings(inputs[3]);
		
		recArr[arrayCounter] = newRecipe; //increase to track

		System.out.println("Array has" + arrayCounter + " recipes.\n");

    	arrayCounter++; //so we don't go over the max

		return;
	} //Add to the Recipe Array

	private static String[] getRecipeInputs() { //can be called repeatedly as needed
		String[] inputs = new String[4];
		System.out.println("\nEnter recipe details:");
		System.out.print("Recipe name: ");
		inputs[0] = scanner.nextLine().trim(); //trims user input; assigns to the array needed values
		System.out.print("Ingredients: ");
		inputs[1] = scanner.nextLine().trim();
		System.out.print("Prep time: ");
		inputs[2] = scanner.nextLine().trim();
		System.out.print("Servings: ");
		inputs[3] = scanner.nextLine().trim();
		return inputs; //returns the whole array of all 4 elements
	}

	static public int validMenuChoice() //Gets User Input for Menu and Validates Input
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



	//Main Method
	public static void main(String[] args) {
		System.out.print("**********************\n");
	
		//ArrayList <recipe> recArrList;


		int userChoice = 999;
		do {
			System.out.print("\nRecipe Manager Menu. Select an option 0-5: \n");
			System.out.print("1. Add New Recipe (array): \n");
			System.out.print("2. Add New Recipe (array list)\n");
			System.out.print("3. Display All " + arrayCounter + " Recipes  (array): \n");
			System.out.print("4. Display All " + recArrList.size() + " Recipes (array list)\n");
			System.out.print("5. Find Recipe By Name: \n");
			System.out.print("0. Exit Program.\n");
			System.out.println("***********************");

			userChoice = validMenuChoice();

			switch (userChoice) {
				case 0: {
					System.out.println("0: Exiting Program.\n"); break;
					}
				case 1: {
					System.out.println("1: Add New Recipe (array):\n");
					if(arrayCounter < arraySize)
					{
						addRecArr();
					}
					else
						System.out.println("Error. Array is at maximum.\n");
					break;
					}
				case 2: {
					System.out.println("2: Add New Recipe (array list):\n");
					addRecArrList();
					break;
					}
				case 3: {
					System.out.println("3: Display All Recipes in (array):\n");
					displayRecipes(userChoice);
					break;
					}
				case 4: {
					System.out.println("4: Display All Recipes in (array list):\n");
					displayRecipes(userChoice);
					break;
					}
				case 5: {
					System.out.println("5: Find Recipe By Name\n");
					findRecipe();
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
}//End recipeManager Class