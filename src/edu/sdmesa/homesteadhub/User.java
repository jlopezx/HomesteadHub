package edu.sdmesa.homesteadhub;
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
 *         Version: 2025-10-30
 */

/**
 * Purpose: The reponsibility of User is to serve as the base class for all
 * users of the
 * HomesteadHub app.
 *
 * This class establishes common properties and methods for both Farmers and
 * Customers.
 */
public abstract class User
{
	// Private field variables
	private String userName; // The user's login identifier
	private String password; // The user's password
	private String userRole; // The user's account type

	/**
	 * Constructor for the User class.
	 * 
	 * @param userName The user's unique login identifier.
	 * @param password The user's password.
	 * @param userRole The role of the user ("Farmer" or "Customer").
	 */
	public User(String userName, String password, String userRole)
	{
		this.userName = userName;
		this.password = password;
		this.userRole = userRole;
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
		return this.userName.equals(inputUser)
				&& this.password.equals(inputPass);
	}

	/**
	 * Purpose: Getter - Returns userName
	 * 
	 * @return userName - String name of the user
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * Purpose: Getter - Returns userRole (Farmer or Customer)
	 * 
	 * @return userRole - String value describing the account type
	 */
	public String getUserRole()
	{
		return userRole;
	}
}
