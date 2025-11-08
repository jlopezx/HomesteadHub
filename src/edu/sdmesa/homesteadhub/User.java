package edu.sdmesa.homesteadhub;

import java.util.UUID;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         GeeksforGeeks. (2025).
 *         What is Java Enterprise Edition (Java EE)?
 *         https://www.geeksforgeeks.org/java/java-enterprise-edition/
 * 
 *         GeeksforGeeks. (2023).
 *         E-commerce Architecture | System Design for E-commerce Website
 *         https://www.geeksforgeeks.org/system-design/e-commerce-architecture-system-design-for-e-commerce-website/
 * 
 *         GeeksforGeeks. (2025).
 *         Inventory Management System in Java
 *         https://www.geeksforgeeks.org/java/inventory-management-system-in-java/
 * 
 *         Java Architecture: Components with Examples. (2025).
 *         Java Architecture: Components with Examples
 *         https://vfunction.com/blog/java-architecture/
 * 
 *         Mahmoud. (2024).
 *         Building a Simple E-Commerce Ordering System in Java Using OOP
 *         https://techwithmahmoud.medium.com/building-a-simple-e-commerce-ordering-system-in-java-using-oop-00f051f4825e
 * 
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 *         Stack Overflow. (2020).
 *         How should I design an E-commerce Class Diagram?
 *         https://stackoverflow.com/questions/65023323/how-should-i-design-an-e-commerce-class-diagram
 * 
 *         Version: 2025-10-30
 */

/**
 * Purpose: The reponsibility of User is to serve as the base class for all
 * users of the HomesteadHub app.
 *
 * This class establishes common properties and methods for both Farmers and
 * Customers.
 */
public abstract class User
{
	// Private field variables
	private final String userId; // The user's ID
	private String username; 	 // The user's login identifier
	private String password; 	 // The user's password
	private String email;		 // The user's email address

	/**
	 * Constructor for the User class.
	 * 
	 * @param userName The user's unique login identifier.
	 * @param password The user's password.
	 * @param email    The user's contact email.
	 */
	public User(String username, String password, String email)
	{
		this.userId = UUID.randomUUID().toString();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * Protected constructor for the User class.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param userId   The unique user's system identifier.
	 * @param username The user's unique login identifier.
	 * @param password The user's password.
	 * @param email    The user's contact email.
	 */
	protected User(String userId, String username, String password,
			String email)
	{
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * Method to authenticate the user's credentials.
	 * 
	 * @param inputUser The username entered by the user.
	 * @param inputPass The password entered by the user.
	 * @return true if credentials match, false otherwise.
	 */
	public boolean authenticate(String inputUser, String inputPass)
	{
		// Simple check if userName and password match
		return this.username.equals(inputUser)
				&& this.password.equals(inputPass);
	}

	/**
	 * Purpose: Getter - Returns userName
	 * 
	 * @return userName - String name of the user
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Purpose: Getter - Returns the stored plaintext password.
	 * 
	 * TODO: For now, we're storing plaintext for testing and impleneting
	 * Will look into hashing password later down the road.
	 * 
	 * @return password String representing the user's password.
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Purpose: Getter - Returns the user's unique system ID.
	 * 
	 * @return userId - String representing the unique user identifier.
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * Purpose: Getter - Returns email address
	 * 
	 * @return email String email address of the user
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Abstract method to be implemented by subclasses (Customer/Farmer) to
	 * return their specific role.
	 * 
	 * @return The role of the user ("Farmer" or "Customer").
	 */
	public abstract String getRole();

}
