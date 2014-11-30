/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PaymentListSelectionModel extends DefaultListSelectionModel implements ListSelectionListener,MouseListener
{
	private static int lastSelected = -1;
	private JTable table;
	private MainFrame parent;
	
	public PaymentListSelectionModel(MainFrame parent,JTable table)
	{
		super();
		this.parent = parent;
		this.table = table;
		table.addMouseListener(this);
		addListSelectionListener(this);
                parent.calculateAndSetPaymentSumAndNumber();
    	}
 	
	public void valueChanged(boolean toggle) {

        	ListSelectionModel lsm =
            		table.getSelectionModel();//(ListSelectionModel)e.getSource();
        	
        	int selectedRowMin = lsm.getMinSelectionIndex();
           	int selectedRowMax = lsm.getMaxSelectionIndex();
           	
           	if(selectedRowMin == selectedRowMax) // only one row is selected
           	{
           		if(lastSelected != selectedRowMin) // was the same row selected twice ?
           			lastSelected = selectedRowMin;
           		else
           		{
           			if(toggle){
           				lsm.clearSelection();
           				lastSelected = -1;
           			}
           		}
           	} else
           		lastSelected = -1;
            	
                parent.calculateAndSetPaymentSumAndNumber();
   	}
   	
   	/**
 	* Method of the ListSelectionListener
 	*/
 	public void valueChanged(ListSelectionEvent e) {
 		// ignore additional messages
 		if (e.getValueIsAdjusting()) return;
 	}
   	
   	/**
 	* Method of the MouseListener
 	*/
   	public void mousePressed(MouseEvent e) {}
    	public void mouseEntered(MouseEvent e) {}
    	public void mouseExited(MouseEvent e) {}
    	
    	public void mouseReleased(MouseEvent e)
    	{	
    		if((e.getModifiers()& MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK)
    		{
    			if(e.getClickCount()==1)
    				valueChanged(true);
    		} else if((e.getModifiers()& MouseEvent.BUTTON2_MASK) == MouseEvent.BUTTON2_MASK)
    		{
    			if(e.getClickCount()==1)
    				valueChanged(false);
    		}
    	}
    	
    	public void mouseClicked(MouseEvent e)
    	{	
       		if(e.getModifiers() == MouseEvent.BUTTON1_MASK)
    		{
       			if(e.getClickCount()==2)
       			{
       				parent.showPaymentSlip(table,table.getSelectionModel().getMinSelectionIndex());
       			}
       		}
    	}
}
  