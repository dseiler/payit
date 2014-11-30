/**
 * Title:        PaymentSlipRed<p>
 * Description:  Payment Software<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import ch.truesolutions.payit.model.*;

public class PaymentSlipBank extends PaymentSlip {

	// command objects
	private JCheckBox salaryBox = new JCheckBox();

	// center objects
	private ImageIcon esBank_image =
		new ImageIcon(ClassLoader.getSystemResource("esBank_image.gif"));
	private JLabel jlabel1;
	private LimitedTextField jTextFieldKtoBank = new LimitedTextField(27);
	private LimitedTextField jTextFieldClearing = new LimitedTextField(5);
	// also used for Bank/Nr, BLZ Nr
	//private LimitedTextArea jTextAreaFor 	= new LimitedTextArea(3,24);
	private LimitedTextField jTextFieldForLine1 = new LimitedTextField(24);
	private LimitedTextField jTextFieldForLine2 = new LimitedTextField(24);
	private LimitedTextField jTextFieldForLine3 = new LimitedTextField(24);
	//private LimitedTextArea jTextAreaAdrBank = new LimitedTextArea(4, 30);
	// orig 24
	private LimitedTextField jTextFieldZipFor = new LimitedTextField(5);
	private LimitedTextField jTextFieldCityFor = new LimitedTextField(18);
	private AmountTextField jTextFieldAmount1 = new AmountTextField(8);
	private AmountTextField jTextFieldAmount2 = new AmountTextField(2);
	private LimitedTextArea jTextAreaReason = new LimitedTextArea(4, 24);

	private JLabel adrBankLine1 = new JLabel();
	private JLabel adrBankLine2 = new JLabel();
	private JLabel adrBankLine3 = new JLabel();
	//private JLabel ktoLabel = new JLabel();
	//private JLabel adrForLabel = new JLabel();
	//private JLabel reasonLabel = new JLabel();
	//private JLabel msgToBankLabel = new JLabel();
	//private JLabel adrFromBankLabel = new JLabel();
	//private JLabel clearingLabel = new JLabel();
	//private JLabel swiftLabel = new JLabel();
	//private JLabel currencyLabel = new JLabel();
	//private JLabel amountLabel = new JLabel();

	private JButton zipCityForButton = new JButton("...");
	private JButton clearingButton = new JButton("...");
	private JButton bankAccountButton1 = new JButton("...");
	private JButton bankAccountButton2 = new JButton("...");

	// attributes
	private MainFrame mainFrame;

	// code tables
	CodeTablePanel bankHistoryCodeTablePanel;
	CodeTablePanel bkstamCodeTablePanel;
	CodeTablePanel zipCityForCodeTablePanel;

	public PaymentSlipBank(MainFrame mainFrame) {
		super(mainFrame);
		this.mainFrame = mainFrame;

		// Command components
		salaryBox.setFont(Fonts.labelFont);
		salaryBox.setSize(100, 20);
		salaryBox.setLocation(10, 160);

		commandPanel.add(salaryBox);

		// Components of the layer pane in the center
		jlabel1 = new JLabel(esBank_image);
		jlabel1.setBounds(
			0,
			0,
			esBank_image.getIconWidth(),
			esBank_image.getIconHeight());

		jTextFieldKtoBank.setSize(110, 20);
		jTextFieldKtoBank.setLocation(5, 50);
		jTextFieldKtoBank.setFont(Fonts.normalFont);
		jTextFieldKtoBank.setNextFocusableComponent(jTextFieldClearing);
		jTextFieldKtoBank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.setCondition(
					"bankhistory.ktoBank",
					jTextFieldKtoBank.getText());
				bankHistoryCodeTablePanel.tryToShowCodeTable();
			}
		});

		bankAccountButton1.setSize(16, 16);
		bankAccountButton1.setLocation(117, 54);
		bankAccountButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.noCondition();
				bankHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldClearing.setSize(40, 20);
		jTextFieldClearing.setLocation(140, 50);
		jTextFieldClearing.setFont(Fonts.normalFont);
		jTextFieldClearing.setNextFocusableComponent(jTextFieldForLine1);
		jTextFieldClearing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bkstamCodeTablePanel.setCondition(
					"clearing",
					jTextFieldClearing.getText());
				bkstamCodeTablePanel.tryToShowCodeTable();
			}
		});
		jTextFieldClearing.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				adrBankLine1.setText("");
				adrBankLine2.setText("");
				adrBankLine3.setText("");
				bkstamCodeTablePanel.setCondition(
					"clearing",
					jTextFieldClearing.getText());
				bkstamCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_NEVER);
			}
		});

		clearingButton.setSize(16, 16);
		clearingButton.setLocation(183, 54);
		clearingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bkstamCodeTablePanel.noCondition();
				bkstamCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		adrBankLine1.setFont(Fonts.normalFont);
		adrBankLine1.setSize(175, 20);
		adrBankLine1.setLocation(5, 75);
		adrBankLine1.setOpaque(true);
		adrBankLine1.setBackground(new Color(242, 242, 249));

		adrBankLine2.setFont(Fonts.normalFont);
		adrBankLine2.setSize(175, 20);
		adrBankLine2.setLocation(5, 95);
		adrBankLine2.setOpaque(true);
		adrBankLine2.setBackground(new Color(242, 242, 249));

		adrBankLine3.setFont(Fonts.normalFont);
		adrBankLine3.setSize(175, 20);
		adrBankLine3.setLocation(5, 115);
		adrBankLine3.setOpaque(true);
		adrBankLine3.setBackground(new Color(242, 242, 249));

		jTextFieldForLine1.setSize(175, 20);
		jTextFieldForLine1.setLocation(5, 165);
		jTextFieldForLine1.setFont(Fonts.normalFont);
		jTextFieldForLine1.setNextFocusableComponent(jTextFieldForLine2);
		jTextFieldForLine1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.setCondition(
					"bankhistory.line1",
					jTextFieldForLine1.getText());
				bankHistoryCodeTablePanel.tryToShowCodeTable();
			}
		});

		bankAccountButton2.setSize(16, 16);
		bankAccountButton2.setLocation(183, 169);
		bankAccountButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.noCondition();
				bankHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldForLine2.setSize(175, 20);
		jTextFieldForLine2.setLocation(5, 185);
		jTextFieldForLine2.setFont(Fonts.normalFont);
		jTextFieldForLine2.setNextFocusableComponent(jTextFieldForLine3);

		jTextFieldForLine3.setSize(175, 20);
		jTextFieldForLine3.setLocation(5, 205);
		jTextFieldForLine3.setFont(Fonts.normalFont);
		jTextFieldForLine3.setNextFocusableComponent(jTextFieldZipFor);

		jTextFieldZipFor.setSize(40, 20);
		jTextFieldZipFor.setLocation(5, 225);
		jTextFieldZipFor.setFont(Fonts.normalFont);
		jTextFieldZipFor.setNextFocusableComponent(jTextFieldCityFor);
		jTextFieldZipFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityForCodeTablePanel.setCondition(
					"zip",
					jTextFieldZipFor.getText());
				zipCityForCodeTablePanel.tryToShowCodeTable();
			}
		});

		jTextFieldCityFor.setSize(130, 20);
		jTextFieldCityFor.setLocation(50, 225);
		jTextFieldCityFor.setFont(Fonts.normalFont);
		jTextFieldCityFor.setNextFocusableComponent(jTextFieldAmount1);
		jTextFieldCityFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityForCodeTablePanel.setCondition(
					"city",
					jTextFieldCityFor.getText());
				zipCityForCodeTablePanel.tryToShowCodeTable();
			}
		});

		zipCityForButton.setSize(16, 16);
		zipCityForButton.setLocation(183, 229);
		zipCityForButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityForCodeTablePanel.noCondition();
				zipCityForCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldAmount1.setSize(110, 25);
		jTextFieldAmount1.setLocation(17, 297);
		jTextFieldAmount1.setHorizontalAlignment(JTextField.RIGHT);
		jTextFieldAmount1.setFont(Fonts.bigFont);
		jTextFieldAmount1.setMargin(new Insets(2, 0, 2, 2));
		jTextFieldAmount1.setNextFocusableComponent(jTextFieldAmount2);

		jTextFieldAmount2.setSize(32, 25);
		jTextFieldAmount2.setLocation(146, 297);
		//jTextFieldAmount2.setHorizontalAlignment(JTextField.RIGHT);
		jTextFieldAmount2.setFont(Fonts.bigFont);
		jTextFieldAmount2.setMargin(new Insets(2, 4, 2, 0));
		jTextFieldAmount2.setNextFocusableComponent(jTextAreaReason);

		jTextAreaReason.setSize(173, 68);
		jTextAreaReason.setLocation(220, 48);
		jTextAreaReason.setFont(Fonts.normalFont);
		jTextAreaReason.setNextFocusableComponent(jTextFieldKtoBank);

		/*
		jTextAreaAdrBank.setSize(175, 70);
		jTextAreaAdrBank.setLocation(5, 60);
		jTextAreaAdrBank.setFont(Fonts.normalFont);
		jTextAreaAdrBank.setEditable(false);
		jTextAreaAdrBank.setNextFocusableComponent(jTextFieldKtoBank);
		*/

		// labels
		/*
		ktoLabel.setSize(100, 20);
		ktoLabel.setLocation(lb_widht, tb_widht);
		ktoLabel.setFont(labelFont);
		
		adrForLabel.setSize(150, 20);
		adrForLabel.setLocation(lb_widht, tb_widht + 45);
		adrForLabel.setFont(labelFont);
		
		reasonLabel.setSize(175, 20);
		reasonLabel.setLocation(lb_widht, tb_widht + 155);
		reasonLabel.setFont(labelFont);
		
		msgToBankLabel.setSize(175, 20);
		msgToBankLabel.setLocation(lb_widht + 275, tb_widht + 145);
		msgToBankLabel.setFont(labelFont);
		
		adrFromBankLabel.setSize(100, 20);
		adrFromBankLabel.setLocation(lb_widht + 275, tb_widht + 45);
		adrFromBankLabel.setFont(labelFont);
		
		clearingLabel.setSize(100, 20);
		clearingLabel.setLocation(lb_widht + 275, tb_widht);
		clearingLabel.setFont(labelFont);
		
		swiftLabel.setSize(100, 20);
		swiftLabel.setLocation(lb_widht + 350, tb_widht);
		swiftLabel.setFont(labelFont);
		
		currencyLabel.setSize(100, 20);
		currencyLabel.setLocation(lb_widht + 155, tb_widht + 265);
		currencyLabel.setFont(labelFont);
		
		amountLabel.setSize(100, 20);
		amountLabel.setLocation(lb_widht, tb_widht + 265);
		amountLabel.setFont(labelFont);
		*/
		// the labels from the PaymentSlip class
		jLabelLine1.setLocation(260, 203);
		jLabelLine2.setLocation(260, 223);
		jLabelLine3.setLocation(260, 243);
		jLabelZip.setLocation(260, 263);
		jLabelCity.setLocation(300, 263);

		//adding the components
		layeredPane.setLayout(null);
		layeredPane.add(jlabel1, new Integer(0));

		//layeredPane.add(ktoLabel, new Integer(1));
		layeredPane.add(jTextFieldKtoBank, new Integer(1));
		layeredPane.add(bankAccountButton1, new Integer(1));

		layeredPane.add(adrBankLine1, new Integer(1));
		layeredPane.add(adrBankLine2, new Integer(1));
		layeredPane.add(adrBankLine3, new Integer(1));

		//layeredPane.add(adrForLabel, new Integer(1));
		//layeredPane.add(jTextAreaFor, new Integer(1));
		layeredPane.add(jTextFieldForLine1, new Integer(1));
		layeredPane.add(bankAccountButton2, new Integer(1));
		layeredPane.add(jTextFieldForLine2, new Integer(1));
		layeredPane.add(jTextFieldForLine3, new Integer(1));
		layeredPane.add(jTextFieldZipFor, new Integer(1));
		layeredPane.add(jTextFieldCityFor, new Integer(1));
		layeredPane.add(zipCityForButton, new Integer(1));
		//layeredPane.add(reasonLabel, new Integer(1));
		layeredPane.add(jTextAreaReason, new Integer(1));
		//layeredPane.add(amountLabel, new Integer(1));
		layeredPane.add(jTextFieldAmount1, new Integer(1));
		layeredPane.add(jTextFieldAmount2, new Integer(1));
		layeredPane.add(jTextFieldClearing, new Integer(1));
		layeredPane.add(clearingButton, new Integer(1));

		layeredPane.add(jLabelLine1, new Integer(1));
		layeredPane.add(jLabelLine2, new Integer(1));
		layeredPane.add(jLabelLine3, new Integer(1));
		layeredPane.add(jLabelZip, new Integer(1));
		layeredPane.add(jLabelCity, new Integer(1));

		// initialize the codetables
		// initialize the codetables
		zipCityForCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setZipCityFor(record);
				}
			},
			MainModel.getInstance().getZipCityCodeTableModel(),
			new int[] { 40, 173 },
			240,200);

		bkstamCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setBkstamFields(record);
				}
			},
			MainModel.getInstance().getBkstamCodeTableModel(),
			new int[] { 60, 150, 150, 50, 120, 80 },
			640,
			200); 

		bankHistoryCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setBankHistoryFields(record);
					//	set the right bank adress if possible
					bkstamCodeTablePanel.setCondition(
							"clearing",
							jTextFieldClearing.getText());
					bkstamCodeTablePanel.tryToShowCodeTable(
							CodeTablePanel.SHOW_NEVER);
				}
			},
			MainModel.getInstance().getBankHistoryCodeTableModel(),
			new int[] { 100, 60, 300, 40, 100, 100 },
			713,
			200); 

		// set the text values
		applyLanguageChange();
	}

	private void setZipCityFor(List al) {
		String zip = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String city = (al.get(1)) != null ? (String) (al.get(1)) : "";

		jTextFieldZipFor.setText(zip);
		jTextFieldCityFor.setText(city);
	}

	private void setBankHistoryFields(List al) {
		String ktobank = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String clearing = (al.get(1)) != null ? (String) (al.get(1)) : "";
		String line1 = (al.get(2)) != null ? (String) (al.get(2)) : "";
		String line2 = (al.get(3)) != null ? (String) (al.get(3)) : "";
		String line3 = (al.get(4)) != null ? (String) (al.get(4)) : "";
		String zip = (al.get(5)) != null ? (String) (al.get(5)) : "";
		String city = (al.get(6)) != null ? (String) (al.get(6)) : "";

		jTextFieldKtoBank.setText(ktobank);
		jTextFieldClearing.setText(clearing);
		jTextFieldForLine1.setText(line1);
		jTextFieldForLine2.setText(line2);
		jTextFieldForLine3.setText(line3);
		jTextFieldZipFor.setText(zip);
		jTextFieldCityFor.setText(city);
	}

	private void setBkstamFields(List al) {
		String clearing = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String bkname = (al.get(1)) != null ? (String) (al.get(1)) : "";
		String adress = (al.get(2)) != null ? (String) (al.get(2)) : "";
		String zip = (al.get(3)) != null ? (String) (al.get(3)) : "";
		String city = (al.get(4)) != null ? (String) (al.get(4)) : "";

		jTextFieldClearing.setText(clearing);
		adrBankLine1.setText(bkname);
		adrBankLine2.setText(adress);
		adrBankLine3.setText(zip + " " + city);
	}

	public void fill(Payment p) {
		this.payment = p;		
		super.fill();

		// East Panel
		Boolean isSalary = payment.getIsSalary();
		if (isSalary != null)
			salaryBox.setSelected(isSalary.booleanValue());

		// Center Panel
		jTextFieldKtoBank.setText(payment.getForKtoBank());

		if (payment.getForClearing() != null) {
			jTextFieldClearing.setText(payment.getForClearing().toString());

		}

		bkstamCodeTablePanel.setCondition(
			"clearing",
			jTextFieldClearing.getText());
		bkstamCodeTablePanel.tryToShowCodeTable(CodeTablePanel.SHOW_NEVER);

		jTextFieldForLine1.setText(payment.getForLine1());
		jTextFieldForLine2.setText(payment.getForLine2());
		jTextFieldForLine3.setText(payment.getForLine3());

		jTextFieldZipFor.setText(payment.getForZip());

		jTextFieldCityFor.setText(payment.getForCity());

		jTextAreaReason.setText(payment.getReason());

		String[] amountStr = Utils.separateNumberByComma(payment.getAmount());
		jTextFieldAmount1.setText(amountStr[0]);
		jTextFieldAmount2.setText(amountStr[1]);
	}

	private Double getAmount() {
		// compose the amount from the two fields and convert it to a double
		try {
			return new Double(
				jTextFieldAmount1.getText()
					+ "."
					+ jTextFieldAmount2.getText());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	void handleSaveBtn() {
		Payment tmpPayment = new Payment();
		tmpPayment.setPaymentType(payment.getPaymentType());
		tmpPayment.setId(payment.getId());
		tmpPayment.setIsPending(payment.getIsPending());
		Double amount = getAmount();
		tmpPayment.setAmount(amount);		
		tmpPayment.setForKtoBank(jTextFieldKtoBank.getText().trim());
		tmpPayment.setForClearing(jTextFieldClearing.getText().trim());
		tmpPayment.setForLine1(jTextFieldForLine1.getText().trim());
		tmpPayment.setForLine2(jTextFieldForLine2.getText().trim());
		tmpPayment.setForLine3(jTextFieldForLine3.getText().trim());
		tmpPayment.setForZip(jTextFieldZipFor.getText().trim());
		tmpPayment.setForCity(jTextFieldCityFor.getText().trim());
		tmpPayment.setReason(jTextAreaReason.getText().trim());
		tmpPayment.setIsSalary(new Boolean(salaryBox.isSelected()));
		// this call to the super class fills in the general fields
		// validates and saves the payment
		super.handleSaveBtn(tmpPayment);
	}

	public void handleClearBtn() {
		// TODO
	}

	/**
	 * Functions of the ConfigChangeListenerInterface
	 */
	public void applyLanguageChange() {
		super.applyLanguageChange();
		this.setTitle(
			Config.getInstance().getText("esbank.title"));
		salaryBox.setText(
			Config.getInstance().getText("checkbox.salary"));
		/*
				ktoLabel.setText(
					ConfigObject.getResourceBundle().getString("label.kto"));
				adrForLabel.setText(
					ConfigObject.getResourceBundle().getString("label.adrfor"));
				reasonLabel.setText(
					ConfigObject.getResourceBundle().getString("label.reason"));
				msgToBankLabel.setText(
					ConfigObject.getResourceBundle().getString("label.msgbank"));
				adrFromBankLabel.setText(
					ConfigObject.getResourceBundle().getString("label.adrfrombank"));
				clearingLabel.setText(
					ConfigObject.getResourceBundle().getString("label.clearing"));
				swiftLabel.setText(
					ConfigObject.getResourceBundle().getString("label.swift"));
				amountLabel.setText(
					ConfigObject.getResourceBundle().getString("label.amount"));
				currencyLabel.setText(
					ConfigObject.getResourceBundle().getString("label.currency"));
		*/
	}
}