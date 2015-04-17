package db.view;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import db.controller.DBGuiAppController;

public class DynamicPanel extends JPanel
{
	/**
	 * changes layout of the panel
	 */
	private SpringLayout baseLayout;
	private DBGuiAppController baseController;
	
	/**
	 * connects chatbot panel to base controller
	 * @param baseController on the panel is the same as baseController on the appController
	 */
	public DynamicPanel(DBGuiAppController baseController)
	{
		baseLayout = new SpringLayout();
		this.baseController = baseController;
		setupPanel();
		setupListeners();
		
	}
	
	/**
	 * sets up the panel view
	 */
	private void setupPanel()
	{	
		this.setLayout(baseLayout);
		int startOff = 20;
		for(int c = 0; c < baseController.getDatabase().getMetaData().length; c++)
		{
			JLabel test = new JLabel("" + c + " label");
			JTextField textField = new JTextField(10);
			this.add(test);
			this.add(textField);
			
			baseLayout.putConstraint(SpringLayout.NORTH, test, startOff, SpringLayout.NORTH, this);
			startOff += 20;
			baseLayout.putConstraint(SpringLayout.NORTH, textField, startOff, SpringLayout.NORTH, this);
			
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
