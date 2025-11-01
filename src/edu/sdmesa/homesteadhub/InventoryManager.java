package edu.sdmesa.homesteadhub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Purpose: The reponsibility of InventoryManager is to manage the entire
 * Product catalog and inventory levels.
 */
public class InventoryManager
{
	// Map is instantiated and ready to hold Product data. Key is SKU for quick lookup.
	private Map<String, Product> productCatalog = new HashMap<>();

	/**
	 * Purpose: no-arg constructor
	 */
	public InventoryManager()
	{
		// TODO: Wait to initialize this once DataRepository is created.
	}

	/**
	 * Adds a product to the catalog.
	 * 
	 * @param product The product to add.
	 */
	public void addProduct(Product product)
	{
		productCatalog.put(product.getSku(), product);
	}

	/**
	 * Gets a list of products that are low in stock.
	 * 
	 * @return A list of low-stock products.
	 */
	public List<Product> getLowStockItems()
	{
		// Create new ArrayList
		List<Product> lowStock = new ArrayList<>();
		// Use enhanced for loop to iterate through catalog
		for (Product product : productCatalog.values())
		{
			// Checks if the current product is less than 5
			if (product.getStockQuantity() < 5)
			{
				// Adds product to lowStock inventory
				lowStock.add(product);
			}
		}
		return lowStock;
	}

	/**
	 * Purpose: Getter - Returns productCatalog
	 * 
	 * @return productCatalog Map object
	 */
	public Map<String, Product> getProductCatalog()
	{
		return productCatalog;
	}
}
