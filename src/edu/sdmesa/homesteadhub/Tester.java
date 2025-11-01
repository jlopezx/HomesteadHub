package edu.sdmesa.homesteadhub;

import java.util.Map;

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
		//----------------------- Testing User Creation -----------------------
		// Initialize the Managers
		PortalManager portalManager = new PortalManager();
		InventoryManager inventoryManager = new InventoryManager();

		// Create Users
		System.out.println("\n-----Testing User Creation-----");
		Farmer farmer = new Farmer("Luke", "jedi", "Skywalker Farm", 0.05);
		Customer customer = new Customer("Josh", "1234",
				"123 Test St, Black Mesa");

		// Add Users to PortalManager
		portalManager.addUser(farmer);
		portalManager.addUser(customer);
		System.out.println("\nUsers added to Roster: "
				+ portalManager.getUserRoster().keySet());

		//-----------------------Testing Product Creation----------------------
		System.out.println("\n-----Testing Product Creation-----");
		Product appleProduct = new Product("APPLE1", "Apples (5lb bag)", 9.99,
				50, false);

		// Add Product to InventoryManager
		inventoryManager.addProduct(appleProduct);
		System.out.println("\nProduct added to Catalog: " + inventoryManager
				.getProductCatalog().get("APPLE1").getTitle());

		//-----------------------Testing Login Functionality----------------------
		System.out.println("\n-----Testing Login Functionality-----");
		User loggedInUser = portalManager.login("Josh", "1234");
		if (loggedInUser != null)
		{
			System.out.println(
					"LOGIN SUCCESS: User " + loggedInUser.getUserName() + " ("
							+ loggedInUser.getUserRole() + ") authenticated.");
		}
		else
		{
			System.out.println("LOGIN FAILED.");
			return;
		}
		
		//-----------------------Testing Customer/Cart Relationship-----------------
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

		System.out.println("\n##### Testing Complete #####");
	}
}
