package edu.sdmesa.homesteadhub;

import javafx.application.Application;

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
 * @Purpose A utility class to launch the JavaFX application
 *          without requiring the Tester class to extend Application.
 *          This keeps the testing environment separate from the GUI setup.
 */
public class AppLauncher
{

	public static void main(String[] args)
	{
		AppInitializer.initialize(AppInitializer.getRepository(),
				AppInitializer.getInventoryManager());
		AppInitializer.userCreation();

		// To start with a clean application, comment out the 3 lines below to
		// ommit test data and purge the 4 data files
		Tester.startTester();
		AppInitializer.initialize(Tester.getRepository(),
				Tester.getInventoryManager());

		// This will launch the JavaFX runtime and display the Login window.
		AppLauncher.launchApp(args);
	}

	/**
	 * Purpose: Launches the main JavaFX application.
	 * 
	 * @param args Command line arguments (passed to Application.launch)
	 */
	public static void launchApp(String[] args)
	{
		// Application.launch() is the entry point for the JavaFX
		// applications.
		// It starts the JavaFX runtime and calls the start() method in
		// the LoginDashboard class.
		Application.launch(LoginDashboard.class, args);
	}
}