package db.controller;

import java.sql.*;
import javax.swing.*;

/**
 * 
 * @author Austin Widmeier
 * @version 1.2  3/4/15
 * controlsyou the database
 */
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
	
	/**
	 * makes statement, exedcutes query, loop takes the answer pastes into results then next line to repeat. once empty it closes
	 * 
	 * shows the tables of the specified DB
	 * 
	 * @return results
	 */
	public String displayTables()
	{
		String results = "";
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			// destructive method .getString/ While loop with .next grabs the next availible thing until theres nothing left/ stores answers into results string
			while(answer.next())
			{
				results += answer.getString(1) + "\n";
			}
			
			//close to prevent data leaks and unintentional updating.
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	/**
	 * shows all the Databases on the server
	 * @return results
	 */
	public String displayDatabases()
	{
		String results = "";
		String query = "SHOW DATABASES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			// destructive method .getString/ While loop with .next grabs the next availible thing until theres nothing left/ stores answers into results string
			while(answer.next())
			{
				results += answer.getString(1) + "\n";
			}
			
			//close to prevent data leaks and unintentional updating.
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		
		return results;
		
	}
	
	/**
	 * displays the contents of the specified table
	 * @return
	 */
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE `heroes`";
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			
			// destructive method .getString/ While loop with .next grabs the next availible thing until theres nothing left/ stores answers into results string
			while(answer.next())
			{
				results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\n";
			}
			
			//close to prevent data leaks and unintentional updating.
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
		
	}
	
	/**
	 * inserts the specified data into a row in a database
	 */
	public int insert()
	{
		int rowsAffected = 0;
		String query = "INSERT INTO `dota2`.`heroes`"
				+ "(`name`, `attribute`, `role`, `role_2`, `role_3`, `role_4`, `attack_type`, `inital_dps`)"
				+ " VALUES('jkfndkfdkf', 2, 1, 1, 1, 1, 1, 66);";
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);

			//close to prevent data leaks and unintentional updating.
			insertStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return rowsAffected;
	}
}


	

	
