package edu.sdmesa.homesteadhub;

import java.util.HashMap;
import java.util.Map;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         Version: 2025-10-31
 */

/**
 * Purpose: The reponsibility of PortalManager is to managee user sessions,
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
