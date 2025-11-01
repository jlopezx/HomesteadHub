package edu.sdmesa.homesteadhub;

import java.util.HashMap;
import java.util.Map;

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
 * Purpose: The reponsibility of PortalManager is to manage user sessions,
 * authenticate, and act as the FarmMarketService API entry point.
 */
public class PortalManager
{
	// Map is instantiated and ready to hold User data. Key is userName.
	private Map<String, User> userRoster = new HashMap<>();

	// TODO: Will decide how to initialize after DataRepository is created.
	public PortalManager()
	{
	}

	/**
	 * Placeholder for the login method.
	 * 
	 * @param userName The username to check.
	 * @param password The password to check.
	 * @return The authenticated User object or null.
	 */
	public User login(String userName, String password)
	{
		// Searches for our user in our userRoster (user database)
		User user = userRoster.get(userName);
		// Check if our user is valid and authenticated
		if (user != null && user.authenticate(userName, password))
		{
			// Returns the user if it is valid and userName/password combo match
			return user;
		}
		return null;
	}

	/**
	 * Adds a user to the roster.
	 * 
	 * @param user The user object to add.
	 */
	public void addUser(User user)
	{
		userRoster.put(user.getUserName(), user);
	}

	/**
	 * Purpose: Getter - Returns userRoster
	 * 
	 * @return userRoster Map object
	 */
	public Map<String, User> getUserRoster()
	{
		return userRoster;
	}
}
