/**
 * Title:        PaymentSlipRed<p>
 * Description:  Payment Software<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.awt.Insets;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import ch.truesolutions.payit.model.*;

public class PaymentSlipRed extends PaymentSlip {

	private boolean hasIban = false;
	
	// command objects
	private JCheckBox salaryBox = new JCheckBox();

	// center objects
	// searches in the class path after esRed_image.gif
	private ImageIcon esRed_image =
		new ImageIcon(ClassLoader.getSystemResource("esRed_image.gif"));

	private IbanTextField ibanTextField = new IbanTextField(34);
	
	private JLabel jlabel1;
	private LimitedTextField jTextFieldToLine1 = new LimitedTextField(24);
	private LimitedTextField jTextFieldToLine2 = new LimitedTextField(24);
	private LimitedTextField jTextFieldToLine3 = new LimitedTextField(24);
	private LimitedTextField jTextFieldZipTo = new LimitedTextField(5);
	private LimitedTextField jTextFieldCityTo = new LimitedTextField(18);
	private LimitedTextField jTextFieldKtoBank = new LimitedTextField(27);
	private LimitedTextField jTextFieldClearing = new LimitedTextField(5);
	private LimitedTextField jTextFieldForLine1 = new LimitedTextField(24);
	private LimitedTextField jTextFieldForLine2 = new LimitedTextField(24);
	private LimitedTextField jTextFieldForLine3 = new LimitedTextField(24);
	private LimitedTextField jTextFieldZipFor = new LimitedTextField(5);
	private LimitedTextField jTextFieldCityFor = new LimitedTextField(18);
	private PostKtoTextField jTextFieldKtoPost = new PostKtoTextField(11);
	private AmountTextField jTextFieldAmount1 = new AmountTextField(8);
	private AmountTextField jTextFieldAmount2 = new AmountTextField(2);
	private LimitedTextArea jTextAreaReason = new LimitedTextArea(4, 24);

	private JButton zipCityToButton = new JButton("...");
	private JButton zipCityForButton = new JButton("...");
	private JButton bankAccountButton1 = new JButton("...");
	private JButton bankAccountButton2 = new JButton("...");
	private JButton postToButton1 = new JButton("...");
	private JButton postToButton2 = new JButton("...");
	private JButton clearingButton = new JButton("...");

	// attributes
	private MainFrame mainFrame;

	// code tables
	CodeTablePanel bankHistoryCodeTablePanel;
	CodeTablePanel postToHistoryCodeTablePanel;
	CodeTablePanel bkstamCodeTablePanel;
	CodeTablePanel zipCityForCodeTablePanel;
	CodeTablePanel zipCityToCodeTablePanel;

	public PaymentSlipRed(MainFrame mainFrame) {
		this(mainFrame,false);
	}
	
	public PaymentSlipRed(MainFrame mainFrame, boolean hasIban) {
		super(mainFrame);
		this.hasIban = hasIban;
		this.mainFrame = mainFrame;
//		this.payment = payment;
//		if (payment == null) {
//			this.payment = new Payment();
//			this.payment.setPaymentType(new Integer(1));
//		} else if (payment.getId() == null) {// it should become a new payment
//			isNew = false;
//		}
//
//		// in case of a new payment it should become pending
//		if (isNew) {
//			this.payment.setIsPending(new Boolean(true));
//			this.payment.setExecDate(getDefaultDate());
//		}

		// Command components
		salaryBox.setFont(Fonts.labelFont);
		salaryBox.setSize(100, 20);
		salaryBox.setLocation(10, 160);

		commandPanel.add(salaryBox);

		// Components of the layer pane in the center
		jlabel1 = new JLabel(esRed_image);
		jlabel1.setBounds(
			0,
			0,
			esRed_image.getIconWidth(),
			esRed_image.getIconHeight());

		jTextFieldToLine1.setSize(173, 20);
		jTextFieldToLine1.setLocation(5, 48);
		jTextFieldToLine1.setFont(Fonts.normalFont);
		jTextFieldToLine1.setNextFocusableComponent(jTextFieldToLine2);
		jTextFieldToLine1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postToHistoryCodeTablePanel.setCondition(
					"line1",
					jTextFieldToLine1.getText());
				postToHistoryCodeTablePanel.tryToShowCodeTable();
			}
		});

		postToButton1.setSize(16, 16);
		postToButton1.setLocation(180, 52);
		postToButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postToHistoryCodeTablePanel.noCondition();
				postToHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldToLine2.setSize(173, 20);
		jTextFieldToLine2.setLocation(5, 68);
		jTextFieldToLine2.setFont(Fonts.normalFont);
		jTextFieldToLine2.setNextFocusableComponent(jTextFieldToLine3);

		jTextFieldToLine3.setSize(173, 20);
		jTextFieldToLine3.setLocation(5, 88);
		jTextFieldToLine3.setFont(Fonts.normalFont);
		jTextFieldToLine3.setNextFocusableComponent(jTextFieldZipTo);

		jTextFieldZipTo.setSize(40, 20);
		jTextFieldZipTo.setLocation(5, 108);
		jTextFieldZipTo.setFont(Fonts.normalFont);
		jTextFieldZipTo.setNextFocusableComponent(jTextFieldCityTo);
		jTextFieldZipTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityToCodeTablePanel.setCondition(
					"zip",
					jTextFieldZipTo.getText());
				zipCityToCodeTablePanel.tryToShowCodeTable();
			}
		});

		jTextFieldCityTo.setSize(130, 20);
		jTextFieldCityTo.setLocation(48, 108);
		jTextFieldCityTo.setFont(Fonts.normalFont);
		if(hasIban) {
			jTextFieldCityTo.setNextFocusableComponent(ibanTextField);
		} else {
			jTextFieldCityTo.setNextFocusableComponent(jTextFieldKtoBank);
		}
		jTextFieldCityTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityToCodeTablePanel.setCondition(
					"city",
					jTextFieldCityTo.getText());
				zipCityToCodeTablePanel.tryToShowCodeTable();
			}
		});

		zipCityToButton.setSize(16, 16);
		zipCityToButton.setLocation(180, 112);
		zipCityToButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityToCodeTablePanel.noCondition();
				zipCityToCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldKtoBank.setSize(110, 20);
		jTextFieldKtoBank.setLocation(5, 151);
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
		
		ibanTextField.setSize(173,20);
		ibanTextField.setLocation(5,151);
		ibanTextField.setFont(Fonts.normalFont);
		ibanTextField.setNextFocusableComponent(jTextFieldForLine1);
		ibanTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.setCondition(
					"bankhistory.ktoBank",
					ibanTextField.getText());
				bankHistoryCodeTablePanel.tryToShowCodeTable();
			}
		});

		
		jTextFieldClearing.setSize(40, 20);
		jTextFieldClearing.setLocation(138, 151);
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

		bankAccountButton1.setSize(16, 16);
		bankAccountButton1.setLocation(117, 155);
		bankAccountButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.noCondition();
				bankHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		clearingButton.setSize(16, 16);
		clearingButton.setLocation(180, 155);
		clearingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bkstamCodeTablePanel.noCondition();
				bkstamCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldForLine1.setSize(173, 20);
		jTextFieldForLine1.setLocation(5, 173);
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
		bankAccountButton2.setLocation(180, 177);
		bankAccountButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bankHistoryCodeTablePanel.noCondition();
				bankHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldForLine2.setSize(173, 20);
		jTextFieldForLine2.setLocation(5, 193);
		jTextFieldForLine2.setFont(Fonts.normalFont);
		jTextFieldForLine2.setNextFocusableComponent(jTextFieldForLine3);

		jTextFieldForLine3.setSize(173, 20);
		jTextFieldForLine3.setLocation(5, 213);
		jTextFieldForLine3.setFont(Fonts.normalFont);
		jTextFieldForLine3.setNextFocusableComponent(jTextFieldZipFor);

		jTextFieldZipFor.setSize(40, 20);
		jTextFieldZipFor.setLocation(5, 233);
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
		jTextFieldCityFor.setLocation(48, 233);
		jTextFieldCityFor.setFont(Fonts.normalFont);
		jTextFieldCityFor.setNextFocusableComponent(jTextFieldKtoPost);
		jTextFieldCityFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityForCodeTablePanel.setCondition(
					"city",
					jTextFieldCityFor.getText());
				zipCityForCodeTablePanel.tryToShowCodeTable();
			}
		});

		zipCityForButton.setSize(16, 16);
		zipCityForButton.setLocation(180, 237);
		zipCityForButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityForCodeTablePanel.noCondition();
				zipCityForCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldKtoPost.setSize(78, 20);
		jTextFieldKtoPost.setLocation(100, 257);
		jTextFieldKtoPost.setFont(Fonts.normalFont);
		jTextFieldKtoPost.setIsBlue(false);
		jTextFieldKtoPost.setNextFocusableComponent(jTextFieldAmount1);
		jTextFieldKtoPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postToHistoryCodeTablePanel.setCondition(
					"ktopost",
					jTextFieldKtoPost.getText());
				postToHistoryCodeTablePanel.tryToShowCodeTable();
			}
		});

		postToButton2.setSize(16, 16);
		postToButton2.setLocation(180, 261);
		postToButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postToHistoryCodeTablePanel.noCondition();
				postToHistoryCodeTablePanel.tryToShowCodeTable(
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
		jTextAreaReason.setNextFocusableComponent(jTextFieldToLine1);

		// the labels from the PaymentSlip class
		jLabelLine1.setLocation(260, 203);
		jLabelLine2.setLocation(260, 223);
		jLabelLine3.setLocation(260, 243);
		jLabelZip.setLocation(260, 263);
		jLabelCity.setLocation(300, 263);

		//adding the components
		layeredPane.setLayout(null);
		layeredPane.add(jlabel1, new Integer(0));

		//layeredPane.add(jTextAreaTo, new Integer(1));
		layeredPane.add(jTextFieldToLine1, new Integer(1));
		layeredPane.add(postToButton1, new Integer(1));
		layeredPane.add(jTextFieldToLine2, new Integer(1));
		layeredPane.add(jTextFieldToLine3, new Integer(1));
		layeredPane.add(jTextFieldZipTo, new Integer(1));
		layeredPane.add(jTextFieldCityTo, new Integer(1));
		layeredPane.add(zipCityToButton, new Integer(1));

		layeredPane.add(ibanTextField, new Integer(1));
		layeredPane.add(jTextFieldKtoBank, new Integer(1));
		layeredPane.add(bankAccountButton1, new Integer(1));
		layeredPane.add(jTextFieldClearing, new Integer(1));
		layeredPane.add(clearingButton, new Integer(1));

		if(hasIban)
		{
			ibanTextField.setVisible(true);
			jTextFieldKtoBank.setVisible(false);
			bankAccountButton1.setVisible(false);
			jTextFieldClearing.setVisible(false);
			clearingButton.setVisible(false);
		} else 
		{
			ibanTextField.setVisible(false);
			jTextFieldKtoBank.setVisible(true);
			bankAccountButton1.setVisible(true);
			jTextFieldClearing.setVisible(true);
			clearingButton.setVisible(true);			
		}

		layeredPane.add(jTextFieldForLine1, new Integer(1));
		layeredPane.add(bankAccountButton2, new Integer(1));
		layeredPane.add(jTextFieldForLine2, new Integer(1));
		layeredPane.add(jTextFieldForLine3, new Integer(1));
		layeredPane.add(jTextFieldZipFor, new Integer(1));
		layeredPane.add(jTextFieldCityFor, new Integer(1));
		layeredPane.add(zipCityForButton, new Integer(1));

		layeredPane.add(jTextFieldKtoPost, new Integer(1));
		layeredPane.add(postToButton2, new Integer(1));
		layeredPane.add(jTextFieldAmount1, new Integer(1));
		layeredPane.add(jTextFieldAmount2, new Integer(1));

		layeredPane.add(jTextAreaReason, new Integer(1));

		layeredPane.add(jLabelLine1, new Integer(1));
		layeredPane.add(jLabelLine2, new Integer(1));
		layeredPane.add(jLabelLine3, new Integer(1));
		layeredPane.add(jLabelZip, new Integer(1));
		layeredPane.add(jLabelCity, new Integer(1));

		// initialize the codetables
		zipCityToCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setZipCityTo(record);
				}
			},
			MainModel.getInstance().getZipCityCodeTableModel(),
			new int[] { 40, 173 },
			240,
			200);

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
				}
			},
			MainModel.getInstance().getBankHistoryCodeTableModel(),
			new int[] { 100, 60, 300, 40, 100, 100 },
			713,
			200); 

		postToHistoryCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setPostToHistoryFields(record);
				}
			},
			MainModel.getInstance().getPostToHistoryCodeTableModel(),
			new int[] { 100, 300, 50, 100 },
			565,
			200); 

		// set the text values
		applyLanguageChange();
	}

	private void setZipCityTo(List al) {
		String zip = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String city = (al.get(1)) != null ? (String) (al.get(1)) : "";

		jTextFieldZipTo.setText(zip);
		jTextFieldCityTo.setText(city);
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
		String ktopost = (al.get(7)) != null ? (String) (al.get(7)) : "";
		String line1To = (al.get(8)) != null ? (String) (al.get(8)) : "";
		String line2To = (al.get(9)) != null ? (String) (al.get(9)) : "";
		String line3To = (al.get(10)) != null ? (String) (al.get(10)) : "";
		String zipTo = (al.get(11)) != null ? (String) (al.get(11)) : "";
		String cityTo = (al.get(12)) != null ? (String) (al.get(12)) : "";

		ibanTextField.setText(ktobank);
		jTextFieldKtoBank.setText(ktobank);
		jTextFieldClearing.setText(clearing);
		jTextFieldForLine1.setText(line1);
		jTextFieldForLine2.setText(line2);
		jTextFieldForLine3.setText(line3);
		jTextFieldZipFor.setText(zip);
		jTextFieldCityFor.setText(city);
		jTextFieldKtoPost.setText(ktopost);
		jTextFieldToLine1.setText(line1To);
		jTextFieldToLine2.setText(line2To);
		jTextFieldToLine3.setText(line3To);
		jTextFieldZipTo.setText(zipTo);
		jTextFieldCityTo.setText(cityTo);
	}

	private void setPostToHistoryFields(List al) {
		String ktopost = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String line1To = (al.get(1)) != null ? (String) (al.get(1)) : "";
		String line2To = (al.get(2)) != null ? (String) (al.get(2)) : "";
		String line3To = (al.get(3)) != null ? (String) (al.get(3)) : "";
		String zipTo = (al.get(4)) != null ? (String) (al.get(4)) : "";
		String cityTo = (al.get(5)) != null ? (String) (al.get(5)) : "";

		jTextFieldKtoPost.setText(ktopost);
		jTextFieldToLine1.setText(line1To);
		jTextFieldToLine2.setText(line2To);
		jTextFieldToLine3.setText(line3To);
		jTextFieldZipTo.setText(zipTo);
		jTextFieldCityTo.setText(cityTo);
	}

	private void setBkstamFields(List al) {
		String clearing = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String bkname = (al.get(1)) != null ? (String) (al.get(1)) : "";
		String adress = (al.get(2)) != null ? (String) (al.get(2)) : "";
		String zip = (al.get(3)) != null ? (String) (al.get(3)) : "";
		String city = (al.get(4)) != null ? (String) (al.get(4)) : "";
		String postaccount = (al.get(5)) != null ? (String) (al.get(5)) : "";
		int ind = postaccount.indexOf('*');
		if (ind != -1)
			postaccount = postaccount.substring(ind + 1).trim();

		jTextFieldClearing.setText(clearing);
		jTextFieldToLine1.setText(bkname);
		jTextFieldToLine2.setText(adress);
		jTextFieldZipTo.setText(zip);
		jTextFieldCityTo.setText(city);
		jTextFieldKtoPost.setText(postaccount);
		jTextFieldKtoPost.focusLost(new FocusEvent(jTextFieldKtoPost, 0));
	}

	public void fill(Payment p) {
		this.payment = p;		
		super.fill();

		// East Panel
		Boolean isSalary = payment.getIsSalary();
		if (isSalary != null)
			salaryBox.setSelected(isSalary.booleanValue());

		// Center Panel
		jTextFieldToLine1.setText(payment.getToLine1());
		jTextFieldToLine2.setText(payment.getToLine2());
		jTextFieldToLine3.setText(payment.getToLine3());

		jTextFieldZipTo.setText(payment.getToZip());

		jTextFieldCityTo.setText(payment.getToCity());

		if(hasIban)
		{
			jTextFieldKtoBank.setVisible(false);
			jTextFieldClearing.setVisible(false);
			ibanTextField.setText(payment.getForKtoBank());
			ibanTextField.setVisible(true);
			jTextFieldCityTo.setNextFocusableComponent(ibanTextField);
		} else {
			ibanTextField.setVisible(false);
			jTextFieldKtoBank.setText(payment.getForKtoBank());
			jTextFieldClearing.setText(payment.getForClearing());
			jTextFieldKtoBank.setVisible(true);
			jTextFieldClearing.setVisible(true);
			jTextFieldCityTo.setNextFocusableComponent(jTextFieldKtoBank);
		}
		
		jTextFieldForLine1.setText(payment.getForLine1());
		jTextFieldForLine2.setText(payment.getForLine2());
		jTextFieldForLine3.setText(payment.getForLine3());

		jTextFieldZipFor.setText(payment.getForZip());

		jTextFieldCityFor.setText(payment.getForCity());

		jTextFieldKtoPost.setText(payment.getToKtoPost());

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
		tmpPayment.setToLine1(jTextFieldToLine1.getText().trim());
		tmpPayment.setToLine2(jTextFieldToLine2.getText().trim());
		tmpPayment.setToLine3(jTextFieldToLine3.getText().trim());
		tmpPayment.setToZip(jTextFieldZipTo.getText().trim());
		tmpPayment.setToCity(jTextFieldCityTo.getText().trim());
		tmpPayment.setToKtoPost(jTextFieldKtoPost.getText().trim());
		if(hasIban) {
			tmpPayment.setForKtoBank(ibanTextField.getText().trim());
		}
		else 
		{
			tmpPayment.setForKtoBank(jTextFieldKtoBank.getText().trim());
			tmpPayment.setForClearing(jTextFieldClearing.getText().trim());
		}
		tmpPayment.setForLine1(jTextFieldForLine1.getText().trim());
		tmpPayment.setForLine2(jTextFieldForLine2.getText().trim());
		tmpPayment.setForLine3(jTextFieldForLine3.getText().trim());
		tmpPayment.setForZip(jTextFieldZipFor.getText().trim());
		tmpPayment.setForCity(jTextFieldCityFor.getText().trim());
		tmpPayment.setReason(jTextAreaReason.getText().trim());
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
			Config.getInstance().getText("esred.title"));
		salaryBox.setText(
			Config.getInstance().getText("checkbox.salary"));
	}
}