package edu.sdmesa.homesteadhub;

import java.util.UUID;

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
 * Purpose: The reponsibility of Product is to represent an item for sale in the
 * catalog.
 */
public abstract class Product
{
	private final String sku; 	// Stock Keeping Unit - unique ID
	private String title;		// Name of the product
	private int stockQuantity;	// Amoung of product available for sale
	private Farmer farmer; 		// Product HAS-A Farmer
	private double unitPrice;	// Base price of the product

	/**
	 * Constructor for Product used when a SKU is provided.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param sku             The unique product identifier.
	 * @param title           The display name of the product.
	 * @param quantityInStock The current inventory count.
	 * @param supplier        The farmer supplying the product.
	 * @param unitPrice       The price per unit/item.
	 */
	public Product(String sku, String title, int stockQuantity, Farmer farmer,
			double unitPrice)
	{
		this.sku = sku;
		this.title = title;
		this.stockQuantity = stockQuantity;
		this.farmer = farmer;
		this.unitPrice = unitPrice;
	}
	
	/**
     * Protected constructor for Product used when a SKU is NOT provided (auto-generates a UUID).
     * 
     * @param title The display name of the product.
     * @param quantityInStock The current inventory count.
     * @param supplier The farmer supplying the product.
     * @param unitPrice The price per unit/item.
     */
    protected Product(String title, int stockQuantity, Farmer farmer, double unitPrice) {
        this.sku = UUID.randomUUID().toString();
        this.title = title;
        this.stockQuantity = stockQuantity;
        this.farmer = farmer;
        this.unitPrice = unitPrice;
    }

	/**
	 * Abstract method to calculate the price. Pricing differs for Simple vs
	 * Bundle products.
	 * 
	 * @return The final unit price of the product.
	 */
	public abstract double calculatePrice();

	/**
	 * Abstract method to get a display description, which differs for bundles.
	 * 
	 * @return A string representing the product details.
	 */
	public abstract String getDetails();

	/**
	 * Updates the stock quantity, used during sales or restocking.
	 * 
	 * @param quantityChange The amount to add or subtract.
	 * @return True if the update was successful.
	 */
	public boolean updateStock(int quantityChange)
	{
		int newQuantity = this.stockQuantity + quantityChange;
		if (newQuantity < 0)
		{
			// Cannot fulfill request because stock would be negative
			return false;
		}
		this.stockQuantity = newQuantity;
		return true;
	}

	/**
	 * Purpose: Getter - Returns sku
	 * 
	 * @return sku String object
	 */
	public String getSku()
	{
		return sku;
	}

	/**
	 * Purpose: Getter - Returns title
	 * 
	 * @return title - String object
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Purpose: Setter - Modifies title of class's title field
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Purpose: Getter - Returns stockQuantity
	 * 
	 * @return stockQuantity int variable
	 */
	public int getStockQuantity()
	{
		return stockQuantity;
	}

	/**
	 * Purpose: Setter - Modifys stockQuantity
	 * 
	 * @param stockQuantity
	 */
	public void setStockQuantity(int stockQuantity)
	{
		this.stockQuantity = stockQuantity;
	}

	/**
	 * Purpose: Getter - Returns farmer
	 * 
	 * @return farmer Farmer object
	 */
	public Farmer getFarmer()
	{
		return farmer;
	}

	/**
	 * Purpose: Getter - Returns unitPrice
	 * 
	 * @return unitPrice double variable
	 */
	public double getUnitPrice()
	{
		return unitPrice;
	}
	
	/**
	 * Purpose: Setter - Modifies unitPrice field
	 * 
	 * @param unitPrice
	 */
	public void setUnitPrice(double unitPrice)
	{
		this.unitPrice = unitPrice;
	}
}
