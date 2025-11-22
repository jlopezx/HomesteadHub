package edu.sdmesa.homesteadhub;

/**
 * /**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         All detailed citations are located in the central REFERENCES.md
 *         file at the project root.
 * 
 * @version 2025-11-11
 * 
 * @Purpose A concrete implementation of the PaymentProcessor interface that
 *          handles transactions for "Cash on Pickup" orders.
 */
public class CashPickupProcessor implements PaymentProcessor
{

	private String message;

	/**
	 * Processes the cash pickup transaction.
	 * This method is used to confirm a successful transaction.
	 * 
	 * @param totalAmount    The total cost of the order
	 * 
	 * @param paymentDetails The input data confirming the cash payment method.
	 * @return A PaymentResult indicating success and an internal reference ID.
	 */
	@Override
	public PaymentResult processTransaction(double totalAmount,
			PaymentDetail paymentDetails)
	{
		message = "PAYMENT EXECUTION: CONFIRMED Cash Pickup order for $%.2f. Customer: %s%n"
				+ totalAmount + paymentDetails.getCustomerName();

		System.out.printf(message);

		// Simulate transaction success
		String internalReferenceId = "TS-" + System.currentTimeMillis();

		// Return the successful result
		return new PaymentResult("SUCCESS", internalReferenceId, message);
	}
}
