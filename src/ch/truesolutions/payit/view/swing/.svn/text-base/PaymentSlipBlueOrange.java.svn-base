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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import ch.truesolutions.payit.model.*;

public class PaymentSlipBlueOrange extends PaymentSlip {

	// center objects
	private ImageIcon esBlue_image =
		new ImageIcon(ClassLoader.getSystemResource("esBlue_image.gif"));
	private ImageIcon esOrange_image =
		new ImageIcon(ClassLoader.getSystemResource("esOrange_image.gif"));
	private JLabel jlabel1;
	//private LimitedTextArea jTextAreaTo 	= new LimitedTextArea(3,20);
	private LimitedTextField jTextFieldToLine1 = new LimitedTextField(20);
	private LimitedTextField jTextFieldToLine2 = new LimitedTextField(20);
	private LimitedTextField jTextFieldToLine3 = new LimitedTextField(20);
	private LimitedTextField jTextFieldZipTo = new LimitedTextField(5);
	private LimitedTextField jTextFieldCityTo = new LimitedTextField(15);
	//private LimitedTextArea jTextAreaFor 	= new LimitedTextArea(3,20);
	private LimitedTextField jTextFieldForLine1 = new LimitedTextField(20);
	private LimitedTextField jTextFieldForLine2 = new LimitedTextField(20);
	private LimitedTextField jTextFieldForLine3 = new LimitedTextField(20);
	private LimitedTextField jTextFieldZipFor = new LimitedTextField(5);
	private LimitedTextField jTextFieldCityFor = new LimitedTextField(15);
	private PostKtoTextField jTextFieldKtoPost = new PostKtoTextField(11);
	private AmountTextField jTextFieldAmount1 = new AmountTextField(8);
	private AmountTextField jTextFieldAmount2 = new AmountTextField(2);
	private LimitedTextField jTextFieldRefNr = new LimitedTextField(32);

	private JButton postToButton1 = new JButton("...");
	private JButton postToButton2 = new JButton("...");
	private JButton zipCityToButton = new JButton("...");
	private JButton postForButton = new JButton("...");
	private JButton zipCityForButton = new JButton("...");

	// attributes
	private boolean isOrange = false;
	private MainFrame mainFrame;

	// code tables
	CodeTablePanel zipCityForCodeTablePanel;
	CodeTablePanel zipCityToCodeTablePanel;
	CodeTablePanel postToHistoryCodeTablePanel;
	CodeTablePanel postForHistoryCodeTablePanel;

	public PaymentSlipBlueOrange(
		MainFrame mainFrame,
		boolean isOrange) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		this.isOrange = isOrange;

		// Components of the layer pane in the center
		if (isOrange) {
			jlabel1 = new JLabel(esOrange_image);
			jlabel1.setBounds(
				0,
				0,
				esOrange_image.getIconWidth(),
				esOrange_image.getIconHeight());
		} else {
			jlabel1 = new JLabel(esBlue_image);
			jlabel1.setBounds(
				0,
				0,
				esBlue_image.getIconWidth(),
				esBlue_image.getIconHeight());
		}

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
		jTextFieldCityTo.setNextFocusableComponent(jTextFieldForLine1);
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

		jTextFieldForLine1.setSize(173, 20);
		jTextFieldForLine1.setLocation(5, 151);
		jTextFieldForLine1.setFont(Fonts.normalFont);
		jTextFieldForLine1.setNextFocusableComponent(jTextFieldForLine2);
		jTextFieldForLine1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postForHistoryCodeTablePanel.setCondition(
					"postforhistory.line1",
					jTextFieldForLine1.getText());
				postForHistoryCodeTablePanel.tryToShowCodeTable();
			}
		});

		postForButton.setSize(16, 16);
		postForButton.setLocation(180, 155);
		postForButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postForHistoryCodeTablePanel.noCondition();
				postForHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldForLine2.setSize(173, 20);
		jTextFieldForLine2.setLocation(5, 171);
		jTextFieldForLine2.setFont(Fonts.normalFont);
		jTextFieldForLine2.setNextFocusableComponent(jTextFieldForLine3);

		jTextFieldForLine3.setSize(173, 20);
		jTextFieldForLine3.setLocation(5, 191);
		jTextFieldForLine3.setFont(Fonts.normalFont);
		jTextFieldForLine3.setNextFocusableComponent(jTextFieldZipFor);

		jTextFieldZipFor.setSize(40, 20);
		jTextFieldZipFor.setLocation(5, 211);
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
		jTextFieldCityFor.setLocation(48, 211);
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
		zipCityForButton.setLocation(180, 215);
		zipCityForButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityForCodeTablePanel.noCondition();
				zipCityForCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldKtoPost.setSize(78, 20);
		jTextFieldKtoPost.setLocation(100, 235);
		jTextFieldKtoPost.setFont(Fonts.normalFont);
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
		postToButton2.setLocation(180, 239);
		postToButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postToHistoryCodeTablePanel.noCondition();
				postToHistoryCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		jTextFieldAmount1.setSize(110, 25);
		jTextFieldAmount1.setLocation(17, 277);
		jTextFieldAmount1.setHorizontalAlignment(JTextField.RIGHT);
		jTextFieldAmount1.setFont(Fonts.bigFont);
		jTextFieldAmount1.setMargin(new Insets(2, 0, 2, 2));
		jTextFieldAmount1.setNextFocusableComponent(jTextFieldAmount2);

		jTextFieldAmount2.setSize(32, 25);
		jTextFieldAmount2.setLocation(137, 277);
		jTextFieldAmount2.setHorizontalAlignment(JTextField.LEFT);
		jTextFieldAmount2.setFont(Fonts.bigFont);
		jTextFieldAmount2.setMargin(new Insets(2, 4, 2, 0));
		jTextFieldAmount2.setNextFocusableComponent(jTextFieldRefNr);

		jTextFieldRefNr.setSize(210, 20);
		jTextFieldRefNr.setLocation(248, 176);
		jTextFieldRefNr.setFont(Fonts.normalFont);
		jTextFieldRefNr.setNextFocusableComponent(jTextFieldToLine1);

		// the labels from the PaymentSlip class
		jLabelLine1.setLocation(260, 223);
		jLabelLine2.setLocation(260, 243);
		jLabelLine3.setLocation(260, 263);
		jLabelZip.setLocation(260, 283);
		jLabelCity.setLocation(300, 283);

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

		layeredPane.add(jTextFieldForLine1, new Integer(1));
		layeredPane.add(postForButton, new Integer(1));
		layeredPane.add(jTextFieldForLine2, new Integer(1));
		layeredPane.add(jTextFieldForLine3, new Integer(1));
		layeredPane.add(jTextFieldZipFor, new Integer(1));
		layeredPane.add(jTextFieldCityFor, new Integer(1));
		layeredPane.add(zipCityForButton, new Integer(1));

		layeredPane.add(jTextFieldKtoPost, new Integer(1));
		layeredPane.add(postToButton2, new Integer(1));
		layeredPane.add(jTextFieldAmount1, new Integer(1));
		layeredPane.add(jTextFieldAmount2, new Integer(1));

		layeredPane.add(jTextFieldRefNr, new Integer(1));

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

		postForHistoryCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setPostForHistoryFields(record);
				}
			},
			MainModel.getInstance().getPostForHistoryCodeTableModel(),
			new int[] { 100, 300, 50, 100 },
			565,
			200);
			
		// set the text values
		applyLanguageChange();
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

	private void setPostForHistoryFields(List al) {
		String ktopost = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String line1For = (al.get(1)) != null ? (String) (al.get(1)) : "";
		String line2For = (al.get(2)) != null ? (String) (al.get(2)) : "";
		String line3For = (al.get(3)) != null ? (String) (al.get(3)) : "";
		String zipFor = (al.get(4)) != null ? (String) (al.get(4)) : "";
		String cityFor = (al.get(5)) != null ? (String) (al.get(5)) : "";
		String line1To = (al.get(6)) != null ? (String) (al.get(6)) : "";
		String line2To = (al.get(7)) != null ? (String) (al.get(7)) : "";
		String line3To = (al.get(8)) != null ? (String) (al.get(8)) : "";
		String zipTo = (al.get(9)) != null ? (String) (al.get(9)) : "";
		String cityTo = (al.get(10)) != null ? (String) (al.get(10)) : "";

		jTextFieldKtoPost.setText(ktopost);
		jTextFieldForLine1.setText(line1For);
		jTextFieldForLine2.setText(line2For);
		jTextFieldForLine3.setText(line3For);
		jTextFieldZipFor.setText(zipFor);
		jTextFieldCityFor.setText(cityFor);
		jTextFieldToLine1.setText(line1To);
		jTextFieldToLine2.setText(line2To);
		jTextFieldToLine3.setText(line3To);
		jTextFieldZipTo.setText(zipTo);
		jTextFieldCityTo.setText(cityTo);
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

	public void fill(Payment p) {
		this.payment = p;		
		super.fill();

		jTextFieldToLine1.setText(payment.getToLine1());
		jTextFieldToLine2.setText(payment.getToLine2());
		jTextFieldToLine3.setText(payment.getToLine3());

		jTextFieldZipTo.setText(payment.getToZip());

		jTextFieldCityTo.setText(payment.getToCity());

		jTextFieldForLine1.setText(payment.getForLine1());
		jTextFieldForLine2.setText(payment.getForLine2());
		jTextFieldForLine3.setText(payment.getForLine3());

		jTextFieldZipFor.setText(payment.getForZip());

		jTextFieldCityFor.setText(payment.getForCity());

		jTextFieldKtoPost.setText(payment.getToKtoPost());
		jTextFieldRefNr.setText(payment.getRefNr());

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
		tmpPayment.setAmount(getAmount());
		tmpPayment.setToLine1(jTextFieldToLine1.getText().trim());
		tmpPayment.setToLine2(jTextFieldToLine2.getText().trim());
		tmpPayment.setToLine3(jTextFieldToLine3.getText().trim());
		tmpPayment.setToZip(jTextFieldZipTo.getText().trim());
		tmpPayment.setToCity(jTextFieldCityTo.getText().trim());
		tmpPayment.setToKtoPost(jTextFieldKtoPost.getText().trim());
		tmpPayment.setForLine1(jTextFieldForLine1.getText().trim());
		tmpPayment.setForLine2(jTextFieldForLine2.getText().trim());
		tmpPayment.setForLine3(jTextFieldForLine3.getText().trim());
		tmpPayment.setForZip(jTextFieldZipFor.getText().trim());
		tmpPayment.setForCity(jTextFieldCityFor.getText().trim());
		tmpPayment.setRefNr(jTextFieldRefNr.getText().trim());
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
		if (isOrange)
			this.setTitle(
				Config.getInstance().getText("esorange.title"));
		else
			this.setTitle(
		Config.getInstance().getText("esblue.title"));
	}
}