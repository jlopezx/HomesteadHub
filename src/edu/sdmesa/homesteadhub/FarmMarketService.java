package edu.sdmesa.homesteadhub;

import java.util.Map;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central **REFERENCES.md**
 *         file at the project root.
 * 
 * @version: 2025-11-21
 * 
 * @Purpose The FarmMarketService interface defines the front facing interface for the application.
 *          Any concrete service implementation must implement all methods
 *          defined here.
 */
public interface FarmMarketService
{
	// ------- Portal methods used for authentication -------

	User login(String username, String password)
			throws UserNotFoundException, InvalidCredentialsException;

	// ------- Inventory methods dealing with products -------

	Map<String, Product> getProducts();

	// ------- Payment methods dealing with payments/transactions -------
	
	PaymentResult processPayment(double totalAmount,
			PaymentDetail paymentDetails);
}
