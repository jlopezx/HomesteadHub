package edu.sdmesa.homesteadhub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
 * @Purpose The reponsibility of Order is to represent a placed Order.
 */
public class Order
{
	private PaymentResult result;
	private String shippingAddress;

	private String orderId;
	private Date orderDate;
	private final double totalAmount;

	// Order references the Customer who placed it.
	private final Customer customer;
	// Order owns its LineItems.
	private final List<LineItem> items;
	private String status; // Set with "PENDING_PICKUP", "SHIPPED", "CANCELLED"
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
		this.items = new ArrayList<>();
		this.orderId = orderId;
		this.customer = customer;
		this.items.addAll(items); // Copying LineItems from the Cart
		this.orderDate = new Date(); // Keep a time record
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.status = "Placed"; // Initial status

	}
	/**
	 * Constructor for Order.
	 * 
	 * @param customer The Customer object who placed the order.
	 * @param payment  The Payment object associated with the order.
	 * @param items    The list of LineItems from the Cart.
	 */
	public Order(Customer customer, List<LineItem> items, double totalAmount,
			String shippingAddress, PaymentResult result)
	{
		this.orderId = "ORD#-" + UUID.randomUUID().toString().substring(0, 8);
		this.customer = customer;
		this.items = items;
		this.orderDate = new Date(); // Keep a time record
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.result = result; // Initial status
		this.status = initializeStatus(result.getStatus());

	}

	/**
	 * Helper method to set initial status based on payment outcome.
	 * 
	 * @param paymentStatus The status returned from the PaymentProcessor.
	 * @return The appropriate initial Order status string.
	 */
	private String initializeStatus(String paymentStatus)
	{
		if ("SUCCESS_PENDING_PICKUP".equals(paymentStatus))
		{
			return "READY_FOR_PICKUP";
		}
		// Default status for other successful payments
		return "PROCESSING";
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
		for (LineItem item : items)
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
	 * 
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
	public List<LineItem> getItems()
	{
		return items;
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
	 * 
	 * @param orderStatus
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	public PaymentResult getPaymentResult()
	{
		return result;
	}

	public void setPaymentResult(PaymentResult result)
	{
		this.result = result;
	}

	/**
	 * Sets the unique ID for the order. Used exclusively for persistence
	 * restoration.
	 * 
	 * @param orderId The unique identifier string.
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	/**
	 * Sets the creation date of the order. Used exclusively for persistence
	 * restoration.
	 * 
	 * @param orderDate The date the order was created.
	 */
	public void setOrderDate(Date orderDate)
	{
		this.orderDate = orderDate;
	}

}
