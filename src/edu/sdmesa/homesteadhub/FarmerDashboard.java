package edu.sdmesa.homesteadhub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
 * @version 2025-12-18
 * 
 * @Purpose Main JavaFX application for the Farmer Dashboard GUI.
 *          This class implements the two main views: Product Inventory and
 *          Orders.
 */
public class FarmerDashboard extends Dashboard
{
	private Farmer farmer;
	private Stage primaryStage;
	private String imagePath;

	private StackPane newImage;

	// Container for Farmer/Supplier products
	private ObservableList<Product> products = null;

	// Sidebar buttons creation
	List<SidebarButtonConfig> farmerNavButtons = Arrays.asList(
			new SidebarButtonConfig("Product Inventory", "inventory", true),
			new SidebarButtonConfig("Orders", "orders", false));

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

		this.primaryStage = primaryStage;
	}

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
		this.farmer = (Farmer) user;

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
		primaryStage.sizeToScene();
		primaryStage.centerOnScreen();
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

		// Initialize farmer's products with saved products
		products = FXCollections.observableArrayList(
				AppInitializer.getRepository().findAllProducts(farmer));

		// Populates the table with product data
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

		GridPane priceGrid = new GridPane();
		priceGrid.setHgap(20);
		priceGrid.setVgap(15);
		priceGrid.setAlignment(Pos.CENTER);

		// Constraint for field's title label columns (Col 0)
		ColumnConstraints labelColConstraint = new ColumnConstraints();
		// Labels only take the space they need
		labelColConstraint.setHgrow(Priority.NEVER);
		labelColConstraint.setMinWidth(60);

		// Constraint for price and qty input fields column (Col 1)
		ColumnConstraints fieldConstraint = new ColumnConstraints();

		// Set a fixed max width and prevent growth
		fieldConstraint.setMinWidth(80);
		fieldConstraint.setMaxWidth(120);
		fieldConstraint.setHgrow(Priority.ALWAYS);

		// Apply constraints in order
		priceGrid.getColumnConstraints().addAll(labelColConstraint,
				fieldConstraint);

		// --------- Input Fields ---------

		// --Title Input--
		formGrid.add(new Label("Title"), 0, 0);
		TextField titleField = new TextField();
		titleField.setPromptText("e.g., Organic Honey");
		titleField.getStyleClass().add("form-input");
		formGrid.add(titleField, 1, 0, 3, 1);

		// -----Inner Grid------
		// --Price Input--
		priceGrid.add(new Label("Price ($)"), 0, 0);
		TextField priceField = new TextField();
		priceField.setPromptText("0.00");
		priceField.getStyleClass().add("form-input");
		priceGrid.add(priceField, 1, 0);

		// --Quantity Input--
		priceGrid.add(new Label("Quantity   "), 0, 1);
		TextField quantityField = new TextField();
		quantityField.setPromptText("0");
		quantityField.getStyleClass().add("form-input");
		priceGrid.add(quantityField, 1, 1);
		// -----Inner Grid End------

		// Place inner grid inside outer grid
		formGrid.add(priceGrid, 0, 1, 3, 2);

		// --Description Input--
		formGrid.add(new Label("Description"), 0, 3);
		TextArea descriptionArea = new TextArea();
		descriptionArea.setPromptText("Description...");
		descriptionArea.setWrapText(true);
		descriptionArea.getStyleClass().add("form-textarea");
		formGrid.add(descriptionArea, 1, 3, 3, 2);

		// --------- Picture Placeholder ---------
		VBox pictureBox = new VBox(5);
		pictureBox.setAlignment(Pos.CENTER);
		Label picLabel = new Label("Picture");
		picLabel.getStyleClass().add("form-label");

		StackPane defaultImage = new StackPane(new Label("🖼"));
		defaultImage.getStyleClass().add("image-placeholder");

		pictureBox.getChildren().addAll(picLabel, defaultImage);
		pictureBox.setOnMouseClicked(e -> {
			// Calls uploadImage method and returns image file path if
			// successful, null otherwise
			imagePath = uploadImage(primaryStage);
			if (imagePath != null)
			{
				// Successful confirmation message
				System.out.println("Image successfully saved to workspace.");
				ImageView productImageView = new ImageView();
				productImageView.setFitWidth(150);
				productImageView.setFitHeight(150);
				productImageView.setPreserveRatio(true);

				// Checks if there's a photoUrl, if true, will set
				// productImageView with
				// product photo, otherwise it will place the default
				// placeholder
				renderImage(productImageView, imagePath);

				// Removes default place holder from picturebox
				pictureBox.getChildren().remove(defaultImage);
				pictureBox.getChildren().remove(newImage);

				// Add new product image to stackpane
				newImage = new StackPane(productImageView);

				// Dynamically adds new image to picturebox
				pictureBox.getChildren().add(newImage);
			}
			else
			{
				// Failed image operation message
				System.err.println(
						"Operation cancelled or failed to select file.");
			}
		});
		formGrid.add(pictureBox, 4, 0, 1, 3);

		// --------- Save Button ---------
		Button saveButton = new Button("SAVE ITEM");
		saveButton.getStyleClass().add("save-button");
		saveButton.setOnAction(e -> {
			System.out.println("Saving Item: " + titleField.getText());

			// Create new Product object
			Product newProduct = new Product(titleField.getText(),
					Integer.parseInt(quantityField.getText()), farmer,
					Double.parseDouble(priceField.getText()),
					descriptionArea.getText());

			// Saved photoUrl if set. This makes photo optional as it's not
			// needed by any constructor.
			if (imagePath != null)
			{
				newProduct.setPhotoUri(imagePath);
			}
			else
			{
				System.err.println("FARMERDASHBOARD: imagePath is null");
			}

			// Saves product to files and adds to inventory manager's catalog
			AppInitializer.getRepository().saveProduct(newProduct);
			AppInitializer.getInventoryManager().addProduct(newProduct);

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

		// --------- Order Items Table ---------
		TableView<LineItem> itemTable = createOrderTable();
		// Pulls order data from the data repository based on the farmer
		itemTable.setItems(FXCollections.observableArrayList(
				AppInitializer.getRepository().findOrdersToFarmer(farmer)));

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