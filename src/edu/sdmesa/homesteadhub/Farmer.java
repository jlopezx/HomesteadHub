package edu.sdmesa.homesteadhub;

import java.util.List;
import java.util.ArrayList;

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
 * @Purpose The reponsibility of Farmer is to represents a Farmer in the
 *          HomesteadHub system.
 * 
 *          Inherits from User and contains farmer-specific properties like the
 *          farm
 *          name.
 */
public class Farmer extends User
{
	// A Farmer contains a List of Products they sell
	private List<Product> offeredProducts;
	private String farmName;
	private String location;

	/**
	 * Constructor for Farmer to create a new Farmer account with a unique ID
	 * 
	 * @param username The user's chosen username.
	 * @param password The passowrd in plaintext (for now).
	 * @param email    The user's contact email.
	 * @param farmName The name of the farmer's operation.
	 * @param location The geographic location of the farm.
	 */
	public Farmer(String username, String password, String email,
			String farmName, String location)
	{
		super(username, password, email);
		this.farmName = farmName;
		this.location = location;
		this.offeredProducts = new ArrayList<>();
	}

	/**
	 * Secondary constructor for Farmer.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param userId   The existing user ID.
	 * @param username The user's chosen username.
	 * @param password The passowrd in plaintext (for now).
	 * @param email    The user's contact email.
	 * @param farmName The name of the farmer's operation.
	 * @param location The geographic location of the farm.
	 */
	public Farmer(String userId, String username, String password, String email,
			String farmName, String location)
	{
		super(userId, username, password, email);
		this.farmName = farmName;
		this.location = location;
		this.offeredProducts = new ArrayList<>();
	}

	/**
	 * Method placeholder to represent the farmer changing an order status.
	 * 
	 * @param order The order to be processed.
	 */
	public void processOrder(Order order)
	{
		// TODO: Implement rest of logic when OrderManager is created
		System.out.println("Farmer " + getUsername() + " processing order: "
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
	 * Purpose: Setter - Modifies farmName
	 * 
	 * @param farmName
	 */
	public void setFarmName(String farmName)
	{
		this.farmName = farmName;
	}

	/**
	 * Implements the abstract method from User.
	 * 
	 * @return The role "Farmer".
	 */
	@Override
	public String getRole()
	{
		return "Farmer";
	}

	/**
	 * Adds a product to the farmer's list of offerings.
	 * 
	 * @param product The Product object to add.
	 */
	public void addProduct(Product product)
	{
		this.offeredProducts.add(product);
	}

	/**
	 * Removes a product from the farmer's list of offerings.
	 * 
	 * @param product The Product object to remove.
	 * @return True if the product was successfully removed.
	 */
	public boolean removeProduct(Product product)
	{
		return this.offeredProducts.remove(product);
	}

	/**
	 * Purpose: Getter - Returns location
	 * 
	 * @return location
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Purpose: Setter - Modifies the location fields
	 * 
	 * @param location
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/**
	 * Purpose: Getter - Returns offeredProducts
	 * 
	 * @return offeredProducts List<Product>
	 */
	public List<Product> getOfferedProducts()
	{
		return offeredProducts;
	}

}
