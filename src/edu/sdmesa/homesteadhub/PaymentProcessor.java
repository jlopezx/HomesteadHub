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
 * @Purpose PaymentProcessor serves as the interface for all payment methods.
 * 
 *          This allows the system to be easily expanded in the future without
 *          changing the FarmMarketService logic.
 */
public interface PaymentProcessor
{
	/**
	 * Purpose: Processes a transaction based on the specific payment method
	 *
	 * @param totalAmount    The total cost of the order
	 * @param paymentDetails Details about the transaction
	 * 
	 * @return A transaction result object (PaymentResult)
	 */
	PaymentResult processTransaction(double totalAmount,
			PaymentDetail paymentDetails);
}