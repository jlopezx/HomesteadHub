package edu.sdmesa.homesteadhub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.net.URL;

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
 * @Purpose Main JavaFX application for the Farmer Dashboard GUI.
 *          This class implements the two main views: Product Inventory and
 *          Orders.
 */
public class FarmerDashboard
{
	// --- Layout Nodes ---
	private StackPane mainContentArea;
	private ToggleButton inventoryButton;
	private ToggleButton ordersButton;

	private ObservableList<Product> products = null;
	// Data we can pull from orders data based on the farmer
	private final List<LineItem> mockOrderItems = Arrays.asList(
			new LineItem("3512", "Heirloom Pumpkins", 20, 6.00, 120.00),
			new LineItem("9876", "Compost Soil", 5, 30.00, 150.00),
			new LineItem("2123", "Eggs (Free Range)", 15, 7.00, 105.00),
			new LineItem("5129", "Apples (Fuji)", 15, 8.00, 120.00),
			new LineItem("1234", "Microgreens Mix", 25, 8.00, 200.00));

	public void startFarmerDashboard(Stage primaryStage, Farmer farmer)
	{

		// Initialize farmer's products with saved products
		products = FXCollections.observableArrayList(
				Tester.getRepository().findAllProducts(farmer));

		// Root Layout as a BorderPane
		BorderPane root = new BorderPane();
		root.getStyleClass().add("app-background");

		// Main Content Area
		mainContentArea = new StackPane();
		mainContentArea.setPadding(new Insets(30));
		root.setCenter(mainContentArea);

		// Left sidebar
		VBox sidebar = createSidebar();
		root.setLeft(sidebar);

		// The default view
		switchToView("inventory");

		// Scene Setup
		Scene scene = new Scene(root, 1000, 700);

		// Apply CSS styling by checking for external CSS file existence to
		// prevent NullPointerException. Pulls stylesheet from workspace.
		URL externalCss = this.getClass().getResource("styles.css");
		if (externalCss != null)
		{
			scene.getStylesheets().add(externalCss.toExternalForm());
		}

		// Launch the Farmer Dashboard scene
		primaryStage.setTitle("Farmer Dashboard");
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

		// Hamburger Icon/Title
		Label headerLabel = new Label("Farm Dashboard");
		headerLabel.getStyleClass().add("sidebar-header");
		VBox.setMargin(headerLabel, new Insets(0, 0, 20, 0));

		// Navigation Buttons
		inventoryButton = createNavButton("Product Inventory", "inventory",
				menuGroup);
		ordersButton = createNavButton("Orders", "orders", menuGroup);

		// Toggle Buttons VBox
		VBox navBox = new VBox(10, headerLabel, inventoryButton, ordersButton);

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setMaxWidth(Double.MAX_VALUE);
		logoutButton.getStyleClass().add("logout-button");
		logoutButton.setOnAction(
				e -> System.out.println("Logout action triggered."));

		// Structure: Header, Nav, Spacer, Logout
		VBox content = new VBox(navBox, new Region(), logoutButton);
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
		// Creates the Nav button as a ToggleButton
		ToggleButton button = new ToggleButton(text);
		// Allows the buttons to be placed in a passed in group
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

		// Set the default selection for Product Inventory
		if (viewId.equals("inventory"))
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
			case "inventory":
				view = createInventoryView();
				break;
			case "orders":
				view = createOrdersView();
				break;
			case "addItemForm":
				view = createAddItemForm();
				break;
			default:
				view = new Label("View Not Found");
				break;
		}
		// Adds newly created scene to main area
		mainContentArea.getChildren().add(view);
		StackPane.setAlignment(view, Pos.TOP_CENTER);
	}

	/**
	 * Purpose: Renders the Product Inventory view (table and Add Item button).
	 * 
	 * @return inventoryLayout Layout with inventory elements
	 */
	private Region createInventoryView()
	{
		VBox inventoryLayout = new VBox(20);
		inventoryLayout.getStyleClass().add("content-card");

		Label title = new Label("Product Inventory");
		title.getStyleClass().add("view-title");

		// --- Table View Setup ---
		TableView<Product> table = new TableView<>();
		// TODO: pull products from class. Create and use data from
		// AppInitializer after testing
		table.setItems(products);
		table.getStyleClass().add("product-table");

		// --------- SKU Column ---------
		TableColumn<Product, String> skuCol = new TableColumn<>("SKU");
		skuCol.setCellValueFactory(new PropertyValueFactory<>("sku"));
		skuCol.setPrefWidth(80);

		// --------- Product Title Column ---------
		TableColumn<Product, String> productCol = new TableColumn<>("Product");
		productCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		productCol.setPrefWidth(150);

		// TODO: Photo column placeholder since no photo system has been
		// implemented yet
		// --------- Photo Column ---------
		TableColumn<Product, String> photoCol = new TableColumn<>("");
		photoCol.setCellValueFactory(new PropertyValueFactory<>("photoUrl"));
		photoCol.setCellFactory(column -> new TableCell<Product, String>()
		{// TODO: Complete the lambda function for the photo once implemented
		});
		photoCol.setPrefWidth(100);

		// --------- Description Column ---------
		TableColumn<Product, String> descCol = new TableColumn<>("Description");
		// PropertyValueFactory looks for a getter in Product matching
		// "description"
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		// Sets column width
		descCol.setPrefWidth(250);

		// --------- Unit Price Column ---------
		TableColumn<Product, Double> priceCol = new TableColumn<>("Unit Price");
		// PropertyValueFactory looks for a getter in Product matching
		// "unitPrice"
		priceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		// Sets column width
		priceCol.setPrefWidth(120);
		// Styles price column to center column to the right
		priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		// setCellFactory requires a callback as an argument. In this case we're
		// using a lambda function to define the column paramter and body
		priceCol.setCellFactory(column -> new TableCell<Product, Double>()
		{
			@Override
			protected void updateItem(Double price, boolean empty)
			{
				super.updateItem(price, empty);
				if (empty || price == null)
				{
					setText(null);
				}
				else
				{
					setText(String.format("$%.2f", price));
				}
			}
		});

		// TODO: add photoCol when implemented
		// Build table with inventory columns
		table.getColumns().addAll(skuCol, productCol, descCol, priceCol);

		// Table takes up available vertical space
		VBox.setVgrow(table, Priority.ALWAYS);

		// Add Item Button for floating action button
		Button addButton = new Button("+");
		addButton.getStyleClass().add("fab-button");
		// Action to switch to the Product Manager Form
		addButton.setOnAction(e -> switchToView("addItemForm"));

		// Use a StackPane to place the table and the button
		StackPane inventoryStack = new StackPane(table, addButton);
		StackPane.setAlignment(addButton, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(addButton, new Insets(0, 0, 10, 0));

		inventoryLayout.getChildren().addAll(title, inventoryStack);
		return inventoryLayout;
	}

	/**
	 * Purpose: Renders the Product Manager form for adding/editing items
	 * 
	 * @return formLayout Form with appropriate input fields
	 */
	private Region createAddItemForm()
	{
		VBox formLayout = new VBox(20);
		formLayout.getStyleClass().add("content-card");
		formLayout.setPadding(new Insets(30));

		Label title = new Label("Product Manager");
		title.getStyleClass().add("view-title");
		formLayout.getChildren().add(title);

		GridPane formGrid = new GridPane();
		formGrid.setHgap(20);
		formGrid.setVgap(15);
		formGrid.setAlignment(Pos.CENTER);

		// --------- Input Fields ---------

		// --Title Input--
		formGrid.add(new Label("Title"), 0, 0);
		TextField titleField = new TextField();
		titleField.setPromptText("e.g., Organic Honey");
		titleField.getStyleClass().add("form-input");
		formGrid.add(titleField, 1, 0);

		// --Price Input--
		formGrid.add(new Label("Price ($)"), 0, 1);
		TextField priceField = new TextField();
		priceField.setPromptText("0.00");
		priceField.getStyleClass().add("form-input");
		formGrid.add(priceField, 1, 1);

		// --Description Input--
		formGrid.add(new Label("Description"), 0, 2);
		TextArea descriptionArea = new TextArea();
		descriptionArea.setPromptText("Description...");
		descriptionArea.setWrapText(true);
		descriptionArea.getStyleClass().add("form-textarea");
		formGrid.add(descriptionArea, 1, 2, 1, 2);

		// --------- Picture Placeholder ---------
		VBox pictureBox = new VBox(5);
		pictureBox.setAlignment(Pos.CENTER);
		Label picLabel = new Label("Picture");
		picLabel.getStyleClass().add("form-label");

		StackPane imagePlaceholder = new StackPane(new Label("🖼"));
		imagePlaceholder.getStyleClass().add("image-placeholder");

		pictureBox.getChildren().addAll(picLabel, imagePlaceholder);
		formGrid.add(pictureBox, 2, 0, 1, 3);

		// --------- Save Button ---------
		Button saveButton = new Button("SAVE ITEM");
		saveButton.getStyleClass().add("save-button");
		saveButton.setOnAction(e -> {
			System.out.println("Saving Item: " + titleField.getText());
			// After save, switch back to inventory view
			switchToView("inventory");
		});

		// Layout the save button below the grid
		VBox saveBox = new VBox(saveButton);
		saveBox.setAlignment(Pos.CENTER);
		VBox.setMargin(saveBox, new Insets(30, 0, 0, 0));

		formLayout.getChildren().addAll(formGrid, saveBox);
		return formLayout;
	}

	/**
	 * Purpose: Renders the Orders view (table and financial summary).
	 * 
	 * @return ordersLayout Fully structured with customer orders
	 */
	private Region createOrdersView()
	{
		// Creates the orders layout
		VBox ordersLayout = new VBox(20);
		ordersLayout.getStyleClass().add("content-card");

		// TODO: Pull from order's transaction ID
		// --------- Order View's Title ---------
		Label title = new Label("Customer Orders (Order ID: O-20251202-001)");
		title.getStyleClass().add("view-title");
		VBox.setMargin(title, new Insets(0, 0, 10, 0));

		// --------- Order Items Table ---------
		TableView<LineItem> itemTable = new TableView<>();
		// TODO: Pull orders from test Create and use data from AppInitializer
		// after testing. Look at week 6's todo
		itemTable.setItems(FXCollections.observableArrayList(mockOrderItems));
		itemTable.getStyleClass().add("order-table");

		// ---- Defines the first order column, SKU ----
		TableColumn<LineItem, String> itemSkuCol = new TableColumn<>(
				"Item # (SKU)");
		itemSkuCol.setCellValueFactory(new PropertyValueFactory<>("sku"));
		// ---- Defines the second order column, Product title ----
		TableColumn<LineItem, String> itemProductCol = new TableColumn<>(
				"Product");
		itemProductCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		// ---- Defines the third order column, Quantity ----
		TableColumn<LineItem, Integer> itemQuantityCol = new TableColumn<>(
				"Quantity");
		itemQuantityCol
				.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		itemQuantityCol.setStyle("-fx-alignment: CENTER;");
		// ---- Defines the fourth order column, unit price ----
		TableColumn<LineItem, Double> itemUnitPriceCol = new TableColumn<>(
				"Unit Price");
		itemUnitPriceCol
				.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		itemUnitPriceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		itemUnitPriceCol
				.setCellFactory(column -> GuiUtility.formatCurrencyCell());
		// ---- Defines the fifth order column, total ----
		TableColumn<LineItem, Double> itemTotalCol = new TableColumn<>("Total");
		itemTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		itemTotalCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		// Formats total column in a currency format
		itemTotalCol.setCellFactory(column -> GuiUtility.formatCurrencyCell());

		// Adds all columns to table
		itemTable.getColumns().addAll(itemSkuCol, itemProductCol,
				itemQuantityCol, itemUnitPriceCol, itemTotalCol);
		// Allows itemTable to expand vertically as needed
		VBox.setVgrow(itemTable, Priority.ALWAYS);

		// --------- Establishes the fiancial summary card ---------
		GridPane summaryGrid = GuiUtility.createFinancialSummary(mockOrderItems);


		// Layout the table and summary. Pushes it to the right
		HBox summaryBox = new HBox(new Region(), summaryGrid);
		HBox.setHgrow(summaryBox.getChildren().get(0), Priority.ALWAYS);

		// Structures Order's elements in ordersLayout
		ordersLayout.getChildren().addAll(title, itemTable, summaryBox);

		return ordersLayout;
	}
}