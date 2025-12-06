package edu.sdmesa.homesteadhub;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
 * @version 2025-12-5
 * 
 * @Purpose The reponsibility of CustomerDashboard is to provide a
 *          UI experience for a customer, allowing them to browse products,
 *          manage their cart, checkout, and view their purchase history.
 */
public class CustomerDashboard
{
	// Area where views are displayed
	private StackPane mainContentArea;

	// Nav buttons
	private ToggleButton catalogButton;
	private ToggleButton purchasesButton;
	private ToggleButton cartButton;

	// Mock data for viewing past orders
	private final List<LineItem> mockCustomerOrders = Arrays.asList(
			new LineItem("3512", "Heirloom Pumpkins", 20, 6.00, 120.00),
			new LineItem("9876", "Compost Soil", 5, 30.00, 150.00),
			new LineItem("2123", "Eggs (Free Range)", 15, 7.00, 105.00),
			new LineItem("5129", "Apples (Fuji)", 15, 8.00, 120.00),
			new LineItem("1234", "Microgreens Mix", 25, 8.00, 200.00));

	// Mock Cart Items
	private final List<LineItem> mockCartItems = Arrays.asList(
			new LineItem("5129", "Apples (Fuji)", 15, 8.00, 120.00),
			new LineItem("9876", "Compost Soil", 1, 30.00, 30.00));

	// Define colors here so they can be used in the confirmation view
	private final String primaryColor = "#52B788";
	private final String darkColor = "#2D6A4F";

	/**
	 * Purpose: Starts the customer dashboard GUI
	 * 
	 * @param primaryStage Stage to carry from
	 * @param customer     User logged in
	 */
	public void startCustomerDashboard(Stage primaryStage, Customer customer)
	{

		// Root Layout as a BorderPane
		BorderPane root = new BorderPane();
		// Refeference root for stylization
		root.getStyleClass().add("app-background");

		// Main Content Area
		mainContentArea = new StackPane();
		mainContentArea.setPadding(new Insets(30));
		root.setCenter(mainContentArea);

		// Left sidebar
		VBox sidebar = createSidebar();
		root.setLeft(sidebar);

		// The default view
		switchToView("catalog");

		// Scene Setup
		Scene scene = new Scene(root, 1000, 700);

		// Apply CSS styling by checking for external CSS file existence to
		// prevent NullPointerException. Pulls stylesheet from workspace.
		URL externalCss = this.getClass().getResource("styles.css");
		if (externalCss != null)
		{
			scene.getStylesheets().add(externalCss.toExternalForm());
		}

		// Launch the Customer Dashboard scene
		primaryStage.setTitle("Customer Dashboard");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Prupose: Creates the left-hand navigation sidebar with menu items and the
	 * Logout button.
	 * 
	 * @return sidebar VBox with appropriate elements
	 */
	private VBox createSidebar()
	{
		VBox sidebar = new VBox();
		sidebar.setPrefWidth(250);
		sidebar.setPadding(new Insets(20));
		sidebar.getStyleClass().add("sidebar");
		sidebar.setSpacing(15);

		// Toggle Group for Navigation
		ToggleGroup menuGroup = new ToggleGroup();

		// Sidebar's title and styling
		Label headerLabel = new Label("Customer Dashboard");
		headerLabel.getStyleClass().add("sidebar-header");
		VBox.setMargin(headerLabel, new Insets(0, 0, 20, 0));

		// Navigation Buttons in the sidebar
		catalogButton = createNavButton("Catalog", "catalog", menuGroup);

		purchasesButton = createNavButton("Purchases", "purchases", menuGroup);
		cartButton = createNavButton("Cart", "cart", menuGroup);

		// Inner layout for the buttons inside a VBox
		VBox navBox = new VBox(10, headerLabel, catalogButton, purchasesButton,
				cartButton);

		// TODO: Add functionality to logout
		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setMaxWidth(Double.MAX_VALUE);
		logoutButton.getStyleClass().add("logout-button");
		logoutButton.setOnAction(
				e -> System.out.println("Logout action triggered."));

		// Structures the Header, NavButtons, Spacer, Logout
		VBox content = new VBox(navBox, new Region(), logoutButton);

		// Provides an empty space between the sidebar's nav buttons and logout
		// button, making the spacer grow vertically
		VBox.setVgrow(content.getChildren().get(1), Priority.ALWAYS);
		sidebar.getChildren().add(content);

		return sidebar;
	}

	/**
	 * Purpose: Helper method to create styled navigation buttons and set their
	 * action.
	 * 
	 * @param text   Button text
	 * @param viewId ID of the view to switch to
	 * @param group  ToggleGroup to manage button selection
	 * @return The created ToggleButton
	 */
	private ToggleButton createNavButton(String text, String viewId,
			ToggleGroup group)
	{

		ToggleButton button = new ToggleButton(text);
		button.setToggleGroup(group);
		button.setMaxWidth(Double.MAX_VALUE);
		button.getStyleClass().add("nav-button");

		// Action Handler to switch views within the portal
		button.setOnAction(e -> {
			if (button.isSelected())
			{
				switchToView(viewId);
			}
		});

		// Set the default selection for Product Catalog
		if (viewId.equals("catalog"))
		{
			button.setSelected(true);
		}

		return button;
	}

	/**
	 * Purpose: Main function to switch the content displayed in the center
	 * StackPane.
	 * 
	 * @param viewId View ID of the scene to create
	 */
	private void switchToView(String viewId)
	{
		// Clear current view of the dashboard
		mainContentArea.getChildren().clear();
		Region view;

		// Switch statement used to switch between different scenes when called
		switch (viewId)
		{
			case "catalog":
				view = createCatalogView();
				break;
			case "productPage":
				view = createProductPageView();
				break;
			case "purchases":
				view = createPurchasesView();
				break;
			case "cart":
				view = createCartView();
				break;
			case "checkout":
				view = createCheckoutView();
				break;
			case "confirmation":
				view = createConfirmationView();
				break;
			default:
				view = new Label("View Not Found");
				break;
		}
		mainContentArea.getChildren().add(view);

		// Centers the loaded view at the top of the StackPane
		StackPane.setAlignment(view, Pos.TOP_CENTER);
	}

	/**
	 * Purpose: Renders the Product Inventory view
	 * 
	 * @return inventoryLayout The VBox layout for the Catalog view
	 */
	private Region createCatalogView()
	{
		VBox inventoryLayout = new VBox(20);
		inventoryLayout.getStyleClass().add("content-card");

		Label title = new Label("Catalog");
		title.getStyleClass().add("view-title");

		GridPane grid = new GridPane();
		// Horizontal gap between cards
		grid.setHgap(20);
		// Vertical gap between cards
		grid.setVgap(20);
		grid.setPadding(new Insets(20));

		// TODO: Use product.txt data to pull all available products a customer
		// can buy
		// Use the custom card creator instead of raw buttons
		Region productCard1 = GuiUtility.createProductCard("Organic Apples",
				"3.99", "url1");
		Region productCard2 = GuiUtility.createProductCard("Fresh Eggs", "5.50",
				"url2");
		Region productCard3 = GuiUtility.createProductCard("Microgreens Mix",
				"8.00", "url3");
		Region productCard4 = GuiUtility.createProductCard("Pumpkin", "12.50",
				"url4");

		// Place cards into the grid
		grid.add(productCard1, 0, 0);
		grid.add(productCard2, 1, 0);
		grid.add(productCard3, 0, 1);
		grid.add(productCard4, 1, 1);
		grid.getStyleClass().add("catalog-grid");

		// Table takes up available vertical space
		VBox.setVgrow(grid, Priority.ALWAYS);

		// Use a StackPane to place the table and the button
		StackPane catalogStack = new StackPane(grid);

		inventoryLayout.getChildren().addAll(title, catalogStack);
		return inventoryLayout;
	}

	/**
	 * TODO: Fully implement product page view
	 * Purpose:Placeholder for a single product page view.
	 * 
	 * @return productPageLayout
	 */
	private Region createProductPageView()
	{
		VBox productPageLayout = new VBox(20);
		productPageLayout.getStyleClass().add("content-card");

		return productPageLayout;
	}

	/**
	 * Purpose: Creates the Purchase History view.
	 * 
	 * @return ordersLayout The VBox layout for the Purchases view
	 */
	private Region createPurchasesView()
	{
		VBox ordersLayout = new VBox(20);
		ordersLayout.getStyleClass().add("content-card");

		Label title = new Label("Purchase History");
		title.getStyleClass().add("view-title");
		VBox.setMargin(title, new Insets(0, 0, 10, 0));

		// --------- Order Items Table ---------
		TableView<LineItem> itemTable = GuiUtility.createLineItemTable(false);
		itemTable.setItems(
				FXCollections.observableArrayList(mockCustomerOrders));

		// --------- Financial Summary Card ---------
		GridPane summaryGrid = GuiUtility
				.createFinancialSummary(mockCustomerOrders);

		// Pushes the summary grid to the right side
		HBox summaryBox = new HBox(new Region(), summaryGrid);
		HBox.setHgrow(summaryBox.getChildren().get(0), Priority.ALWAYS);

		ordersLayout.getChildren().addAll(title, itemTable, summaryBox);

		return ordersLayout;
	}

	/**
	 * Purpose: Creates the Cart view, including the item table and checkout
	 * summary.
	 * 
	 * @return cartLayout The VBox layout for the Cart view
	 */
	private Region createCartView()
	{
		VBox cartLayout = new VBox(20);
		cartLayout.getStyleClass().add("content-card");

		Label title = new Label("Your Cart");
		title.getStyleClass().add("view-title");
		VBox.setMargin(title, new Insets(0, 0, 10, 0));

		// Create a table that is editable
		TableView<LineItem> cartTable = GuiUtility.createLineItemTable(true);

		cartTable.setItems(FXCollections.observableArrayList(mockCartItems));

		// --- Action Button ---
		Button checkoutButton = new Button("Proceed to Checkout");
		checkoutButton.getStyleClass().addAll("save-button", "big-button");
		checkoutButton.setOnAction(e -> switchToView("checkout"));

		HBox buttonContainer = new HBox(checkoutButton);
		buttonContainer.setAlignment(Pos.CENTER_RIGHT);

		// --- Summary Card ---
		GridPane summaryGrid = GuiUtility.createFinancialSummary(mockCartItems);
		// Pushes the summary grid to the right side of the view
		HBox summaryBox = new HBox(new Region(), summaryGrid);
		HBox.setHgrow(summaryBox.getChildren().get(0), Priority.ALWAYS);

		cartLayout.getChildren().addAll(title, cartTable, summaryBox,
				buttonContainer);
		return cartLayout;
	}

	/**
	 * Purpose: Creates the Checkout view with order summary and payment
	 * options.
	 * 
	 * @return checkoutLayout The VBox layout for the Checkout view
	 */
	private Region createCheckoutView()
	{
		VBox checkoutLayout = new VBox(20);
		checkoutLayout.getStyleClass().add("content-card");
		checkoutLayout.setMaxWidth(800);

		Label title = new Label("Checkout");
		title.getStyleClass().add("view-title");

		// --------- Order Summary Table ---------
		TableView<LineItem> orderSummaryTable = GuiUtility
				.createLineItemTable(false);
		orderSummaryTable
				.setItems(FXCollections.observableArrayList(mockCartItems));
		orderSummaryTable.setPrefHeight(150);

		// --------- Payment Method Selector ---------
		Label paymentLabel = new Label("Payment Method:");
		paymentLabel.getStyleClass().add("section-header");

		// Dropdown for selecting payment method
		ComboBox<String> paymentMethod = new ComboBox<>(
				FXCollections.observableArrayList("Cash on Pickup",
						"Credit Card", "PayPal"));
		paymentMethod.getStyleClass().add("form-input");
		paymentMethod.getSelectionModel().selectFirst();

		HBox paymentBox = new HBox(10, paymentLabel, paymentMethod);
		paymentBox.setAlignment(Pos.CENTER_LEFT);
		VBox.setMargin(paymentBox, new Insets(10, 0, 10, 0));

		// Summary and Place Order Button in a vertical column
		GridPane summaryGrid = GuiUtility.createFinancialSummary(mockCartItems);

		// --------- Place Order Button ---------
		Button placeOrderButton = new Button("Place Order");
		placeOrderButton.getStyleClass().addAll("save-button", "big-button",
				"place-order-button");
		placeOrderButton.setOnAction(e -> {
			// Logic: Submit order, then go to confirmation
			switchToView("confirmation");
		});

		// Structure Summary and Button in a VBox
		VBox summaryAndButton = new VBox(15, summaryGrid, placeOrderButton);
		summaryAndButton.setAlignment(Pos.TOP_RIGHT);

		// Place elements in main layout (Table/Payment on Left, Summary/Button
		// on Right)
		HBox mainContent = new HBox(40);
		mainContent.setAlignment(Pos.TOP_LEFT);
		mainContent.getChildren().addAll(
				new VBox(15, orderSummaryTable, paymentBox), summaryAndButton);

		checkoutLayout.getChildren().addAll(title, mainContent);
		return checkoutLayout;
	}

	/**
	 * Purpose: Creates the successful order confirmation view.
	 * 
	 * @return confirmationLayout The VBox layout for the Confirmation view
	 */
	private Region createConfirmationView()
	{
		VBox confirmationLayout = new VBox(20);
		confirmationLayout.getStyleClass().add("content-card");
		confirmationLayout.setAlignment(Pos.CENTER);
		confirmationLayout.setMaxWidth(600);

		// --------- Confirmation Labels ---------
		Label orderTitle = new Label("Order Placed Successfully!");
		orderTitle.getStyleClass().add("view-title");
		orderTitle.setStyle(
				"-fx-text-fill: " + darkColor + "; -fx-font-size: 30px;");

		// TODO: Populate with real Order ID number (transactionId)
		Label thankYou = new Label("Order ID: " + "11010");
		thankYou.setStyle("-fx-font-size: 20px; -fx-text-fill: #666666;");

		Label emailConfirmation = new Label(
				"A confirmation and pickup instructions have been sent to your email.");
		emailConfirmation.setStyle("-fx-font-size: 16px; -fx-text-fill: "
				+ primaryColor + "; -fx-font-weight: bold;");
		VBox.setMargin(emailConfirmation, new Insets(10, 0, 15, 0));

		Label pickupNote = new Label(
				"Your farmer will notify you when your items are ready for pickup.");
		pickupNote.setWrapText(true);
		pickupNote.setStyle("-fx-font-size: 16px; -fx-text-fill: #555;");
		VBox.setMargin(pickupNote, new Insets(0, 0, 20, 0));

		// --------- Buttons to navigate away ---------
		Button continueShoppingButton = new Button("Continue Shopping");
		continueShoppingButton.getStyleClass().addAll("nav-button",
				"big-button");
		continueShoppingButton.setOnAction(e -> {
			catalogButton.setSelected(true);
			switchToView("catalog");
		});

		Button viewPurchasesButton = new Button("View Purchases");
		viewPurchasesButton.getStyleClass().addAll("save-button", "big-button");
		viewPurchasesButton.setOnAction(e -> {
			purchasesButton.setSelected(true);
			switchToView("purchases");
		});

		HBox buttonBar = new HBox(20, continueShoppingButton,
				viewPurchasesButton);
		buttonBar.setAlignment(Pos.CENTER);

		// Add all elements into confirmationLayout to be returned
		confirmationLayout.getChildren().addAll(orderTitle, thankYou,
				emailConfirmation, pickupNote, buttonBar);

		return confirmationLayout;
	}

}
