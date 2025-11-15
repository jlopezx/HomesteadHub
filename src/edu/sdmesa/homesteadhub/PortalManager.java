package edu.sdmesa.homesteadhub;

import java.util.HashMap;
import java.util.Map;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-11-11
 * 
 * @Purpose The reponsibility of PortalManager is to manage user sessions,
 *          authenticate, and act as the FarmMarketService API entry point.
 */
public class PortalManager
{
	// Map is instantiated and ready to hold User data. Key is userName.
	private Map<String, User> userRoster;

	public PortalManager()
	{
		// Initializes the user roster map
		userRoster = new HashMap<>();
	}

	/**
	 * Purpose: Authenticates a user based on the provided username and password
	 *
	 *
	 * @throws UserNotFoundException       if the username is not found
	 * @throws InvalidCredentialsException if the password does not match
	 * 
	 * @param username The username provided by the user
	 * @param password The password provided by the user
	 * 
	 * @return The authenticated User object
	 */
	public User login(String username, String password)
			throws UserNotFoundException, InvalidCredentialsException
	{
		// Searches for our user in our userRoster (user database)
		User user = userRoster.get(username);

		// Check if user exists (UserNotFoundException)
		if (user == null)
		{
			throw new UserNotFoundException(username);
		}

		// Check if password matches (InvalidCredentialsException)
		if (!user.getPassword().equals(password))
		{
			throw new InvalidCredentialsException();
		}

		// Returns the user if it is valid and userName/password combo match
		return user;

	}

	/**
	 * Adds a user to the roster
	 * 
	 * @param user The user object to add
	 */
	public void addUser(User user)
	{
		userRoster.put(user.getUsername(), user);
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
