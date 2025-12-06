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
 * @version 2025-12-5
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
	 * @return A PaymentResult indicating success and an transaction ID.
	 */
	@Override
	public PaymentResult processTransaction(double totalAmount,
			PaymentDetail paymentDetails)
	{
		message = String.format(
				"CASH PAYMENT EXECUTION: CONFIRMED %s order for $%.2f. Customer: %s",
				paymentDetails.getPaymentMethod().getPaymentType(), totalAmount,
				paymentDetails.getCustomer().getName() + "\n");

		System.out.printf(message);

		// Simulate transaction success
		String transactionId = "TS-" + System.currentTimeMillis();

		// Return the successful result
		return new PaymentResult("SUCCESS", transactionId, message);
	}

	/**
	 * Purpose: Returns the payment type of the processor.
	 * 
	 * @return "CashOnPickup" String object
	 */
	@Override
	public String getPaymentType()
	{
		return "CashOnPickUp";
	}
}
