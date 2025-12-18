package edu.sdmesa.homesteadhub;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-12-18
 * 
 * @Purpose The reponsibility of InventoryManager is to manage the entire
 *          Product catalog and inventory levels.
 */
public class InventoryManager
{
	// Map is instantiated and ready to hold Product data. Key is SKU for quick lookup.
	private Map<String, Product> productCatalog;

	/**
	 * Purpose: no-arg constructor
	 */
	public InventoryManager()
	{
		this.productCatalog = new LinkedHashMap<>();
	}

	/**
	 * Adds a product to the catalog.
	 * 
	 * @param product The product to add.
	 */
	public void addProduct(Product product)
	{
		productCatalog.put(product.getSku(), product);
		System.out.println("Product added: " + product.getTitle() + " (SKU: "
				+ product.getSku() + ")");
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
	 * Retrieves a Product by its unique SKU.
	 * 
	 * @param sku The unique SKU string.
	 * @return The Product object, or null if not found.
	 */
	public Product getProduct(String sku)
	{
		// Retrieves the Product object (which could be a SimpleProduct or
		// BundleProduct)
		return this.productCatalog.get(sku);
	}

	/**
	 * Removes a Product from the catalog using its SKU.
	 * 
	 * @param sku The unique SKU string of the product to remove.
	 * @return True if the product was removed, false otherwise.
	 */
	public boolean removeProduct(String sku)
	{
		return this.productCatalog.remove(sku) != null;
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

	/**
	 * Purpose: Adjusts the product's stock as it's ordered
	 * 
	 * @param sku Stocking keeping unit of the product
	 * @param purchasedInventory Amount of inventory purchased
	 */
	public void adjustStock(String sku, int purchasedInventory)
	{
		Product tempProduct = productCatalog.get(sku);
		// TESTING PRINTS FOR TROUBLESHOOTING
		System.out.println("INVENTORYMANAGER: AdjustStock: Temp Product Stock Quantity: " + tempProduct.getStockQuantity());
		System.out.println("INVENTORYMANAGER: AdjustStock: passed in purchasedInventory: " + purchasedInventory);
		
		tempProduct.setStockQuantity(tempProduct.getStockQuantity() - purchasedInventory);
		// TESTING PRINTS FOR TROUBLESHOOTING
		System.out.println("INVENTORYMANAGER: AdjustStock: Temp Product Stock Quantity: " + tempProduct.getStockQuantity());
		
	}
}
