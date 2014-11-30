package ch.truesolutions.payit.view.swing;

import javax.swing.JTextField;

import ch.truesolutions.payit.model.Utils;

import java.awt.event.FocusEvent;

public class AmountTextField extends LimitedTextField
{
	int maxSigns = 0;
	public AmountTextField(int maxSigns)
	{
		super(maxSigns);
		this.maxSigns = maxSigns;
	}
	
	protected boolean validateChar(char c)
	{
		if (Character.isDigit(c))
			return true;
		else
			return false;
	}
	
	public void focusLost(FocusEvent e)
	{
		// we treat this event only if this is the after coma amount field
		if(maxSigns < 3)
		{
		JTextField source = (JTextField)(e.getComponent());
		String amountStr = source.getText();
		amountStr = Utils.fillStringBuffer(amountStr, 2, '0',true).toString();
		Integer amount;
		if(!amountStr.equals(""))
		{
			amount = new Integer(amountStr);
			int a = amount.intValue();
			int remainder = a % 5;
			if(remainder != 0)
			{
				if(remainder >= 2.5)
					a = a - remainder + 5;
				else
					a = a - remainder;
					
				if(a>99) a = 95;
				amountStr = ""+a;
				amountStr = Utils.fillStringBuffer(amountStr, 2, '0',false).toString();
			}
			source.setText(amountStr);
		}
		}
	}
}