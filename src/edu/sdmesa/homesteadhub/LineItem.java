package edu.sdmesa.homesteadhub;
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
 * Purpose: The reponsibility of LineItem is to represent a single line entry in
 * a Cart or an Order.
 * 
 * It tracks the specific Product and the quantity ordered.
 */
public class LineItem
{
	
	private Product product;	// LineItem has-a Product 
	private int quantity; 		// The amount of product ordered
	private double finalPrice; 	// Price captured at the time of creation/checkout

	/**
	 * Constructor for LineItem.
	 * 
	 * @param product    The product object referenced by this line item.
	 * @param quantity   The amount of the product being ordered.
	 * @param finalPrice The price calculated at the moment this LineItem is finalized.
	 */
	public LineItem(Product product, int quantity, double finalPrice)
	{
		this.product = product;
		this.quantity = quantity;
		this.finalPrice = finalPrice;
	}

	/**
	 * Calculates the total price for this specific line item.
	 * 
	 * @return The quantity multiplied by the fixed price.
	 */
	public double calculateLineTotal()
	{
		return this.quantity * this.finalPrice;
	}

	/**
	 * Purpose: Getter - Returns product
	 * 
	 * @return product Product object
	 */
	public Product getProduct()
	{
		return product;
	}
	
	/**
	 * Purpose: Getter - Returns quantity
	 * 
	 * @return quantity int variable
	 */
	public int getQuantity()
	{
		return quantity;
	}
	
	/**
	 * Purpose: Getter - Returns finalPrice
	 * 
	 * @return finalPrice double variable
	 */
	public double getFinalPrice()
	{
		return finalPrice;
	}
}
