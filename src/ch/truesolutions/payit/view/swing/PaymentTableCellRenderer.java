/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

class PaymentTableCellRenderer
    extends DefaultTableCellRenderer
{
    private boolean highliteDateInPast = false;
    
    public void setHighliteDateInPast(boolean hdip)
    {
        highliteDateInPast = hdip;
    }
    
    public Component getTableCellRendererComponent
        (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        if (value.getClass().getSuperclass() == java.lang.Number.class)
        {
        	// if it is a number we have to align it right
        	setHorizontalAlignment(JLabel.RIGHT);
        } else
        {
        	setHorizontalAlignment(JLabel.LEFT);
        }
        setOpaque(true);
        
        setEnabled(table == null || table.isEnabled()); // see question above

        java.util.Date d = (java.util.Date)(table.getModel().getValueAt(row, 1));
        if(d != null && highliteDateInPast){
	        java.util.Date now = Calendar.getInstance().getTime();
	        if(d.before(now))
	        {
	            	setForeground(Color.red);
	       	} else
	       	{
	            	setForeground(Color.black);
	        }
	    }
	    
	    // setting the background color alternating
	    if((row % 2)== 0){
	        setBackground(new Color(242, 242, 249));
	    } else {
	        setBackground(Color.white);
	    }
	    
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        return this;
    }
}
