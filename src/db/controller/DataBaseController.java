package db.controller;

import java.sql.*;
import javax.swing.*;

public class DataBaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private DBGuiAppController baseController;
	
	/**
	 * calls the two methods checkDriver and setupConnection
	 * @param baseController
	 */
	public DataBaseController(DBGuiAppController baseController)
	{
		connectionString = "jdbc:mysql://localhost/dota2?user=root";
		this.baseController = baseController;
		checkDriver();
		setupConnection();
	}
	
	/**
	 * checks for the needed driver to run, if its not there, then quit app and run errors
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	/**
	 * shows any SQL errors, called in checkDriver
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		// instanceof: checks to see if obj, currentException is a subtype of SQLException
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
	}
	
	/**
	 * closes the connection to a Database
	 */
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * initiates a connection to a Database
	 */
	private void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
}

	

	
