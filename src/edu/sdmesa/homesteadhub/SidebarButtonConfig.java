package edu.sdmesa.homesteadhub;

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
 * @Purpose The reponsibility of SidebarButtonConfig is encapsulate the function
 *          of sidebar buttons allowing for dynamic sidebar button creation.
 */
public class SidebarButtonConfig
{
	String text;
	String viewId;
	boolean isDefault;

	/**
	 * Purpose: Constructor to initialize fields 
	 * 
	 * @param text Button's text
	 * @param viewId Button's view ID to redirect
	 * @param isDefault Button default Toggle view
	 */
	public SidebarButtonConfig(String text, String viewId, boolean isDefault)
	{
		this.text = text;
		this.viewId = viewId;
		this.isDefault = isDefault;
	}

	/**
	 * Purpose: Getter - Returns the viewId of the sidebar
	 * 
	 * @return viewId
	 */
	public String getViewId()
	{
		return this.viewId;
	}

}
