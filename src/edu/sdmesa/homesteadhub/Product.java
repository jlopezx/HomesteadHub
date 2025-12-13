package edu.sdmesa.homesteadhub;

import java.util.UUID;

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
 * @Purpose The reponsibility of Product is to represent an item for sale in the
 *          catalog.
 */
public abstract class Product
{
	private final String sku; // Stock Keeping Unit - unique ID
	private String title; // Name of the product
	private int stockQuantity; // Amoung of product available for sale
	private Farmer farmer; // Product HAS-A Farmer
	private String farmerUsername;
	private double unitPrice; // Base price of the product
	private String description;

	/**
	 * Constructor for Product used when a SKU is provided.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param sku           The unique product identifier.
	 * @param title         The display name of the product.
	 * @param description   The product's details.
	 * @param stockQuantity The current inventory count.
	 * @param farmer        The farmer supplying the product.
	 * @param unitPrice     The price per unit/item.
	 */
	public Product(String sku, String title, String description,
			int stockQuantity, Farmer farmer, double unitPrice)
	{
		this.sku = sku;
		this.title = title;
		this.description = description;
		this.stockQuantity = stockQuantity;
		this.farmer = farmer;
		this.unitPrice = unitPrice;
	}

	/**
	 * Purpose: Constructor for Product used when a SKU is provided. Used for
	 * finding ALL products.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param sku            The unique product identifier.
	 * @param title          The display name of the product.
	 * @param description    The product's details.
	 * @param stockQuantity  The current inventory count.
	 * @param farmerUsername The farmer supplying the product.
	 * @param unitPrice      The price per unit/item.
	 */
	public Product(String sku, String title, String description,
			int stockQuantity, String farmerUsername, double unitPrice)
	{
		this.sku = sku;
		this.title = title;
		this.description = description;
		this.stockQuantity = stockQuantity;
		this.farmerUsername = farmerUsername;
		this.unitPrice = unitPrice;
	}

	/**
	 * Protected constructor for Product used when a SKU is NOT provided
	 * (auto-generates a UUID).
	 * 
	 * @param title           The display name of the product.
	 * @param quantityInStock The current inventory count.
	 * @param supplier        The farmer supplying the product.
	 * @param unitPrice       The price per unit/item.
	 */
	protected Product(String title, int stockQuantity, Farmer farmer,
			double unitPrice)
	{
		this.sku = UUID.randomUUID().toString();
		this.title = title;
		this.stockQuantity = stockQuantity;
		this.farmer = farmer;
		this.unitPrice = unitPrice;
	}

	/**
	 * Abstract method to calculate the price. Pricing differs for Simple vs
	 * Bundle products.
	 * 
	 * @return The final unit price of the product.
	 */
	public abstract double calculatePrice();

	/**
	 * Method to get a get description
	 * 
	 * @return A string representing the product description.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Updates the stock quantity, used during sales or restocking.
	 * 
	 * @param quantityChange The amount to add or subtract.
	 * @return True if the update was successful.
	 */
	public boolean updateStock(int quantityChange)
	{
		int newQuantity = this.stockQuantity + quantityChange;
		if (newQuantity < 0)
		{
			// Cannot fulfill request because stock would be negative
			return false;
		}
		this.stockQuantity = newQuantity;
		return true;
	}

	/**
	 * Purpose: Getter - Returns sku
	 * 
	 * @return sku String object
	 */
	public String getSku()
	{
		return sku;
	}

	/**
	 * Purpose: Getter - Returns title
	 * 
	 * @return title - String object
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Purpose: Setter - Modifies title of class's title field
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Purpose: Getter - Returns stockQuantity
	 * 
	 * @return stockQuantity int variable
	 */
	public int getStockQuantity()
	{
		return stockQuantity;
	}

	/**
	 * Purpose: Setter - Modifys stockQuantity
	 * 
	 * @param stockQuantity
	 */
	public void setStockQuantity(int stockQuantity)
	{
		this.stockQuantity = stockQuantity;
	}

	/**
	 * Purpose: Getter - Returns farmer
	 * 
	 * @return farmer Farmer object
	 */
	public Farmer getFarmer()
	{
		return farmer;
	}

	/**
	 * Purpose: Getter - Returns unitPrice
	 * 
	 * @return unitPrice double variable
	 */
	public double getUnitPrice()
	{
		return unitPrice;
	}

	/**
	 * Purpose: Setter - Modifies unitPrice field
	 * 
	 * @param unitPrice
	 */
	public void setUnitPrice(double unitPrice)
	{
		this.unitPrice = unitPrice;
	}
}