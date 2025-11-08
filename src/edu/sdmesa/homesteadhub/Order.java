package edu.sdmesa.homesteadhub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * Purpose: The reponsibility of Order is to represent a placed Order.
 */
public class Order
{
	private String status;
	private String shippingAddress;
	
	private final String orderId;
	private final Date orderDate;
	private final double totalAmount;
	
	// Order references the Customer who placed it.
	private final Customer customer; 
	// Order owns its LineItems.
	private final List<LineItem> lineItems = new ArrayList<>(); 


	/**
	 * Constructor for Order.
	 * 
	 * @param orderId  The unique ID for the order.
	 * @param customer The Customer object who placed the order.
	 * @param payment  The Payment object associated with the order.
	 * @param items    The list of LineItems from the Cart.
	 */
	public Order(String orderId, Customer customer, List<LineItem> items,
			double totalAmount, String shippingAddress)
	{
		this.orderId = orderId;
		this.customer = customer;
		this.lineItems.addAll(items); // Copying LineItems from the Cart
		this.orderDate = new Date(); // Keep a time record
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.status = "Placed"; // Initial status

	}

	/**
	 * Calculates the subtotal of all LineItems.
	 * 
	 * @return The subtotal.
	 */
	public double calculateSubtotal()
	{

		double subtotal = 0.0;
		// Enhanced for loop used to go through all the lineItems, call their
		// calculateLineTotal method, and store their returned value to
		// subtotal.
		for (LineItem item : lineItems)
		{
			subtotal += item.calculateLineTotal();
		}
		return subtotal;
	}

	/**
	 * Purpose: Getter - Returns orderId
	 * 
	 * @return orderId String object
	 */
	public String getOrderId()
	{
		return orderId;
	}
	
	/**
	 * Purpose: Getter - Returns customer
	 * @return customer Customer object
	 */
    public Customer getCustomer() 
    {
        return customer;
    }

    /**
     * Purpose: Getter - Returns lineItems
     * 
     * @return lineItems List<lineItems>
     */
    public List<LineItem> getLineItems() 
    {
        return lineItems;
    }

    /**
     * Purpose: Getter - Returns orderDate
     * 
     * @return orderDate Date object
     */
    public Date getOrderDate() 
    {
        return orderDate;
    }

	/**
	 * Purpose: Getter - Returns totalAmount
	 * 
	 * @return totalAmount double variable
	 */
	public double getTotalAmount()
	{
		return totalAmount;
	}
	
	/**
	 * Purpose: Getter - Returns shippingAddress
	 * 
	 * @return shippingAddress String object
	 */
    public String getShippingAddress() 
    {
        return shippingAddress;
    }

	/**
	 * Purpose: Getter - Returns orderStatus
	 * 
	 * @return orderStatus String object
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Purpose: Setter - Modifies status private field
	 * @param orderStatus
	 */
	public void setStatus(String orderStatus)
	{
		this.status = orderStatus;
	}

}
