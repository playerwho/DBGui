package db.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import db.controller.DBGuiAppController;

/**
 * Dynamic database Pane builder
 * @author awid5247
 *
 */
public class DynamicPanel extends JPanel
{
	/**
	 * changes layout of the panel
	 */
	private SpringLayout baseLayout;
	
	/**
	 * The controller from AppController Class
	 */
	private DBGuiAppController baseController;
	
	/**
	 * Buttons
	 */
	private JButton submitButton;
	/**
	 *  A list for componants
	 */
	private ArrayList<JTextField> inputFieldList;
	/**
	 * a Table!
	 */
	private String table;
	
	/**
	 * connects chatbot panel to base controller
	 * @param baseController on the panel is the same as baseController on the appController
	 */
	public DynamicPanel(DBGuiAppController baseController, String table)
	{
		this.baseController = baseController;
		this.table = table;
		baseLayout = new SpringLayout();
		submitButton = new JButton();
		inputFieldList = new ArrayList<JTextField>();
		
		setupPanel(table);
		setupListeners();	
	}
	
	/**
	 * sets up the panel view
	 */
	private void setupPanel(String table)
	{	
		this.setLayout(baseLayout);
		this.add(submitButton);
		int startOff = 20;
		String [] columns = baseController.getDatabase().getDBColumnNames(table);
		
		for(int c = 0; c < columns.length; c++)
		{
			if (!columns[c].equalsIgnoreCase("id"));
			{
				JTextField textField = new JTextField(20);
				JLabel dynamicLable = new JLabel(columns[c] + " entry: ");
			
			
				this.add(dynamicLable);
				this.add(textField);
			
				dynamicLable.setName(columns[c] + "label");
				textField.setName(columns[c] + "field");
			
				inputFieldList.add(textField);
			
				baseLayout.putConstraint(SpringLayout.NORTH, dynamicLable, startOff, SpringLayout.NORTH, this);
				baseLayout.putConstraint(SpringLayout.NORTH, textField, startOff, SpringLayout.NORTH, this);
				baseLayout.putConstraint(SpringLayout.EAST, textField, 60, SpringLayout.EAST, dynamicLable);
			
				startOff += 50;
			}
		}
	}
	
	/**
	 * values for a database
	 * @return value
	 */
	private String getValueList()
	{
		String value = "(";
		
		for(int spot = 0; spot < inputFieldList.size(); spot++)
		{
			String temp = inputFieldList.get(spot).getText();
			
			if(spot == inputFieldList.size()-1)
			{
				value += "'" + temp + "')";
			}
			else
			{
				value += "'" + temp + "', ";
			}
			
		}
		
		return value;
	}
	
	/**
	 * fields for a database
	 * @return field
	 */
	private String getFieldList()
	{
		String field = "(";
		
		//needs format (`field`, `field`, `field`, .......)
		for(int spot = 0; spot < inputFieldList.size(); spot++)
		{
			String temp = inputFieldList.get(spot).getName();
			int cut = temp.indexOf("field");
			temp = temp.substring(0,cut);
			if(spot == inputFieldList.size()-1)
			{
				field += "`" + temp + "`)";
			}
			else
			{
				field += "`" + temp + "`, ";
			}
			
		}
		
		return field;
	}
	
	/**
	 * buttons!!
	 */
	private void setupListeners()
	{
		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String myQuery = "INSERT INTO " + table + " " + getFieldList() + " VALUES " + getValueList() + ";";
				baseController.getDatabase().submitQuery(myQuery);
			}
		
		});
	}
}
