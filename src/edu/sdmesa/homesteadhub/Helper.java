package edu.sdmesa.homesteadhub;

import java.util.ArrayList;

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
 * @Purpose The reponsibility of Helper is to provid helper methods for
 *          converting domain objects (User, Product, Order) to and from their
 *          storable string format.
 */
public class Helper
{
	/**
	 * Converts a User object into a storable string format.
	 */
	public String serializeUser(User user)
	{
		String role = user.getRole();
		// userId is kept in the serialized string because Product and Order
		// depend on it.
		String base = String.format("%s,%s,%s,%s,%s", role, user.getUserId(),
				user.getUsername(), user.getPassword(), user.getEmail());

		if (role.equals("Customer"))
		{
			Customer customer = (Customer) user;
			return base + "," + customer.getShippingAddress();
		}
		else if (role.equals("Farmer"))
		{
			Farmer farmer = (Farmer) user;
			return base + "," + farmer.getFarmName() + ","
					+ farmer.getLocation();
		}
		return null;
	}

	/**
	 * Converts a storable string format back into a User object.
	 */
	public User deserializeUser(String data)
	{
		String[] parts = data.split(",");
		// Provides a layer of security to make sure we're not reading from an
		// invalid line
		if (parts.length < 5) return null;

		String role = parts[0];
		String userId = parts[1];
		String username = parts[2];
		String mockPass = parts[3];
		String email = parts[4];

		// Use secondary constructors that accept the existing userId
		if (role.equals("Customer") && parts.length >= 6)
		{
			String address = parts[5];
			return new Customer(userId, username, mockPass, email, address);
		}
		else if (role.equals("Farmer") && parts.length >= 7)
		{
			String farmName = parts[5];
			String location = parts[6];
			return new Farmer(userId, username, mockPass, email, farmName,
					location);
		}
		return null;
	}

	/**
	 * Converts a Product object into a storable string format (Type, SKU, Name,
	 * Stock, Price, FarmerID).
	 */
	public String serializeProduct(Product product)
	{
		if (product instanceof SimpleProduct)
		{
			SimpleProduct sp = (SimpleProduct) product;
			return String.format("SIMPLE,%s,%s,%s,%d,%.2f,%s", sp.getSku(),
					sp.getTitle(), sp.getDescription(), sp.getStockQuantity(), sp.calculatePrice(),
					sp.getFarmer().getUserId());
		}
		return null;
	}

	// TODO: Implement serialization and deserialization for BundleProduct
	// (Requires recursion)

	/**
	 * Converts a storable string format back into a Product object to
	 * reconstruct it in memory.
	 */
	public Product deserializeProduct(String data, Farmer farmer)
	{
		String[] parts = data.split(",");
		// Provides a layer of security to make sure we're not reading from an
		// invalid line
		if (parts.length < 8) return null;

		String type = parts[0];
		String sku = parts[1];
		String title = parts[2];
		String description = parts[3];
		int stock = Integer.parseInt(parts[3]);
		double price = Double.parseDouble(parts[4]);
		// parts[5] is the Farmer ID. Not needed right now
		if (type.equals("SIMPLE"))
		{
			return new SimpleProduct(sku, title, description, stock, farmer, price);
		}
		return null;
	}

	/**
	 * Purpose: Converts an Order object into a storable string format (OrderID,
	 * CustomerID, Total, Status).
	 * 
	 * @param order object we want to save
	 * @return String formatted as an Order
	 */
	public String serializeOrder(Order order)
	{
		return String.format("%s,%s,%.2f,%s", order.getOrderId(),
				order.getCustomer().getUserId(), order.getTotalAmount(),
				order.getStatus());
	}

	/**
	 * Purpose: Converts a storable string format back into an Order object.
	 * 
	 * @param data
	 * @param customer
	 * @return Order object with
	 */
	public Order deserializeOrder(String data, Customer customer)
	{
		String[] parts = data.split(",");
		// Provides a layer of security to make sure we're not reading from an
		// invalid line
		if (parts.length < 4) return null;

		String orderId = parts[0];
		// parts[1] is customerId. Not needed right now
		double total = Double.parseDouble(parts[2]);
		// parts[3] is order status. Not needed right now

		// Object is recreated in memory using Order constructor
		return new Order(orderId, customer, new ArrayList<>(), total,
				customer.getShippingAddress());
	}
}