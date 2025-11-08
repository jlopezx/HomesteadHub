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
 * Purpose: The reponsibility of Tetser is to test classes from our HomesteadHub
 * project.
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

		// -----------------------Testing Login Functionality----------------------
		System.out.println("\n-----Testing Login Functionality-----");
		User loggedInUser = portalManager.login("Josh", "1234");
		if (loggedInUser != null)
		{
			System.out.println(
					"LOGIN SUCCESS: User " + loggedInUser.getUsername() + " ("
							+ loggedInUser.getRole() + ") authenticated.");
		}
		else
		{
			System.out.println("LOGIN FAILED.");
			return;
		}

		// -----------------------Testing Customer/Cart Relationship-----------------
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
				System.out.println("TEST FAILED: Subtotal is incorrect.");
			}
		}

		System.out.println("\n##### Week 2 Testing Complete #####");

		System.out.println("\n##### HomesteadHub Week 3 Tests #####");
		Tester tester = new Tester();
		tester.setup(farmer, customer);
		tester.testUserPersistence(farmer, customer);
		tester.testProductPersistence(farmer);
		tester.testOrderPersistence(customer);

		System.out.println("\n##### Week 3 Testing Complete #####");
	}

	private final DataRepository repository = new FileDataSource();

	private SimpleProduct testProduct;
	private Order testOrder;

	/**
	 * Purpose: Setup method to initialize the environment and create mock objects.
	 * 
	 * @param farmer
	 * @param customer
	 */
	public void setup(Farmer farmer, Customer customer)
	{
		// Clean directory
		new File("users.txt").delete();
		new File("products.txt").delete();
		new File("orders.txt").delete();

		// Testing new SimpleProduct class. Creates new product based on the farmer
		testProduct = new SimpleProduct("Carrots", 50, farmer, 3.99);

		// Adds a product to a LineItem list to simulate a listed item a customer can buy
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
	public void testUserPersistence(Farmer farmer, Customer customer)
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
	public void testProductPersistence(Farmer farmer)
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
	public void testOrderPersistence(Customer customer)
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
		List<Order> customerOrders = repository
				.findOrdersByCustomer(customer);
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
}