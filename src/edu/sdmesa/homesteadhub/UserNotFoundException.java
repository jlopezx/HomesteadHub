package edu.sdmesa.homesteadhub;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-11-21
 * 
 * @Purpose Custom exception thrown when a login attempt fails because
 *          the provided username does not exist in the system.
 */
@SuppressWarnings("serial")
public class UserNotFoundException extends Exception
{

	/**
	 * Purpose: Constructs a new UserNotFoundException with a detail message.
	 * 
	 * @param username The username that was not found.
	 */
	public UserNotFoundException(String username)
	{
		super("Login failed: User '" + username
				+ "' was not found in the system.");
	}
}