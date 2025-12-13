package edu.sdmesa.homesteadhub;

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
 * @version 2025-12-12
 * 
 * @Purpose This interface sets the rules for all data persistence operations.
 * 
 *          It separates the Service layer from the database technology used.
 */
public interface DataRepository
{
	// ------User Operations------

	/**
	 * Purpose: Stores a new User or updates an existing User in the data
	 * source.
	 * 
	 * @param user The User object to be saved or updated.
	 * 
	 * @return The saved or updated User object, typically returned for
	 *         confirmation or to reflect any system-generated changes.
	 */
	User saveUser(User user);

	/**
	 * Purpose: Retrieves a specific User from the data source using their
	 * unique system ID.
	 * 
	 * @param userId The unique ID string (UUID) of the User to find.
	 * 
	 * @return The User object if found, or null if no matching ID exists.
	 */
	User findUserById(String userId);

	/**
	 * Purpose: Retrieves a specific User from the data source using their login
	 * username.
	 * 
	 * @param username The unique username string used for login.
	 * 
	 * @return The User object if found, or null if no matching username exists.
	 */
	User findUserByUsername(String username);

	/**
	 * Purpose: Retrieves all User objects currently stored in the data source.
	 * 
	 * * @return A list containing all stored User objects.
	 */
	List<User> findAllUsers();

	// ------Product Operations------

	/**
	 * Purpose: Stores a new Product or updates an existing Product in the data
	 * source.
	 * 
	 * @param product The Product object to be saved or updated.
	 * 
	 * @return The saved or updated Product object.
	 */
	Product saveProduct(Product product);

	/**
	 * Purpose: Retrieves a specific Product from the data source using its
	 * unique Stock Keeping Unit (SKU).
	 * 
	 * @param sku    The unique SKU string of the Product to find.
	 * @param farmer The Farmer object that supplies this product.
	 * 
	 * @return The Product object if found, or null otherwise.
	 */
	Product findProductBySku(String sku, Farmer farmer);

	/**
	 * Purpose: Retrieves all Products associated with a specific Farmer.
	 * 
	 * @param farmer The Farmer object whose products should be retrieved.
	 * 
	 * @return A list containing all Products supplied by the specified Farmer.
	 */
	List<Product> findAllProducts(Farmer farmer);

	/**
	 * Purpose:  Retrieves ALL Products
	 * @return A list containing all products on platform
	 */
	List<Product> findAllProducts();

	// ------Order Operations------

	/**
	 * Purpose: Saves a finalized Order to the data source.
	 * 
	 * @param order The completed Order object to be stored.
	 * 
	 * @return The saved Order object.
	 */
	Order saveOrder(Order order);

	/**
	 * Purpose: Retrieves a specific Order from the data source using its unique
	 * Order ID.
	 * 
	 * @param orderId  The unique ID string of the Order to find.
	 * @param customer The Customer object who placed the order.
	 * 
	 * @return The Order object if found, or null otherwise.
	 */
	Order findOrderById(String orderId, Customer customer);

	/**
	 * Purpose: Retrieves all Orders placed by a specific customer.
	 * This uses the customer's ID for lookup and the Customer object for
	 * reconstruction.
	 * 
	 * @param customer The Customer object who placed the orders
	 * 
	 * @return A list of orders placed by the customer.
	 */
	List<Order> findOrdersByCustomer(Customer customer);
}