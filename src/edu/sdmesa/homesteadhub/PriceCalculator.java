package edu.sdmesa.homesteadhub;

import java.util.List;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-11-21
 * 
 * @Purpose Calculates the total cost of an order, including item prices,
 *          tax, and any associated fees, ensuring correct price computations
 *          for the checkout process.
 */
public class PriceCalculator
{
	// Define constants for calculation
	private static final double SALES_TAX_RATE = 0.0825;
	private static final double SERVICE_FEE_RATE = 0.05;

	/**
	 * Calculates the subtotal price of all products in the order.
	 * 
	 * @param items A list of items in the order, where each item is a
	 *              Product.
	 * 
	 * @return The subtotal price before tax and fees.
	 */
	public double calculateSubtotal(List<Product> items)
	{
		if (items == null || items.isEmpty())
		{
			return 0.0;
		}

		double subtotal = items.stream().mapToDouble(
				product -> product.getUnitPrice() * product.getStockQuantity())
				.sum();

		return roundToTwoDecimals(subtotal);
	}

	/**
	 * Calculates the total sales tax for the given subtotal.
	 * 
	 * @param subtotal The price of the items before tax.
	 * 
	 * @return The calculated tax amount.
	 */
	public double calculateTax(double subtotal)
	{
		double tax = subtotal * SALES_TAX_RATE;
		return roundToTwoDecimals(tax);
	}

	/**
	 * Calculates the service fee for the order.
	 * 
	 * @param subtotal The price of the items before fees.
	 * 
	 * @return The calculated service fee amount.
	 */
	public double calculateServiceFee(double subtotal)
	{
		double fee = subtotal * SERVICE_FEE_RATE;
		return roundToTwoDecimals(fee);
	}

	/**
	 * Calculates the final total price of the order.
	 * 
	 * @param items The list of products in the order.
	 * 
	 * @return The final total price including tax and fees.
	 */
	public double calculateTotal(List<Product> items)
	{
		double subtotal = calculateSubtotal(items);
		double tax = calculateTax(subtotal);
		double fee = calculateServiceFee(subtotal);

		return roundToTwoDecimals(subtotal + tax + fee);
	}

	/**
	 * Helper method to round a double to two decimal places.
	 * 
	 * @param value The double value to round.
	 * 
	 * @return The rounded double value.
	 */
	private double roundToTwoDecimals(double value)
	{
		return Math.round(value * 100.0) / 100.0;
	}

}
