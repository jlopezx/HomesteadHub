package edu.sdmesa.homesteadhub;

import java.io.File;
import java.util.ArrayList;
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
 * @version 2025-12-12
 * 
 * @Purpose The reponsibility of Tetser is to test classes from our HomesteadHub
 *          project.
 */
public class Tester
{

	private final static DataRepository repository = new FileDataSource();

	// private static SimpleProduct testProduct;
	private static Order testOrder;

	private static User loggedInUser;

	private static InventoryManager inventoryManager;
	private static PortalManager portalManager;

	private static OrderManager orderManager;
	private static Farmer farmer;
	private static Customer customer;
	private static PaymentDetail detail;
	private static PaymentList payList;

	private static Product appleProduct;
	private static Product carrotProduct;

	public static void main(String[] args)
	{

		System.out.println("##### HomesteadHub Week 2 Tests #####");

		// Initialize the portalManager
		portalManager = new PortalManager(repository);
		inventoryManager = new InventoryManager();
		// ----------------------- Testing User Creation -----------------------
		userCreationTest();

		// -----------------------Testing Product Creation----------------------
		productCreationTest();

		// ----------------Testing Login Functionality-------------------

		userLoginTest();

		// -------------Testing Customer/Cart Relationship---------------
		cartCustomerTest();

		System.out.println("\n##### Week 2 Testing Complete #####");

		System.out.println("\n##### HomesteadHub Week 3 Tests #####");
		setupUsers(farmer, customer);
		testUserPersistence(farmer, customer);
		testProductPersistence(farmer);
		testOrderPersistence(customer);
		System.out.println("\n##### Week 3 Testing Complete #####");

		System.out.println("\n##### Week 4 Testing Start #####");

		testLoginExceptions(portalManager);
		System.out.println("\n##### Week 4 Testing Complete #####");

		System.out.println("##### Week 5 Tests START #####");

		// TODO: PaymentDetail needs a Payment and customer. The idea is when a
		// customer places an order,
		// Customer and PaymentDetail is already known

		// Simulate putting payment details in order
		payList = new PaymentList();

		// Loading available payment methods
		payList.registerProcessor("CashOnPickup", new CashPickupProcessor());

		// Selected processor by customer
		String selectedProcessor = "CashOnPickup";

		// Consolidating payment details placed by customer
		detail = new PaymentDetail(testOrder.getTotalAmount(),
				payList.getProcessor(selectedProcessor), customer);

		// Initialize the system components
		// inventoryManager = new InventoryManager();
		setupInitialData();

		orderManager = new OrderManager(inventoryManager,
				detail.getPaymentMethod());

		System.out.println("\n--- Testing Business Logic ---");
		testSuccessfulOrderPlacement();
		testInsufficientStockException();

		// Initailize my FarmMarketService object which will serve as my
		// front-facing interface similiar to an API
		FarmMarketService farmService = new FarmMarketServiceImpl(portalManager,
				inventoryManager, detail.getPaymentMethod());
		testProcessPayment(farmService);

		System.out.println("\n##### Week 5 Tests COMPLETE #####");

		System.out.println("\n##### LAUNCHING HOMESTEAD HUB GUI #####");
		// This will launch the JavaFX runtime and display the Login window.
		AppLauncher.launchApp(args);
	}

	// ----------------User Creation Test Function----------------
	private static void userCreationTest()
	{

		// Create Users
		System.out.println("\n-----Testing User Creation-----");
		farmer = new Farmer("Luke", "jedi", "theChosenOne@jedi.com",
				"Skywalker Farm", "Tatooine");
		customer = new Customer("Josh", "1234", "josh@test.com",
				"123 Test St, Black Mesa");

		// Setting customer's name after user creation
		customer.setName("Joshua Lopez");

		// Add Users to PortalManager
		portalManager.addUser(farmer);
		portalManager.addUser(customer);
		System.out.println("\nUsers added to Roster: "
				+ portalManager.getUserRoster().keySet());
	}

	// ----------------Product Creation Test Function----------------
	private static void productCreationTest()
	{
		System.out.println("\n-----Testing Product Creation-----");

		// SimpleProduct(sku, title, initialStock, supplier, price)
		appleProduct = new SimpleProduct("APPLE1", "Apples",
				"Gala Apples (5lb)", 100, farmer, 9.99);

		// Add Product to InventoryManager
		inventoryManager.addProduct(appleProduct);

		System.out.println("\nProduct added to Catalog: " + inventoryManager
				.getProductCatalog().get("APPLE1").getTitle());
	}

	// ----------------User Login Test Function----------------
	private static void userLoginTest()
	{
		System.out.println("\n-----Testing Login Functionality-----");
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
	}

	// -------------Customer/Cart Test Function---------------
	private static void cartCustomerTest()
	{
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
	}

	// ---------------User Creation Methods---------------
	/**
	 * Purpose: Setup method to initialize the environment and create mock
	 * objects.
	 * 
	 * @param farmer
	 * @param customer
	 */
	public static void setupUsers(Farmer farmer, Customer customer)
	{
		// Clean directory
		new File("users.txt").delete();
		new File("products.txt").delete();
		new File("orders.txt").delete();

		// Testing new SimpleProduct class. Creates new product based on the
		// farmer
		carrotProduct = new SimpleProduct("Carrots", 50, farmer, 3.99);

		// Adds a product to a LineItem list to simulate a listed item a
		// customer can buy
		List<LineItem> items = new ArrayList<>();
		items.add(new LineItem(carrotProduct, 5));

		// Create new test order
		testOrder = new Order("ORD-100", customer, items,
				items.get(0).calculateLineTotal(),
				customer.getShippingAddress(), "PROCESSING");

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

		System.out.println("\n--- Testing Customer Persistence ---");

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

		// -----------------------Farmer Test-------------------------------

		// Find by Username
		User foundFarmerByUsername = repository.findUserByUsername("Luke");

		// Find by User ID
		User foundFarmerById = repository.findUserById(farmer.getUserId());

		System.out.println("\n--- Testing Farmer Persistence ---");

		// Find by Username
		if (foundFarmerByUsername != null
				&& foundFarmerByUsername.getRole().equals("Farmer"))
		{
			System.out.println("PASS: Found Farmer by username: "
					+ foundFarmerByUsername.getUsername());
		}
		else
		{
			System.err.println(
					"FAIL: Could not find farmer by username or role was incorrect.");
		}

		// Find by User ID
		if (foundFarmerById != null
				&& foundFarmerById.getUserId().equals(farmer.getUserId()))
		{
			System.out.println(
					"PASS: Found farmer by ID: " + foundFarmerById.getUserId());
		}
		else
		{
			System.err.println(
					"FAIL: Could not find farmer by ID or ID was incorrect.");
		}
		// -------------------------Find All -----------------------------
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
		repository.saveProduct(carrotProduct);
		repository.saveProduct(appleProduct);

		System.out.println("Farmer: " + farmer.getUsername());
		System.out.println("Farmer ID: " + farmer.getUserId());
		System.out.println(
				"Product Farmer: " + carrotProduct.getFarmer().getUsername());
		System.out.println(
				"Product Farmer ID: " + carrotProduct.getFarmer().getUserId());

		// Find by SKU
		Product foundProduct = repository
				.findProductBySku(carrotProduct.getSku(), farmer);

		System.out.println("\n--- Testing Product Persistence ---");
		System.out.println("Carrot Product SKU: " + carrotProduct.getSku());
		System.out.println(
				"\n Carrot Product Title: " + carrotProduct.getTitle());
		System.out.println("\n Found Product: " + foundProduct.getTitle());
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
	 * 
	 * @param marketService The service instance to test.
	 */
	public static void testProcessPayment(FarmMarketService marketService)
	{
		System.out.println(
				"\n--- Testing Payment Processing with FarmMarketService ---");

		PaymentResult result = marketService
				.processPayment(testOrder.getTotalAmount(), detail);

		if (result != null && result.getStatus() == "SUCCESS")
		{
			System.out.println("PASS: Payment processing successful. Status: "
					+ result.getStatus() + ", ID: "
					+ result.getTransactionId());
		}
		else
		{
			System.err.println("FAIL: Payment processing failed.");
		}
	}

	/**
	 * Sets up initial Product data required for testing.
	 */
	private static void setupInitialData()
	{
		System.out.println("\n--- Initializing Setup Data ---");

		// Initial Stock: 100 apples, 50 carrots
		appleProduct = new SimpleProduct("APPLE1", "Apples",
				"Gala Apples (5lb)", 100, farmer, 9.99);
		carrotProduct = new SimpleProduct("CARROT2", "Carrots",
				"Organic Carrots (1lb)", 50, farmer, 3.99);

		// Load products into InventoryManager catalog
		inventoryManager.addProduct(appleProduct);
		inventoryManager.addProduct(carrotProduct);
		System.out.println("Products loaded into InventoryManager's catalog.");
		System.out.println("Initial Stock: Apples: 100, Carrots: 50");
	}

	/**
	 * Tests a successful order placement, verifying inventory deduction.
	 */
	private static void testSuccessfulOrderPlacement()
	{
		System.out.println("\n--- Testing Successful Order Placement ---");

		int initialStock = appleProduct.getStockQuantity();
		int quantityToOrder = 10;
		// -2 because of added stock to cart in the beginning
		int expectedRemainingStock = initialStock - quantityToOrder - 2;

		// Prepare Cart
		Cart testCart = customer.getCart();
		testCart.addProduct(appleProduct, quantityToOrder);

		// Set subtotal for the cart
		testCart.calculateSubtotal();

		// Place Order
		try
		{
			Order newOrder = orderManager.placeOrder(customer, testCart,
					detail);

			// Order Status
			if (newOrder != null && newOrder.getStatus().equals("SUCCESS"))
			{
				System.out.println(
						"PASS: Order successfully created with 'SUCCESS' status.");
			}
			else
			{
				System.err.println(
						"FAIL: Order creation failed or status was incorrect.");
			}

			// Inventory Deduction
			Product updatedProduct = inventoryManager
					.getProduct(appleProduct.getSku());

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

		int initialStock = carrotProduct.getStockQuantity(); // Should be 50
		int quantityToOrder = 51;

		// Prepare Cart
		Cart testCart = new Cart(customer);
		testCart.addProduct(carrotProduct, quantityToOrder);
		testCart.calculateSubtotal();

		boolean exceptionCaught = false;

		try
		{
			orderManager.placeOrder(customer, testCart, detail);

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
				.getProduct(carrotProduct.getSku());

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

	/**
	 * Purpose: Public static getter for the repository, allowing the GUI to
	 * access it. TESTING ONLY
	 */
	public static DataRepository getRepository()
	{
		return repository;
	}

	/**
	 * Purpose: Returns Tester's inventory manager. USed for TESTING PURPOSES 
	 * @return
	 */
	public static InventoryManager getInventoryManager()
	{
		return inventoryManager;

	}

}