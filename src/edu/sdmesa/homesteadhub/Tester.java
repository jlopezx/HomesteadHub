package edu.sdmesa.homesteadhub;

import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * @Purpose The reponsibility of Tetser is to test classes from our HomesteadHub
 *          project.
 */
public class Tester
{
	public static void main(String[] args)
	{

		System.out.println("##### HomesteadHub Week 2 Tests #####");
		// ----------------------- Testing User Creation -----------------------
		// Initialize the Managers
		PortalManager portalManager = new PortalManager();
		InventoryManager inventoryManager = new InventoryManager();

		// Create Users
		System.out.println("\n-----Testing User Creation-----");
		Farmer farmer = new Farmer("Luke", "jedi", "theChosenOne@jedi.com",
				"Skywalker Farm", "Tatooine");
		Customer customer = new Customer("Josh", "1234", "josh@test.com",
				"123 Test St, Black Mesa");

		// Add Users to PortalManager
		portalManager.addUser(farmer);
		portalManager.addUser(customer);
		System.out.println("\nUsers added to Roster: "
				+ portalManager.getUserRoster().keySet());

		// -----------------------Testing Product Creation----------------------
		System.out.println("\n-----Testing Product Creation-----");

		// SimpleProduct(sku, title, initialStock, supplier, price)
		Product appleProduct = new SimpleProduct("APPLE1", "Gala Apples (5lb)",
				100, farmer, 9.99);

		// Add Product to InventoryManager
		inventoryManager.addProduct(appleProduct);

		System.out.println("\nProduct added to Catalog: " + inventoryManager
				.getProductCatalog().get("APPLE1").getTitle());

		// ----------------Testing Login Functionality-------------------
		System.out.println("\n-----Testing Login Functionality-----");
		User loggedInUser = null;
		try
		{
			loggedInUser = portalManager.login("Josh", "1234");
			if (loggedInUser != null)
			{
				System.out.println("LOGIN SUCCESS: User "
						+ loggedInUser.getUsername() + " ("
						+ loggedInUser.getRole() + ") authenticated.");
			}
			else
			{
				System.out.println("LOGIN FAILED.");
				return;
			}
		}
		catch (UserNotFoundException | InvalidCredentialsException e)
		{
			// Report invalid username or password
			System.err.println("WEEK 2 login test FAILED: " + e);
		}

		// -------------Testing Customer/Cart Relationship---------------
		System.out.println("\n-----Testing Customer/Cart Relationship-----");
		if (loggedInUser instanceof Customer)
		{
			Customer loggedInCustomer = (Customer) loggedInUser;
			System.out.println("\n-----CART TEST-----");

			// Add items to the Cart (demonstrates LineItem creation)
			int qty = 2;
			loggedInCustomer.addProductToCart(appleProduct, qty);
			System.out.println("Added " + qty + " of " + appleProduct.getTitle()
					+ " to cart.");

			// Verify LineItem exists and calculate subtotal
			Map<String, LineItem> cartItems = loggedInCustomer.getCart()
					.getItemMap();

			LineItem item = cartItems.get("APPLE1");

			if (item != null)
			{
				System.out.println(
						"Line Item Total: $" + item.calculateLineTotal());
			}
			else
			{
				System.err.println("TEST FAILED: Item does not exist.");
			}

			// Test Cart subtotal
			double subtotal = loggedInCustomer.getCart().calculateSubtotal();
			System.out.printf("CART SUB-TOTAL: $%.2f%n", subtotal);

			// Expected Calculation: 2 * 9.99 = 19.98
			if (subtotal == 19.98)
			{
				System.out.println(
						"TEST PASSED: Subtotal calculation is correct.");
			}
			else
			{
				System.err.println("TEST FAILED: Subtotal is incorrect.");
			}
		}
		else
		{
			System.err.println("FAIL: loggedInUser no a Customer object.");
		}

		System.out.println("\n##### Week 2 Testing Complete #####");

		System.out.println("\n##### HomesteadHub Week 3 Tests #####");
		setup(farmer, customer);
		testUserPersistence(farmer, customer);
		testProductPersistence(farmer);
		testOrderPersistence(customer);
		System.out.println("\n##### Week 3 Testing Complete #####");

		System.out.println("\n##### Week 4 Testing Start #####");
		testLoginExceptions(portalManager);
		System.out.println("\n##### Week 4 Testing Complete #####");

		// TODO: Week 5 FarmMarketService farmService = new
		// testProcessPayment();
	}

	private final static DataRepository repository = new FileDataSource();

	private static SimpleProduct testProduct;
	private static Order testOrder;

	/**
	 * Purpose: Setup method to initialize the environment and create mock
	 * objects.
	 * 
	 * @param farmer
	 * @param customer
	 */
	public static void setup(Farmer farmer, Customer customer)
	{
		// Clean directory
		new File("users.txt").delete();
		new File("products.txt").delete();
		new File("orders.txt").delete();

		// Testing new SimpleProduct class. Creates new product based on the
		// farmer
		testProduct = new SimpleProduct("Carrots", 50, farmer, 3.99);

		// Adds a product to a LineItem list to simulate a listed item a
		// customer can buy
		List<LineItem> items = new ArrayList<>();
		items.add(new LineItem(testProduct, 5));

		// Create new test order
		testOrder = new Order("ORD-100", customer, items,
				items.get(0).calculateLineTotal(),
				customer.getShippingAddress());

		System.out.println(
				"Setup complete. Test data directory assumed to be working.");
	}

	/**
	 * Purpose: Test case for saving and finding a User.
	 * 
	 * @param farmer
	 * @param customer
	 */
	public static void testUserPersistence(Farmer farmer, Customer customer)
	{
		// Save the User
		repository.saveUser(customer);
		repository.saveUser(farmer);

		// Find by Username
		User foundUserByUsername = repository.findUserByUsername("Josh");

		// Find by User ID
		User foundUserById = repository.findUserById(customer.getUserId());

		System.out.println("\n--- Testing User Persistence ---");

		// Find by Username
		if (foundUserByUsername != null
				&& foundUserByUsername.getRole().equals("Customer"))
		{
			System.out.println("PASS: Found user by username: "
					+ foundUserByUsername.getUsername());
		}
		else
		{
			System.err.println(
					"FAIL: Could not find customer by username or role was incorrect.");
		}

		// Find by User ID
		if (foundUserById != null
				&& foundUserById.getUserId().equals(customer.getUserId()))
		{
			System.out.println(
					"PASS: Found user by ID: " + foundUserById.getUserId());
		}
		else
		{
			System.err.println(
					"FAIL: Could not find customer by ID or ID was incorrect.");
		}

		// Find All
		List<User> allUsers = repository.findAllUsers();
		if (allUsers.size() >= 2)
		{
			System.out.println("PASS: Found at least 2 users (Total: "
					+ allUsers.size() + ")");
		}
		else
		{
			System.err.println("FAIL: Expected at least 2 users, found "
					+ allUsers.size());
		}
	}

	/**
	 * Purpose:Test case for saving and finding a Product.
	 * 
	 * @param farmer
	 */
	public static void testProductPersistence(Farmer farmer)
	{
		// Save the Product
		repository.saveProduct(testProduct);

		// Find by SKU
		Product foundProduct = repository.findProductBySku(testProduct.getSku(),
				farmer);

		System.out.println("\n--- Testing Product Persistence ---");
		if (foundProduct != null && foundProduct.getTitle().equals("Carrots"))
		{
			System.out.println(
					"PASS: Found product by SKU: " + foundProduct.getTitle());
		}
		else
		{
			System.err.println(
					"FAIL: Could not find product or name was incorrect.");
		}
	}

	/**
	 * Purpose: Test case for saving and finding an Order.
	 * 
	 * @param customer
	 */
	public static void testOrderPersistence(Customer customer)
	{
		// Save the Order
		repository.saveOrder(testOrder);

		// Find by Order ID
		Order foundOrder = repository.findOrderById(testOrder.getOrderId(),
				customer);

		System.out.println("\n--- Testing Order Persistence ---");
		if (foundOrder != null
				&& foundOrder.getTotalAmount() == testOrder.getTotalAmount())
		{
			System.out.println("PASS: Found order by ID with correct total: $"
					+ foundOrder.getTotalAmount());
		}
		else
		{
			System.err.println(
					"FAIL: Could not find order or total was incorrect.");
		}

		// Find Orders by Customer
		List<Order> customerOrders = repository.findOrdersByCustomer(customer);
		if (customerOrders.size() >= 1)
		{
			System.out.println("PASS: Found " + customerOrders.size()
					+ " order(s) for the customer.");
		}
		else
		{
			System.err.println(
					"FAIL: Expected at least 1 order for the customer.");
		}

	}

	/**
	 * Purpose: Tests the PortalManager login method to check if it correctly
	 * throws UserNotFoundException and InvalidCredentialsException.
	 * 
	 * @param portalManager The initialized PortalManager instance.
	 */
	public static void testLoginExceptions(PortalManager portalManager)
	{
		System.out.println("\n--- Testing Login Exceptions ---");
		final String unknownUser = "UnknownPerson";
		final String knownUser = "Josh";
		final String wrongPass = "wrongpassword";

		// Test case: User Not Found (UserNotFoundException)
		System.out.println(
				"\nUserNotFoundException Test Case: Unknown User Login...");
		try
		{
			portalManager.login(unknownUser, "anypass");
			System.err.println(
					"FAIL: Expected UserNotFoundException but login succeeded.");
		}
		catch (UserNotFoundException e)
		{
			System.out.println(
					"PASS: Caught expected exception: " + e.getMessage());
		}
		catch (InvalidCredentialsException e)
		{
			System.err.println(
					"FAIL: Expected UserNotFoundException but caught InvalidCredentialsException.");
		}

		// Test case: Invalid Password (InvalidCredentialsException)
		System.out.println(
				"\nInvalidCredentialsException Test Case: Known User, Wrong Password...");
		try
		{
			portalManager.login(knownUser, wrongPass);
			System.err.println(
					"FAIL: Expected InvalidCredentialsException but login succeeded.");
		}
		catch (InvalidCredentialsException e)
		{
			System.out.println(
					"PASS: Caught expected exception: " + e.getMessage());
		}
		catch (UserNotFoundException e)
		{
			System.err.println(
					"FAIL: Expected InvalidCredentialsException but caught UserNotFoundException.");
		}
	}

	/**
	 * Purpose: Tests the FarmMarketService's payment processing method.
	 * TODO: Week 5 testing method. Need PendingPickupCheckout
	 * 
	 * @param marketService The service instance to test.
	 */
	public static void testProcessPayment(FarmMarketService marketService)
	{
		System.out.println(
				"\n--- Testing Payment Processing with FarmMarketService ---");

		double testAmount = 31.40;
		PaymentDetail paymentDetails = new PickupDetail();

		PaymentResult result = marketService.processPayment(testAmount,
				paymentDetails);

		if (result != null && result.isSuccess())
		{
			System.out.println("PASS: Payment processing successful. Status: "
					+ result.isSuccess() + ", ID: "
					+ result.getConfirmationId());
		}
		else
		{
			System.err.println("FAIL: Payment processing failed.");
		}
	}
}