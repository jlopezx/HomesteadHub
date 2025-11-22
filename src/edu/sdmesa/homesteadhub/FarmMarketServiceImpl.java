package edu.sdmesa.homesteadhub;

import java.util.Map;

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
 * @Purpose The reponsibility of FarmMarketServiceImpl is to implement the
 *          FarmMarketService interface.
 * 
 *          This class acts as the system's service layer, managing calls
 *          to the managers.
 */
public class FarmMarketServiceImpl implements FarmMarketService
{
	// Private instance variables for the managers
	private PortalManager portalManager;
	private InventoryManager inventoryManager;
	private PaymentProcessor paymentProcessor;

	/**
	 * Constructor to initialize field objects with required data
	 * 
	 * @param portalManager    The configured PortalManager
	 * @param inventoryManager The configured InventoryManager
	 * @param paymentProcessor The configured PaymentProcessor
	 */
	public FarmMarketServiceImpl(PortalManager portalManager,
			InventoryManager inventoryManager,
			PaymentProcessor paymentProcessor)
	{
		this.portalManager = portalManager;
		this.inventoryManager = inventoryManager;
		this.paymentProcessor = paymentProcessor;
		System.out.println(
				"FarmMarketServiceImpl initialized with all required data.");
	}

	// ------- Portal methods used for authentication -------

	/**
	 * Purpose: Manages
	 * 
	 * @throws UserNotFoundException       if the username is not found
	 * @throws InvalidCredentialsException if the password does not match
	 * 
	 * @param username The username for authentication
	 * @param password The password for authentication
	 * 
	 * @return
	 */
	@Override
	public User login(String username, String password)
			throws UserNotFoundException, InvalidCredentialsException
	{
		// Try-Catch block to find our user is valid
		try
		{
			// Returns user if valid
			return portalManager.login(username, password);
		}
		catch (UserNotFoundException | InvalidCredentialsException e)
		{
			// Reports invalid username or password
			System.err.println(e);

			// Return null if exception is thrown
			return null;
		}
	}

	// ------- Inventory methods dealing with products -------

	/**
	 * Purpose: Retrieves the entire product catalog
	 * 
	 * @return The entire product catalog
	 */
	@Override
	public Map<String, Product> getProducts()
	{
		// Returns entire product catalog
		return inventoryManager.getProductCatalog();
	}

	// ------- Payment methods dealing with payments/transactions -------

	/**
	 * Purpose: Manages the payment/pickup confirmation to the PaymentProcessor
	 * instance
	 * 
	 * @param totalAmount    The total amount due upon pickup
	 * @param paymentDetails Placeholder details
	 * 
	 * @returns The result of the pickup confirmation transaction
	 */
	@Override
	public PaymentResult processPayment(double totalAmount,
			PaymentDetail paymentDetails)
	{
		// Returns the status of the transaction after its processed
		return paymentProcessor.processTransaction(totalAmount, paymentDetails);
	}
}
