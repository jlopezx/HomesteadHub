package edu.sdmesa.homesteadhub;

import java.util.ArrayList;
import java.util.List;

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
 * /**
 * Represents a complex product bundle such as a "Weekly Veggie Box" which will
 * be composed of other Products.
 * 
 * Demonstrates composition and the application of recursion.
 */
public class BundleProduct extends Product
{
	// A BundleProduct contains a list of SimpleProducts
	private List<SimpleProduct> components;
	private double bundleDiscountPercentage;

	/**
	 * Constructor for BundleProduct.
	 * 
	 * @param sku          The unique product identifier.
	 * @param title        The display name of the product.
	 * @param initialStock The starting inventory count.
	 * @param supplier     The farmer supplying the product.
	 * @param unitPrice    The base price of the bundle before discount.
	 * @param discount     The discount percentage.
	 */
	public BundleProduct(String sku, String title, int initialStock,
			Farmer supplier, double unitPrice, double discount)
	{

		super(sku, title, initialStock, supplier, unitPrice);
		this.bundleDiscountPercentage = discount;
		this.components = new ArrayList<>();
	}

	/**
	 * Calculates the price of the bundle, summing the component prices and
	 * applying the discount.
	 * 
	 * TODO: Calculate the sum of components' prices here.
	 * 
	 * @return The discounted unit price.
	 */
	@Override
	public double calculatePrice()
	{
		return getUnitPrice() * (1 - bundleDiscountPercentage);
	}

	/**
	 * Adds a SimpleProduct component to the bundle.
	 * 
	 * @param component The SimpleProduct to add.
	 */
	public void addComponent(SimpleProduct component)
	{
		this.components.add(component);
	}

	public List<SimpleProduct> getComponents()
	{
		return components;
	}

	public double getBundleDiscountPercentage()
	{
		return bundleDiscountPercentage;
	}

	public void setBundleDiscountPercentage(double bundleDiscountPercentage)
	{
		this.bundleDiscountPercentage = bundleDiscountPercentage;
	}

	@Override
	public String getDetails()
	{
		// TODO Implement this later
		return null;
	}
}
