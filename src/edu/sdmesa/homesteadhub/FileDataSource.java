package edu.sdmesa.homesteadhub;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
 * @Purpose The reponsibility of FileDataSource is Concrete implementation of
 *          DataRepository that persists data to local text files.
 *          This class handles all file I/O operations
 *
 *          FileDataSource is-a DataRepository
 */
public class FileDataSource implements DataRepository
{
	// File paths to store system data
	private static final String USERS_FILE = "users.txt";
	private static final String PRODUCTS_FILE = "products.txt";
	private static final String ORDERS_FILE = "orders.txt";
	private static final String LINE_ITEM_FILE = "lineitems.txt";

	private Helper helper = new Helper();

	/**
	 * Purpose: Constructor to create data files
	 */
	public FileDataSource()
	{
		try
		{
			// Updated to use files directly without a path prefix
			new File(USERS_FILE).toPath().toFile().createNewFile();
			new File(PRODUCTS_FILE).toPath().toFile().createNewFile();
			new File(ORDERS_FILE).toPath().toFile().createNewFile();
			new File(LINE_ITEM_FILE).toPath().toFile().createNewFile();
		}
		catch (IOException e)
		{
			System.err.println("Error creating data files: " + e.getMessage());
		}
	}

	/**
	 * Purpose: Writes user to USERS_FILE for presistence
	 * 
	 * @param user User object to save
	 * 
	 * @return user Specific User type (Customer or Farmer); null otherwise
	 */
	@Override
	public User saveUser(User user)
	{
		// Writing to file to save user
		try (FileWriter writer = new FileWriter(USERS_FILE, true))
		{
			writer.write(helper.serializeUser(user) + "\n");
		}
		catch (IOException e)
		{
			System.err.println("Error saving user to file: " + e.getMessage());
		}
		return user;
	}

	/**
	 * Purpose: Finds a user by their unique ID.
	 * Utilizes an ehanced for loop to find user
	 * 
	 * @param userId UserId to be found
	 * 
	 * @return user Found User object matching userId; null if not found
	 */
	@Override
	public User findUserById(String userId)
	{
		for (User user : findAllUsers())
		{
			if (user.getUserId().equals(userId))
			{
				return user;
			}
		}
		return null;
	}

	/**
	 * Purpose: Finds a user by username
	 * 
	 * @param username Username to search
	 * 
	 * @return user Found User object matching userId; null if not found
	 */
	@Override
	public User findUserByUsername(String username)
	{
		List<User> users = findAllUsers();
		for (User user : users)
		{
			if (user.getUsername().equalsIgnoreCase(username))
			{
				return user;
			}
		}
		return null;
	}

	/**
	 * Purpose: Finds all users inside USERS_FILE
	 * 
	 * @return users List of all User objects
	 */
	@Override
	public List<User> findAllUsers()
	{
		List<User> users = new ArrayList<>();
		// Updated file access
		try (Scanner scanner = new Scanner(new File(USERS_FILE)))
		{
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				User user = helper.deserializeUser(line);
				if (user != null)
				{
					users.add(user);
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("Error reading users file: " + e.getMessage());
		}
		return users;
	}

	/**
	 * Purpose: Writes Project object to PRODUCTS_FILE for presistence.
	 * 
	 * @param product Product object to save
	 * 
	 * @return product Returns product object; null otherwise
	 */
	@Override
	public Product saveProduct(Product product)
	{
		// Updated file access
		try (FileWriter writer = new FileWriter(PRODUCTS_FILE, true))
		{
			writer.write(helper.serializeProduct(product) + "\n");
		}
		catch (IOException e)
		{
			System.err
					.println("Error saving product to file: " + e.getMessage());
		}
		return product;
	}

	/**
	 * Purpose: Finds product by SKU belonging to a farmer
	 * 
	 * @param sku    Stock keeping unit of product to find
	 * @param farmer Farmer to focus search
	 * @return product Product if found; null otherwise
	 */
	@Override
	public Product findProductBySku(String sku, Farmer farmer)
	{
		// Retrieves products belonging to the farmer
		List<Product> products = findAllProducts(farmer);
		for (Product product : products)
		{
			// Compares prouct in products list with sku passed in
			if (product.getSku().equals(sku))
			{
				// If SKUs match, it returns the product
				return product;
			}
			else
			{
				System.err.println("FileDataSource couldn't find product for "
						+ farmer.getUsername());
			}
		}
		return null;
	}

	/**
	 * Purpose: Find all products belonging to a farmer
	 * 
	 * @param farmer Farmer to focus search
	 * @return products List of all products belonging to farmer if any
	 */
	@Override
	public List<Product> findAllProducts(Farmer farmer)
	{
		List<Product> products = new ArrayList<>();

		// Updated file access
		try (Scanner scanner = new Scanner(new File(PRODUCTS_FILE)))
		{
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				Product product = helper.deserializeProduct(line, farmer);

				if (product != null)
				{
					products.add(product);
				}
			}
		}
		catch (IOException e)
		{
			System.err
					.println("Error reading products file: " + e.getMessage());
		}

		return products;
	}

	/**
	 * Purpose: Find ALL products
	 * 
	 * @return products List of all products
	 */
	@Override
	public List<Product> findAllProducts()
	{
		List<Product> products = new ArrayList<>();

		// Updated file access
		try (Scanner scanner = new Scanner(new File(PRODUCTS_FILE)))
		{
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				Product product = helper.deserializeProduct(line);

				if (product != null)
				{
					products.add(product);
				}
			}
		}
		catch (IOException e)
		{
			System.err
					.println("Error reading products file: " + e.getMessage());
		}

		return products;
	}

	/**
	 * Purpose: Writes Order object to ORDERS_FILE for presistence
	 * 
	 * @param order Order to save
	 * 
	 * @return order Order object if successful; null otherwise
	 */
	@Override
	public Order saveOrder(Order order)
	{
		// Updated file access
		try (FileWriter writer = new FileWriter(ORDERS_FILE, true))
		{
			writer.write(helper.serializeOrder(order) + "\n");
		}
		catch (IOException e)
		{
			System.err.println("Error saving order to file: " + e.getMessage());
		}
		return order;
	}

	/**
	 * Purpose: Finds order object by Id belonging to a customer
	 * 
	 * @param orderId  Order ID of object to be found
	 * @param customer Customer to focus search
	 * 
	 * @return order Order object if found; null otherwise
	 */
	@Override
	public Order findOrderById(String orderId, Customer customer)
	{
		for (Order order : findOrdersByCustomer(customer))
		{
			if (order.getOrderId().equals(orderId))
			{
				return order;
			}
		}
		return null;
	}

	/**
	 * Purpose: Finds ALL orders belonging to a customer
	 * 
	 * @param customer Customer to focus search on
	 * 
	 * @return orders List of all Order objects belonging to customer
	 */
	@Override
	public List<Order> findOrdersByCustomer(Customer customer)
	{
		// Simpler implementation: Find all and filter.
		List<Order> orders = new ArrayList<>();

		// Updated file access
		try (Scanner scanner = new Scanner(new File(ORDERS_FILE)))
		{
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				// We use the simpler deserializeOrder helper
				Order order = helper.deserializeOrder(line, customer);
				if (order != null)
				{
					orders.add(order);
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("Error reading orders file: " + e.getMessage());
		}
		return orders;
	}

	/**
	 * Purpose: Individually saves line items into a list.
	 * 
	 * @param order    Order associated with line item.
	 * @param lineItem Line item to serialize.
	 * @return LineItem object if successful; null otherwise.
	 */
	@Override
	public LineItem saveLineItem(Order order, LineItem lineItem)
	{
		// Updated file access
		try (FileWriter writer = new FileWriter(LINE_ITEM_FILE, true))
		{
			writer.write(helper.serializeLineItem(order, lineItem) + "\n");
		}
		catch (IOException e)
		{
			System.err.println(
					"Error saving Line Item to file: " + e.getMessage());
		}
		return lineItem;
	}

	/**
	 * Purpose: Retrieves all LineItem orders made to the Farmer.
	 * 
	 * @param farmer Farmer object to find line items.
	 * @return List of LineItems associated with farmer.
	 */
	@Override
	public List<LineItem> findOrdersToFarmer(Farmer farmer)
	{
		List<LineItem> lineItems = new ArrayList<>();
		for (LineItem lineItem : findLineItems())
		{
			if (lineItem.getFarmer().equals(farmer.getUsername()))
			{
				lineItems.add(lineItem);
			}
		}

		if (!lineItems.isEmpty())
		{
			return lineItems;
		}
		System.err.println(
				"FILEDATESOURCE (findOrdersToFarmer): Returning null...");
		return null;

	}

	/**
	 * Purpose: Retirieve all line items.
	 * 
	 * @return List of ALL line items.
	 */
	@Override
	public List<LineItem> findLineItems()
	{
		// Simpler implementation: Find all and filter.
		List<LineItem> lineItems = new ArrayList<>();

		// Updated file access
		try (Scanner scanner = new Scanner(new File(LINE_ITEM_FILE)))
		{
			while (scanner.hasNextLine())
			{
				String line = scanner.nextLine();
				// We use the simpler deserializeOrder helper
				LineItem lineItem = helper.deserializeLineItem(line);
				if (lineItem != null)
				{
					lineItems.add(lineItem);
				}
			}
		}
		catch (IOException e)
		{
			System.err
					.println("Error reading lineitems file: " + e.getMessage());
		}
		return lineItems;
	}

}