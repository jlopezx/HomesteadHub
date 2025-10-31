package edu.sdmesa.homesteadhub;

import java.util.ArrayList;
import java.util.List;

/**
 * Lead Author(s):
 * 
 * @author Joshua Lopez
 *
 *         References:
 *         Morelli, R., & Walde, R. (2016).
 *         Java, Java, Java: Object-Oriented Problem Solving
 *         https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 *         Version: 2025-10-30
 */

/**
 * Purpose: The reponsibility of Customer is to represent a Customer in the
 * HomesteadHub system.
 * 
 * Inherits from User and contains customer-specific data, including the Cart and purchase history.
 */
public class Customer extends User
{
	// Customer has a cart
	private Cart cart;
	// purchaseHistory is a collection that is instantiated and ready to hold data.
	private List<Order> purchaseHistory = new ArrayList<>();
	// Customer's shipping address
	private String shippingAddress;

	/**
	 * Constructor for Customer.
	 * 
	 * @param userName        The customer's username.
	 * @param password        The customer's password.
	 * @param shippingAddress The customer's default shipping address.
	 */
	public Customer(String userName, String password, String shippingAddress)
	{
		// Passes required information to the User class through the super
		// constructor
		// "Customer" is passed as the user role.
		super(userName, password, "Customer");

		this.shippingAddress = shippingAddress;

		// Composition implementation: The Customer creates its own Cart upon
		// existence.
		this.cart = new Cart(this);
	}

	/**
	 * Adds a product to the customer's cart.
	 * 
	 * @param product  The product to add.
	 * @param quantity The quantity of the product.
	 */
	public void addProductToCart(Product product, int quantity)
	{
		// Calls cart's method to store item in cart
		cart.addItemProduct(product, quantity);
	}

	/**
	 * Adds a finalized order to the customer's purchase history.
	 * 
	 * @param order The completed order object.
	 */
	public void addOrderToHistory(Order order)
	{
		purchaseHistory.add(order);
	}

	/**
	 * Purpose: Getter - Returns the cart object
	 * 
	 * @return cart Cart object
	 */
	public Cart getCart()
	{
		return cart;
	}

	/**
	 * Purpose: Getter - Returns the purchaseHistory list
	 * 
	 * @return purchaseHistory A list of Order objects
	 */
	public List<Order> getPurchaseHistory()
	{
		return purchaseHistory;
	}

}
