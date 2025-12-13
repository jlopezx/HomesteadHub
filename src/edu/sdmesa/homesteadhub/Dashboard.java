package edu.sdmesa.homesteadhub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
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
 * @version 2025-12-12
 * 
 * @Purpose The Dashboard class acts as the abstract base for user-specific
 *          portals.It provides the main structure and navigation for these
 *          portals, handles session state, manages view switching, and creates
 *          common UI components such as tables and financial summary panels, to
 *          faciliate the user interface in staying consistent and working after
 *          login.
 *
 *          Dashboard is-a abstract class.
 *          It manages the visual layout, session state, and navigation
 *          structure of the main application interface.
 */
public abstract class Dashboard
{

	private Stage primaryStage;
	private Scene loginScene;
	private User loggedInUser;

	// Area where views are displayed
	private StackPane mainContentArea;

	private String currentViewId;

	private TableView<LineItem> cartTable;
	private Label subtotalLabel, taxLabel, discountLabel, shippingLabel,
			grandTotalLabel;

	// Calculate totals based on the current items
	private final double taxRate = 0.07;

	// Provides access to dynamically created toggle buttons
	private Map<String, ToggleButton> navButtonsMap = new HashMap<>();

	Dashboard(Stage primaryStage, Scene loginScene, User loggedInUser)
	{
		this.primaryStage = primaryStage;
		this.loginScene = loginScene;
		this.loggedInUser = loggedInUser;
		this.mainContentArea = new StackPane();
	}

	/**
	 * Purpose: Every dashboard must have a method to start its display
	 * 
	 * @param primaryStage Main stage from Application
	 * @param loginScene   Login Scene used to start dashboard
	 * @param loggedInUser User logged into dashboard
	 */
	public abstract void startDashboard(Stage primaryStage, Scene loginScene,
			User loggedInUser);

	/**
	 * Purpose: Helper method to format currency in table cells.
	 * 
	 * @return A new TableCell instance configured to display cell's value as a
	 *         currency string.
	 */
	static TableCell<LineItem, Double> formatCurrencyCell()
	{
		// Returns a TableCell<S, T> where S is the row item (LineItem) and T is
		// the column type (Double)
		return new TableCell<LineItem, Double>()
		{
			/**
			 * Purpose: This method is called by the TableView when a new item
			 * is set.
			 * 
			 * @param price The currency amount from the
			 *              LineItem object for this cell
			 * 
			 * @param empty True if the cell is not currently displaying any
			 *              data item
			 */
			@Override
			protected void updateItem(Double price, boolean empty)
			{
				// Standard practice to always call the super constructor
				super.updateItem(price, empty);

				// Check if the cell is empty or null
				if (empty || price == null)
				{
					// If empty, clear the text displayed in the cell.
					setText(null);
				}
				else
				{
					// Format the Double price value into a currency string
					setText(String.format("$%.2f", price));
				}
			}
		};
	}

	/**
	 * Purpose: Helper to add a label row to the summary grid.
	 * 
	 * @param grid       The JavaFX GridPane where the row will be added.
	 * @param row        The row index where the content should be placed.
	 * @param key        The text for the left-side label.
	 * @param valueLabel The right-side label
	 * @param styleClass A string used to apply specific CSS styles.
	 * @return
	 */
	private int addSummaryRow(GridPane grid, int row, String key,
			Label valueLabel, String styleClass)
	{
		Label keyNode = new Label(key);
		Label valueNode = valueLabel;

		HBox keyContainer = new HBox(keyNode);
		keyContainer.setAlignment(Pos.CENTER_LEFT);

		HBox valueContainer = new HBox(valueLabel);
		valueContainer.setAlignment(Pos.CENTER_RIGHT);

		// Apply specific CSS classes based on the row. Allows for unique
		// styling
		keyContainer.getStyleClass().add(styleClass + "-label");
		valueContainer.getStyleClass().add(styleClass + "-value");

		keyNode.getStyleClass().add("summary-label");
		valueNode.getStyleClass().add("summary-value");

		// Grand Total Row
		if (styleClass.contains("grandtotal"))
		{
			// Combine label and value containers into a single row HBox
			HBox fullRow = new HBox(keyContainer, valueContainer);
			HBox.setHgrow(keyContainer, Priority.ALWAYS);
			fullRow.getStyleClass().add("grandtotal-bar");

			// Wrap in a VBox to ensure it takes up the grid row space
			VBox wrapper = new VBox(fullRow);
			grid.add(wrapper, 0, row, 2, 1);
		}
		// Subtotal Row
		else if (styleClass.contains("subtotal"))
		{
			HBox fullRow = new HBox(keyContainer, valueContainer);
			HBox.setHgrow(keyContainer, Priority.ALWAYS);
			fullRow.getStyleClass().add("subtotal-bar");

			VBox wrapper = new VBox(fullRow);
			grid.add(wrapper, 0, row, 2, 1);
		}
		// Standard Row
		else
		{
			// Add label to the first column (Column 0)
			grid.add(keyContainer, 0, row);
			// Add value to the second column (Column 1)
			grid.add(valueContainer, 1, row);
		}

		// Returns the index of the next available row
		return row + 1;
	}

	/**
	 * Purpose: Creates a custom TableCell with '+' and '-' buttons for quantity
	 * adjustment.
	 * 
	 * @return A new TableCell instance configured with updated quantity.
	 */
	protected TableCell<LineItem, Integer> createQuantityControlCell()
	{
		return new TableCell<LineItem, Integer>()
		{
			// Cell components
			private final Button minusButton = new Button("-");
			private final Button plusButton = new Button("+");
			private final TextField quantityField = new TextField();
			private final HBox controls = new HBox(5, minusButton,
					quantityField, plusButton);

			{
				controls.setAlignment(Pos.CENTER);
				quantityField.setPrefWidth(40);
				quantityField.setAlignment(Pos.CENTER);

				minusButton.getStyleClass().add("quantity-control-button");
				plusButton.getStyleClass().add("quantity-control-button");

				// Action handlers for the -/+ buttons
				minusButton.setOnAction(e -> updateQuantity(-1));
				plusButton.setOnAction(e -> updateQuantity(1));
			}

			/**
			 * Purpose: Logic to update the LineItem quantity and refresh the
			 * table.
			 * 
			 * @param quantity The amount to change the quantity by (-1 or +1).
			 */
			private void updateQuantity(int quantity)
			{
				// Get the LineItem instance for this specific row
				LineItem lineItem = getTableRow().getItem();
				if (lineItem == null) return;

				int newQuantity = lineItem.getQuantity() + quantity;

				if (newQuantity <= 0)
				{
					if (loggedInUser instanceof Customer)
					{
						// Converts User to Customer to access cart
						Customer loggedInCustomer = (Customer) loggedInUser;

						// Removes item from Cart object (data)
						loggedInCustomer.getCart()
								.removeItem(lineItem.getSku());
						System.out.println(
								"Removed 1" + " of " + lineItem.getTitle()
										+ " from customer's cart.");
					}
					// If quantity drops to 0 or less, remove the item from the
					// cart/table (view)
					getTableView().getItems().remove(lineItem);
					System.out.println("Removed item: "
							+ lineItem.getProduct().getTitle());
				}
				else
				{
					lineItem.setQuantity(newQuantity);

					// Refresh the table to update other columns
					// This triggers updateItem again for all visible cells
					getTableView().refresh();

					// Update the text field immediately
					quantityField.setText(String.valueOf(newQuantity));
					System.out.println(
							"Quantity for " + lineItem.getProduct().getTitle()
									+ " updated to: " + newQuantity);
				}
				// Updates totals when called
				updateFinancialSummary();
			}

			/**
			 * Purpose: Needed method to set the cell's content.
			 * 
			 * @param item  The quantity of the LineItem.
			 * @param empty Flag indicating if the row is empty.
			 */
			@Override
			protected void updateItem(Integer item, boolean empty)
			{
				super.updateItem(item, empty);

				// If the cell/row is empty, hide everything.
				if (empty || item == null)
				{
					setGraphic(null);
				}
				// If the cell has data, display the controls and the
				// quantity.
				else
				{
					// Set the quantity text in the TextField
					quantityField.setText(item.toString());

					// Set the HBox containing the buttons and field as the
					// cell's graphic
					setGraphic(controls);
				}
			}
		};
	}

	/**
	 * Purpose: Helper method to create a visual "card" for a product tile.
	 * 
	 * @param product Product to view
	 * @return Product card visual
	 */
	protected VBox createProductCard(Product product)
	{
		VBox card = new VBox(10);
		card.getStyleClass().add("product-card");
		card.setAlignment(Pos.CENTER);

		card.setOnMouseClicked(event -> {
			switchToView("productPage", product);
		});

		// Placeholder for Image (use a Label for simplicity)
		Label imagePlaceholder = new Label("🖼");
		imagePlaceholder.setStyle("-fx-font-size: 60px;");

		// Product Name
		Label nameLabel = new Label(product.getTitle());
		nameLabel.getStyleClass().add("product-name");

		// Price
		Label priceLabel = new Label("$" + product.getUnitPrice());
		priceLabel.getStyleClass().add("product-price");

		// Add to Cart Button
		Button addButton = new Button("Add to Cart");
		addButton.getStyleClass().add("add-to-cart-button");
		addButton.setOnAction(e -> {
			if (loggedInUser instanceof Customer)
			{
				Customer loggedInCustomer = (Customer) loggedInUser;

				int qty = 1;
				loggedInCustomer.addProductToCart(product, qty);
				System.out.println("Added " + qty + " of " + product.getTitle()
						+ " to cart.");
			}
		});

		card.getChildren().addAll(imagePlaceholder, nameLabel, priceLabel,
				addButton);
		return card;
	}

	/**
	 * Purpose:Placeholder for a single product page view.
	 * 
	 * @return productPageLayout
	 */
	protected Region createProductPageView(Product product)
	{
		VBox productPageLayout = new VBox(20);
		productPageLayout.getStyleClass().add("content-card");

		// Display the product title and details
		Label title = new Label(product.getTitle() + " Details");
		title.getStyleClass().add("view-title");

		Label price = new Label(
				"Price: " + String.format("$%.2f", product.getUnitPrice()));
		Label description = new Label(
				"Description: " + product.getDescription());

		// Simple Back Button
		Button backButton = new Button("Back to Catalog");
		backButton.getStyleClass().add("big-button");
		backButton.setOnAction(e -> {
			switchToView("catalog");
		});

		productPageLayout.getChildren().addAll(title, price, description,
				backButton);

		return productPageLayout;
	}

	/**
	 * Purpose: Helper function to create a TableView with LineItem columns.
	 * 
	 * @param editable If true, the Quantity column will have +/- buttons.
	 * @return table TableView with LineItem columns
	 */
	protected TableView<LineItem> createLineItemTable(boolean editable)
	{
		TableView<LineItem> table = new TableView<>();
		table.getStyleClass().add("order-table");
		// Helps columns fill the width of the table
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// --------- Defining Columns ---------
		// Title Column
		TableColumn<LineItem, String> itemProductCol = new TableColumn<>(
				"Product");
		itemProductCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		itemProductCol.setPrefWidth(200);

		// Item Quantity Column
		TableColumn<LineItem, Integer> itemQuantityCol = new TableColumn<>(
				"Quantity");
		itemQuantityCol
				.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		itemQuantityCol.setStyle("-fx-alignment: CENTER;");

		// Sets quantity controls if editable, otherwise it's a simple display
		if (editable)
		{
			System.out.println("Entered editable mode!");
			// Custom cell factory for quantity buttons (used in Cart view)
			itemQuantityCol
					.setCellFactory(column -> createQuantityControlCell());
			itemQuantityCol.setPrefWidth(150);
		}
		else
		{
			// Standard width for read-only table
			itemQuantityCol.setPrefWidth(80);
		}

		// Unit Price Column
		TableColumn<LineItem, Double> itemUnitPriceCol = new TableColumn<>(
				"Unit Price");
		itemUnitPriceCol
				.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		itemUnitPriceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		// Formats cell with formatCurrencyCell()
		itemUnitPriceCol.setCellFactory(column -> formatCurrencyCell());

		// Total Column
		TableColumn<LineItem, Double> itemTotalCol = new TableColumn<>("Total");
		itemTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		itemTotalCol.setStyle("-fx-alignment: CENTER-RIGHT;");

		// Formats cell with formatCurrencyCell()
		itemTotalCol.setCellFactory(column -> formatCurrencyCell());

		// Add all columns to the table
		table.getColumns().addAll(itemProductCol, itemQuantityCol,
				itemUnitPriceCol, itemTotalCol);

		// Allow table to grow vertically within its container
		VBox.setVgrow(table, Priority.ALWAYS);

		return table;
	}

	/**
	 * Purpose: Helper function to create a TableView with Order columns.
	 * 
	 * @return table TableView with Order columns
	 */
	protected TableView<Order> createOrdersTable()
	{
		TableView<Order> table = new TableView<>();
		table.getStyleClass().add("order-table");

		// Helps columns fill the width of the table
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// --------- Defining Columns ---------
		// Order ID Column
		TableColumn<Order, String> orderIdCol = new TableColumn<>("Order ID");
		orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		orderIdCol.setPrefWidth(200);

		// Order Total Column
		TableColumn<Order, Double> orderTotalCol = new TableColumn<>("Total");
		orderTotalCol
				.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
		orderTotalCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		orderTotalCol.setCellFactory(column ->
		// Returns a TableCell<S, T> where S is the row item (Order) and T is
		// the column type (Double)
		new TableCell<Order, Double>()
		{
			/**
			 * Purpose: This method is called by the TableView when a new item
			 * is set.
			 * 
			 * @param price The currency amount from the
			 *              Order object for this cell
			 * @param empty True if the cell is not currently displaying any
			 *              data item
			 */
			@Override
			protected void updateItem(Double price, boolean empty)
			{
				// Standard practice to always call the super constructor
				super.updateItem(price, empty);

				// Check if the cell is empty or null
				if (empty || price == null)
				{
					// If empty, clear the text displayed in the cell.
					setText(null);
				}
				else
				{
					// Format the Double price value into a currency string
					setText(String.format("$%.2f", price));
				}
			}
		});

		// Order Status Column
		TableColumn<Order, String> orderStatusCol = new TableColumn<>("Status");
		orderStatusCol
				.setCellValueFactory(new PropertyValueFactory<>("status"));
		orderStatusCol.setPrefWidth(200);

		// Add all columns to the table
		table.getColumns().addAll(orderIdCol, orderTotalCol, orderStatusCol);
		// Allow table to grow vertically within its container
		VBox.setVgrow(table, Priority.ALWAYS);

		return table;
	}

	/**
	 * Purpose: Creates a financial summary card based on the items provided.
	 * 
	 * @param items List items to build GridPane
	 * @return summaryGrid Gridpane with financial summary rows
	 */
	protected GridPane createFinancialSummary()
	{
		GridPane summaryGrid = new GridPane();
		summaryGrid.getStyleClass().add("summary-card");
		summaryGrid.setHgap(10);
		summaryGrid.setVgap(5);
		summaryGrid.setMinWidth(210);
		summaryGrid.setAlignment(Pos.CENTER);

		// Initialize all Label fields
		subtotalLabel = new Label();
		taxLabel = new Label();
		discountLabel = new Label();
		shippingLabel = new Label();
		grandTotalLabel = new Label();

		int row = 0;
		// Add Subtotal row
		row = addSummaryRow(summaryGrid, row, "Subtotal", subtotalLabel,
				"summary-subtotal-row");
		// Add Tax row
		row = addSummaryRow(summaryGrid, row,
				String.format("Tax rate (%.0f%%)", taxRate * 100), taxLabel,
				"summary-detail-row");
		// Add Discount row
		row = addSummaryRow(summaryGrid, row, "Discount", discountLabel,
				"summary-detail-row discount");
		// Add Shipping row
		row = addSummaryRow(summaryGrid, row, "Shipping", shippingLabel,
				"summary-detail-row");
		// Add Grand Total row
		row = addSummaryRow(summaryGrid, row, "Grand total", grandTotalLabel,
				"summary-grandtotal-row");

		updateFinancialSummary();

		return summaryGrid;
	}

	/**
	 * Purpose: Calculates the totals from the current cart items and updates
	 * the Label fields.
	 * 
	 * This is the method that MUST be called whenever the cart changes.
	 */
	protected void updateFinancialSummary()
	{
		// Get the current list of items from the ObservableList to the
		// table. This list reflects any items removed or added.
		ObservableList<LineItem> items = cartTable.getItems();

		// Calculate totals based on the current items
		double subtotal = items.stream().mapToDouble(LineItem::getTotal).sum();
		double taxAmount = subtotal * taxRate;
		double discount = subtotal > 100 ? 5.00 : 0.00;
		double shipping = 0.00;
		double grandTotal = subtotal + taxAmount - discount + shipping;

		// Update the Label text fields explicitly
		subtotalLabel.setText(String.format("$%.2f", subtotal));
		taxLabel.setText(String.format("$%.2f", taxAmount));
		discountLabel.setText(String.format("-$%.2f", discount));
		shippingLabel.setText(String.format("$%.2f", shipping));
		grandTotalLabel.setText(String.format("$%.2f", grandTotal));
	}

	/**
	 * Prupose: Creates the left-hand navigation sidebar with menu items and the
	 * Logout button.
	 * 
	 * @param labelName
	 * @param buttonConfigs
	 * @return sidebar VBox with appropriate elements
	 */
	protected VBox createSidebar(String labelName,
			List<SidebarButtonConfig> buttonConfigs)
	{
		VBox sidebar = new VBox();
		sidebar.setPrefWidth(250);
		sidebar.setPadding(new Insets(20));
		sidebar.getStyleClass().add("sidebar");
		sidebar.setSpacing(15);

		// Clear the map on creation
		navButtonsMap.clear();

		// Toggle Group for Navigation
		ToggleGroup menuGroup = new ToggleGroup();

		// Sidebar's title and styling
		Label headerLabel = new Label(labelName);
		headerLabel.getStyleClass().add("sidebar-header");
		VBox.setMargin(headerLabel, new Insets(0, 0, 20, 0));

		// Start of Inner layout for the buttons inside a VBox
		VBox navBox = new VBox(10, headerLabel);

		// Dynamic Navigation Button Creation in the sidebar
		for (SidebarButtonConfig config : buttonConfigs)
		{
			ToggleButton button = createNavButton(config.text, config.viewId,
					menuGroup);

			navButtonsMap.put(config.getViewId(), button);

			if (config.isDefault)
			{
				button.setSelected(true);
			}

			// Adds each button to nav box on each iteration
			navBox.getChildren().add(button);
		}

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setMaxWidth(Double.MAX_VALUE);
		logoutButton.getStyleClass().add("logout-button");
		logoutButton.setOnAction(e -> handleLogout());

		// Structures the Header, NavButtons, Spacer, Logout
		VBox content = new VBox(navBox, new Region(), logoutButton);

		VBox.setVgrow(content, Priority.ALWAYS);

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
	protected ToggleButton createNavButton(String text, String viewId,
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

		return button;
	}

	/**
	 * Purpose: Main function to switch the content displayed in the
	 * StackPane.
	 * 
	 * @param viewId View ID of the scene to create
	 */
	protected final void switchToView(String viewId)
	{
		// Clear current view of the dashboard
		mainContentArea.getChildren().clear();

		// Call the abstract switcher based on what the children define
		Region view = createSpecificView(viewId);

		// Handle case where the view is null
		if (view == null)
		{
			view = new Label("View Not Found: " + viewId);
		}

		// Add the view to the main scene
		mainContentArea.getChildren().add(view);

		// Centers the loaded view at the top of the StackPane
		StackPane.setAlignment(view, Pos.TOP_CENTER);

		this.currentViewId = viewId;
	}

	/**
	 * Purpose: Main function to switch the content displayed in the
	 * StackPane. Mainly for product page view
	 * 
	 * @param viewId  String id of view to switch
	 * @param product Passes Product object to view
	 */
	private void switchToView(String viewId, Product product)
	{
		// Clear current view of the dashboard
		mainContentArea.getChildren().clear();

		// Call the abstract switcher based on what the children define
		Region view = createSpecificView(viewId, product);

		// Handle case where the view is null
		if (view == null)
		{
			view = new Label("View Not Found: " + viewId);
		}

		// Add the view to the main scene
		mainContentArea.getChildren().add(view);

		// Centers the loaded view at the top of the StackPane
		StackPane.setAlignment(view, Pos.TOP_CENTER);

		this.currentViewId = viewId;

	}

	/**
	 * Purpose: Abstract method used for children's scene switching
	 * 
	 * @param viewId Scene's view ID
	 * @return Entirely created scene
	 */
	protected abstract Region createSpecificView(String viewId);

	/**
	 * Purpose: Abstract method for product page view
	 * 
	 * @param viewId
	 * @param product
	 * @return Entirely created scene
	 */
	protected abstract Region createSpecificView(String viewId,
			Product product);

	/**
	 * Purpose: Public utility to programmatically select a navigation button.
	 * 
	 * @param viewId The ID of the button to select.
	 */
	public void selectNavButton(String viewId)
	{
		ToggleButton button = navButtonsMap.get(viewId);
		if (button != null)
		{
			button.setSelected(true);
			switchToView(viewId);
		}
		else
		{
			System.err.println(
					"Navigation button not found for view ID: " + viewId);
		}
	}

	/**
	 * Purpose: Getter - Returns the dashboard's main scene
	 * 
	 * @return mainContentArea Dashboard's main scene
	 */
	protected StackPane getMainScene()
	{
		return this.mainContentArea;
	}

	/**
	 * Purpose: Getter - Gets class's cart table
	 * 
	 * @return cartTable TableView item used for Customer's cart
	 */
	protected TableView<LineItem> getCartTable()
	{
		return this.cartTable;
	}

	/**
	 * Purpose: Defines the cartTable
	 * 
	 * @param table New cart table
	 */
	protected void setCartTable(TableView<LineItem> table)
	{
		this.cartTable = table;
	}

	/**
	 * Purpose: Returns tax rate defined in class
	 * 
	 * @return taxRate
	 */
	protected double getTaxRate()
	{
		return this.taxRate;
	}

	/**
	 * Purpose: Logs the user out
	 */
	protected void handleLogout()
	{
		// Clears the logged in user
		loggedInUser = null;

		System.out.println("User logged out. Switching to login scene.");

		// Return to the login scene
		primaryStage.setScene(loginScene);
		primaryStage.setWidth(500);
		primaryStage.setHeight(350);
		primaryStage.centerOnScreen();
	}

}
