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
 * @version 2025-11-11
 * 
 * @Purpose The reponsibility of SimpleProduct is ...
 *
 * SimpleProduct is-a ...
 * SimpleProduct is ...
 */
public class SimpleProduct extends Product
{

	/**
	 * Constructor for SimpleProduct.
	 * 
	 * @param sku          The unique product identifier.
	 * @param title        The display name of the product.
	 * @param initialStock The starting inventory count.
	 * @param supplier     The farmer supplying the product.
	 * @param price        The price per unit/item.
	 */
	public SimpleProduct(String sku, String title, int initialStock,
			Farmer supplier, double price)
	{

		super(sku, title, initialStock, supplier, price);
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

	@Override
	public String getDetails()
	{
		// TODO Implement this later
		return null;
	}

}
