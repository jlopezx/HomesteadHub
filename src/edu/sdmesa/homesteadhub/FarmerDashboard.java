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
 * @version 2025-12-12
 * 
 * @Purpose Main JavaFX application for the Farmer Dashboard GUI.
 *          This class implements the two main views: Product Inventory and
 *          Orders.
 */
public class FarmerDashboard extends Dashboard
{
	/**
	 * Purpose: Constructor to initialize Dashboard's essential field variables
	 * 
	 * @param primaryStage Main stage from Application
	 * @param loginScene   Login Scene used to start dashboard
	 * @param loggedInUser User logged into dashboard
	 */
	FarmerDashboard(Stage primaryStage, Scene loginScene, User loggedInUser)
	{
		super(primaryStage, loginScene, loggedInUser);
	}

	// Container for Farmer/Supplier products
	private ObservableList<Product> products = null;

	// Data we can pull from orders data based on the farmer
	private final List<LineItem> mockOrderItems = Arrays.asList(
			new LineItem("3512", "Heirloom Pumpkins", 20, 6.00, 120.00),
			new LineItem("9876", "Compost Soil", 5, 30.00, 150.00),
			new LineItem("2123", "Eggs (Free Range)", 15, 7.00, 105.00),
			new LineItem("5129", "Apples (Fuji)", 15, 8.00, 120.00),
			new LineItem("1234", "Microgreens Mix", 25, 8.00, 200.00));

	// Sidebar buttons creation
	List<SidebarButtonConfig> farmerNavButtons = Arrays.asList(
			new SidebarButtonConfig("Product Inventory", "inventory", true),
			new SidebarButtonConfig("Orders", "orders", false));

	public void startDashboard(Stage primaryStage, Scene loginScene, User user)
	{

		// Safe Check and Downcast
		if (!(user instanceof Farmer))
		{
			System.err.println(
					"Error: FarmerDashboard initialized with non-Farmer user type.");
			handleLogout();
		}

		// Now we can safely cast the generic User object to a Farmer object
		Farmer farmer = (Farmer) user;

		// Initialize farmer's products with saved products
		products = FXCollections.observableArrayList(
				Tester.getRepository().findAllProducts(farmer));

		// Root Layout as a BorderPane
		BorderPane root = new BorderPane();
		root.getStyleClass().add("app-background");

		// Main Content Area
		getMainScene().setPadding(new Insets(30));
		root.setCenter(getMainScene());

		// Left sidebar
		VBox sidebar = createSidebar("Farmer Dashboard", farmerNavButtons);
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
		VBox ordersLayout = new VBox(20);
		ordersLayout.getStyleClass().add("content-card");

		Label title = new Label("Purchase History");
		title.getStyleClass().add("view-title");
		VBox.setMargin(title, new Insets(0, 0, 10, 0));

		// TODO: need to add a findByFarmer() that will find based on orders
		// based on farmers only. There can be multiple farmers that a customer
		// buys from so I have to find a way to serialize the orders made to
		// each specific farmer/supplier based on a customer order.
		// List<Order> orders =
		// Tester.getRepository().findOrdersByCustomer(customer);

		// --------- Order Items Table ---------
		TableView<LineItem> itemTable = createLineItemTable(false);
		itemTable.setItems(FXCollections.observableArrayList(mockOrderItems));

		ordersLayout.getChildren().addAll(title, itemTable);

		return ordersLayout;
	}

	@Override
	protected Region createSpecificView(String viewId)
	{
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
		return view;
	}

	// Unused method for FarmerDashboard
	@Override
	protected Region createSpecificView(String viewId, Product product)
	{
		return null;
	}
}