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
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         Version: 2025-10-31
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
