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
 * @version 2025-12-5
 * 
 * @Purpose The reponsibility of PaymentDetail is to transfer data for payment
 *          information provided by the customer to initiate a transaction. This
 *          is the INPUT data.
 */
public class PaymentDetail
{
	// Payment's amount
	private double amount;
	// Processor method customer selected
	private final PaymentProcessor paymentMethod;
	// The customer who is making the purchase
	private Customer customer;

	/**
	 * Purpose: Constructor to initialize payment details
	 * 
	 * @param amount Payment amount
	 * @param paymentMethod Chosen payment method
	 * @param customer User who is making the purchase
	 */
	public PaymentDetail(double amount, PaymentProcessor paymentMethod,
			Customer customer)
	{
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.customer = customer;
	}

	/**
	 * Purpose: Getter - Returns the payment method inputed by customer
	 * 
	 * @return paymentMethod PaymentProcessor object of payment method chosen
	 */
	public PaymentProcessor getPaymentMethod()
	{
		return paymentMethod;
	}

	/**
	 * Purpose: Getter - Returns amount of Payment
	 * 
	 * @return amount Total amount of purhcase
	 */
	public double getAmount()
	{
		return amount;
	}

	/**
	 * Purpose: Getter - Returns the customer who is making the payment
	 * 
	 * @return customer User who is making the purchase
	 */
	public Customer getCustomer()
	{
		return customer;
	}
}
