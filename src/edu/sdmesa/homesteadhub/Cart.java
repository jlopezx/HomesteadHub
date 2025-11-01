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
 * Purpose: The reponsibility of Cart is to represent the shopping cart for a
 * Customer.
 */
public class Cart
{

	// Cart holds a reference to its owner.
	private Customer owner;
	// Cart owns its LineItems. Key is the Product SKU for quick lookup.
	private Map<String, LineItem> itemMap = new HashMap<>();

	/**
	 * Constructor for Cart.
	 * 
	 * @param owner The Customer object that owns this cart.
	 */
	public Cart(Customer owner)
	{
		this.owner = owner;
	}

	/**
	 * Adds or updates a product in the cart, converting it into a LineItem.
	 * 
	 * @param product  The product being added.
	 * @param quantity The quantity to add.
	 */
	public void addItemProduct(Product product, int quantity)
	{
		// TODO: For now, we're using a temp placeholder for holding the price
		// but we eventually want to involve PriceCalculator here.
		double tempPrice = product.getUnitPrice();

		// Pass the product, quantity, and price to a new LineItem object
		LineItem item = new LineItem(product, quantity, tempPrice);
		
		// Add LineItem objec to the itemMap to hold it
		itemMap.put(product.getSku(), item);
	}

	/**
	 * Removes an item from the cart.
	 * 
	 * @param sku The SKU of the product to remove.
	 */
	public void removeItem(String sku)
	{
		itemMap.remove(sku);
	}

	/**
	 * Calculates the total price of all LineItems in the cart.
	 * 
	 * @return The cart subtotal.
	 */
	public double calculateSubtotal()
	{
		double subtotal = 0.0;
		// Enhanced for loop to iterate through the values of LineItem objects
		// inside itemMap and adding them all together in subtotal
		for (LineItem item : itemMap.values())
		{
			subtotal += item.calculateLineTotal();
		}
		return subtotal;
	}

	/**
	 * Purpose: Getter - Returns itemMap
	 * 
	 * @return itemMap Map object
	 */
	public Map<String, LineItem> getItemMap()
	{
		return itemMap;
	}

}
