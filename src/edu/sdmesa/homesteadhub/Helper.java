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
 * @version 2025-12-18
 * 
 * @Purpose The reponsibility of Helper is to provid helper methods for
 *          converting domain objects (User, Product, Order) to and from their
 *          storable string format.
 */
public class Helper
{
	/**
	 * Purpose: Converts a User object into a storable string format.
	 * 
	 * @param user User object to save
	 * 
	 * @return String formatted with User object details
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
	 * Purpose: Converts a storable string format back into a User object.
	 * 
	 * @param data Data inside user file
	 * 
	 * @return Stored Customer object as a new Customer object; null if
	 *         unsuccessful
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
	 * Purpose: Converts a Product object into a storable string format (Type,
	 * SKU, Name,
	 * Stock, Price, FarmerID).
	 * 
	 * @param product Product object to be saved
	 * 
	 * @return String format of Product object; null if unsuccessful
	 */
	public String serializeProduct(Product product)
	{

		return String.format("SIMPLE,%s,%s,%s,%d,%.2f,%s", product.getSku(),
				product.getTitle(), product.getDescription(), product.getStockQuantity(),
				product.calculatePrice(), product.getFarmer().getUsername());
	}

	/**
	 * Purpose: Converts a storable string format back into a Product object to
	 * reconstruct it in memory. Used to find ALL products.
	 * 
	 * @param data Data from products file
	 * 
	 * @return Stored Product object as a new SimpleProduct object; null if
	 *         unsuccessful
	 */
	public Product deserializeProduct(String data)
	{
		String[] parts = data.split(",");
		// Provides a layer of security to make sure we're not reading from an
		// invalid line
		if (parts.length < 7) return null;

		String type = parts[0];
		String sku = parts[1];
		String title = parts[2];
		String description = parts[3];
		int stock = Integer.parseInt(parts[4]);
		double price = Double.parseDouble(parts[5]);
		// Finds farmer's username instead of ID. Can change later
		String farmer = parts[6];
		if (type.equals("SIMPLE"))
		{
			return new Product(sku, title, description, stock, farmer,
					price);
		}
		else
		{
			System.err.println("Helper couldn't return simple product");
		}
		return null;
	}

	/**
	 * Purpose: Converts a storable string format back into a Product object to
	 * reconstruct it in memory.
	 * 
	 * @param data   Data from products file
	 * @param farmer Farmer who's product belongs to
	 * 
	 * @return Stored Product object as a new SimpleProduct object; null if
	 *         unsuccessful
	 */
	public Product deserializeProduct(String data, Farmer farmer)
	{
		String[] parts = data.split(",");
		// Provides a layer of security to make sure we're not reading from an
		// invalid line
		if (parts.length < 7) return null;

		String type = parts[0];
		String sku = parts[1];
		String title = parts[2];
		String description = parts[3];
		int stock = Integer.parseInt(parts[4]);
		double price = Double.parseDouble(parts[5]);
		// parts[6] is the Farmer ID. Not needed right now
		if (type.equals("SIMPLE"))
		{
			return new Product(sku, title, description, stock, farmer,
					price);
		}
		else
		{
			System.err.println("Helper couldn't return simple product");
		}
		return null;
	}

	/**
	 * Purpose: Converts an Order object into a storable string format (OrderID,
	 * CustomerID, Total, Status).
	 * 
	 * @param order object we want to save
	 * 
	 * @return String formatted from an Order object
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
	 * @param data     Data from stored orders file
	 * @param customer Customer order belongs to
	 * 
	 * @return Stored order object as a new Order object; null if unsuccessful
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
		String status = parts[3];

		// Object is recreated in memory using Order constructor
		return new Order(orderId, customer, new ArrayList<>(), total,
				customer.getShippingAddress(), status);
	}

	/**
	 * Purpose: Converts an LineItem object into a storable string format.
	 * 
	 * @param order Order object LineItem is apart of
	 * @param lineItem Individual line item to read from
	 * @return Serialized LineItem String 
	 */
	public String serializeLineItem(Order order, LineItem lineItem)
	{
		return String.format("%s,%s,%s,%d,%.2f,%.2f,%s,%s", order.getOrderId(),
				lineItem.getSku(), lineItem.getTitle(), lineItem.getQuantity(),
				lineItem.getUnitPrice(), lineItem.getTotal(),
				order.getCustomer().getUsername(),
				lineItem.getProduct().getFarmer().getUsername());
	}

	/**
	 * Purpose: Restores String data back into LineItem objects. These are itemized orders per product.
	 * 
	 * @param data Data to read string data from
	 * 
	 * @return Restored LineItem object
	 */
	public LineItem deserializeLineItem(String data)
	{
		String[] parts = data.split(",");

		// Provides a layer of security to make sure we're not reading from an
		// invalid line
		if (parts.length < 8) return null;

		// Parses line items parts needed for Farmer Orders
		String orderId = parts[0];
		String sku = parts[1];
		String title = parts[2];
		int qty = Integer.parseInt(parts[3]);
		double unitPrice = Double.parseDouble(parts[4]);
		double total = Double.parseDouble(parts[5]);
		String customer = parts[6];
		String farmer = parts[7];

		return new LineItem(orderId, sku, title, qty, unitPrice, total,
				customer, farmer);

	}

}