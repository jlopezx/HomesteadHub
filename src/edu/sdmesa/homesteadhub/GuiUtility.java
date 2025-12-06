package edu.sdmesa.homesteadhub;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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
 *          Purpose: The reponsibility of GuiUtility is to hold helper functions
 *          that can be shared between dashboards.
 */
public class GuiUtility
{

	/**
	 * Purpose: Helper method to format currency in table cells.
	 * 
	 * @return A new TableCell instance configured to display cell's value as a
	 *         currency string.
	 */
	protected static TableCell<LineItem, Double> formatCurrencyCell()
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
	 * Purpose: Helper method to add a row to the financial summary GridPane.
	 * 
	 * @param grid       The JavaFX GridPane where the row will be added.
	 * @param row        The row index where the content should beplaced.
	 * @param label      The text for the left-side label.
	 * @param value      The text for the right-side value.
	 * @param styleClass A string used to apply specific CSS styles.
	 * 
	 * @return The next available row index (row + 1) for the next item to add.
	 */
	protected static int addSummaryRow(GridPane grid, int row, String label,
			String value, String styleClass)
	{
		// Create the visual nodes for the label
		Label labelNode = new Label(label);
		Label valueNode = new Label(value);

		// Wraps the labels in HBoxes
		HBox labelContainer = new HBox(labelNode);
		HBox valueContainer = new HBox(valueNode);

		// Apply specific CSS classes based on the row. Allows for unique
		// styling
		labelContainer.getStyleClass().add(styleClass + "-label");
		valueContainer.getStyleClass().add(styleClass + "-value");

		labelNode.getStyleClass().add("summary-label");
		valueNode.getStyleClass().add("summary-value");

		// Grand Total Row
		if (styleClass.contains("grandtotal"))
		{
			// Combine label and value containers into a single row HBox
			HBox fullRow = new HBox(labelContainer, valueContainer);
			HBox.setHgrow(labelContainer, Priority.ALWAYS);
			fullRow.getStyleClass().add("grandtotal-bar");

			// Wrap in a VBox to ensure it takes up the grid row space
			VBox wrapper = new VBox(fullRow);
			grid.add(wrapper, 0, row, 2, 1);
		}
		// Subtotal Row
		else if (styleClass.contains("subtotal"))
		{
			HBox fullRow = new HBox(labelContainer, valueContainer);
			HBox.setHgrow(labelContainer, Priority.ALWAYS);
			fullRow.getStyleClass().add("subtotal-bar");

			VBox wrapper = new VBox(fullRow);
			grid.add(wrapper, 0, row, 2, 1);
		}
		// Standard Row
		else
		{
			// Add label to the first column (Column 0)
			grid.add(labelContainer, 0, row);
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
	protected static TableCell<LineItem, Integer> createQuantityControlCell()
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

			// TODO: Implement quantity and item update logic
			private void updateQuantity(int delta)
			{

				System.out.println("Quantity Updated!");
			}

			@Override
			protected void updateItem(Integer item, boolean empty)
			{
				System.out.println("Item Updated!");
			}
		};
	}

	/**
	 * Purpose: Helper method to create a visual "card" for a product tile.
	 * 
	 * @param name
	 * @param price
	 * @param imageUrl
	 * @return card
	 */
	protected static VBox createProductCard(String name, String price,
			String imageUrl)
	{
		VBox card = new VBox(10);
		card.getStyleClass().add("product-card");
		card.setAlignment(Pos.CENTER);

		// Placeholder for Image (use a Label for simplicity)
		Label imagePlaceholder = new Label("🖼");
		imagePlaceholder.setStyle("-fx-font-size: 60px;");

		// Product Name
		Label nameLabel = new Label(name);
		nameLabel.getStyleClass().add("product-name");

		// Price
		Label priceLabel = new Label("$" + price);
		priceLabel.getStyleClass().add("product-price");

		// Add to Cart Button
		Button addButton = new Button("Add to Cart");
		addButton.getStyleClass().add("add-to-cart-button");

		card.getChildren().addAll(imagePlaceholder, nameLabel, priceLabel,
				addButton);
		return card;
	}

	/**
	 * Purpose: Helper function to create a TableView with LineItem columns.
	 * 
	 * @param editable If true, the Quantity column will have +/- buttons.
	 * @return table TableView with LineItem columns
	 */
	protected static TableView<LineItem> createLineItemTable(boolean editable)
	{
		TableView<LineItem> table = new TableView<>();
		table.getStyleClass().add("order-table");
		// Helps columns fill the width of the table
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// --------- Defining Columns ---------
		TableColumn<LineItem, String> itemProductCol = new TableColumn<>(
				"Product");
		itemProductCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		itemProductCol.setPrefWidth(200);

		TableColumn<LineItem, Integer> itemQuantityCol = new TableColumn<>(
				"Quantity");
		itemQuantityCol
				.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		itemQuantityCol.setStyle("-fx-alignment: CENTER;");

		// Sets quantity controls if editable, otherwise it's a simple display
		if (editable)
		{
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

		TableColumn<LineItem, Double> itemUnitPriceCol = new TableColumn<>(
				"Unit Price");
		itemUnitPriceCol
				.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
		itemUnitPriceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		// Formats cell with formatCurrencyCell()
		itemUnitPriceCol.setCellFactory(column -> formatCurrencyCell());

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
	 * Purpose: Creates a financial summary card based on the items provided.
	 * 
	 * @param items List items to build GridPane
	 * @return summaryGrid Gridpane with financial summary rows
	 */
	protected static GridPane createFinancialSummary(List<LineItem> items)
	{
		GridPane summaryGrid = new GridPane();
		summaryGrid.getStyleClass().add("summary-card");
		summaryGrid.setHgap(10);
		summaryGrid.setVgap(5);
		summaryGrid.setMinWidth(300);

		// TODO: Rework with real data
		// Calculate mock totals
		double subtotal = items.stream().mapToDouble(LineItem::getTotal).sum();
		double taxRate = 0.07;
		double taxAmount = subtotal * taxRate;
		double discount = subtotal > 100 ? 5.00 : 0.00; // Small mock discount
		double shipping = 0.00;
		double grandTotal = subtotal + taxAmount - discount + shipping;

		int row = 0;
		// Add Subtotal row
		row = addSummaryRow(summaryGrid, row, "Subtotal",
				String.format("$%.2f", subtotal), "summary-subtotal-row");
		// Add Tax row
		row = addSummaryRow(summaryGrid, row,
				String.format("Tax rate (%.0f%%)", taxRate * 100),
				String.format("$%.2f", taxAmount), "summary-detail-row");
		// Add Discount row
		row = addSummaryRow(summaryGrid, row, "Discount",
				String.format("-$%.2f", discount),
				"summary-detail-row discount");
		// Add Shipping row
		row = addSummaryRow(summaryGrid, row, "Shipping",
				String.format("$%.2f", shipping), "summary-detail-row");
		// Add Grand Total row
		row = addSummaryRow(summaryGrid, row, "Grand total",
				String.format("$%.2f", grandTotal), "summary-grandtotal-row");

		return summaryGrid;
	}

	// TODO: Leaving implementation for week 7
	public void handleLogout()
	{
	}
}