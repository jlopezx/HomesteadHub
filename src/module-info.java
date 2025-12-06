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
 * 
 */
module HomesteadHub
{
	exports edu.sdmesa.homesteadhub;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	opens edu.sdmesa.homesteadhub to javafx.graphics, javafx.fxml;
}