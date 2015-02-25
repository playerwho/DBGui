package db.view;

import java.awt.Color;
import javax.swing.*;
import db.controller.DBGuiAppController;

public class DBGuiPanel extends JPanel
{
	private DBGuiAppController baseController;
	
	/**
	 * the baseContoller from the appController is the same as baseController in the Panel, also calls 4 methods
	 * @param baseController
	 */
	public DBGuiPanel(DBGuiAppController baseController)
	{
		this.baseController = baseController;
		
		setupPane();   
		setupPanel();
		setupLayout();
		setupListeners();
	}

	/**
	 * currently unused method
	 */
	private void setupListeners()
	{
		
	}

	/**
	 * sets up the layout of the window
	 */
	private void setupLayout()
	{
		
	}
	/**
	 * sets pane color to blue
	 */
	private void setupPanel()
	{
		this.setBackground(Color.BLUE);	
	}

	/**
	 * currently unused method
	 */
	private void setupPane()
	{
		
	}
	
	

}
