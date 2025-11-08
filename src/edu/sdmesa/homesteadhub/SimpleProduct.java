package edu.sdmesa.homesteadhub;
/**
 * Lead Author(s):
 * 
 * @author Josh; student ID
 * @author Full name; student ID
 *         <<Add additional lead authors here>>
 *
 *         Other Contributors:
 *         Full name; student ID or contact information if not in class
 *         <<Add additional contributors (mentors, tutors, friends) here, with
 *         contact information>>
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         <<Add more references here>>
 *
 *         Version: 2025-11-06
 */

/**
 * Purpose: The reponsibility of SimpleProduct is ...
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
