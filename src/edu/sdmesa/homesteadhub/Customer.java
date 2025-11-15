package edu.sdmesa.homesteadhub;

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
 * @Purpose The reponsibility of Customer is to represent a Customer in the
 *          HomesteadHub system.
 * 
 *          Inherits from User and contains customer-specific data, including
 *          the Cart
 *          and purchase history.
 */
public class Customer extends User
{
	// Customer has a cart
	private Cart cart;
	// purchaseHistory is a collection that is instantiated and ready to hold
	// data.
	private List<Order> purchaseHistory;
	// Customer's shipping address
	private String shippingAddress;

	/**
	 * Constructor for Customer.
	 * 
	 * @param userName        The customer's username.
	 * @param password        The customer's password.
	 * @param shippingAddress The customer's default shipping address.
	 */
	public Customer(String username, String password, String email,
			String shippingAddress)
	{
		// Passes required information to the User class through the super
		// constructor
		super(username, password, email);

		this.shippingAddress = shippingAddress;

		// Composition implementation: The Customer creates its own Cart upon
		// existence.
		this.cart = new Cart(this);
	}

	/**
	 * Secondary constructor for Customer.
	 * Mainly used for DESERIALIZATION/LOADING.
	 * 
	 * @param userId          The existing user ID.
	 * @param username        The user's chosen username.
	 * @param passwordHash    The hashed version of the user's password.
	 * @param email           The user's contact email.
	 * @param shippingAddress The default shipping address.
	 */
	public Customer(String userId, String username, String password,
			String email, String shippingAddress)
	{
		super(userId, username, password, email);
		this.shippingAddress = shippingAddress;
		this.purchaseHistory = new ArrayList<>();
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
		cart.addProduct(product, quantity);
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

	/**
	 * Implements the abstract method from User.
	 * 
	 * @return The role "Customer".
	 */
	@Override
	public String getRole()
	{
		return "Customer";
	}

	/**
	 * Purpose: Getter - Returns shippingAddress
	 * 
	 * @return shippingAddress
	 */
	public String getShippingAddress()
	{
		return shippingAddress;
	}

	/**
	 * Purpose: Setter - Sets Shipping address
	 * 
	 * @param shippingAddress
	 */
	public void setShippingAddress(String shippingAddress)
	{
		this.shippingAddress = shippingAddress;
	}

}
