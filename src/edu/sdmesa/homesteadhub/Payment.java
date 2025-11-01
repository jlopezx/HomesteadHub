package edu.sdmesa.homesteadhub;
/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         GeeksforGeeks. (2025).
 *         What is Java Enterprise Edition (Java EE)?
 *         https://www.geeksforgeeks.org/java/java-enterprise-edition/
 * 
 *         GeeksforGeeks. (2023).
 *         E-commerce Architecture | System Design for E-commerce Website
 *         https://www.geeksforgeeks.org/system-design/e-commerce-architecture-system-design-for-e-commerce-website/
 * 
 *         GeeksforGeeks. (2025).
 *         Inventory Management System in Java
 *         https://www.geeksforgeeks.org/java/inventory-management-system-in-java/
 * 
 *         Java Architecture: Components with Examples. (2025).
 *         Java Architecture: Components with Examples
 *         https://vfunction.com/blog/java-architecture/
 * 
 *         Mahmoud. (2024).
 *         Building a Simple E-Commerce Ordering System in Java Using OOP
 *         https://techwithmahmoud.medium.com/building-a-simple-e-commerce-ordering-system-in-java-using-oop-00f051f4825e
 * 
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 *         Stack Overflow. (2020).
 *         How should I design an E-commerce Class Diagram?
 *         https://stackoverflow.com/questions/65023323/how-should-i-design-an-e-commerce-class-diagram
 * 
 *         Version: 2025-10-30
 */

/**
 * Purpose: The reponsibility of Payment is to represent the payment record for
 * an Order.
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
