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
 * @version 2025-11-21
 * 
 * @Purpose The reponsibility of LineItem is to represent a single line entry in
 *          a Cart or an Order.
 * 
 *          It tracks the specific Product and the quantity ordered.
 */
public class LineItem
{
	private Product product; // LineItem has-a Product
	private int quantity; // The amount of product ordered
	private final double unitPrice; // Price captured at the time of creation/checkout

	/**
	 * Constructor for LineItem.
	 * 
	 * @param product  The Product being added.
	 * @param quantity The amount of the product.
	 */
	public LineItem(Product product, int quantity)
	{
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = product.getUnitPrice();
	}

	/**
	 * Calculates the total price for this specific line item.
	 * 
	 * @return The quantity multiplied by the fixed price.
	 */
	public double calculateLineTotal()
	{
		double rawTotal = this.quantity * this.unitPrice;

		// Rounds a double to 2 decimal places.
		double roundedTotal = Math.round(rawTotal * 100.0) / 100.0;

		return roundedTotal;
	}

	/**
	 * Purpose: Getter - Returns product
	 * 
	 * @return product Product object
	 */
	public Product getProduct()
	{
		return product;
	}

	/**
	 * Purpose: Getter - Returns quantity
	 * 
	 * @return quantity int variable
	 */
	public int getQuantity()
	{
		return quantity;
	}

	/**
	 * Purpose: Getter - Returns finalPrice
	 * 
	 * @return finalPrice double variable
	 */
	public double getUnitPrice()
	{
		return unitPrice;
	}

	/**
	 * Purpose: Setter - Sets quantity class field
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public String getSku()
	{
		return this.product.getSku();
	}
}
