package ch.truesolutions.payit.view.swing;

import javax.swing.JTextField;

import ch.truesolutions.payit.model.Utils;

import java.awt.event.FocusEvent;

public class PostKtoTextField extends LimitedTextField
{
	private boolean isBlue = true;
	
	public PostKtoTextField(int maxSigns)
	{
		super(maxSigns);
	}
	
	protected boolean validateChar(char c)
	{
		if (Character.isDigit(c) || c == '-')
			return true;
		else
			return false;
	}
	
	public void focusLost(FocusEvent e)
	{
		JTextField source = (JTextField)(e.getComponent());
		String pcnr = source.getText();
		/*
		int i = pcnr.indexOf('-');
		if(i == -1 && isBlue) // only for the blue slip
		{
			String newPcnr = Utils.fillStringBuffer(pcnr,5,'0',false).toString();
			source.setText(newPcnr);
		} else
		{
			String newPcnr = Utils.deleteChars(pcnr,'-');
			if(newPcnr.length() > 3)
			{
				String a = newPcnr.substring(0,2);
				String b = newPcnr.substring(2,newPcnr.length()-1);
				String c = newPcnr.substring(newPcnr.length()-1);
				//System.out.println("a="+a+",b="+b+",c="+c);
				b = Utils.fillStringBuffer(b,6,'0',false).toString();
				newPcnr = a + "-" + b + "-" + c;
				source.setText(newPcnr);
			}
		}
		*/
		String newPcnr = Utils.formatPostKtoNr(pcnr,isBlue);
		if(newPcnr != null)
		{
			source.setText(newPcnr);
		}
	}
	/*
	protected void handleEnterKey()
	{
		//System.out.println("Query:SELECT city FROM ZipCodes WHERE zip="+getText());
		String zip = getText();
		if(zip.equals(""))
			dest.setText("");
		else
		{
			ArrayList al = DatabaseObject.executeQuery("SELECT city FROM ZipCodes WHERE zip="+getText(),1);
			if(al != null && al.size() > 0)
			{
				if(dest != null)
					dest.setText((String)(((ArrayList)(al.get(0))).get(0)));
			} else
				dest.setText("");
		}
		transferFocus();
	}
	*/
	
	public void setIsBlue(boolean isBlue)
	{
		this.isBlue = isBlue;
	}
}