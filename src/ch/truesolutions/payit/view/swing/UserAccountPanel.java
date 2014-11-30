/*
                A basic implementation of the JDialog class.
 */

package ch.truesolutions.payit.view.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import ch.truesolutions.payit.exceptions.InvalidDataException;
import ch.truesolutions.payit.model.*;


public class UserAccountPanel extends JDialog implements ConfigChangeListener {
	
	// fonts
/*
	Font labelFont = ConfigObject.labelFont;
	Font normalFont = ConfigObject.normalFont;
	Font mediumFont = ConfigObject.mediumFont;
	Font bigFont = ConfigObject.bigFont;
*/
	// panels
	JPanel mainPanel = new JPanel();
	JPanel mainPanelNorth = new JPanel();
	JPanel mainPanelSouth = new JPanel();
	JPanel commandPanel = new JPanel();

	// center objects
	private LimitedTextField line1Field = new LimitedTextField(20);
	private LimitedTextField line2Field = new LimitedTextField(20);
	private LimitedTextField line3Field = new LimitedTextField(20);
	private LimitedTextField zipField = new LimitedTextField(5);
	private LimitedTextField cityField = new LimitedTextField(15);
	private LimitedTextField descField = new LimitedTextField(30);
	private IbanTextField ibanField = new IbanTextField(21);
	private LimitedTextField ktoNrField = new LimitedTextField(24);
	private LimitedTextField clearingField = new LimitedTextField(5);
	private JButton clearingButton = new JButton("...");

	private JLabel adrLabel = new JLabel();
	private JLabel nameLabel = new JLabel();
	private JLabel zipCityLabel = new JLabel();
	private JLabel descLabel = new JLabel();
	private JLabel ibanLabel = new JLabel();
	private JLabel ktoNrLabel = new JLabel();
	private JLabel clearingLabel = new JLabel();
	private JLabel bankLabel = new JLabel();
	private JLabel bankValueLabel = new JLabel();

	// command objects
	JButton saveBtn = new JButton();
	JButton cancelBtn = new JButton();
	JButton helpBtn = new JButton();

	// code tables
	CodeTablePanel bkstamCodeTablePanel;
	CodeTablePanel zipCityCodeTablePanel;

	private UserAccount userAccount;
	private JDialog parentDialog;

	public UserAccountPanel(JDialog parentDialog, UserAccount userAccount) {
		super(parentDialog);

		this.userAccount = userAccount;
		this.parentDialog = parentDialog;

		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		//setSize(330,300);
		setVisible(false);

		mainPanel.setLayout(new BorderLayout());

		mainPanelNorth.setLayout(null);
		mainPanelNorth.setPreferredSize(new Dimension(300, 150));
		mainPanelNorth.setBackground(new java.awt.Color(204, 204, 255));
		mainPanelNorth.setBorder(
			new javax.swing.border.TitledBorder(Config.getInstance().getText("account.owner")));

		mainPanelSouth.setLayout(null);
		mainPanelSouth.setPreferredSize(new Dimension(300, 170));
		mainPanelSouth.setBackground(new java.awt.Color(204, 204, 255));
		mainPanelSouth.setBorder(
			new javax.swing.border.TitledBorder(Config.getInstance().getText("account.nr")));

		commandPanel.setLayout(null);
		commandPanel.setPreferredSize(new Dimension(330, 40));

		// initialize the comp on the main north panel
		line1Field.setSize(200, 20);
		line1Field.setLocation(100, 30);
		//line1Field.setFont(normalFont);
		line1Field.setNextFocusableComponent(line2Field);

		line2Field.setSize(200, 20);
		line2Field.setLocation(100, 55);
		//line2Field.setFont(normalFont);
		line2Field.setNextFocusableComponent(line3Field);

		line3Field.setSize(200, 20);
		line3Field.setLocation(100, 80);
		//line3Field.setFont(normalFont);
		line3Field.setNextFocusableComponent(zipField);

		zipField.setSize(45, 20);
		zipField.setLocation(100, 105);
		//zipField.setFont(normalFont);
		zipField.setNextFocusableComponent(cityField);
		zipField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityCodeTablePanel.setCondition("zip", zipField.getText());
				zipCityCodeTablePanel.tryToShowCodeTable();
			}
		});

		cityField.setSize(150, 20);
		cityField.setLocation(150, 105);
		//cityField.setFont(normalFont);
		cityField.setNextFocusableComponent(descField);
		cityField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zipCityCodeTablePanel.setCondition("city", cityField.getText());
				zipCityCodeTablePanel.tryToShowCodeTable();
			}
		});

		//nameLabel.setFont(labelFont);
		nameLabel.setSize(90, 20);
		nameLabel.setLocation(15, 30);

		//adrLabel.setFont(labelFont);
		adrLabel.setSize(90, 20);
		adrLabel.setLocation(15, 55);

		//zipCityLabel.setFont(labelFont);
		zipCityLabel.setSize(90, 20);
		zipCityLabel.setLocation(15, 105);

		// initialie the com on the main south panel
		descField.setSize(200, 20);
		descField.setLocation(100, 30);
		//descField.setFont(normalFont);
		descField.setNextFocusableComponent(ibanField);

		ibanField.setSize(200, 20);
		ibanField.setLocation(100, 55);
		//ibanField.setFont(normalFont);
		ibanField.setNextFocusableComponent(ktoNrField);

		ktoNrField.setSize(200, 20);
		ktoNrField.setLocation(100, 80);
		//ktoNrField.setFont(normalFont);
		ktoNrField.setNextFocusableComponent(clearingField);

		clearingField.setSize(45, 20);
		clearingField.setLocation(100, 105);
		//clearingField.setFont(normalFont);
		clearingField.setNextFocusableComponent(saveBtn);
		clearingField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bkstamCodeTablePanel.setCondition(
					"clearing",
					clearingField.getText());
				bkstamCodeTablePanel.tryToShowCodeTable();
			}
		});
		clearingField.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				bankValueLabel.setText("");
				bkstamCodeTablePanel.setCondition(
					"clearing",
					clearingField.getText());
				bkstamCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_NEVER);
			}
		});

		clearingButton.setSize(16, 16);
		clearingButton.setLocation(150, 109);
		clearingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bkstamCodeTablePanel.noCondition();
				bkstamCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});

		//descLabel.setFont(labelFont);
		descLabel.setSize(90, 20);
		descLabel.setLocation(15, 30);

		//ibanLabel.setFont(labelFont);
		ibanLabel.setSize(90, 20);
		ibanLabel.setLocation(15, 55);

		//ktoNrLabel.setFont(labelFont);
		ktoNrLabel.setSize(90, 20);
		ktoNrLabel.setLocation(15, 80);

		//clearingLabel.setFont(labelFont);
		clearingLabel.setSize(90, 20);
		clearingLabel.setLocation(15, 105);

		//bankLabel.setFont(labelFont);
		bankLabel.setSize(90, 20);
		bankLabel.setLocation(15, 130);

		//bankValueLabel.setFont(normalFont);
		bankValueLabel.setSize(200, 20);
		bankValueLabel.setLocation(100, 130);
		bankValueLabel.setOpaque(true);
		bankValueLabel.setBackground(Color.white);

		// initialize the comp on the command panel
		//saveBtn.setFont(labelFont);
		saveBtn.setSize(100, 30);
		saveBtn.setLocation(5, 5);
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSaveBtn();
			}
		});
		//cancelBtn.setFont(labelFont);
		cancelBtn.setSize(100, 30);
		cancelBtn.setLocation(110, 5);
		cancelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleCancelBtn();
			}
		});
		//helpBtn.setFont(labelFont);
		helpBtn.setSize(100, 30);
		helpBtn.setLocation(215, 5);
		helpBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleHelpBtn();
			}
		});

		// adding the components to the panel
		mainPanelNorth.add(nameLabel);
		mainPanelNorth.add(adrLabel);
		mainPanelNorth.add(line1Field);
		mainPanelNorth.add(line2Field);
		mainPanelNorth.add(line3Field);
		mainPanelNorth.add(zipCityLabel);
		mainPanelNorth.add(zipField);
		mainPanelNorth.add(cityField);

		mainPanelSouth.add(descLabel);
		mainPanelSouth.add(descField);
		mainPanelSouth.add(ibanLabel);
		mainPanelSouth.add(ibanField);
		mainPanelSouth.add(ktoNrLabel);
		mainPanelSouth.add(ktoNrField);
		mainPanelSouth.add(clearingLabel);
		mainPanelSouth.add(clearingField);
		mainPanelSouth.add(clearingButton);
		mainPanelSouth.add(bankLabel);
		mainPanelSouth.add(bankValueLabel);

		commandPanel.add(saveBtn);
		commandPanel.add(cancelBtn);
		//commandPanel.add(helpBtn);

		// Adding the panels to the frame
		mainPanel.add(mainPanelNorth, BorderLayout.NORTH);
		mainPanel.add(mainPanelSouth, BorderLayout.SOUTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(commandPanel, BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				//this.setVisible(false);
			}
		});

		// fill the data from the user account in the fields (if any)
		line1Field.setText(userAccount.getLine1());
		line2Field.setText(userAccount.getLine2());
		line3Field.setText(userAccount.getLine3());
		zipField.setText(userAccount.getZip());
		cityField.setText(userAccount.getCity());
		descField.setText(userAccount.getDescription());
		ktoNrField.setText(userAccount.getAccountNr());
		ibanField.setText(userAccount.getIban());
		clearingField.setText(userAccount.getClearing());
		bankValueLabel.setText(userAccount.getBankName());

		// initialize code tables		
		zipCityCodeTablePanel =
			new CodeTablePanel(this, new CodeTableListener() {
				public void handleDoubleClick(List record) {
					setZipCity(record);
				}
			},
			MainModel.getInstance().getZipCityCodeTableModel(),
			new int[] { 40, 173 },
			240,
			200);

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

		applyLanguageChange();
		pack();
	}

	// handler methods
	private void setZipCity(List al) {
		String zip = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String city = (al.get(1)) != null ? (String) (al.get(1)) : "";

		zipField.setText(zip);
		cityField.setText(city);
	}

	private void setBkstamFields(List al) {
		String clearing = (al.get(0)) != null ? (String) (al.get(0)) : "";
		String fullname = (al.get(1)) != null ? (String) (al.get(1)) : "";
		clearingField.setText(clearing);
		bankValueLabel.setText(fullname);
	}

	/*
	private void showPanel(JDialog ctp) {
		int xoff = (this.getWidth() - ctp.getWidth()) / 2;
		xoff = (xoff > 0) ? xoff : 0;
		int yoff = (this.getHeight() - ctp.getHeight()) / 2;
		yoff = (yoff > 0) ? yoff : 0;
		ctp.setLocation(this.getX() + xoff, this.getY() + yoff);
		ctp.show();
	}*/

	private void handleSaveBtn() {
		UserAccount newAccount = new UserAccount();
		newAccount.setAccountNr(userAccount.getAccountNr());
		newAccount.setNewAccountNr(ktoNrField.getText());
		newAccount.setClearing(clearingField.getText());
		newAccount.setLine1(line1Field.getText());
		newAccount.setLine2(line2Field.getText());
		newAccount.setLine3(line3Field.getText());
		newAccount.setZip(zipField.getText());
		newAccount.setCity(cityField.getText());
		newAccount.setIban(ibanField.getText());
		newAccount.setDescription(descField.getText());
		
		try{
			newAccount.validate();
			if(userAccount.getAccountNr().length() == 0){
				newAccount.setAccountNr(newAccount.getNewAccountNr());
				MainModel.getInstance().getUserAccountsModel().addUserAccount(newAccount);
			} else {
				MainModel.getInstance().getUserAccountsModel().updateUserAccount(newAccount);
			}
			this.setVisible(false);
		} catch (InvalidDataException e) {
			// msg popup
			JOptionPane.showMessageDialog(
				this,
				Config.getInstance().getText(e.getMessage()),
				"Warning",
				JOptionPane.WARNING_MESSAGE);
		}
	}

	private void handleCancelBtn() {
		this.setVisible(false);
	}
	private void handleHelpBtn() {
	}

	/**
	 * Methods of the ConfigChangeListenerInterface
	 */
	public void applyLanguageChange() {
		setTitle(
			Config.getInstance().getText(
				"useraccountpanel.title"));

		saveBtn.setText(
			Config.getInstance().getText("button.save"));
		cancelBtn.setText(
			Config.getInstance().getText("button.cancel"));
		helpBtn.setText(
			Config.getInstance().getText("button.help"));

		nameLabel.setText(
			Config.getInstance().getText("label.name"));
		adrLabel.setText(
			Config.getInstance().getText("label.address"));
		zipCityLabel.setText(
			Config.getInstance().getText("label.zipcity"));
		descLabel.setText(
			Config.getInstance().getText("label.description"));
		ibanLabel.setText(
			Config.getInstance().getText("label.iban"));
		ktoNrLabel.setText(
			Config.getInstance().getText("label.kto"));
		clearingLabel.setText(
			Config.getInstance().getText("label.clearing"));
		bankLabel.setText(
			Config.getInstance().getText("label.bankname"));
	}

	public void setLookAndFeel(String lnfName) {
	}
}