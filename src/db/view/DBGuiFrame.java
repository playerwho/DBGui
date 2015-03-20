package db.view;

import javax.swing.*;
import db.controller.DBGuiAppController;

public class DBGuiFrame extends JFrame
{
	/**
	 * connection to Panel
	 */
	private DBGuiPanel appPanel;
	
	/**
	 * defines appPanel and calls setupFrame
	 * @param baseController
	 */
	public DBGuiFrame(DBGuiAppController baseController)
	{
		appPanel = new DBGuiPanel(baseController);
		setupFrame();
	}

	/**
	 * sets the windowed frame to be a size of 1000,1000 , and a true visibilitiy
	 */
	private void setupFrame()
	{
		this.setSize(1000,1000);
		this.setContentPane(appPanel);
		this.setVisible(true);	
	}
}
