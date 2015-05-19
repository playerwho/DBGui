package db.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;

import db.controller.DBGuiAppController;
import db.model.QueryInfo;

public class DBGuiFrame extends JFrame
{
	/**
	 * connection to Panel
	 */
	private DBGuiPanel appPanel;
	
	/**
	 * declares the baseController from the AppController Class
	 */
	private DBGuiAppController baseController;
	
	/**
	 * defines appPanel and calls setupFrame
	 * @param baseController
	 */
	public DBGuiFrame(DBGuiAppController baseController)
	{
		this.baseController = baseController;
		appPanel = new DBGuiPanel(baseController);
		setupFrame();
		setupListeners();
	}

	/**
	 * sets the windowed frame to be a size of 1000,1000 , and a true visibilitiy
	 */
	private void setupFrame()
	{
		this.setSize(1000,1000);
		this.setContentPane(appPanel);
		this.setVisible(true);	
	}
	
	/**
	 * The required methods for the Window Listener Interface, When the window closes, call the save QueryTiming method.
	 */
	private void setupListeners()
	{
		this.addWindowListener(new WindowListener()
		{
			@Override
			public void windowOpened(WindowEvent arg0)
			{
				
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0)
			{
				
				baseController.saveQueryTimingInfo();
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0)
			{
				
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			
			
		});
	}
}
