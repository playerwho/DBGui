package db.controller;

import javax.swing.JOptionPane;

import db.model.QueryInfo;
import db.view.DBGuiFrame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

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
		loadQueryTimingInfo();
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

	public void loadQueryTimingInfo()
	{
		File saveFile = new File("/Users/awid5247/Documents/Database/Saved.txt");
		try
		{	
			Scanner readFileScanner;
			if(saveFile.exists())
			{
				timingInfoList.clear();
				readFileScanner = new Scanner(saveFile);
				while(readFileScanner.hasNext())
				{
					String tempQuery = readFileScanner.nextLine();
					readFileScanner.next();
					long tempTime = readFileScanner.nextLong();
					timingInfoList.add(new QueryInfo(tempQuery, tempTime));
				}
				readFileScanner.close();
			}
			
		}
		catch(IOException current)
		{
			this.getDatabase().displayErrors(current);
		}
	}
	
	public void saveQueryTimingInfo()
	{
		String fileName = "/Users/awid5247/Documents/DatabaseText/Saved.txt";
		ArrayList<QueryInfo> output = getTimingInfoList();
		PrintWriter outputWriter;
		
			try
			{
				outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
				for(QueryInfo currentInfo: output)
				{
					 outputWriter.write(currentInfo.getQuery() + " ");
					 outputWriter.write(currentInfo.getQueryTime() + " milliseconds, " + "\n");
				}
				outputWriter.close();
			}
			catch(FileNotFoundException noExistingFile)
			{
				JOptionPane.showMessageDialog(appFrame, "There is no file here");
				JOptionPane.showMessageDialog(appFrame, noExistingFile.getMessage());
			}
			catch(IOException inputOutputError)
			{
				JOptionPane.showMessageDialog(appFrame, "There is no file here");
				JOptionPane.showMessageDialog(appFrame, inputOutputError.getMessage());
			}
		}	
}

