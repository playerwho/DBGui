package db.view;

import java.awt.Component;
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
	private DBGuiAppController baseController;
	private JButton submitButton;
	
	/**
	 * connects chatbot panel to base controller
	 * @param baseController on the panel is the same as baseController on the appController
	 */
	public DynamicPanel(DBGuiAppController baseController, String table)
	{
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		submitButton = new JButton();
		
		setupPanel();
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
		for(int c = 0; c < baseController.getDatabase().getDBColumnNames(table).length; c++)
		{
			JLabel dynamicLable = new JLabel(baseController.getDatabase().getDBColumnNames(table)[c]);
			JTextField textField = new JTextField(20);
			this.add(dynamicLable);
			this.add(textField);
			
			baseLayout.putConstraint(SpringLayout.NORTH, dynamicLable, startOff, SpringLayout.NORTH, this);
			baseLayout.putConstraint(SpringLayout.NORTH, textField, startOff, SpringLayout.NORTH, this);
			baseLayout.putConstraint(SpringLayout.EAST, textField, 60, SpringLayout.EAST, dynamicLable);
			
			startOff += 50;
		}
	}
	
	private void setupListeners()
	{
		ArrayList<JTextField> myTextFields = new ArrayList<JTextField>();
		for(Component current : this.getComponents())
		{
			if(current instanceof JTextField)
			{
				myTextFields.add((JTextField)current);
			}
		}
	}
}
