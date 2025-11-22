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
 * @Purpose: Custom exception thrown when a customer attempts to purchase more
 *           of a product than is currently available in inventory.
 */
@SuppressWarnings("serial")
public class InsufficientStockException extends Exception
{
	/**
	 * Constructs a new InsufficientStockException with a detailed message.
	 * 
	 * @param message The detail message.
	 */
	public InsufficientStockException(String message)
	{
		super(message);
	}
}
