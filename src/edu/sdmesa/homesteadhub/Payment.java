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
 * @version 2025-11-11
 * 
 * @Purpose The reponsibility of Payment is to represent the payment record for
 *          an Order.
 */
public class Payment
{
	private String transactionId;
	private double amount;
	private String paymentMethod;

	/**
	 * Constructor for Payment.
	 * 
	 * @param transactionId The ID of the transaction.
	 * @param amount        The total amount paid.
	 * @param paymentMethod The method of payment.
	 */
	public Payment(String transactionId, double amount, String paymentMethod)
	{
		this.transactionId = transactionId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Placeholder to verify payment data.
	 * 
	 * @return true if payment is valid, false otherwise.
	 */
	public boolean verifyPayment()
	{
		// TODO: Placeholder for now, need PaymentProcessor interface to fully
		// implement this.
		return true;
	}

	/**
	 * Purpose: Getter - Returns transactionId
	 * 
	 * @return transactionId
	 */
	public String getTransactionId()
	{
		return transactionId;
	}
}
