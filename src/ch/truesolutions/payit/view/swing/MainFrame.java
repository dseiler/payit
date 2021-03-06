/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

import ch.truesolutions.payit.model.*;


public class MainFrame
	extends JFrame implements ChangeListener, ConfigChangeListener {

	private MainModel mainModel;
	private PaymentsModel pendingPaymentsModel;
	private PaymentsModel archivedPaymentsModel;

	JPanel contentPane;

	// Actions
	Action dtaExportAction;
	Action deletePaymentAction;
	Action archivePaymentAction;
	Action reactivatePaymentAction;

	JMenuBar menuBar1 = new JMenuBar();

	JMenu menuFile = new JMenu();
	JMenuItem menuFileBackup = new JMenuItem();
	JMenuItem menuFileRestore = new JMenuItem();
	JMenuItem menuFileExit = new JMenuItem();

	JMenu menuPay = new JMenu();
	JMenuItem menuPayDta;
	JMenuItem menuPayDtaImport = new JMenuItem();
	JMenuItem menuPayDtaView = new JMenuItem();
	JMenuItem menuPayDelete;
	JMenuItem menuPayReuse = new JMenuItem();
	JMenuItem menuPayChart = new JMenuItem();
	JMenuItem menuPayArchive;
	JMenuItem menuPayReactivate;

	JMenu menuData = new JMenu();
	JMenuItem menuDataKtoFrom = new JMenuItem();
	JMenuItem menuDataUpdateHistory = new JMenuItem();
	JMenuItem menuDataPostDirect = new JMenuItem();
	JMenuItem menuDataPostIndirect = new JMenuItem();
	JMenuItem menuDataBank = new JMenuItem();
	JMenuItem menuDataBkstamm = new JMenuItem();
	JMenuItem menuDataZipCity = new JMenuItem();

	JMenu menuExtra = new JMenu();
	JMenu menuExtraLanguage = new JMenu(); // submenu
	JMenu menuExtraLookAndFeel = new JMenu(); // submenu

	//language group of radio button menu items
	ButtonGroup languageGroup = new ButtonGroup();
	JRadioButtonMenuItem menuExtraLanguageGerman = new JRadioButtonMenuItem();
	JRadioButtonMenuItem menuExtraLanguageFrench = new JRadioButtonMenuItem();
	JRadioButtonMenuItem menuExtraLanguageEnglish = new JRadioButtonMenuItem();
	JRadioButtonMenuItem menuExtraLanguageItalian = new JRadioButtonMenuItem();

	//language group of radio button menu items
	ButtonGroup lookAndFeelGroup = new ButtonGroup();
	JRadioButtonMenuItem menuExtraLookAndFeelWindows =
		new JRadioButtonMenuItem();
	JRadioButtonMenuItem menuExtraLookAndFeelMotif = new JRadioButtonMenuItem();
	JRadioButtonMenuItem menuExtraLookAndFeelJava = new JRadioButtonMenuItem();
	JRadioButtonMenuItem menuExtraLookAndFeelMacintosh =
		new JRadioButtonMenuItem();

	JMenu menuView = new JMenu();
	JMenuItem menuViewPendent = new JMenuItem();
	JMenuItem menuViewArchived = new JMenuItem();

	JMenu menuHelp = new JMenu();
	JMenuItem menuHelpAbout = new JMenuItem();
	JMenuItem menuHelpRegister = new JMenuItem();

	// popup menu
	JPopupMenu menuPopup = new JPopupMenu();
	JMenuItem menuPopupDta;
	JMenuItem menuPopupDtaImport = new JMenuItem();
	JMenuItem menuPopupDelete;
	JMenuItem menuPopupReuse = new JMenuItem();
	JMenuItem menuPopupArchive;
	JMenuItem menuPopupReactivate;

	JToolBar toolBar = new JToolBar();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	JButton jButton3 = new JButton();
	JButton jButton4 = new JButton();
	JButton jButton5 = new JButton();
	JButton jButton6 = new JButton();
	ImageIcon image1;
	ImageIcon image2;
	ImageIcon image3;
	ImageIcon image4;
	ImageIcon image5;
	ImageIcon image6;
	BorderLayout borderLayout1 = new BorderLayout();
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	JScrollPane jScrollPane1 = new JScrollPane();
	JScrollPane jScrollPane2 = new JScrollPane();
	JTable jTablePendingPayments = new JTable();
	JTable jTableArchivedPayments = new JTable();

	JPanel jPanelStatusBar = new JPanel();
	JPanel statisticPanel = new JPanel(null);
	JPanel infoPanel = new JPanel(null);
	JLabel jLabelNumber = new JLabel();
	JLabel jLabelNumberResult = new JLabel();
	JLabel jLabelSum = new JLabel();
	JLabel jLabelSumResult = new JLabel();
	GridLayout gridLayout1 = new GridLayout(1, 2, 5, 0);

	private int activeTabIndex = 0;

	//Construct the frame
	public MainFrame() {
		init();
	}

	//Component initialization
	private void init() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		
		mainModel = MainModel.getInstance();
		pendingPaymentsModel = mainModel.getPendingPaymentsModel();
		archivedPaymentsModel = mainModel.getArchivedPaymentsModel();

		mainModel.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent event){
				// check if it is from the pending or from the archived payments
				refreshPaymentsTableDisplay(event.getPropertyName(),(String)event.getOldValue());
			}
		});

		// register this frame at the ConfigObject
		Config.getInstance().addConfigChangeListener(this);
		
		image1 = new ImageIcon(ClassLoader.getSystemResource("esRed_icon.gif"));
		image2 =
			new ImageIcon(ClassLoader.getSystemResource("esBlue_icon.gif"));
		image3 =
			new ImageIcon(ClassLoader.getSystemResource("esBank_icon.gif"));
		image4 =
			new ImageIcon(ClassLoader.getSystemResource("esOrange_icon.gif"));
		image5 = 
			new ImageIcon(ClassLoader.getSystemResource("ipi_icon.gif"));
		image6 = 
			new ImageIcon(ClassLoader.getSystemResource("esRedIBAN_icon.gif"));

		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);

		this.setSize(new Dimension(800, 600));

		// change the frame icon
		//java.net.URL url = this.getClass().getResource("images/frame_icon.gif");
		this.setIconImage(
			java.awt.Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("frame_icon.gif")));

		//actions
		dtaExportAction = new AbstractAction("", null) {
			public void actionPerformed(ActionEvent e) {
				handleDtaExport();
			}
		};
		deletePaymentAction = new AbstractAction("", null) {
			public void actionPerformed(ActionEvent e) {
				handleDeletePayments();
			}
		};
		archivePaymentAction = new AbstractAction("", null) {
			public void actionPerformed(ActionEvent e) {
				handleArchivePayments(false);
			}
		};
		reactivatePaymentAction = new AbstractAction("", null) {
			public void actionPerformed(ActionEvent e) {
				handleReactivatePayments();
			}
		};

		// action listeners
		menuFileBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDbBackup();
			}
		});

		menuFileRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDbRestore();
			}
		});

		menuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		menuPayDtaImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDtaImport();
			}
		});

		menuPayDtaView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDtaView();
			}
		});

		menuPayReuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleReusePayment();
			}
		});
		
		menuPayChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleShowChart();
			}
		});

		menuDataKtoFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showUserAccounts();
			}
		});

		menuDataUpdateHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//handleUpdateHistory();
			}
		});
		
		menuDataBank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBankHistoryCodeTablePanel();
			}
		});

		menuDataPostDirect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPostToHistoryCodeTablePanel();
			}
		});
		menuDataPostIndirect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPostForHistoryCodeTablePanel();
			}
		});

		menuDataBkstamm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBkstamCodeTablePanel();
			}
		});

		menuDataZipCity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showZipCityCodeTablePanel();
			}
		});

		menuExtraLanguageGerman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLanguage(Locale.GERMAN.getISO3Language());
			}
		});

		menuExtraLanguageFrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLanguage(Locale.FRENCH.getISO3Language());
			}
		});

		menuExtraLanguageItalian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLanguage(Locale.ITALIAN.getISO3Language());
			}
		});

		menuExtraLanguageEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLanguage(Locale.ENGLISH.getISO3Language());
			}
		});

		menuExtraLookAndFeelJava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());
				// "javax.swing.plaf.metal.MetalLookAndFeel"
			}
		});

		menuExtraLookAndFeelWindows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLookAndFeel(
					"com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
		});

		menuExtraLookAndFeelMotif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLookAndFeel(
					"com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			}
		});

		menuExtraLookAndFeelMacintosh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.getInstance().setLookAndFeel(
					"javax.swing.plaf.mac.MacLookAndFeel");
			}
		});

		menuHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});

		menuHelpRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleRegister();
			}
		});

		menuPopupReuse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleReusePayment();
			}
		});

		jButton1.setIcon(image1);
		//jButton1.setPreferredSize(new Dimension(100,50));
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.PAYMENT_TYPE_RED));
				showPaymentSlip(p);
			}
		});

		jButton2.setIcon(image2);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.PAYMENT_TYPE_BLUE));
				showPaymentSlip(p);
			}
		});

		jButton3.setIcon(image3);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.PAYMENT_TYPE_BANKCH));
				showPaymentSlip(p);
			}
		});

		jButton4.setIcon(image4);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.PAYMENT_TYPE_ORANGE));
				showPaymentSlip(p);
			}
		});

		jButton5.setIcon(image5);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.PAYMENT_TYPE_IPI));
				showPaymentSlip(p);
			}
		});
		
		// adding red payment slip with IBAN
		jButton6.setIcon(image6);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.PAYMENT_TYPE_RED_IBAN));
				showPaymentSlip(p);
			}
		});

		toolBar.setMargin(new Insets(5, 5, 5, 5));
		toolBar.add(jButton1);
		toolBar.addSeparator();
		toolBar.add(jButton2);
		toolBar.addSeparator();
		toolBar.add(jButton4);
		toolBar.addSeparator();
		toolBar.add(jButton3);
		toolBar.addSeparator();
		toolBar.add(jButton5);
		toolBar.addSeparator();
		toolBar.add(jButton6);

		menuFile.setFont(Fonts.menuFont);
		menuFileExit.setFont(Fonts.menuFont);
		menuFileBackup.setFont(Fonts.menuFont);
		menuFileRestore.setFont(Fonts.menuFont);

		menuFile.add(menuFileBackup);
		menuFile.add(menuFileRestore);
		menuFile.addSeparator();
		menuFile.add(menuFileExit);

		menuPay.setFont(Fonts.menuFont);
		menuPayDta = menuPay.add(dtaExportAction);
		menuPayDta.setFont(Fonts.menuFont);
		menuPayDtaImport.setFont(Fonts.menuFont);
		menuPayDtaView.setFont(Fonts.menuFont);
		menuPayDelete = menuPay.add(deletePaymentAction);
		menuPayDelete.setFont(Fonts.menuFont);
		menuPayArchive = menuPay.add(archivePaymentAction);
		menuPayArchive.setFont(Fonts.menuFont);
		menuPayReactivate = menuPay.add(reactivatePaymentAction);
		menuPayReactivate.setFont(Fonts.menuFont);
		menuPayReuse.setFont(Fonts.menuFont);
		menuPayChart.setFont(Fonts.menuFont);
		menuPay.add(menuPayDta);
		menuPay.add(menuPayDtaImport);
		menuPay.add(menuPayDtaView);
		menuPay.addSeparator();
		menuPay.add(menuPayDelete);
		menuPay.add(menuPayArchive);
		menuPay.add(menuPayReactivate);
		menuPay.add(menuPayReuse);
		menuPay.addSeparator();
		menuPay.add(menuPayChart);

		menuData.setFont(Fonts.menuFont);
		menuDataKtoFrom.setFont(Fonts.menuFont);
		menuDataUpdateHistory.setFont(Fonts.menuFont);
		menuDataPostDirect.setFont(Fonts.menuFont);
		menuDataPostIndirect.setFont(Fonts.menuFont);
		menuDataBank.setFont(Fonts.menuFont);
		menuDataBkstamm.setFont(Fonts.menuFont);
		menuDataZipCity.setFont(Fonts.menuFont);
		
		menuData.add(menuDataKtoFrom);
		menuData.addSeparator();
		//menuData.add(menuDataUpdateHistory);
		menuData.add(menuDataPostDirect);
		menuData.add(menuDataPostIndirect);
		menuData.add(menuDataBank);
		menuData.add(menuDataBkstamm);
		menuData.add(menuDataZipCity);

		languageGroup.add(menuExtraLanguageGerman);
		languageGroup.add(menuExtraLanguageFrench);
		languageGroup.add(menuExtraLanguageItalian);
		languageGroup.add(menuExtraLanguageEnglish);

		menuExtraLanguage.setFont(Fonts.menuFont);
		menuExtraLanguageGerman.setFont(Fonts.menuFont);
		menuExtraLanguageFrench.setFont(Fonts.menuFont);
		menuExtraLanguageItalian.setFont(Fonts.menuFont);
		menuExtraLanguageEnglish.setFont(Fonts.menuFont);
		
		menuExtraLanguage.add(menuExtraLanguageGerman);
		menuExtraLanguage.add(menuExtraLanguageFrench);
		//menuExtraLanguage.add(menuExtraLanguageItalian);
		menuExtraLanguage.add(menuExtraLanguageEnglish);

		// setting the selected language to checked
		String language =
			Config.getInstance().getLanguage();
		ButtonModel bm;
		if (language.equals("deu")) {
			bm = menuExtraLanguageGerman.getModel();
			languageGroup.setSelected(bm, true);
		} else if (language.equals("fra")) {
			bm = menuExtraLanguageFrench.getModel();
			languageGroup.setSelected(bm, true);
		} else if (language.equals("ita")) {
			bm = menuExtraLanguageItalian.getModel();
			languageGroup.setSelected(bm, true);
		} else if (language.equals("eng")) {
			bm = menuExtraLanguageEnglish.getModel();
			languageGroup.setSelected(bm, true);
		}

		lookAndFeelGroup.add(menuExtraLookAndFeelWindows);
		lookAndFeelGroup.add(menuExtraLookAndFeelMotif);
		lookAndFeelGroup.add(menuExtraLookAndFeelJava);
		lookAndFeelGroup.add(menuExtraLookAndFeelMacintosh);

		menuExtraLookAndFeel.setFont(Fonts.menuFont);
		menuExtraLookAndFeelWindows.setFont(Fonts.menuFont);
		menuExtraLookAndFeelMotif.setFont(Fonts.menuFont);
		menuExtraLookAndFeelJava.setFont(Fonts.menuFont);
		menuExtraLookAndFeelMacintosh.setFont(Fonts.menuFont);

		menuExtraLookAndFeel.add(menuExtraLookAndFeelWindows);
		menuExtraLookAndFeel.add(menuExtraLookAndFeelMotif);
		menuExtraLookAndFeel.add(menuExtraLookAndFeelJava);
		menuExtraLookAndFeel.add(menuExtraLookAndFeelMacintosh);

		// setting the selected look and feel to checked
		String lookandfeel = Config.getInstance().getLookAndFeel();
		if (lookandfeel
			.equals(UIManager.getCrossPlatformLookAndFeelClassName())) {
			bm = menuExtraLookAndFeelJava.getModel();
			lookAndFeelGroup.setSelected(bm, true);
		} else if (
			lookandfeel.equals(
				"com.sun.java.swing.plaf.windows.WindowsLookAndFeel")) {
			bm = menuExtraLookAndFeelWindows.getModel();
			lookAndFeelGroup.setSelected(bm, true);
		} else if (
			lookandfeel.equals(
				"com.sun.java.swing.plaf.motif.MotifLookAndFeel")) {
			bm = menuExtraLookAndFeelMotif.getModel();
			lookAndFeelGroup.setSelected(bm, true);
		} else if (lookandfeel.equals("javax.swing.plaf.mac.MacLookAndFeel")) {
			bm = menuExtraLookAndFeelMacintosh.getModel();
			lookAndFeelGroup.setSelected(bm, true);
		} else {
			Config.getInstance().setLookAndFeel(
				UIManager.getCrossPlatformLookAndFeelClassName());
				bm = menuExtraLookAndFeelJava.getModel();
				lookAndFeelGroup.setSelected(bm, true);			
		}

		menuExtra.setFont(Fonts.menuFont);
		menuExtraLanguage.setFont(Fonts.menuFont);

		menuExtra.add(menuExtraLanguage);
		menuExtra.addSeparator();
		menuExtra.add(menuExtraLookAndFeel);

		menuView.setFont(Fonts.menuFont);
		menuViewPendent.setFont(Fonts.menuFont);

		menuView.add(menuViewPendent);
		menuViewArchived.setFont(Fonts.menuFont);
		menuView.add(menuViewArchived);

		menuHelp.setFont(Fonts.menuFont);
		menuHelpAbout.setFont(Fonts.menuFont);
		menuHelpRegister.setFont(Fonts.menuFont);
		if (Config.getInstance().isDemoVersion()) {
			menuHelp.add(menuHelpRegister);
			menuHelp.addSeparator();
		}
		menuHelp.add(menuHelpAbout);

		menuPopup.setFont(Fonts.menuFont);
		menuPopupDta = menuPopup.add(dtaExportAction);
		menuPopupDta.setFont(Fonts.menuFont);
		menuPopupDtaImport.setFont(Fonts.menuFont);
		menuPopupDelete = menuPopup.add(deletePaymentAction);
		menuPopupDelete.setFont(Fonts.menuFont);
		menuPopupArchive = menuPopup.add(archivePaymentAction);
		menuPopupArchive.setFont(Fonts.menuFont);
		menuPopupReactivate = menuPopup.add(reactivatePaymentAction);
		menuPopupReactivate.setFont(Fonts.menuFont);
		menuPopupReuse.setFont(Fonts.menuFont);
		menuPopup.add(menuPopupDta);
		//menuPopup.add(menuPopupDtaImport);
		menuPopup.addSeparator();
		menuPopup.add(menuPopupDelete);
		menuPopup.add(menuPopupArchive);
		menuPopup.add(menuPopupReactivate);
		menuPopup.add(menuPopupReuse);

		menuBar1.add(menuFile);
		menuBar1.add(menuPay);
		menuBar1.add(menuData);
		menuBar1.add(menuExtra);
		//menuBar1.add(menuView);
		menuBar1.add(menuHelp);
		this.setJMenuBar(menuBar1);

		jTablePendingPayments.setFont(Fonts.tableFont);
		jTableArchivedPayments.setFont(Fonts.tableFont);
		jScrollPane1.getViewport().add(jTablePendingPayments, null);
		jScrollPane2.getViewport().add(jTableArchivedPayments, null);

		jTabbedPane1.setFont(Fonts.tabFont);
		jTabbedPane1.addChangeListener(this);
		jTabbedPane1.add(jScrollPane1);
		jTabbedPane1.add(jScrollPane2);

		jLabelNumber.setSize(100, 20);
		jLabelNumber.setLocation(10, 5);
		jLabelNumberResult.setSize(100, 20);
		jLabelNumberResult.setLocation(90, 5);
		jLabelSum.setSize(100, 20);
		jLabelSum.setLocation(150, 5);
		jLabelSumResult.setSize(100, 20);
		jLabelSumResult.setLocation(230, 5);

		infoPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		infoPanel.setPreferredSize(new Dimension(300, 30));

		statisticPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		statisticPanel.setPreferredSize(new Dimension(300, 30));
		statisticPanel.add(jLabelNumber);
		statisticPanel.add(jLabelNumberResult);
		statisticPanel.add(jLabelSum);
		statisticPanel.add(jLabelSumResult);

		jPanelStatusBar.setLayout(gridLayout1);
		jPanelStatusBar.add(infoPanel, null);
		jPanelStatusBar.add(statisticPanel, null);

		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(jTabbedPane1, BorderLayout.CENTER);
		contentPane.add(jPanelStatusBar, BorderLayout.SOUTH);

		// init the menu items
		setMenuItemsEnabled();

		// init the popup menu
		MouseListener popupListener = new PopupListener();
		jTablePendingPayments.addMouseListener(popupListener);
		jTableArchivedPayments.addMouseListener(popupListener);
		
		// init the code table panels
/*
		bkstamCodeTablePanel = new BkstamCodeTablePanel(this);
		bankHistoryCodeTablePanel = new BankHistoryCodeTablePanel(this);
		postToHistoryCodeTablePanel = new PostToHistoryCodeTablePanel(this);
		postForHistoryCodeTablePanel = new PostForHistoryCodeTablePanel(this);		
		zipCityCodeTablePanel = new ZipCityCodeTablePanel(this);
*/		
		// initialize the tables
		initTables();

		// set the text values
		applyLanguageChange();
		
		// set the saved look and feel
		Config.getInstance().setLookAndFeel(Config.getInstance().getLookAndFeel());
	}
		
	void showBankHistoryCodeTablePanel(){
		//bankHistoryCodeTablePanel.showPanel();
	}
	
	void showPostToHistoryCodeTablePanel(){
		//postToHistoryCodeTablePanel.showPanel();
	}
	
	void showPostForHistoryCodeTablePanel(){
		//postForHistoryCodeTablePanel.showPanel();
	}
	
	void showBkstamCodeTablePanel(){
		//bkstamCodeTablePanel.showPanel();
	}
	
	void showZipCityCodeTablePanel(){
		//zipCityCodeTablePanel.showPanel();
	}

	public void handleDbBackup() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		fc.setDialogTitle(
			Config.getInstance().getText(
				"filechooser.title.dbbackup"));
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File backupFile = fc.getSelectedFile();
			mainModel.backupDatabase(backupFile);
		}
	}

	public void handleDbRestore() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		fc.setDialogTitle(
			Config.getInstance().getText(
				"filechooser.title.dbrestore"));
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			mainModel.restoreDatabase(fc.getSelectedFile());
		}
	}

	public void handleDtaExport() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		fc.setDialogTitle(
			Config.getInstance().getText("filechooser.title.dtaexport"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new AllFileFilter());
		fc.addChoosableFileFilter(new DtaFileFilter());
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			int[] selRows = jTablePendingPayments.getSelectedRows();
			int numRows =
				selRows.length > 0
					? selRows.length
					: jTablePendingPayments.getRowCount();
			// create a sorted array of the original row indices
			int[] selRowsOrig = new int[numRows];
			if (selRows.length > 0) {
				for (int i = 0; i < numRows; i++) {
					selRowsOrig[i] =
						(
							(TableSorter)
								(
									jTablePendingPayments
										.getModel()))
										.getOriginalRowNumber(
							selRows[i]);
				}
				// sort the original array of indices
				Arrays.sort(selRowsOrig);
			} 

			List payments = new ArrayList();
			if (selRows != null) {
				PaymentTableModel tm =
					(PaymentTableModel) ((TableSorter) (jTablePendingPayments
						.getModel()))
						.getModel();
				for (int i = 0; i < numRows; i++) {
					if (selRows.length > 0) {
						payments.add(tm.getPayment(selRowsOrig[i]));
					} else {
						payments.add(tm.getPayment(i));
					}
				}
			}

			// add the .dta suffix to the file
			File selFile = fc.getSelectedFile();
			String fileName = Utils.addSuffix(selFile.getName(), "dta");
			File newFile = new File(selFile.getParent(), fileName);

			DataConverter.exportDtaFile(newFile, payments);
			Object[] options =
				{
					Config.getInstance().getText("msg.yes"),
					Config.getInstance().getText("msg.no")};
			int n =
				JOptionPane.showOptionDialog(
					this,
					Config.getInstance().getText("question_msg.dtacreated"),
					"",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (n == 0) {
				showDtaFile(newFile);
			}
			handleArchivePayments((selRows.length > 0) ? false : true);
		}
	}

	//Payments | DTA import action
	public void handleDtaImport() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		fc.setDialogTitle(
			Config.getInstance().getText(
				"filechooser.title.dtaimport"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new AllFileFilter());
		fc.addChoosableFileFilter(new DtaFileFilter());
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			DataConverter.importDtaFile(fc.getSelectedFile());
			//((PaymentTableModel)(jTablePendingPayments.getModel())).tableRowInserted();
			(
				(PaymentTableModel) ((TableSorter) (jTablePendingPayments
					.getModel()))
				.getModel())
				.tableRowInserted();
			// we are updating the history database
			//handleUpdateHistory();
		}
	}

	//Payments | DTA view action
	public void handleDtaView() {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();

		fc.setDialogTitle(
			Config.getInstance().getText(
				"filechooser.title.dtaimport"));
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new AllFileFilter());
		fc.addChoosableFileFilter(new DtaFileFilter());
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			showDtaFile(fc.getSelectedFile());
		}
	}

	public void showDtaFile(File dtaFile) {
		String htmlString = "<h2>DTA-File: " + dtaFile.getName() + "</h2>";
		htmlString
			+= "<table border=\"0\" cellpadding=\"3\" cellspacing=\"3\">";
		htmlString
			+= "<tr bgcolor=\"#C0C0C0\"><td>Art</td><td>Datum</td><td>Kto von</td><td>Begünstigter</td><td>Betrag</td></tr>";
		String tmp = DataConverter.readDtaFileIntoHtmlString(dtaFile);
		// replace all the ??row?? with alternating row color
		int i = 0;
		String rowTag = "??row??";
		String totalTag = "??total??";
		int l = rowTag.length();
		while (i != -1) {
			i = tmp.indexOf(rowTag);
			if (i != -1) {
				tmp = tmp.substring(0, i) + "white" + tmp.substring(i + l);
				i = tmp.indexOf(rowTag);
				if (i != -1) {
					tmp =
						tmp.substring(0, i) + "#F2F2F9" + tmp.substring(i + l);
				}
			}
		}
		i = tmp.indexOf(totalTag);
		if (i != -1) {
			tmp =
				tmp.substring(0, i)
					+ "#D1D1A3"
					+ tmp.substring(i + totalTag.length());
		}
		htmlString += tmp;
		htmlString += "</table>";

		JDialog htmlDialog = new JDialog(this);
		htmlDialog.setModal(true);
		htmlDialog.setSize(800, 600);
		JEditorPane htmlArea = new JEditorPane();
		htmlArea.setEnabled(false);
		htmlArea.setContentType("text/html");
		htmlArea.setText(htmlString);
		htmlDialog.getContentPane().add(htmlArea);
		htmlDialog.setVisible(true);
	}

	public void handleDeletePayments() {
		Object[] options =
			{
				Config.getInstance().getText("msg.yes"),
				Config.getInstance().getText("msg.no")};
		int n =
			JOptionPane.showOptionDialog(
				this,
				Config.getInstance().getText(
					"question_msg.delete"),
				Config.getInstance().getText("delete_msg.title"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]);
		if (n == 0) {
			int[] selRows = jTablePendingPayments.getSelectedRows();
			if (selRows != null) {
				List payments = new ArrayList();				
				for (int i = 0; i < selRows.length; i++) {
					payments.add(getPayment(jTablePendingPayments, selRows[i]));
				}
				MainModel.getInstance().removePayments(payments);
			}
		}
	}

	void handleArchivePayments(boolean all) {
		Object[] options =
			{
				Config.getInstance().getText("msg.yes"),
				Config.getInstance().getText("msg.no")};
		int n =
			JOptionPane.showOptionDialog(
				this,
				all
					? Config.getInstance().getText(
						"question_msg.archiveall")
					: Config.getInstance().getText(
						"question_msg.archive"),
				Config.getInstance().getText("archive_msg.title"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);

		if (n == 0) {
			int[] selRows;
			if (all) {
				selRows = new int[jTablePendingPayments.getRowCount()];
				for (int i = 0; i < selRows.length; i++) {
					selRows[i] = i;
				}
			} else {
				selRows = jTablePendingPayments.getSelectedRows();
			}
			if (selRows != null && selRows.length > 0) {
				List payments = new ArrayList();
				for (int i = 0; i < selRows.length; i++) {
					payments.add(getPayment(jTablePendingPayments, selRows[i]));
				}
				MainModel.getInstance().archivePayments(payments);
			}
		}
	}

	public void handleReactivatePayments() {
		Object[] options =
			{
				Config.getInstance().getText("msg.move"),
				Config.getInstance().getText("msg.cancel")};
		int n =
			JOptionPane.showOptionDialog(
				this,
				Config.getInstance().getText(
					"question_msg.reactivate"),
				Config.getInstance().getText(
					"reactivate_msg.title"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);

		if (n == 0) {
			int[] selRows = jTableArchivedPayments.getSelectedRows();
			if (selRows != null && selRows.length > 0) {
				List payments = new ArrayList();
				for (int i = 0; i < selRows.length; i++) {
					payments.add(getPayment(jTableArchivedPayments, selRows[i]));
				}
				MainModel.getInstance().reactivatePayments(payments);
			}
		}
	}

	void handleReusePayment() {
		JTable activeTable = getActivePaymentsTable();
		int[] selRows = activeTable.getSelectedRows();
		if (selRows.length == 1) {
			Payment oldPayment = getPayment(activeTable, selRows[0]);
			Payment newPayment = new Payment();
			newPayment.fill(oldPayment);
			newPayment.setId(null);
			newPayment.setIsPending(new Boolean(true));
			newPayment.setAmount(null);
			newPayment.setRefNr("");

			showPaymentSlip(newPayment);
//			switch (newPayment.getPaymentType().intValue()) {
//				case 1 :
//					showDialogInCenter(new PaymentSlipRed(this, newPayment));
//					break;
//				case 2 :
//					showDialogInCenter(
//						new PaymentSlipBlueOrange(this, newPayment, false));
//					break;
//				case 3 :
//					showDialogInCenter(new PaymentSlipBank(this, newPayment));
//					break;
//				case 4 :
//					showDialogInCenter(
//						new PaymentSlipBlueOrange(this, newPayment, true));
//					break;
//				default :
//					break;
//			}
		} else {
			// TODO
		}
		activeTable.clearSelection();
	}
	
	void handleShowChart(){
		new Charts(this);
	}


	public void showUserAccounts() {
		showDialogInCenter(new UserAccountTablePanel(this));
	}

	//Help | About action performed
	
	public void showAboutDialog() {
		showDialogInCenter(new AboutDialog(this));
	}

	void handleRegister() {
		// msg popup
		JOptionPane.showMessageDialog(
			this,Config.getInstance().getText("registration.text"),
			/*
			"This is a demoversion of PayIT. You can use all the \n"
				+ "functions of this program, but the data will not be saved \n"
				+ "on your harddisk. If you like this program, you can try out PayIT \n"
				+ "and pay the small fee of 20.-. Then write an email to: \n"
				+ "payit@truesolutions.ch and you will get the link for downloading \n"
				+ "the full version. You will also receive all the updates for free.",
			*/
			Config.getInstance().getText("msg.payittitle"),
			JOptionPane.INFORMATION_MESSAGE);

		Payment payment = new Payment();
		payment.setPaymentType(new Integer(Config.PAYMENT_TYPE_BANKCH)); // bank payment
		payment.setAmount(new Double(20));
		payment.setComment(Config.getInstance().getText("registration.comment"));
		payment.setForLine1("Daniel Seiler");
		payment.setForLine2("");
		payment.setForZip("");
		payment.setForCity("");
		payment.setForKtoBank("1137-0041.646");
		payment.setForClearing("713");
		payment.setReason(Config.getInstance().getText("registration.reason"));

		showPaymentSlip(payment);
	}
	
	/*
	 * Method needed by the PaymentListSelectionModel to show
	 * the payment slip that was selected on a JTable
	 */
	public void showPaymentSlip(JTable table, int row) {
		showPaymentSlip(getPayment(table,row));
	}

	public void showPaymentSlip(Payment p) {
		switch (p.getPaymentType().intValue()) {
			case Config.PAYMENT_TYPE_RED :
				PaymentSlip redSlip = new PaymentSlipRed(this);
				redSlip.fill(p);
				showDialogInCenter(redSlip);
				break;
			case Config.PAYMENT_TYPE_BLUE :
				PaymentSlip blueSlip = new PaymentSlipBlueOrange(this,false);
				blueSlip.fill(p);
				showDialogInCenter(blueSlip);
				break;
			case Config.PAYMENT_TYPE_BANKCH :
				PaymentSlip bankSlip = new PaymentSlipBank(this);
				bankSlip.fill(p);
				showDialogInCenter(bankSlip);
				break;
			case Config.PAYMENT_TYPE_ORANGE :
				PaymentSlip orangeSlip = new PaymentSlipBlueOrange(this,true);
				orangeSlip.fill(p);
				showDialogInCenter(orangeSlip);
				break;
			case Config.PAYMENT_TYPE_IPI :
				PaymentSlip ipiSlip = new PaymentSlipIpi(this);
				ipiSlip.fill(p);
				showDialogInCenter(ipiSlip);
				break;
			case Config.PAYMENT_TYPE_RED_IBAN:
				//p.setPaymentType(Config.PAYMENT_TYPE_IPI);
				PaymentSlip redSlipIban = new PaymentSlipRed(this,true);
				redSlipIban.fill(p);
				showDialogInCenter(redSlipIban);
				break;
			default :
				break;
		}
	}

	private Payment getPayment(JTable table, int row) {
		int origRow =
			((TableSorter) (table.getModel())).getOriginalRowNumber(row);
		Payment payment =
			(
				(PaymentTableModel) ((TableSorter) (table.getModel()))
					.getModel())
					.getPayment(
				origRow);
		return payment;
	}

	private void showDialogInCenter(JDialog dialog) {
		int xoff = (this.getWidth() - dialog.getWidth()) / 2;
		xoff = (xoff > 0) ? xoff : 0;
		int yoff = (this.getHeight() - dialog.getHeight()) / 2;
		yoff = (yoff > 0) ? yoff : 0;
		dialog.setLocation(this.getX() + xoff, this.getY() + yoff);
		dialog.show();
	}

	public JTable getActivePaymentsTable() {
		if (jTabbedPane1.getSelectedIndex() == 0)
			return jTablePendingPayments;
		else if (jTabbedPane1.getSelectedIndex() == 1)
			return jTableArchivedPayments;
		else {
			return jTablePendingPayments;
		}
	}

	public JTable getPassivePaymentsTable() {
		if (jTabbedPane1.getSelectedIndex() == 1)
			return jTablePendingPayments;
		else if (jTabbedPane1.getSelectedIndex() == 0)
			return jTableArchivedPayments;
		else {
			return jTablePendingPayments;
		}
	}

	private void initTables() {
		int[] preferredWidth = new int[]{40,80,70,120,120,260,93};
		TableSorter pendingSorter =
			new TableSorter(
				new PaymentTableModel(
					pendingPaymentsModel,
					jTablePendingPayments));
		jTablePendingPayments.setModel(pendingSorter);
		pendingSorter.addMouseListenerToHeaderInTable(jTablePendingPayments);

		jTablePendingPayments.setSelectionModel(
			new PaymentListSelectionModel(this, jTablePendingPayments));
		jTablePendingPayments.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//setting the default cell renderer for each column class
		PaymentTableCellRenderer pendingTcr = new PaymentTableCellRenderer();
		pendingTcr.setHighliteDateInPast(true);
		jTablePendingPayments.setDefaultRenderer(Number.class, pendingTcr);
		jTablePendingPayments.setDefaultRenderer(
			java.util.Date.class,
			pendingTcr);
		jTablePendingPayments.setDefaultRenderer(Object.class, pendingTcr);
		
		// setting the preferred widths
		for(int i=0; i<preferredWidth.length;i++) {
			jTablePendingPayments.getColumnModel().getColumn(i).setPreferredWidth(preferredWidth[i]);
		}
		
		TableSorter archivedSorter =
			new TableSorter(
				new PaymentTableModel(
					archivedPaymentsModel,
					jTableArchivedPayments));
		jTableArchivedPayments.setModel(archivedSorter);
		archivedSorter.addMouseListenerToHeaderInTable(jTableArchivedPayments);

		jTableArchivedPayments.setSelectionModel(
			new PaymentListSelectionModel(this, jTableArchivedPayments));

		//jTableArchivedPayments.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		//jTableArchivedPayments.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		jTableArchivedPayments.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//setting the default cell renderer for each column class
		PaymentTableCellRenderer archivedTcr = new PaymentTableCellRenderer();
		jTableArchivedPayments.setDefaultRenderer(Number.class, archivedTcr);
		jTableArchivedPayments.setDefaultRenderer(
			java.util.Date.class,
			archivedTcr);
		jTableArchivedPayments.setDefaultRenderer(Object.class, archivedTcr);

		// setting the preferred widths
		for(int i=0; i<preferredWidth.length;i++) {
			jTableArchivedPayments.getColumnModel().getColumn(i).setPreferredWidth(preferredWidth[i]);
		}
		/*
		jTableArchivedPayments.getColumnModel().getColumn(0).setPreferredWidth(
			40);
		jTableArchivedPayments.getColumnModel().getColumn(1).setPreferredWidth(
			80);
		jTableArchivedPayments.getColumnModel().getColumn(2).setPreferredWidth(
			70);
		jTableArchivedPayments.getColumnModel().getColumn(3).setPreferredWidth(
			120);
		jTableArchivedPayments.getColumnModel().getColumn(4).setPreferredWidth(
			120);
		jTableArchivedPayments.getColumnModel().getColumn(5).setPreferredWidth(
			260);
		jTableArchivedPayments.getColumnModel().getColumn(6).setPreferredWidth(
			93);
		*/
	}

	public void calculateAndSetPaymentSumAndNumber() {
		double sum = 0.0;
		int numOfPayments = 0;
		boolean hasSelectedColumns = true;

		// get the active table and all the selected rows on that table
		JTable activeTable = getActivePaymentsTable();
		int[] selRows = activeTable.getSelectedRows();

		// if no row was selected we take all the rows
		if (selRows == null || selRows.length == 0) {
			hasSelectedColumns = false;
			selRows = new int[activeTable.getRowCount()];
			for (int k = 0; k < selRows.length; k++)
				selRows[k] = k;
		}

		for (int i = 0; i < selRows.length; i++) {
			try {
				Payment p = getPayment(activeTable,selRows[i]);
				sum += p.getAmount().doubleValue();
				numOfPayments++;				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		// set the value in the status bar
		if (!hasSelectedColumns)
			jLabelNumber.setText(
				Config.getInstance().getText("statusbar.number")
					+ " :");
		else
			jLabelNumber.setText(
				Config.getInstance().getText(
					"statusbar.selected")
					+ " :");

		jLabelNumberResult.setText("" + numOfPayments);
		BigDecimal roundedSum = new BigDecimal(sum);
		roundedSum = roundedSum.setScale(2, BigDecimal.ROUND_HALF_UP);
		jLabelSumResult.setText(roundedSum.toString());
	}

	public void stateChanged(ChangeEvent e) {
		if (jTabbedPane1.getSelectedIndex() != activeTabIndex) {
			activeTabIndex = jTabbedPane1.getSelectedIndex();
			calculateAndSetPaymentSumAndNumber();
			setMenuItemsEnabled();
		}
	}

	private void setMenuItemsEnabled() {
		if (activeTabIndex == 0) // pending payments are shown
			{
			//actions
			dtaExportAction.setEnabled(true);
			deletePaymentAction.setEnabled(true);
			archivePaymentAction.setEnabled(true);
			reactivatePaymentAction.setEnabled(false);
		} else if (activeTabIndex == 1) // archived payments are shown
			{
			//actions
			dtaExportAction.setEnabled(false);
			deletePaymentAction.setEnabled(false);
			archivePaymentAction.setEnabled(false);
			reactivatePaymentAction.setEnabled(true);
		}
	}

	// type: "insert" "update" "remove"
	// target: "pending_payments" "archived_payments"
	public void refreshPaymentsTableDisplay(String target, String type) {
		if("pending_payments".equals(target)) {
			if("insert".equals(type)){
				jTablePendingPayments.clearSelection();
				((PaymentTableModel) ((TableSorter) (jTablePendingPayments.getModel()))
					.getModel())
					.tableRowInserted();
			} else if("remove".equals(type)) {
				jTablePendingPayments.clearSelection();
				((PaymentTableModel) ((TableSorter) (jTablePendingPayments.getModel()))
					.getModel())
					.tableRowDeleted();
			} else if("update".equals(type)) {
				((PaymentTableModel) ((TableSorter) (jTablePendingPayments.getModel()))
					.getModel())
					.tableRowUpdated();
			}
			// only recalculate the status bar sum if the pending
			// payments are in the active tab
			if (jTabbedPane1.getSelectedIndex() == 0) {
				calculateAndSetPaymentSumAndNumber();
			}
		}
		else if("archived_payments".equals(target)) {
			if("insert".equals(type)){
				jTableArchivedPayments.clearSelection();
				((PaymentTableModel) ((TableSorter) (jTableArchivedPayments.getModel()))
					.getModel())
					.tableRowInserted();
			} else if("remove".equals(type)) {
				jTableArchivedPayments.clearSelection();
				((PaymentTableModel) ((TableSorter) (jTableArchivedPayments.getModel()))
					.getModel())
					.tableRowDeleted();
			} else if("update".equals(type)) {
				((PaymentTableModel) ((TableSorter) (jTableArchivedPayments.getModel()))
					.getModel())
					.tableRowUpdated();
			}
			// only recalculate the status bar sum if the archived
			// payments are in the active tab
			if (jTabbedPane1.getSelectedIndex() == 1) {
				calculateAndSetPaymentSumAndNumber();
			}
		}
	}

	/**
	 * Methods of the Config.getInstance().ChangeListenerInterface
	 */
	public void applyLanguageChange() {
		
		// Sets the text values on every element
		this.setTitle((Config.getInstance().getText("main.title")+" "+Config.VERSION+(Config.getInstance().isDemoVersion() ? " (Demoversion)" : "")));
		
		menuFile.setText(
			Config.getInstance().getText("menu.file"));
		menuFileBackup.setText(
			Config.getInstance().getText("menu.file.backup"));
		menuFileRestore.setText(
			Config.getInstance().getText("menu.file.restore"));
		menuFileExit.setText(
			Config.getInstance().getText("menu.file.exit"));

		menuPay.setText(Config.getInstance().getText("menu.payments"));
		menuPayDta.setText(
			Config.getInstance().getText("menu.payments.dta"));
		menuPayDtaImport.setText(
			Config.getInstance().getText("menu.payments.dtaimport"));
		menuPayDtaView.setText(
			Config.getInstance().getText("menu.payments.dtaview"));
		menuPayDelete.setText(
			Config.getInstance().getText("menu.payments.delete"));
		menuPayArchive.setText(
			Config.getInstance().getText("menu.payments.archive"));
		menuPayReactivate.setText(
			Config.getInstance().getText("menu.payments.reactivate"));
		menuPayReuse.setText(
			Config.getInstance().getText("menu.payments.reuse"));
		menuPayChart.setText(
			Config.getInstance().getText("menu.payments.chart"));

		menuData.setText(
			Config.getInstance().getText("menu.data"));
		menuDataKtoFrom.setText(
			Config.getInstance().getText("menu.data.ktfrom"));
		menuDataUpdateHistory.setText(
			Config.getInstance().getText(
				"menu.data.updatehistory"));
		menuDataPostDirect.setText(
			Config.getInstance().getText("menu.data.postdirect"));
		menuDataPostIndirect.setText(
			Config.getInstance().getText("menu.data.postindirect"));
		menuDataBank.setText(
			Config.getInstance().getText("menu.data.bank"));
		menuDataBkstamm.setText(
			Config.getInstance().getText("menu.data.bkstam"));
		menuDataZipCity.setText(
			Config.getInstance().getText("menu.data.zipcity"));
				
		menuExtra.setText(
			Config.getInstance().getText("menu.extra"));
		menuExtraLanguage.setText(
			Config.getInstance().getText("menu.extra.language"));
		menuExtraLanguageGerman.setText(
			Config.getInstance().getText(
				"menu.extra.language.german"));
		menuExtraLanguageFrench.setText(
			Config.getInstance().getText(
				"menu.extra.language.french"));
		menuExtraLanguageItalian.setText(
			Config.getInstance().getText(
				"menu.extra.language.italian"));
		menuExtraLanguageEnglish.setText(
			Config.getInstance().getText(
				"menu.extra.language.english"));
		menuExtraLookAndFeel.setText(
			Config.getInstance().getText(
				"menu.extra.lookandfeel"));
		menuExtraLookAndFeelWindows.setText(
			Config.getInstance().getText(
				"menu.extra.lookandfeel.windows"));
		menuExtraLookAndFeelMotif.setText(
			Config.getInstance().getText(
				"menu.extra.lookandfeel.motif"));
		menuExtraLookAndFeelJava.setText(
			Config.getInstance().getText(
				"menu.extra.lookandfeel.java"));
		menuExtraLookAndFeelMacintosh.setText(
			Config.getInstance().getText(
				"menu.extra.lookandfeel.macintosh"));

		menuView.setText(
			Config.getInstance().getText("menu.view"));
		menuViewArchived.setText(
			Config.getInstance().getText("menu.view.archived"));
		menuViewPendent.setText(
			Config.getInstance().getText("menu.view.pendent"));

		menuHelp.setText(
			Config.getInstance().getText("menu.help"));
		menuHelpAbout.setText(
			Config.getInstance().getText("menu.help.about"));
		menuHelpRegister.setText(
			Config.getInstance().getText("menu.help.register"));

		menuPopupDta.setText(
			Config.getInstance().getText("menu.payments.dta"));
		//menuPopupDtaImport.setText(Config.getInstance().getText("menu.pay.dtaimport"));
		menuPopupDelete.setText(
			Config.getInstance().getText("menu.payments.delete"));
		menuPopupArchive.setText(
			Config.getInstance().getText("menu.payments.archive"));
		menuPopupReactivate.setText(
			Config.getInstance().getText("menu.payments.reactivate"));
		menuPopupReuse.setText(
			Config.getInstance().getText("menu.payments.reuse"));

		jButton1.setToolTipText(
			Config.getInstance().getText("toolbar.esred"));
		jButton2.setToolTipText(
			Config.getInstance().getText("toolbar.esblue"));
		jButton3.setToolTipText(
			Config.getInstance().getText("toolbar.esbank"));
		jButton4.setToolTipText(
			Config.getInstance().getText("toolbar.esorange"));
		jButton5.setToolTipText(
				Config.getInstance().getText("toolbar.esipi"));
		jButton6.setToolTipText(
				Config.getInstance().getText("toolbar.esrediban"));

		JTable table = null;
		if (jTabbedPane1.getSelectedIndex() == 0)
			table = jTablePendingPayments;
		else
			table = jTableArchivedPayments;
		if (table.getSelectionModel().isSelectionEmpty())
			jLabelNumber.setText(
				Config.getInstance().getText("statusbar.number")
					+ " :");
		else
			jLabelNumber.setText(
				Config.getInstance().getText(
					"statusbar.selected")
					+ " :");
		jLabelSum.setText(
			Config.getInstance().getText("statusbar.total")
				+ " :");

		jTabbedPane1.setTitleAt(
			0,
			Config.getInstance().getText("main.tab1"));
		jTabbedPane1.setTitleAt(
			1,
			Config.getInstance().getText("main.tab2"));
		jTabbedPane1.setToolTipTextAt(
			0,
			Config.getInstance().getText("main.tab1"));
		jTabbedPane1.setToolTipTextAt(
			1,
			Config.getInstance().getText("main.tab2"));

		// customize the JFileChooser
		UIManager.put(
			"FileChooser.fileNameLabelText",
			Config.getInstance().getText(
				"filechooser.label.filename"));
		//UIManager.put ("FileChooser.fileNameLabelMnemonic",new Integer ('f'));
		UIManager.put(
			"FileChooser.lookInLabelText",
			Config.getInstance().getText(
				"filechooser.label.search"));
		//UIManager.put ("FileChooser.lookInLabelMnemonic",new Integer ('d'));
		UIManager.put(
			"FileChooser.filesOfTypeLabelText",
			Config.getInstance().getText(
				"filechooser.label.filetype"));
		//UIManager.put ("FileChooser.filesOfTypeLabelMnemonic",new Integer('t'));
		UIManager.put(
			"FileChooser.upFolderToolTipText",
			Config.getInstance().getText(
				"filechooser.tooltip.up"));
		UIManager.put(
			"FileChooser.homeFolderToolTipText",
			Config.getInstance().getText(
				"filechooser.tooltip.home"));
		UIManager.put(
			"FileChooser.newFolderToolTipText",
			Config.getInstance().getText(
				"filechooser.tooltip.newfolder"));
		UIManager.put(
			"FileChooser.listFolderToolTipText",
			Config.getInstance().getText(
				"filechooser.tooltip.list"));
		UIManager.put(
			"FileChooser.detailsFolderToolTipText",
			Config.getInstance().getText(
				"filechooser.tooltip.details"));

		UIManager.put(
			"FileChooser.cancelButtonText",
			Config.getInstance().getText(
				"filechooser.button.cancel"));
		UIManager.put(
			"FileChooser.openButtonText",
			Config.getInstance().getText(
				"filechooser.button.open"));
	}

	public void setLookAndFeel(String lnfName) {
		try {
			UIManager.setLookAndFeel(lnfName);
			SwingUtilities.updateComponentTreeUI(this);
			//this.pack();
		} catch (Exception e) {
		}
	}

	/**
	 * Inner Class for the PopUp menu
	 */
	class PopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				menuPopup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
