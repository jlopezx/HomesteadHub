package edu.sdmesa.homesteadhub;

import java.util.HashMap;
import java.util.List;
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
 * @version 2025-12-5
 * 
 * @Purpose The reponsibility of PortalManager is to manage user sessions,
 *          authenticate, and act as the FarmMarketService API entry point.
 */
public class PortalManager
{
	// Map is instantiated and ready to hold User data. Key is username.
	private Map<String, User> userRoster;

	private final DataRepository dataRepository;

	/**
	 * Initializes the PortalManager by injecting the data repository
	 * and loading all user data into the roster.
	 * 
	 * @param dataRepository The source of user data.
	 */
	public PortalManager(DataRepository dataRepository)
	{
		this.dataRepository = dataRepository;
		// Initializes the user roster map
		userRoster = new HashMap<>();
		loadAllUsers();
	}

	/**
	 * Loads all user accounts from the repository into the roster.
	 */
	private void loadAllUsers()
	{
		// Fetch all users from the data layer
		List<User> allUsers = dataRepository.findAllUsers();

		// Populate the map for fast lookups
		// Could use a binary tree search alogrithm for faster, effcient lookups
		// instead of a for loop
		for (User user : allUsers)
		{
			// Store user by their username
			userRoster.put(user.getUsername(), user);
		}
		System.out.println("PortalManager initialized. Loaded "
				+ allUsers.size() + " user accounts.");
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
		if (!user.authenticate(username, password))
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