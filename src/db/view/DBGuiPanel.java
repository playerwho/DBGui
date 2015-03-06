package db.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import db.controller.DBGuiAppController;

public class DBGuiPanel extends JPanel
{
	private DBGuiAppController baseController;
	private SpringLayout baseLayout;
	private JButton tableButton;
	private JButton DBButton;
	private JButton describeButton;
	private JScrollPane displayPane;
	private JTextArea displayArea;
	
	/**
	 * the baseContoller from the appController is the same as baseController in the Panel, also calls 4 methods
	 * @param baseController
	 */
	public DBGuiPanel(DBGuiAppController baseController)
	{
		this.baseController = baseController;
		tableButton = new JButton("Show Table");
		DBButton = new JButton("Show Databases");
		displayArea = new JTextArea(10,30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		describeButton = new JButton("Describe dota2");
		
		setupPane();   
		setupPanel();
		setupLayout();
		setupListeners();
	}

	/**
	 * each button sends a differen query torwards the database
	 */
	private void setupListeners()
	{
		tableButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{
				String databaseAnswer = baseController.getDatabase().displayTables();
				displayArea.setText(databaseAnswer);
			}
		});
		DBButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{
//				String databaseAnswer = baseController.getDatabase().displayDatabases();
//				displayArea.setText(databaseAnswer);
				
				int answer = baseController.getDatabase().insert();
				displayArea.setText(displayArea.getText() + "\nRowsAffected: " + answer);
			}
		});
		describeButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{
				String databaseAnswer = baseController.getDatabase().describeTable();
				displayArea.setText(databaseAnswer);
			}
		});
	}

	/**
	 * sets up the layout of the window
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 50, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, tableButton, 250, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, tableButton, 150, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 50, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, DBButton, 250, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, DBButton, 300, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, describeButton, 250, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, describeButton, 25, SpringLayout.WEST, this);
		
	}
	/**
	 * sets pane color to blue
	 */
	private void setupPanel()
	{
		this.setBackground(Color.BLUE);
		this.setLayout(baseLayout);
		this.add(tableButton);
		this.add(DBButton);
		this.add(displayPane);
		this.add(describeButton);
	}

	/**
	 * sets up the Text Pane
	 */
	private void setupPane()
	{
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);
		displayArea.setEditable(false);
	}
	
	

}
