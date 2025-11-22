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
 * @Purpose The reponsibility of Tetser is to test classes from our HomesteadHub
 *          project.
 */
public class Tester
{
	private static InventoryManager inventoryManager;
	private static OrderManager orderManager;
	private static Farmer farmer;
	private static Customer josh;
	private static PaymentDetail detail;

	private static Product productApples;
	private static Product productCarrots;

	public static void main(String[] args)
	{
		System.out.println("##### Week 5 Tests START #####");

		// Customer and PaymentDetail
		josh = new Customer("Josh", "1234", "josh@test.com",
				"123 Test St, Black Mesa");
		detail = new PaymentDetail("CashOnPickup");
		// Initialize the system components
		inventoryManager = new InventoryManager();
		setupInitialData();

		// The OrderManager only needs the InventoryManager
		PaymentProcessor paymentProcessor = new CashPickupProcessor();
		orderManager = new OrderManager(inventoryManager, paymentProcessor);

		System.out.println("\n--- Testing Business Logic ---");
		testSuccessfulOrderPlacement();
		testInsufficientStockException();

		System.out.println("\n##### Week 5 Tests COMPLETE #####");
	}

	/**
	 * Sets up initial Product data required for testing.
	 */
	private static void setupInitialData()
	{
		System.out.println("\n--- Initializing Setup Data ---");

		// Initial Stock: 100 apples, 50 carrots
		productApples = new SimpleProduct("APPLE1", "Apples",
				"Gala Apples (5lb)", 100, farmer, 9.99);
		productCarrots = new SimpleProduct("CARROT2", "Oranges",
				"Organic Carrots (1lb)", 50, farmer, 3.99);

		// Load products into InventoryManager catalog
		inventoryManager.addProduct(productApples);
		inventoryManager.addProduct(productCarrots);
		System.out.println("Products loaded into InventoryManager's catalog.");
		System.out.println("Initial Stock: Apples: 100, Carrots: 50");
	}

	/**
	 * Tests a successful order placement, verifying inventory deduction.
	 */
	private static void testSuccessfulOrderPlacement()
	{
		System.out.println("\n--- Testing Successful Order Placement ---");

		int initialStock = productApples.getStockQuantity();
		int quantityToOrder = 10;
		int expectedRemainingStock = initialStock - quantityToOrder;

		// Prepare Cart
		Cart testCart = new Cart();
		testCart.addProduct(productApples, quantityToOrder);

		// Set subtotal for the cart
		testCart.calculateSubtotal();

		// Place Order
		try
		{
			Order newOrder = orderManager.placeOrder(josh, testCart, detail);

			// Order Status
			if (newOrder != null && newOrder.getStatus().equals("PLACED"))
			{
				System.out.println(
						"PASS: Order successfully created with 'PLACED' status.");
			}
			else
			{
				System.err.println(
						"FAIL: Order creation failed or status was incorrect.");
			}

			// Inventory Deduction
			Product updatedProduct = inventoryManager
					.getProduct(productApples.getSku());

			if (updatedProduct.getStockQuantity() == expectedRemainingStock)
			{
				System.out.println(
						"PASS: Inventory correctly deducted. Remaining stock: "
								+ updatedProduct.getStockQuantity());
			}
			else
			{
				System.err
						.println("FAIL: Inventory deduction failed. Expected: "
								+ expectedRemainingStock + ", Actual: "
								+ updatedProduct.getStockQuantity());
			}

			// Cart Cleared
			if (testCart.getItemMap().isEmpty())
			{
				System.out.println(
						"PASS: Customer cart was cleared successfully.");
			}
			else
			{
				System.err.println(
						"FAIL: Customer cart was NOT cleared after successful order.");
			}
		}
		catch (InsufficientStockException e)
		{
			System.err.println(
					"FAIL: Successful order threw unexpected exception: "
							+ e.getMessage());
		}
	}

	/**
	 * Tests the OrderManager's ability to throw InsufficientStockException.
	 */
	private static void testInsufficientStockException()
	{
		System.out.println("\n--- Testing InsufficientStockException ---");

		int initialStock = productCarrots.getStockQuantity(); // Should be 50
		int quantityToOrder = 51;

		// Prepare Cart
		Cart testCart = new Cart();
		testCart.addProduct(productCarrots, quantityToOrder);
		testCart.calculateSubtotal();

		boolean exceptionCaught = false;

		try
		{
			orderManager.placeOrder(josh, testCart, detail);

			System.err.println(
					"FAIL: Order placed successfully despite insufficient stock.");
		}
		catch (InsufficientStockException e)
		{

			System.out.println(
					"PASS: Correctly caught InsufficientStockException: "
							+ e.getMessage());
			exceptionCaught = true;
		}

		if (!exceptionCaught)
		{
			System.err.println(
					"FAIL: InsufficientStockException was not caught.");
		}

		// Check if inventory was deducted
		Product productAfterFailedOrder = inventoryManager
				.getProduct(productCarrots.getSku());

		if (productAfterFailedOrder.getStockQuantity() == initialStock)
		{
			System.out.println(
					"PASS: Inventory NOT deducted after failed order. Stock remains: "
							+ initialStock);
		}
		else
		{
			System.err.println(
					"FAIL: Inventory WAS deducted after failed order. Stock: "
							+ productAfterFailedOrder.getStockQuantity());
		}
	}
}