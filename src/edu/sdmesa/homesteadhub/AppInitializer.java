package edu.sdmesa.homesteadhub;

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
 * @Purpose The reponsibility of AppInitializer is to define and initialize the
 *          central managers and data repository for the main buisness logic to
 *          function.
 *
 *          AppInitializer has-a Farmer, Customer, InventoryManager,
 *          PortalManager, and DataRepository
 */
public class AppInitializer
{
	private static DataRepository repository = new FileDataSource();

	private static InventoryManager inventoryManager = new InventoryManager();
	private static PortalManager portalManager = new PortalManager(repository);

	// Not needed if sign up method is created
	private static Farmer farmer;
	private static Customer customer;

	/**
	 * Purpose: Constructor to insitaite global repoitory and managers
	 * 
	 * @param repo Central data repository for application.
	 * @param im   Central inventory manager for application
	 */
	private AppInitializer(DataRepository repo, InventoryManager im)
	{
		repository = repo;
		inventoryManager = im;
	}

	/**
	 * Purpose: User Creation
	 * NEEDED UNTIL SIGN UP SYSTEM IS CREATED
	 */
	public static void userCreation()
	{

		// Create Users
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

	/**
	 * Purpose: Returns the central data repository, allowing the GUI to
	 * access it.
	 * 
	 * @return repository The central database for the application.
	 */
	public static DataRepository getRepository()
	{
		return repository;
	}

	/**
	 * Purpose: Returns the central inventory manager.
	 * 
	 * @return inventoryManager The central inventory manager for the
	 *         application.
	 */
	public static InventoryManager getInventoryManager()
	{
		return inventoryManager;

	}

	/**
	 * Purpose: Returns the central portal manager.
	 * 
	 * @return portalManager The central portal manager for the application.
	 */
	public static PortalManager getPortalManager()
	{
		return portalManager;
	}

	/**
	 * Purpose: Allows us to pull data repository from diffrent classes such as
	 * Tester and AppInitializer(here)
	 * 
	 * @param repo Central data repository for application.
	 * @param im   Central inventory manager for application
	 */
	public static void initialize(DataRepository repo, InventoryManager im)
	{
		new AppInitializer(repo, im);
	}

}
