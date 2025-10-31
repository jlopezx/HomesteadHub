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
 * Purpose: The reponsibility of Farmer is to represents a Farmer in the
 * HomesteadHub system.
 * 
 * Inherits from User and contains farmer-specific properties like the farm
 * name.
 */
public class Farmer extends User
{
	private String farmName;
	private double commissionRate;

	/**
	 * Constructor for Farmer.
	 * 
	 * @param userName The farmer's username.
	 * @param password The farmer's password.
	 * @param farmName The name of the farmer's operation.
	 */
	public Farmer(String userName, String password, String farmName,
			double commissionRate)
	{
		// Passes required information to the User class through the super
		// constructor
		// "Farmer" is passed as the user role.
		super(userName, password, "Farmer");
		
		this.farmName = farmName;
		this.commissionRate = commissionRate;
	}

	/**
	 * Method placeholder to represent the farmer changing an order status.
	 * 
	 * @param order The order to be processed.
	 */
	public void processOrder(Order order)
	{
		//TODO: Implement rest of logic when OrderManager is created
		System.out.println("Farmer " + getUserName() + " processing order: "
				+ order.getOrderId());
	}

	/**
	 * Purpose: Getter - Returns farmName
	 * 
	 * @return farmName String object
	 */
	public String getFarmName()
	{
		return farmName;
	}

	/**
	 * Purpose: Getter - Returns commissionRate
	 * 
	 * @return commissionRate double variable
	 */
	public double getCommissionRate()
	{
		return commissionRate;
	}
}
