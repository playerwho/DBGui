package db.view;

import db.view.TableCellWrapRenderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import db.controller.DBGuiAppController;

@SuppressWarnings("serial")
public class DBGuiPanel extends JPanel
{
	/**
	 * declares app Controller as baseController
	 */
	private DBGuiAppController baseController;
	/**
	 * declares the Gui layout
	 */
	private SpringLayout baseLayout;
	/**
	 * declares Buttons
	 */
	private JButton tableButton;
	/**
	 * more buttons
	 */
	private JButton DBButton;
	/**
	 * even more buttons
	 */
	private JButton describeButton;
	
	/**
	 * MOREEEEEEE
	 */
	private JButton connectionButton;
	/**
	 * declares a Gui scroll pane
	 */
	private JScrollPane displayPane;
	/**
	 * declares a different Gui Scroll pane
	 */
	private JScrollPane textPane;
	/**
	 * declares a Gui text area
	 */
	private JTextArea textArea;
	/**
	 * declares a different Gui text area
	 */
	private JTextArea displayArea;
	/**
	 * declares a Gui table for use with the DB
	 */
	private JTable tableData;
	/**
	 * declares a password field to be used on the DB
	 */
	private JPasswordField passwordField;
	/**
	 * declares the cell renderer for the DB
	 */
	private TableCellWrapRenderer cellRenderer;
	 
	
	/**
	 * the baseContoller from the appController is the same as baseController in the Panel, also calls 4 methods and creates varius objects
	 * @param baseController
	 */
	public DBGuiPanel(DBGuiAppController baseController)
	{
		this.baseController = baseController;
		
		tableButton = new JButton("Show Table");
		DBButton = new JButton("Input Password");
		connectionButton = new JButton("Connect to DB");
		textArea = new JTextArea(10,30);
		textPane = new JScrollPane(textArea);
		displayArea = new JTextArea(10,30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		describeButton = new JButton("Describe dota2");
		passwordField = new JPasswordField(null, 20);
		cellRenderer = new TableCellWrapRenderer();
		
		
    	//setupTable();
		setupPane();   
		setupPanel();
		setupLayout();
		setupListeners();
	}

	/**
	 * sets up the JTable for database use
	 */
	private void setupTable()
	{
		//one D Array for column titles
		//2D Array for contents
		tableData = new JTable(new DefaultTableModel(baseController.getDatabase().tableInfo(), baseController.getDatabase().getMetaData()));
		displayPane = new JScrollPane(tableData);
		
		for(int spot = 0; spot < tableData.getColumnCount(); spot++)
		{
			tableData.getColumnModel().getColumn(spot).setCellRenderer(cellRenderer);
		}
		
		displayPane.repaint();
	
		
	}

	/**
	 * each button sends a differen query torwards the database or just does some cool things like send a password to the database.
	 */
	private void setupListeners()
	{
		tableButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{	
				textArea.setText(baseController.getDatabase().displayTables());
			}
		});
		DBButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{
				char[] passwordArray = passwordField.getPassword();
				String pword = "";
				
				for(int x = 0; x < passwordArray.length; x++)
				{
					pword += passwordArray[x];
				}
				
				baseController.getDatabase().connectionStringBuilder("localhost", "dota2", "root", pword);
				
			}
		});
		describeButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{
				textArea.setText(baseController.getDatabase().describeTable());
			}
		});
		connectionButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent click)
			{
				baseController.getDatabase().setupConnection();
				
				setupTable();
				displayPane.repaint();
				
				textArea.setText("");
				passwordField.setText("");
				
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
		baseLayout.putConstraint(SpringLayout.NORTH, DBButton, 250, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, DBButton, 300, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, describeButton, 250, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, describeButton, 25, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 500, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, textPane, 60, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, connectionButton, 50, SpringLayout.SOUTH, tableButton);
		baseLayout.putConstraint(SpringLayout.EAST, connectionButton, -550, SpringLayout.EAST, this);
	}
	/**
	 * sets up the window panel
	 */
	private void setupPanel()
	{
		this.setBackground(Color.BLUE);
		this.setLayout(baseLayout);
		this.add(tableButton);
		this.add(DBButton);
		this.add(connectionButton);
		this.add(displayPane);
		this.add(textPane);
		this.add(describeButton);
		this.add(passwordField);
		passwordField.setEchoChar('♋');
		passwordField.setFont(new Font("Serif", Font.BOLD, 40));
		textArea.setText("enter password above to connect");
		
	}

	/**
	 * sets up a Text Pane for use with databases.
	 */
	private void setupPane()
	{
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(true);
	}
}
