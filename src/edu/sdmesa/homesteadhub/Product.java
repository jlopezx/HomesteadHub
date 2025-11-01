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
 * Purpose: The reponsibility of Product is to represent an item for sale in the catalog.
 */
public class Product
{
	private String sku;
	private String title;
	private double unitPrice;
	private int stockQuantity;
	private boolean isBundle;

	/**
	 * Constructor for Product.
	 * 
	 * @param sku           Unique identifier for the product.
	 * @param title         Display name.
	 * @param unitPrice     Price per unit.
	 * @param stockQuantity Current stock level.
	 * @param isBundle      True if this product is composed of other products
	 */
	public Product(String sku, String title, double unitPrice,
			int stockQuantity, boolean isBundle)
	{
		this.sku = sku;
		this.title = title;
		this.unitPrice = unitPrice;
		this.stockQuantity = stockQuantity;
		this.isBundle = isBundle;
	}

	/**
	 * Calculates the price based on the quantity of this product.
	 * 
	 * @param quantity The quantity desired.
	 * @return The simple calculated price.
	 */
	public double calculatePriceForQuantity(int quantity)
	{
		return this.unitPrice * quantity;
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
	 * Purpose: Getter - Returns unitPrice
	 * 
	 * @return unitPrice double variable
	 */
	public double getUnitPrice()
	{
		return unitPrice;
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
	 * Purpose: Getter - Returns isBundle
	 * 
	 * @return isBundle boolean variable
	 */
	public boolean getIsBundle()
	{
		return isBundle;
	}

	/**
	 * Purpose: Setter - Modifys isBundle
	 * 
	 * @param isBundle
	 */
	public void setIsBundle(boolean isBundle)
	{
		this.isBundle = isBundle;
	}
}
