package edu.sdmesa.homesteadhub;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
 * @Purpose The reponsibility of LoginDashboard is to provide a starting point
 *          for the application and allows the user to login.
 * 
 */
public class LoginDashboard extends Application
{
	private PortalManager portalManager = AppInitializer.getPortalManager();
	private User loggedInUser = null;

	// UI Components
	private Stage primaryStage;
	private Scene loginScene;
	private Label messageLabel = new Label("");
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();

	/**
	 * Purpose: The application's starting point
	 * 
	 * @param stage The starting stage of the Application
	 */
	@Override
	public void start(Stage stage)
	{
		this.primaryStage = stage;
		this.primaryStage.setTitle("Homestead Hub Market Portal");

		// Initialize and set the Login Scene
		this.loginScene = createLoginScene();

		this.primaryStage.setScene(loginScene);
		this.primaryStage.centerOnScreen();
		this.primaryStage.show();
	}

	/**
	 * Purpose: Login scene creation
	 * 
	 * @return The login scene
	 */
	private Scene createLoginScene()
	{
		// Establish a vertical box and stylize
		VBox layout = new VBox(15);
		layout.setPadding(new Insets(30));
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: #3b934f;");

		// Create welcome text and stylize
		Label title = new Label("Welcome to Homestead Hub");
		title.setStyle(
				"-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #FFF;");

		// Customize and stylize username and password fields
		usernameField.setPromptText("Username");
		usernameField.setStyle(
				"-fx-background-radius:50; -fx-prompt-text-fill: #69b27c; -fx-font-weight: bold;");
		passwordField.setPromptText("Password");
		passwordField.setStyle(
				"-fx-background-radius:50; -fx-prompt-text-fill: #69b27c; -fx-font-weight: bold;");

		// Create login button and stylize
		Button loginButton = new Button("Login");
		loginButton.setStyle(
				"-fx-background-color: #427a8c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 50;");

		// Action handler to handle login
		loginButton.setOnAction(e -> handleLogin());

		messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

		// Input container
		VBox formContainer = new VBox(10, usernameField, passwordField,
				loginButton, messageLabel);
		formContainer.setMaxWidth(300);
		formContainer.setAlignment(Pos.CENTER);

		// Place title and input container in main layout
		layout.getChildren().addAll(title, formContainer);

		// Return entire scene when called
		return new Scene(layout, 500, 350);
	}

	/**
	 * Purpose: Handles user login
	 */
	private void handleLogin()
	{
		// Retrieves username and password entered
		String username = usernameField.getText();
		String password = passwordField.getText();
		// Ensure messageLabel is clear
		messageLabel.setText("");

		try
		{
			// Call to business logic in PortalManager to handle login
			loggedInUser = portalManager.login(username, password);

			// If user is a Farmer account type, take this branch
			if (loggedInUser instanceof Farmer)
			{
				// If it's a Farmer, then we downcast it.
				Farmer farmer = (Farmer) loggedInUser;

				// Create new FarmerDashboard instance
				FarmerDashboard dashboard = new FarmerDashboard(primaryStage,
						loginScene, farmer);

				// Transition to farmer dashboard scene
				dashboard.startDashboard(primaryStage, loginScene, farmer);

			}
			// If user is a Customer account type, take this branch
			else if (loggedInUser instanceof Customer)
			{
				// If it's a Customer, then we downcast it.
				Customer customer = (Customer) loggedInUser;

				System.out.println(
						"Successfully logged in as: " + customer.getUsername());

				// Create new CustomerDashboard instance
				CustomerDashboard dashboard = new CustomerDashboard(
						primaryStage, loginScene, customer);

				// Transition to customer dashboard scene
				dashboard.startDashboard(primaryStage, loginScene, customer);

			}
			else
			{
				// Error if there are other User type not handled
				System.err.println("Unrecognized User type logged in.");
			}

		}
		catch (UserNotFoundException e)
		{
			messageLabel.setText("Login failed: User not found.");
		}
		catch (InvalidCredentialsException e)
		{
			messageLabel.setText("Login failed: Invalid credentials.");
		}
		catch (Exception e)
		{
			messageLabel
					.setText("An unexpected error occurred: " + e.getMessage());
		}
	}
}