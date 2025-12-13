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
 * @Purpose The reponsibility of LineItem is to represent a single line entry in
 *          a Cart or an Order.
 * 
 *          It tracks the specific Product and the quantity ordered.
 */
public class LineItem
{
	private Product product; // LineItem has-a Product
	private int quantity; // The amount of product ordered
	private final double unitPrice; // Price captured at the time of
									// creation/checkout
	private String sku;
	private String title;
	private double total;

	/**
	 * Constructor for LineItem.
	 * 
	 * @param product  The Product being added.
	 * @param quantity The amount of the product.
	 */
	public LineItem(Product product, int quantity)
	{
		this.sku = product.getSku();
		this.product = product;
		this.title = product.getTitle();
		this.quantity = quantity;
		this.unitPrice = product.getUnitPrice();
		this.total = calculateLineTotal();
	}

	/**
	 * 
	 * Purpose: TEST CONSTRUCTOR TO BUILD MOCK DATA FOR GUI CREATION
	 * 
	 * @param sku
	 * @param title
	 * @param qty
	 * @param price
	 * @param total
	 */
	public LineItem(String sku, String title, int qty, double price,
			double total)
	{
		this.sku = sku;
		this.title = title;
		this.quantity = qty;
		this.unitPrice = price;
		this.total = total;
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
		return this.product;
	}

	/**
	 * Purpose: Getter - Returns quantity
	 * 
	 * @return quantity int variable
	 */
	public int getQuantity()
	{
		return this.quantity;
	}

	/**
	 * Purpose: Getter - Returns finalPrice
	 * 
	 * @return finalPrice double variable
	 */
	public double getUnitPrice()
	{
		return this.unitPrice;
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

	/**
	 * Purpose: Getter - Returns the sku
	 * 
	 * @return sku Stocking keeping unit of the product
	 */
	public String getSku()
	{
		return this.sku;
	}

	/**
	 * Purpose: Getter - Returns title of product
	 * 
	 * @return title Product title
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Purpose: Getter - Returns total purchase amount
	 * 
	 * @return total Purchase total
	 */
	public double getTotal()
	{
		this.total = calculateLineTotal();
		return this.total;
	}
}