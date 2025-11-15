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
 * @version 2025-11-11
 * 
 * @Purpose Custom exception thrown when a user is found, but the provided
 *          password does not match the stored credentials.
 */
@SuppressWarnings("serial")
public class InvalidCredentialsException extends Exception
{

	/**
	 * Purpose: Constructs a new InvalidCredentialsException with a detail message
	 */
	public InvalidCredentialsException()
	{
		super("Login failed: Invalid password provided.");
	}
}
