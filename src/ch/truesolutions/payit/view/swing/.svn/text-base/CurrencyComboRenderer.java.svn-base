package ch.truesolutions.payit.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author dseiler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CurrencyComboRenderer extends JLabel implements ListCellRenderer {

	private JComboBox comboBox;
	
	public CurrencyComboRenderer(JComboBox comboBox) {
		this.comboBox = comboBox;
		this.comboBox.setBackground(Color.white);
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}

	/**
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean isSelected,
		boolean cellHasFocus) {
		
		List currency = (List) value;
		setBackground(Color.white);
		setText((String)currency.get(0));
		comboBox.setToolTipText(
			currency.get(1)
				+ ","
				+ currency.get(2));
		return this;
	}

}
