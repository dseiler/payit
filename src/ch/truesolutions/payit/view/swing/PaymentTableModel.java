/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import ch.truesolutions.payit.model.*;

public class PaymentTableModel
	extends AbstractTableModel implements ConfigChangeListener {
	private List tableData;
	private JTable table;
	private PaymentsModel model;

	private static ImageIcon redIcon =
		new ImageIcon(
			ClassLoader.getSystemResource("esRed_icon_table.gif"),
			"A");
	private static ImageIcon blueIcon =
		new ImageIcon(
			ClassLoader.getSystemResource("esBlue_icon_table.gif"),
			"B");
	private static ImageIcon orangeIcon =
		new ImageIcon(
			ClassLoader.getSystemResource("esOrange_icon_table.gif"),
			"C");
	private static ImageIcon bankIcon =
		new ImageIcon(
			ClassLoader.getSystemResource("esBank_icon_table.gif"),
			"D");
	private static ImageIcon ipiIcon =
		new ImageIcon(ClassLoader.getSystemResource("ipi_icon_table.gif"), "E");

	private static ImageIcon redIbanIcon =
		new ImageIcon(ClassLoader.getSystemResource("esRedIBAN_icon_table.gif"), "F");

	public PaymentTableModel(PaymentsModel model, JTable table) {
		super();
		this.model = model;
		this.table = table;
		// register this frame at the Config
		Config.getInstance().addConfigChangeListener(this);
		refresh();
	}

	public void tableRowUpdated() {
		refresh();
		fireTableRowsUpdated(0, getRowCount());
	}

	public void tableRowDeleted() {
		refresh();
		fireTableRowsDeleted(0, getRowCount());
	}

	public void tableRowInserted() {
		refresh();
		fireTableRowsInserted(0, getRowCount());
	}

	public void refresh() {
		this.tableData = model.getPayments();
	}

	public Payment getPayment(int row) {
		return (Payment)tableData.get(row);
	}

	public int getRowCount() {
		if (tableData != null) {
			return tableData.size();
		} else {
			return 0;
		}
	}
	public int getColumnCount() {
		return 7;
	}
	public Object getValueAt(int row, int column) {
		String kto;
		Payment p = (Payment) (tableData.get(row));
		switch (column) {
			case 0 : // type of payment
				int type = p.getPaymentType().intValue();
				if (type == Config.PAYMENT_TYPE_RED)
					return redIcon;
				else if (type == Config.PAYMENT_TYPE_BLUE)
					return blueIcon;
				else if (type == Config.PAYMENT_TYPE_BANKCH)
					return bankIcon;
				else if (type == Config.PAYMENT_TYPE_ORANGE)
					return orangeIcon;
				else if (type == Config.PAYMENT_TYPE_IPI)
					return ipiIcon;
				else if (type == Config.PAYMENT_TYPE_RED_IBAN)
					return redIbanIcon;
				else
					return redIcon;
			case 1 : // execution date
				java.util.Date d = Config.getInstance().getDateFromDisplayDateString(p.getExecDate());
				if (d == null)
					return null;
				else {
					return new MyDate(d.getTime());
				}
			case 2 : // amount
				
				if (p.getAmount() == null)
					return ""; //o = new Double(0);
				BigDecimal amount =
					new BigDecimal(p.getAmount().doubleValue());
				amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
				return amount;
			case 3 : // from kto
				return p.getFromKto();
			case 4 : // to kto
				// test if there is a bank account
				kto = p.getForKtoBank();
				if (kto == null || kto.equals(""))
					kto = p.getToKtoPost();
				return kto;
			case 5 : // to name
				String to = "";
				if (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) {
					to = p.getToDisplayString();
				} else {

					// test if there is an entry in the for fields
					String forLine1 = p.getForLine1();
					String forLine2 = p.getForLine2();
					String forLine3 = p.getForLine3();
					String tmp;
					if ((forLine1 == null || forLine1.equals(""))
						&& (forLine2 == null || forLine2.equals(""))
						&& (forLine3 == null || forLine3.equals(""))) {
						tmp = p.getToLine1();
						if (tmp != null && !tmp.equals("")) {
							to = to + tmp;
						}
						tmp = p.getToLine2();
						if (tmp != null && !tmp.equals("")) {
							to = to + "," + tmp;
						}
						tmp = p.getToLine3();
						if (tmp != null && !tmp.equals("")) {
							to = to + "," + tmp;
						}
						tmp = p.getToZip();
						if (tmp != null && !tmp.equals("")) {
							to = to + "," + tmp;
						}
						tmp = p.getToCity();
						if (tmp != null && !tmp.equals("")) {
							to = to + " " + tmp;
						}
					} else {
						tmp = p.getForLine1();
						if (tmp != null && !tmp.equals("")) {
							to = to + tmp;
						}
						tmp = p.getForLine2();
						if (tmp != null && !tmp.equals("")) {
							to = to + "," + tmp;
						}
						tmp = p.getForLine3();
						if (tmp != null && !tmp.equals("")) {
							to = to + "," + tmp;
						}
						tmp = p.getForZip();
						if (tmp != null && !tmp.equals("")) {
							to = to + "," + tmp;
						}
						tmp = p.getForCity();
						if (tmp != null && !tmp.equals("")) {
							to = to + " " + tmp;
						}
					}
				}
				return to;
			case 6 : // comment
				return p.getComment();
			default :
				return new Integer(row * column);
		}
	}

	public String getColumnName(int column) {
		switch (column) {
			case 0 :
				return Config.getInstance().getText("table.type");
					case 1 :
				return Config.getInstance().getText(
					"table.execdate");
					case 2 :
				return Config.getInstance().getText(
					"table.amount");
					case 3 :
				return Config.getInstance().getText(
					"table.ktofrom");
					case 4 :
				return Config.getInstance().getText(
					"table.ktoto");
					case 5 :
				return Config.getInstance().getText("table.to");
					case 6 :
				return Config.getInstance().getText(
					"table.comment");
					default :
				return ""; }
	}

	public Class getColumnClass(int column) {
		switch (column) {
			case 0 :
				return ImageIcon.class; case 1 :
				return java.util.Date.class; case 2 :
				return Number.class; case 3 :
			case 4 :
			case 5 :
			case 6 :
				return String.class; default :
				return Object.class; }
	}

	public void applyLanguageChange() {
		// store the width of the table columsn
		int col0 =
		table.getColumnModel().getColumn(0).getPreferredWidth();
				int col1 = table.getColumnModel().getColumn(1).getPreferredWidth();
				int col2 = table.getColumnModel().getColumn(2).getPreferredWidth();
				int col3 = table.getColumnModel().getColumn(3).getPreferredWidth();
				int col4 = table.getColumnModel().getColumn(4).getPreferredWidth();
				int col5 = table.getColumnModel().getColumn(5).getPreferredWidth();
				int col6 = table.getColumnModel().getColumn(6).getPreferredWidth();
				fireTableChanged(
					new TableModelEvent(this, TableModelEvent.HEADER_ROW));
		// restore the width of the table columns
		table
			.getColumnModel()
			.getColumn(0)
			.setPreferredWidth(col0);
				table.getColumnModel().getColumn(1).setPreferredWidth(col1);
				table.getColumnModel().getColumn(2).setPreferredWidth(col2);
				table.getColumnModel().getColumn(3).setPreferredWidth(col3);
				table.getColumnModel().getColumn(4).setPreferredWidth(col4);
				table.getColumnModel().getColumn(5).setPreferredWidth(col5);
				table.getColumnModel().getColumn(6).setPreferredWidth(col6);
	}

	public void setLookAndFeel(String lnfName) {
	}

	class MyDate extends java.util.Date {
		public MyDate(long time) {
			super(time); }

		public String toString() {
			return Config.getInstance().getDisplayDateString(this); }
	}
}
