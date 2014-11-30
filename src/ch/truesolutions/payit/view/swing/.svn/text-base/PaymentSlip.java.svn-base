/*
                A basic implementation of the JDialog class.
 */

package ch.truesolutions.payit.view.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ch.truesolutions.calendar.EasyDatePickerSwing;
import ch.truesolutions.payit.exceptions.InvalidDataException;
import ch.truesolutions.payit.model.*;
import ch.truesolutions.payit.model.Config;
import ch.truesolutions.payit.model.UserAccount;

public abstract class PaymentSlip
	extends JDialog implements ItemListener, ConfigChangeListener{

	protected Payment payment;
	// panels
	JLayeredPane layeredPane = new JLayeredPane();
	JPanel commandPanel = new JPanel();
	JPanel headerPanel = new JPanel();
	JPanel statusPanel = new JPanel();

	// command objects
	JButton saveBtn = new JButton();
	JButton cancelBtn = new JButton();
	JButton clearBtn = new JButton();
	JButton helpBtn = new JButton();

	// header objects
	JLabel jLabelExecDate = new JLabel();
	LimitedTextField jTextFieldExecDate = new LimitedTextField(10);
	JButton calBtn = new JButton();
	ImageIcon calIcon =
		new ImageIcon(ClassLoader.getSystemResource("calendar.gif"));
	JLabel calIconLabel = new JLabel(calIcon);
	JLabel jLabelKtoFrom = new JLabel();
	JComboBox jComboBoxKtoFrom;
	JLabel jLabelComment = new JLabel();
	LimitedTextField jTextFieldComment = new LimitedTextField(50);

	// center objects
	JLabel ibanFrom = new JLabel();
	JLabel jLabelLine1 = new JLabel();
	JLabel jLabelLine2 = new JLabel();
	JLabel jLabelLine3 = new JLabel();
	JLabel jLabelZip = new JLabel();
	JLabel jLabelCity = new JLabel();

	// calendar
	EasyDatePickerSwing tsCalendar;

	// Properties
	protected boolean isPaymentValid = true;

	public PaymentSlip(Frame parentFrame) {
		super(parentFrame);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});

		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(true);
		setSize(650, 450);
		setVisible(false);

		// initialize the calendar
		tsCalendar = new EasyDatePickerSwing(this, jTextFieldExecDate);
		tsCalendar.setDateFormat(Config.getInstance().getDisplayDateFormat());
		tsCalendar.setLookAndFeel(Config.getInstance().getLookAndFeel());
		// Components of the panel in the north (header)
		jLabelExecDate.setFont(Fonts.labelFont);
		jLabelExecDate.setSize(90, 20);
		jLabelExecDate.setLocation(5, 10);

		jTextFieldExecDate.setSize(70, 20);
		jTextFieldExecDate.setFont(Fonts.normalFont);
		jTextFieldExecDate.setLocation(100, 10);
		jTextFieldExecDate.setEditable(false);
		jTextFieldExecDate.setBackground(Color.white);

		calBtn.setIcon(calIcon);
		calBtn.setSize(28, 20);
		calBtn.setLocation(175, 10);
		calBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				showCalendar(e);
			}
		});

		//calIconLabel.setBounds(0,0,calIcon.getIconWidth(),calIcon.getIconHeight());
		calIconLabel.setSize(28, 20);
		calIconLabel.setLocation(175, 10);
		calIconLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				showCalendar(e);
			}
		});

		jLabelKtoFrom.setFont(Fonts.labelFont);
		jLabelKtoFrom.setSize(90, 20);
		jLabelKtoFrom.setLocation(230, 10);

		jComboBoxKtoFrom = new JComboBox(MainModel.getInstance().getUserAccountsModel().getUserAccounts().toArray());
		jComboBoxKtoFrom.setRenderer(new ComboRend(jComboBoxKtoFrom));
		jComboBoxKtoFrom.setSize(150, 20);
		jComboBoxKtoFrom.setLocation(330, 10);
		jComboBoxKtoFrom.addItemListener(this);
		// set the default user account as selected
		for (int i = 0; i < jComboBoxKtoFrom.getItemCount(); i++) {
			if (((UserAccount) (jComboBoxKtoFrom.getItemAt(i)))
				.toString()
				.equals(Config.getInstance().getDefaultAccount())) {
				jComboBoxKtoFrom.setSelectedIndex(i);
			}
		}

		headerPanel.setLayout(null);
		headerPanel.setPreferredSize(new Dimension(650, 40));
		headerPanel.add(jLabelExecDate);
		headerPanel.add(jTextFieldExecDate);
		headerPanel.add(calIconLabel);
		//headerPanel.add(calBtn);
		headerPanel.add(jLabelKtoFrom);
		headerPanel.add(jComboBoxKtoFrom);

		// Components of the panel in the east (command)
		saveBtn.setFont(Fonts.labelFont);
		saveBtn.setSize(100, 30);
		saveBtn.setLocation(10, 280);
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSaveBtn();
			}
		});

		cancelBtn.setFont(Fonts.labelFont);
		cancelBtn.setSize(100, 30);
		cancelBtn.setLocation(10, 320);
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleCancelBtn();
			}
		});

		clearBtn.setFont(Fonts.labelFont);
		clearBtn.setSize(100, 30);
		clearBtn.setLocation(10, 200);
		clearBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleClearBtn();
			}
		});

		helpBtn.setFont(Fonts.labelFont);
		helpBtn.setSize(100, 30);
		helpBtn.setLocation(10, 240);

		commandPanel.setLayout(null);
		commandPanel.setPreferredSize(new Dimension(120, 400));

		commandPanel.add(saveBtn);
		commandPanel.add(cancelBtn);
		//commandPanel.add(clearBtn);
		//commandPanel.add(helpBtn);

		// components from the center (not yet added)
		jLabelLine1.setFont(Fonts.bigFont);
		jLabelLine1.setSize(200, 20);

		jLabelLine2.setFont(Fonts.bigFont);
		jLabelLine2.setSize(200, 20);
		//jLabelLine2.setLocation(5,10);

		jLabelLine3.setFont(Fonts.bigFont);
		jLabelLine3.setSize(200, 20);
		//jLabelLine3.setLocation(5,10);

		jLabelZip.setFont(Fonts.bigFont);
		jLabelZip.setSize(200, 20);
		//jLabelZip.setLocation(5,10);

		jLabelCity.setFont(Fonts.bigFont);
		jLabelCity.setSize(200, 20);
		//jLabelLine1.setLocation(5,10);

		// status panel
		jLabelComment.setFont(Fonts.labelFont);
		jLabelComment.setSize(95, 20);
		jLabelComment.setLocation(5, 5);

		jTextFieldComment.setSize(410, 20);
		jTextFieldComment.setLocation(80, 5);

		statusPanel.setLayout(null);
		statusPanel.setPreferredSize(new Dimension(650, 30));
		//statusPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		statusPanel.add(jLabelComment);
		statusPanel.add(jTextFieldComment);

		// Adding the panels to the frame
		getContentPane().add(layeredPane, BorderLayout.CENTER);
		getContentPane().add(headerPanel, BorderLayout.NORTH);
		getContentPane().add(commandPanel, BorderLayout.EAST);
		getContentPane().add(statusPanel, BorderLayout.SOUTH);

		// init the labels
		itemStateChanged(null);

	}
	
	public void showCalendar(MouseEvent e) {
		e.translatePoint(this.getX(), this.getY());
		// adding the offset of this slip
		e.translatePoint(calBtn.getX(), calBtn.getY());
		tsCalendar.setLocation(e.getPoint());
		tsCalendar.setVisible(true);
	}

	// will be overriden by the subclass
	void handleSaveBtn(){}
	
	public abstract void fill(Payment p);

	// North Panel
	public void fill() {
		// disable the save button if the payment is archived
		if (!this.payment.getIsPending().booleanValue()) {
			saveBtn.setEnabled(false);
		}

		if (payment.getExecDate() != null)
			jTextFieldExecDate.setText(payment.getExecDate());
	
		jTextFieldComment.setText(payment.getComment());
	
		// go through all the combo box items (kto from) and select the right one
		for (int i = 0; i < jComboBoxKtoFrom.getItemCount(); i++) {
			if (((UserAccount) (jComboBoxKtoFrom.getItemAt(i)))
				.toString()
				.equals(payment.getFromKto())) {
				jComboBoxKtoFrom.setSelectedIndex(i);
			}
		}
	}



	void handleSaveBtn(Payment tmpPayment) {
		// fill the general payments fields
		UserAccount ua = (UserAccount) (jComboBoxKtoFrom.getSelectedItem());
		// if there isn't a valid user account the user account panel pops up
		if (ua.getAccountNr().equals("")) {
			UserAccountPanel uap = new UserAccountPanel(this, ua);
			int xoff = (this.getWidth() - uap.getWidth()) / 2;
			xoff = (xoff > 0) ? xoff : 0;
			int yoff = (this.getHeight() - uap.getHeight()) / 2;
			yoff = (yoff > 0) ? yoff : 0;
			uap.setLocation(this.getX() + xoff, this.getY() + yoff);
			uap.setVisible(true);

			jComboBoxKtoFrom.setSelectedIndex(0);
		}

		tmpPayment.setExecDate(jTextFieldExecDate.getText());
		tmpPayment.setFromKto(ua.getAccountNr());
		tmpPayment.setComment(jTextFieldComment.getText().trim());
		try {
			tmpPayment.validate();
			// if the tmpPayment is valid we copy the data into the
			// original payment
			payment.fill(tmpPayment);
			// save or update the payment
			if(payment.getId() == null){
				MainModel.getInstance().addPayment(payment);
			} else {
				MainModel.getInstance().updatePayment(payment);
			}
			// store this account nr as default if not allready set
			if(!ua.getAccountNr().equals(Config.getInstance().getDefaultAccount())) {
				Config.getInstance().setDefaultAccount(ua.getAccountNr());
			}
			this.setVisible(false);
		} catch (InvalidDataException e) {
			// msg popup
			JOptionPane.showMessageDialog(
				this,
				e.getMessage(),
				Config.getInstance().getText("msg.payittitle"),
				JOptionPane.WARNING_MESSAGE);
		}
	}

	public void handleClearBtn() {
	};

	public void handleCancelBtn() {
		try {
			this.setVisible(false);
		} catch (Exception e) {
		}
	}

	String getDefaultDate() {
		return tsCalendar.getModel().getNextSelectableDateAsString();
	}

	/**
	 * Function of the ItemListenerInterface
	 */
	public void itemStateChanged(ItemEvent e) {
		if (jComboBoxKtoFrom.getItemCount() > 0) {
			jLabelLine1.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem()))
					.getLine1());
			jLabelLine2.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem()))
					.getLine2());
			jLabelLine3.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem()))
					.getLine3());
			jLabelZip.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem())).getZip());
			jLabelCity.setText(
				((UserAccount) (jComboBoxKtoFrom.getSelectedItem())).getCity());
		}
	}

	/**
	 * Functions of the ConfigChangeListenerInterface
	 */
	public void applyLanguageChange() {
		jLabelExecDate.setText(
			Config.getInstance().getText("label.execdate"));
		jLabelKtoFrom.setText(
			Config.getInstance().getText("label.ktofrom"));
		jLabelComment.setText(
			Config.getInstance().getText("label.comment"));
		saveBtn.setText(
			Config.getInstance().getText("button.save"));
		cancelBtn.setText(
			Config.getInstance().getText("button.cancel"));
		clearBtn.setText(
			Config.getInstance().getText("button.clear"));
		helpBtn.setText(
			Config.getInstance().getText("button.help"));
	}

	public void setLookAndFeel(String lnfName) {
	}

	// custom renderer class
	class ComboRend extends JLabel implements ListCellRenderer {

		private JComboBox comboBox;
		
		public ComboRend(JComboBox comboBox) {
			this.comboBox = comboBox;
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			UserAccount userAccount = (UserAccount) value;
			if(userAccount == null) {
				userAccount = new UserAccount();
			}
			setText(userAccount.getAccountNr());
			comboBox.setToolTipText(
				userAccount.getDescription()
					+ ","
					+ userAccount.getBankName()
					+ ","
					+ userAccount.getClearing()
					+ ","
					+ userAccount.getCurrency());
			return this;
		}
	}
}