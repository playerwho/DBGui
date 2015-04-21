package db.controller;

import javax.swing.JOptionPane;

import db.model.QueryInfo;
import db.view.DBGuiFrame;
import java.util.ArrayList;

public class DBGuiAppController
{
	/**
	 * connection to Frame
	 */
	private DBGuiFrame appFrame;
	/**
	 * connection to DataBaseController
	 */
	private DataBaseController database;
	
	private ArrayList<QueryInfo> timingInfoList;
	
	/**
	 * defines database and appFrame
	 */
	public DBGuiAppController()
	{
		timingInfoList = new ArrayList<QueryInfo>();
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
	
	/**
	 * getter for timing info
	 * @return
	 */
	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}
}
