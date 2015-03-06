package db.controller;

import db.view.DBGuiFrame;

public class DBGuiAppController
{
	private DBGuiFrame appFrame;
	private DataBaseController database;
	
	/**
	 * defines database and appFrame
	 */
	public DBGuiAppController()
	{
		database = new DataBaseController(this);
		appFrame = new DBGuiFrame(this);
		
	}
	
	/**
	 * runs the methods located inside
	 */
	public void start()
	{
		
	}
	
	/**
	 * getter for appFrame
	 * @return
	 */
	public DBGuiFrame getAppFrame()
	{	
		return appFrame;
	}
	
	/**
	 * getter for the database
	 * @return
	 */
	public DataBaseController getDatabase()
	{
		return database;
	}
}
