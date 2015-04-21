package db.controller;

import java.sql.*;

import javax.swing.*;

import db.model.QueryInfo;

/**
 * 
 * @author Austin Widmeier
 * @version 1.5
 *  3-24-15: added a cell renderer for the DB, previous problems still exist
 */
public class DataBaseController
{
	/**
	 * string to connect to DB
	 */
	private String connectionString;
	/**
	 * connection towards the DB Server
	 */
	private Connection databaseConnection;
	/**
	 * controller towards the app controller
	 */
	private DBGuiAppController baseController;

	/**
	 * Questions torwards DB
	 */
	private String currentQuery;
	
	/**
	 * builds a DB connection String with the given parameters.
	 * 	? breaks/ ends the path and starts sending information to Server.
	 *  & add specific information to be sent to Server
	 * @param pathToServer - self explanitory
	 * @param DBName - name of the Database
	 * @param userName - your username on the DB you are trying to access
	 * @param password - your password to grant you access to the DB
	 */
	public void connectionStringBuilder(String pathToServer, String DBName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToServer;
		connectionString += "/" + DBName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;		
	}

	/**
	 * calls the two methods checkDriver and setupConnection
	 * 
	 * @param baseController
	 */
	public DataBaseController(DBGuiAppController baseController)
	{
		
		connectionString = "jdbc:mysql://10.228.5.160/book_reading?user=a.widmeier&password=widm140";
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
		} catch (Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}

	/**
	 * shows any SQL errors, called in checkDriver
	 * 
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		// instanceof: checks to see if obj, currentException is a subtype of SQLException
		if (currentException instanceof SQLException)
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
		catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
	}

	/**
	 * initiates a connection to a Database
	 */
	public void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		} catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * checks for a violation in the DB
	 * @return checkForDataViolation is either true or false
	 */
	private boolean checkForDataViolation()
	{
		
		if(currentQuery.toUpperCase().contains(" DROP ")
				|| currentQuery.toUpperCase().contains(" TRUNCATE ")
				|| currentQuery.toUpperCase().contains(" SET ")
				|| currentQuery.toUpperCase().contains (" ALTER "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * A checker that checks for an sql structure error
	 * @return true or false checkStructureViolation, its either bad struture or not.
	 */
	private boolean checkStructureViolation()
	{
		if(currentQuery.toUpperCase().contains(" DATABASE ") || currentQuery.toUpperCase().contains(" TABLE ") )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * An sql statement to alter a column, or index
	 */
	public void alterStatement()
	{
		String results;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			if(checkStructureViolation())
			{
				throw new SQLException("Bad alter",
										"try again",
										Integer.MIN_VALUE);
			}
			
			if(currentQuery.toUpperCase().contains(" ADD "))
			{
				results = "the amount of added columns is ";
			}
			else if(currentQuery.toUpperCase().contains(" DROP COLUMN "))
			{
				results = "Dropped column";
			}
			else if(currentQuery.toUpperCase().contains(" DROP INDEX "))
			{
				results = "Dropped The index";
			}
			else
			{
				results = "";
			}
			Statement alterStatement = databaseConnection.createStatement();
			int affected = alterStatement.executeUpdate(currentQuery);
			alterStatement.close();
			
			if(affected != 0)
			{
				 results += affected;
			}
			endTime = System.currentTimeMillis();
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch(SQLException alterError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(alterError);
		}
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
	}
	
	/**
	 * A stement to drop indexes or tables
	 */
	public void dropStatement()
	{
		String results;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			if(checkStructureViolation())
			{
				throw new SQLException("no Dropping out",
										"go back to school",
										Integer.MIN_VALUE);
			}
			if(currentQuery.toUpperCase().contains("INDEX"))
			{
				results = "The index was ";
			}
			else
			{
				results = "The table was ";
			}
			
			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);
			dropStatement.close();
			
			if(affected == 0)
			{
				results += " Dropped";
			}
			endTime = System.currentTimeMillis();
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch(SQLException dropError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(dropError);
		}
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		
	}

	/**
	 * makes statement, executes query, loop takes the answer pastes into results then next line to repeat. once empty it closes
	 * 
	 * shows the tables of the specified DB
	 * 
	 * @return results
	 */
	public String displayTables()
	{
		String results = "";
		String query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);

			// destructive method .getString/ While loop with .next grabs the next availible thing until theres nothing left/ stores answers into results string
			while (answer.next())
			{
				results += answer.getString(1) + "\n";
			}

			// close to prevent data leaks and unintentional updating.
			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return results;
	}
	
	/**
	 * Generic select based query for DBController
	 * checks for no destructive methods
	 * calls checkViolation method to do such checks
	 * @param query
	 * @return
	 */
	public String [][] selectQueryResults(String query)
	{
		this.currentQuery = query;
		String [][] results;
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		
		try
		{
			if(checkForDataViolation())
			{
				throw new SQLException("illegal modification of data!!!", "you don messed up...", Integer.MIN_VALUE);
			}
			
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);

			int colCount = answer.getMetaData().getColumnCount();
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();	
			results = new String [rowCount][colCount];
			
			// moves through all rows
			while(answer.next())
			{
				for(int col = 0; col < colCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
				
			}
			
			endTime = System.currentTimeMillis();
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String [][] {
										{"Query error"},
										{"try sending better Query? :D"},
										{currentSQLError.getMessage()}
									 };
			displayErrors(currentSQLError);
		}
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		
		return results;
	}
	
	/**
	 * uses metaData to retrieve a table and display it in a table from the Gui
	 * @return
	 */
	public String [][] bestInfo (String DBName, String tableName)
	{
		String [][] results;
		String query = "SELECT * FROM `" + DBName + "`.`" + tableName + "`";
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int colCount = answer.getMetaData().getColumnCount();
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();	
			results = new String [rowCount][colCount];
			
			// moves through all rows
			while(answer.next())
			{
				for(int col = 0; col < colCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
				
			}
			
			endTime = System.currentTimeMillis();
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String [][] {{"you get, NOTHING!!!! :D"}};
			displayErrors(currentSQLError);
		}
		
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return results;
	}
			
	/**
	 * converts  1D data into 2D data
	 * @return
	 */
	public String[][] tableInfo()
	{
		String [][] results;
		String query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();	
			results = new String [rowCount][1];
			
			while(answer.next())
			{
				results[answer.getRow() - 1][0] = answer.getString(1);
			}
			
			endTime = System.currentTimeMillis();
			answer.close();
			firstStatement.close();
		} 
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String [][] {{"you get, NOTHING!!!! :D"}}; //is Robust!
			displayErrors(currentSQLError);
		}

		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return results;
	}
	
	/**
	 * goes thorugh columns using metaData and gets the name of each column 
	 * @return colInfo - column name
	 */
	public String [] getMetaData()
	{
		String [] colInfo;
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData myMeta = answer.getMetaData();
			
			
			colInfo = new String[myMeta.getColumnCount()];
			
			for(int spot = 0; spot < myMeta.getColumnCount(); spot++)
			{
				colInfo[spot] = myMeta.getColumnName(spot + 1);
			}
			
			endTime = System.currentTimeMillis();
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			colInfo = new String [] {"NOTHING!!"};
			displayErrors(currentSQLError);
		}
		
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return colInfo;
	}
	
	/**
	 * more genaric way of getting metadata
	 * @param tableName specified table name
	 * @return new array of string(s)
	 */
	public String [] getDBColumnNames(String tableName)
	{
		String [] cols;
		currentQuery = "SELECT * FROM `" + tableName + "`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(currentQuery);
			ResultSetMetaData myMeta = answer.getMetaData();
			
			
			cols = new String[myMeta.getColumnCount()];
			
			for(int spot = 0; spot < myMeta.getColumnCount(); spot++)
			{
				cols[spot] = myMeta.getColumnName(spot + 1);
			}
			
			
			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			cols = new String [] {"NOTHING!!"};
			displayErrors(currentSQLError);
		}
		
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return cols;
	}

	/**
	 * shows all the Databases on the server
	 * 
	 * @return results
	 */
	public String displayDatabases()
	{
		String results = "";
		String query = "SHOW DATABASES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);

			// destructive method .getString/ While loop with .next grabs the next availible thing until theres nothing left/ stores answers into results string
			while (answer.next())
			{
				results += answer.getString(1) + "\n";
			}

			// close to prevent data leaks and unintentional updating.
			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		} 
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}

		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return results;

	}

	/**
	 * displays the contents of the specified table
	 * 
	 * @return
	 */
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE `books`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);

			// destructive method .getString/ While loop with .next grabs the next availible thing until theres nothing left/ stores answers into results string
			while (answer.next())
			{
				results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\n";
			}

			// close to prevent data leaks and unintentional updating.
			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		} 
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}

		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return results;

	}

	/**
	 * inserts the specified data into a row in a database
	 */
	public int insert()
	{
		int rowsAffected = 0;
		String query = "";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);

			// close to prevent data leaks and unintentional updating.
			insertStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}

		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
		return rowsAffected;
	}
}
