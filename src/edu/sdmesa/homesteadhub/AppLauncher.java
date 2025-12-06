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
 * @version 2025-12-5
 * 
 * @Purpose A utility class to launch the JavaFX application
 *          without requiring the Tester class to extend Application.
 *          This keeps the testing environment separate from the GUI setup.
 */
public class AppLauncher
{

	/**
	 * Launches the main JavaFX application.
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