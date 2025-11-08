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
	private Customer customer;
	// Cart owns its LineItems. Key is the Product SKU for quick lookup.
	private Map<String, LineItem> items;

	/**
	 * Constructor for Cart.
	 * 
	 * @param owner The Customer object that owns this cart.
	 */
	public Cart(Customer customer)
	{
		this.customer = customer;
		this.items = new HashMap<>();
	}

	/**
	 * Adds a product to the cart or updates the quantity if it already exists.
	 * 
	 * @param product  The product to add.
	 * @param quantity The quantity to add.
	 * @return The updated LineItem.
	 */
	public LineItem addProduct(Product product, int quantity)
	{
		String sku = product.getSku();

		if (items.containsKey(sku))
		{
			// Update existing line item
			LineItem item = items.get(sku);
			item.setQuantity(item.getQuantity() + quantity);
			return item;
		}
		else
		{
			// Create new line item
			LineItem newItem = new LineItem(product, quantity);
			items.put(sku, newItem);
			return newItem;
		}
	}

	/**
	 * Removes an item from the cart.
	 * 
	 * @param sku The SKU of the product to remove.
	 */
	public void removeItem(String sku)
	{
		items.remove(sku);
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
		for (LineItem item : items.values())
		{
			subtotal += item.calculateLineTotal();
		}
		return subtotal;
	}

	/**
	 * Purpose: Clears all items from the cart.
	 */
	public void clearCart()
	{
		items.clear();
	}

	/**
	 * Purpose: Getter - Returns customer
	 * 
	 * @return customer
	 */
	public Customer getCustomer()
	{
		return customer;
	}

	/**
	 * Purpose: Getter - Returns items
	 * 
	 * @return items Map object
	 */
	public Map<String, LineItem> getItemMap()
	{
		return items;
	}

}
