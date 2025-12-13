package edu.sdmesa.homesteadhub;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-12-12
 * 
 * @Purpose The reponsibility of SimpleProduct is deine a single product.
 */
public class SimpleProduct extends Product
{
	private String farmerUsername;

	/**
	 * Constructor for SimpleProduct.
	 * 
	 * @param sku          The unique product identifier.
	 * @param title        The display name of the product.
	 * @param initialStock The starting inventory count.
	 * @param supplier     The farmer supplying the product.
	 * @param price        The price per unit/item.
	 */
	public SimpleProduct(String sku, String title, String description,
			int initialStock, Farmer supplier, double price)
	{

		super(sku, title, description, initialStock, supplier, price);
	}

	/**
	 * Purpose: Constructor for Product used when a SKU is provided. Used for
	 * finding ALL products.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param sku            The unique product identifier.
	 * @param title          The display name of the product.
	 * @param description    The product's details.
	 * @param stockQuantity  The starting inventory count.
	 * @param farmerUsername The farmer supplying the product.
	 * @param unitPrice      The price per unit/item.
	 */
	public SimpleProduct(String sku, String title, String description,
			int stockQuantity, String farmerUsername, double unitPrice)
	{

		super(sku, title, description, stockQuantity, farmerUsername,
				unitPrice);
	}

	/**
	 * Simplified constructor for SimpleProduct
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param title        The name of the product.
	 * @param initialStock The starting inventory count.
	 * @param farmer       The farmer who owns the product.
	 * @param price        The price per unit/item.
	 */
	public SimpleProduct(String title, int initialStock, Farmer supplier,
			double price)
	{
		super(title, initialStock, supplier, price);
	}

	/**
	 * Calculates the price for a SimpleProduct, which is just its unit price.
	 * 
	 * @return The unit price.
	 */
	@Override
	public double calculatePrice()
	{
		return getUnitPrice();
	}

}