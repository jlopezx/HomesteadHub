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
 * @Purpose The reponsibility of PaymentDetail is to transfer data for payment
 *          information provided
 *          by the customer to initiate a transaction. This is the INPUT data.
 */
public class PaymentDetail
{
	private final String paymentMethod;
	private final String paymentToken;
	private final String customerName;

	// Test constructor
	public PaymentDetail(String method)
	{
		this.paymentMethod = method;
		this.paymentToken = "";
		this.customerName = "";
	}

	public PaymentDetail(Payment payment, Customer customer)
	{
		this.paymentMethod = payment.getPaymentMethod();
		this.paymentToken = payment.getTransactionId();
		this.customerName = customer.getName();
	}

	public String getPaymentMethod()
	{
		return paymentMethod;
	}

	public String getPaymentToken()
	{
		return paymentToken;
	}

	public String getCustomerName()
	{
		return customerName;
	}
}
