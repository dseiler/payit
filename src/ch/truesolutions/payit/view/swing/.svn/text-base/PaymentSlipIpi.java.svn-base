package ch.truesolutions.payit.view.swing;

/**
 * @author dseiler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import ch.truesolutions.payit.model.*;

public class PaymentSlipIpi extends PaymentSlip {

	// center objects
	private JLabel mainLabel;
	private ImageIcon ipi_image =
		new ImageIcon(ClassLoader.getSystemResource("ipi_image.gif"));

	private IbanTextField ibanTextField = new IbanTextField(34);
	private LimitedTextField nameTextField = new LimitedTextField(35);
	private LimitedTextField swiftTextField = new LimitedTextField(35);
	private LimitedTextField reasonTextField = new LimitedTextField(35);
	private LimitedTextField amountTextField = new LimitedTextField(9);
	private JComboBox currenciesComboBox;
	private JComboBox costsComboBox;
	private JComboBox formComboBox;

	// attributes
	private MainFrame mainFrame;

	public PaymentSlipIpi(MainFrame mainFrame) {
		super(mainFrame);
		setSize(725, 399);
		this.mainFrame = mainFrame;
		// Components of the layer pane in the center
		mainLabel = new JLabel(ipi_image);
		mainLabel.setBounds(
			0,
			0,
			ipi_image.getIconWidth(),
			ipi_image.getIconHeight());

		ibanTextField.setSize(250, 20);
		ibanTextField.setLocation(128, 162);
		ibanTextField.setFont(Fonts.normalFont);
		ibanTextField.setNextFocusableComponent(nameTextField);

		nameTextField.setSize(250, 20);
		nameTextField.setLocation(128, 196);
		nameTextField.setFont(Fonts.normalFont);
		nameTextField.setNextFocusableComponent(swiftTextField);

		swiftTextField.setSize(250, 20);
		swiftTextField.setLocation(128, 226);
		swiftTextField.setFont(Fonts.normalFont);
		swiftTextField.setNextFocusableComponent(reasonTextField);

		reasonTextField.setSize(250, 20);
		reasonTextField.setLocation(128, 262);
		reasonTextField.setFont(Fonts.normalFont);
		reasonTextField.setNextFocusableComponent(amountTextField);

		amountTextField.setSize(96, 20);
		amountTextField.setLocation(582, 82);
		amountTextField.setFont(Fonts.normalFont);
		amountTextField.setHorizontalAlignment(JTextField.RIGHT);
		amountTextField.setNextFocusableComponent(ibanTextField);
		amountTextField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent fe) {
				formatAmountTextField();
			}
		});

		CodeTableModel ctm = MainModel.getInstance().getCurrencyCodeTableModel();
		currenciesComboBox = new JComboBox(ctm.getTableData().toArray());
		currenciesComboBox.setRenderer(
			new CurrencyComboRenderer(currenciesComboBox));
		currenciesComboBox.setSize(60, 20);
		currenciesComboBox.setLocation(494, 82);

		Vector costsData = new Vector();
		costsData.add("SHA"); // costs are shared	:2
		costsData.add("OUR"); // costs by me			:0
		costsData.add("BEN"); // costs by beneficary	:1
		costsComboBox = new JComboBox(costsData);
		costsComboBox.setRenderer(
			new CostsComboRenderer(costsComboBox));
		costsComboBox.setSize(60, 20);
		costsComboBox.setLocation(494, 118);

		Vector formData = new Vector();
		formData.add("00");
		formData.add("01");
		formData.add("02");
		formData.add("03");
		formData.add("04");
		formData.add("05");
		formComboBox = new JComboBox(formData);
		formComboBox.setSize(50, 20);
		formComboBox.setBackground(Color.white);
		formComboBox.setLocation(642, 252);

		ibanFrom.setSize(250, 20);
		ibanFrom.setFont(Fonts.normalFont);
		ibanFrom.setLocation(128, 46);
		// the labels from the PaymentSlip class
		jLabelLine1.setSize(250, 20);
		jLabelLine1.setFont(Fonts.normalFont);
		jLabelLine1.setLocation(128, 82);
		jLabelLine2.setSize(250, 20);
		jLabelLine2.setFont(Fonts.normalFont);
		jLabelLine2.setLocation(128, 102);

		// move the save and cancle button to the status panel
		commandPanel.setPreferredSize(new Dimension(0, 400));
		statusPanel.setPreferredSize(new Dimension(795, 35));
		saveBtn.setLocation(510, 5);
		cancelBtn.setLocation(615, 5);
		statusPanel.add(saveBtn);
		statusPanel.add(cancelBtn);

		//adding the components
		layeredPane.setLayout(null);
		layeredPane.add(mainLabel, new Integer(0));

		layeredPane.add(ibanTextField, new Integer(1));
		layeredPane.add(nameTextField, new Integer(1));
		layeredPane.add(swiftTextField, new Integer(1));
		layeredPane.add(reasonTextField, new Integer(1));
		layeredPane.add(amountTextField, new Integer(1));
		layeredPane.add(currenciesComboBox, new Integer(1));
		layeredPane.add(costsComboBox, new Integer(1));
		layeredPane.add(formComboBox, new Integer(1));

		layeredPane.add(ibanFrom, new Integer(1));
		layeredPane.add(jLabelLine1, new Integer(1));
		layeredPane.add(jLabelLine2, new Integer(1));

		applyLanguageChange();
	}

	public void fill(Payment p) {
		this.payment = p;		
		super.fill();

		// Center Panel
		nameTextField.setText(payment.getForLine1());
		ibanTextField.setText(payment.getForKtoBank());
		swiftTextField.setText(payment.getSwift());
		reasonTextField.setText(payment.getReason());

		if (payment.getAmount() != null) {
			double amount = payment.getAmount().doubleValue();
			String amountStr = Double.toString(amount);
			amountTextField.setText(amountStr);
		}

		// go through all the combo box currency items and select the right one
		for (int i = 0; i < currenciesComboBox.getItemCount(); i++) {
			if (((List) (currenciesComboBox.getItemAt(i)))
				.get(0)
				.equals(payment.getCurrency())) {
				currenciesComboBox.setSelectedIndex(i);
			}
		}

		// go through all the combo box costs items and select the right one
		for (int i = 0; i < costsComboBox.getItemCount(); i++) {
			if (((String) (costsComboBox.getItemAt(i)))
				.equals(payment.getCosts())) {
				costsComboBox.setSelectedIndex(i);
			}
		}

		// go through all the combo box form items and select the right one
		for (int i = 0; i < formComboBox.getItemCount(); i++) {
			if (((String) (formComboBox.getItemAt(i)))
				.equals(payment.getForm())) {
				formComboBox.setSelectedIndex(i);
			}
		}
	}

	void handleSaveBtn() {
		Payment tmpPayment = new Payment();
		tmpPayment.setPaymentType(payment.getPaymentType());
		tmpPayment.setId(payment.getId());
		tmpPayment.setIsPending(payment.getIsPending());
		Double amount = getAmount();
		tmpPayment.setAmount(amount);		
		tmpPayment.setForKtoBank(ibanTextField.getText());
		tmpPayment.setForLine1(nameTextField.getText());
		tmpPayment.setSwift(swiftTextField.getText());
		tmpPayment.setReason(reasonTextField.getText());
		tmpPayment.setCurrency((String)((List)currenciesComboBox.getSelectedItem()).get(0));
		tmpPayment.setCosts(costsComboBox.getSelectedItem().toString());
		tmpPayment.setForm(formComboBox.getSelectedItem().toString());
		// this call to the super class fills in the general fields
		// validates and saves the payment
		super.handleSaveBtn(tmpPayment);
	}

	public void handleClearBtn() {
		// TODO
	}

	Double getAmount() {
		String amountStr = amountTextField.getText();
		Double amount = null;
		try {
			amount = new Double(amountStr);
		} catch (NumberFormatException e) {
			//cat.warn(e);
		} catch (Exception e) {
			//cat.error(e);
		}
		return amount;
	}

	private void formatAmountTextField() {
		String amountStr = amountTextField.getText();
		// replace , with .
		amountStr = amountStr.replace(',', '.');
		amountTextField.setText(amountStr);
		Double amount = getAmount();
		if (amount != null) {
			BigDecimal roundedSum = new BigDecimal(amount.doubleValue());
			roundedSum = roundedSum.setScale(2, BigDecimal.ROUND_HALF_UP);
			amountTextField.setText(roundedSum.toString());
		}
	}

	/**
	 * Overriden Function of the ItemListenerInterface
	 */
	public void itemStateChanged(ItemEvent e) {
		if (jComboBoxKtoFrom.getItemCount() > 0) {
			ibanFrom.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem())).getIban());
			jLabelLine1.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem()))
					.getLine1());
			jLabelLine2.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem())).getLine2()
					+ ","
					+ ((UserAccount) (jComboBoxKtoFrom.getSelectedItem()))
						.getZip()
					+ " "
					+ ((UserAccount) (jComboBoxKtoFrom.getSelectedItem()))
						.getCity());
		}
	}

	/**
	 * Functions of the ConfigChangeListenerInterface
	 */
	public void applyLanguageChange() {
		super.applyLanguageChange();
		this.setTitle(
			Config.getInstance().getText("ipi.title"));
	}
}
