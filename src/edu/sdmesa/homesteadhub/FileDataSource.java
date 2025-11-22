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
 * @version 2025-11-21
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
		}
		catch (IOException e)
		{
			System.err.println("Error creating data files: " + e.getMessage());
		}
	}

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
	 * Finds a user by their unique ID.
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

	@Override
	public Product findProductBySku(String sku, Farmer farmer)
	{
		List<Product> products = findAllProducts(farmer);
		for (Product product : products)
		{
			// Compares prouct in products list with sku passed in
			if (product.getSku().equals(sku))
			{
				// If SKUs match, it returns the product
				return product;
			}
		}
		return null;
	}

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
}