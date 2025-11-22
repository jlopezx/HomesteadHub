
package edu.sdmesa.homesteadhub;

import java.util.List;
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
 * @Purpose Manages the business logic for placing an order,
 *          coordinating inventory management and payment processing.
 */
public class OrderManager
{
	private final InventoryManager inventoryManager;
	private final PaymentProcessor paymentProcessor;

	/**
	 * Constructor that accepts dependencies via Dependency Injection.
	 * 
	 * @param inventoryManager The manager responsible for product stock.
	 * @param paymentProcessor The handler for transaction processing.
	 */
	public OrderManager(InventoryManager inventoryManager,
			PaymentProcessor paymentProcessor)
	{
		this.inventoryManager = inventoryManager;
		this.paymentProcessor = paymentProcessor;
		System.out.println(
				"OrderManager initialized with InventoryManager and PaymentProcessor.");
	}

	/**
	 * Manages the order by validating and attempting to update inventory.
	 * 
	 * @param customer      The customer placing the order.
	 * @param cart          The Cart containing the items and quantities.
	 * @param paymentDetail The payment method details.
	 * @return The finalized Order object.
	 * @throws InsufficientStockException If any item in the cart is out of
	 *                                    stock.
	 */
	public Order placeOrder(Customer customer, Cart cart,
			PaymentDetail paymentDetail) throws InsufficientStockException
	{
		// Calculate totals and prepare data
		double subtotal = cart.calculateSubtotal();
		Map<String, LineItem> items = cart.getItemMap();

		System.out.printf(
				"\nORDER MANAGER: Starting order process for %s (Total: $%.2f).%n",
				customer.getUsername(), subtotal);

		// Validate and reduce inventory for all items

		System.out.println("ORDER MANAGER: Checking and updating inventory...");

		for (LineItem item : items.values())
		{
			// Check stock before attempting to adjust
			Product product = inventoryManager.getProduct(item.getSku());

			if (product == null
					|| product.getStockQuantity() < item.getQuantity())
			{
				throw new InsufficientStockException(
						"Insufficient stock for SKU: " + item.getSku()
								+ ". Requested: " + item.getQuantity()
								+ ", Available: "
								+ (product != null ? product.getStockQuantity()
										: 0));
			}

			// If successful, reduce the stock
			inventoryManager.adjustStock(item.getSku(), item.getQuantity());
		}

		System.out.println("ORDER MANAGER: Inventory update successful.");

		// Process Payment
		System.out.println("ORDER MANAGER: Processing payment via "
				+ paymentDetail.getPaymentMethod() + "...");
		PaymentResult result = paymentProcessor.processTransaction(subtotal,
				paymentDetail);

		System.out.println(
				"ORDER MANAGER: Payment result: " + result.getStatus());

		// Create and return the final Order
		// Convert map values to list
		Order newOrder = new Order(customer, List.copyOf(items.values()),
				subtotal, customer.getShippingAddress(), result);

		System.out.printf(
				"ORDER MANAGER: Order %s placed successfully. Status: %s.%n",
				newOrder.getOrderId(), newOrder.getStatus());

		// Clear the customer's cart after successful order placement
		customer.getCart().clearCart();

		return newOrder;
	}
}
