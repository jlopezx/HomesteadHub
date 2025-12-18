package edu.sdmesa.homesteadhub;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
 * @version 2025-12-18
 * 
 * @Purpose The reponsibility of CustomerDashboard is to provide a
 *          UI experience for a customer, allowing them to browse products,
 *          manage their cart, checkout, and view their purchase history.
 */
public class CustomerDashboard extends Dashboard
{

	private Customer customer;
	private OrderManager orderManager;
	private String selectedMethod;
	private Order newOrder;

	// Sidebar buttons creation
	List<SidebarButtonConfig> customerNavButtons = Arrays.asList(
			new SidebarButtonConfig("Catalog", "catalog", true),
			new SidebarButtonConfig("Purchases", "purchases", false),
			new SidebarButtonConfig("Cart", "cart", false));

	// Define colors here so they can be used in the confirmation view
	private final String primaryColor = "#52B788";
	private final String darkColor = "#2D6A4F";

	public CustomerDashboard(Stage primaryStage, Scene loginScene,
			User loggedInUser)
	{
		super(primaryStage, loginScene, loggedInUser);
	}

	/**
	 * Purpose: Starts the customer dashboard GUI
	 * 
	 * @param primaryStage Main stage from Application
	 * @param loginScene   Login Scene used to start dashboard
	 * @param loggedInUser User logged into dashboard
	 */
	public void startDashboard(Stage primaryStage, Scene loginScene, User user)
	{

		customer = (Customer) user;
		// Root Layout as a BorderPane
		BorderPane root = new BorderPane();
		// Refeference root for stylization
		root.getStyleClass().add("app-background");

		getMainScene().setPadding(new Insets(30));
		root.setCenter(getMainScene());

		// Left sidebar
		VBox sidebar = createSidebar("Customer Dashboard", customerNavButtons);
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
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
		primaryStage.show();
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

		// Pulls product catalog items from Inventory manager's map values
		List<Product> products = new ArrayList<>(AppInitializer
				.getInventoryManager().getProductCatalog().values());

		// List to hold all product cards used in the catalog
		List<Region> productCards = new ArrayList<>();

		// Creates product cards for products inside products.txt
		for (Product product : products)
		{
			productCards.add(createProductCard(product));
		}

		// Defines the number of columns in catalog view
		final int COLUMNS = 3;

		for (int index = 0; index < productCards.size(); index++)
		{
			// Calculate the column index (x) using the modulo operator (%)
			int x = index % COLUMNS;

			// Calculate the row index (y) using integer division (/)
			int y = index / COLUMNS;

			// Place the card into the grid at the calculated position (x, y)
			grid.add(productCards.get(index), x, y);

			System.out.println("Placing product at x: " + x + " | y: " + y
					+ " | Index: " + index);
		}

		grid.getStyleClass().add("catalog-grid");
		grid.setAlignment(Pos.CENTER);

		// Table takes up available vertical space
		VBox.setVgrow(grid, Priority.ALWAYS);

		// Use a StackPane to place the table and the button
		StackPane catalogStack = new StackPane(grid);

		inventoryLayout.getChildren().addAll(title, catalogStack);
		return inventoryLayout;
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

		// Pulls customer's orders from data repository
		List<Order> orders = AppInitializer.getRepository()
				.findOrdersByCustomer(customer);

		// --------- Order Items Table ---------
		TableView<Order> itemTable = createOrdersTable();
		// Populates table with orders made
		itemTable.setItems(FXCollections.observableArrayList(orders));

		ordersLayout.getChildren().addAll(title, itemTable);

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
		setCartTable(createLineItemTable(true));

		// Pulls customer's cart items
		getCartTable().setItems(FXCollections
				.observableArrayList(customer.getCart().getItemMap().values()));

		System.out.println("Cart Items: " + customer.getCart().getItemMap());

		// --- Action Button ---
		Button checkoutButton = new Button("Proceed to Checkout");
		checkoutButton.getStyleClass().addAll("save-button", "big-button");
		checkoutButton.setOnAction(e -> switchToView("checkout"));

		HBox buttonContainer = new HBox(checkoutButton);
		buttonContainer.setAlignment(Pos.CENTER_RIGHT);

		// --- Summary Card ---
		GridPane summaryGrid = createFinancialSummary();
		// Pushes the summary grid to the right side of the view
		HBox summaryBox = new HBox(new Region(), summaryGrid);
		HBox.setHgrow(summaryBox.getChildren().get(0), Priority.ALWAYS);

		cartLayout.getChildren().addAll(title, getCartTable(), summaryBox,
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
		TableView<LineItem> orderSummaryTable = createLineItemTable(false);
		orderSummaryTable.setItems(FXCollections
				.observableArrayList(customer.getCart().getItemMap().values()));
		orderSummaryTable.setPrefHeight(150);

		// --------- Payment Method Selector ---------
		Label paymentLabel = new Label("Payment Method:");
		paymentLabel.getStyleClass().add("section-header");

		PaymentList payList = new PaymentList();
		payList.registerProcessor("Cash on Pickup", new CashPickupProcessor());

		// Dropdown for selecting payment method
		ComboBox<String> paymentMethod = new ComboBox<>(
				FXCollections.observableArrayList("Cash on Pickup", " "));
		paymentMethod.getStyleClass().add("form-input");
		paymentMethod.getSelectionModel().selectFirst();

		HBox paymentBox = new HBox(10, paymentLabel, paymentMethod);
		paymentBox.setAlignment(Pos.CENTER_LEFT);
		VBox.setMargin(paymentBox, new Insets(10, 0, 10, 0));

		// Summary and Place Order Button in a vertical column
		GridPane summaryGrid = createFinancialSummary();

		// Sets default selected method
		selectedMethod = "Cash on Pickup";

		// Handles payment method selection
		paymentMethod.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					// Check if newValue is not null
					if (newValue != null)
					{

						// Update the variable/object with the new value
						selectedMethod = newValue;

						System.out.println("Payment method updated from "
								+ oldValue + " to " + newValue);
					}
				});
		// --------- Place Order Button ---------
		Button placeOrderButton = new Button("Place Order");
		placeOrderButton.getStyleClass().addAll("save-button", "big-button",
				"place-order-button");

		// Sets action handler for placing order
		placeOrderButton.setOnAction(e -> {
			// Pulls cart data to retrieve grand total
			ObservableList<LineItem> items = getCartTable().getItems();
			// Calculate totals based on the current items
			double subtotal = items.stream().mapToDouble(LineItem::getTotal)
					.sum();
			double taxAmount = subtotal * getTaxRate();
			double discount = subtotal > 100 ? 5.00 : 0.00;
			double shipping = 0.00;
			double grandTotal = subtotal + taxAmount - discount + shipping;

			// Creates payment detail object entered by customer
			PaymentDetail detail = new PaymentDetail(grandTotal,
					payList.getProcessor(selectedMethod), customer);

			// Creates order manager to prep order placement
			orderManager = new OrderManager(
					AppInitializer.getInventoryManager(),
					detail.getPaymentMethod());

			// Handles order placement
			try
			{
				// newOrder holds a reference to the order.
				newOrder = orderManager.placeOrder(customer, customer.getCart(),
						detail);

				// Switches to confirmation view if order was successful
				switchToView("confirmation");

			}
			catch (InsufficientStockException e1)
			{
				System.err.println("FAIL: Order threw unexpected exception: "
						+ e1.getMessage());
			}

		});

		// Structure Summary and Button in a VBox
		VBox summaryAndButton = new VBox(15, summaryGrid, placeOrderButton);
		summaryAndButton.setAlignment(Pos.TOP_RIGHT);

		// Place elements in main layout
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

		// Thank you confirmation with order ID
		Label thankYou = new Label("Order ID: " + newOrder.getOrderId());
		thankYou.setStyle("-fx-font-size: 20px; -fx-text-fill: #777777;");

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
			selectNavButton("catalog");
		});

		Button viewPurchasesButton = new Button("View Purchases");
		viewPurchasesButton.getStyleClass().addAll("save-button", "big-button");
		viewPurchasesButton.setOnAction(e -> {
			selectNavButton("purchases");
		});

		HBox buttonBar = new HBox(20, continueShoppingButton,
				viewPurchasesButton);
		buttonBar.setAlignment(Pos.CENTER);

		// Add all elements into confirmationLayout to be returned
		confirmationLayout.getChildren().addAll(orderTitle, thankYou,
				emailConfirmation, pickupNote, buttonBar);

		return confirmationLayout;
	}

	@Override
	protected Region createSpecificView(String viewId, Product product)
	{
		Region view;
		switch (viewId)
		{
			case "productPage":
				view = createProductPageView(product);
				break;
			default:
				view = new Label("View Not Found");
				break;
		}
		return view;
	}

	@Override
	protected Region createSpecificView(String viewId)
	{
		Region view;
		switch (viewId)
		{
			case "catalog":
				view = createCatalogView();
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
		return view;
	}

}