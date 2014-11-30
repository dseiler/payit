package ch.truesolutions.payit.model;

import java.util.*;
import java.io.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Config {
		
	private ResourceBundle messages;
	private Locale locale = Locale.getDefault();
	private static Properties props = new Properties();
	private Vector configChangeListenerList = new Vector();
	private boolean isDemoVersion = true;
	private String lookAndFeel = "";
	private String defaultAccount = "";
	private String dbDateFormat = "yyyy-MM-dd";
	// date format for saving to the database
	private String displayDateFormat = "dd.MM.yyyy";
	// date format for displaying
	private SimpleDateFormat sdf = new SimpleDateFormat();

	private static Config instance = new Config();
	// some constants
	public static final int PAYMENT_TYPE_RED = 1;
	public static final int PAYMENT_TYPE_BLUE = 2;
	public static final int PAYMENT_TYPE_BANKCH = 3;
	public static final int PAYMENT_TYPE_ORANGE = 4;
	public static final int PAYMENT_TYPE_IPI = 5;
	public static final int PAYMENT_TYPE_RED_IBAN = 6; // only used in the gui not in the dta module, real type is ipi
	
	public static final String VERSION = "1.6";

	public static Config getInstance() {
		return instance;
	}
	
	private Config() {
		// load the properties file
		String languageISO = locale.getISO3Language();
		//FileInputStream fi = null;
		try {
			//fi = new FileInputStream("payit.properties");
			props.load(ClassLoader.getSystemResourceAsStream("payit.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		languageISO = props.getProperty("language",Locale.getDefault().getISO3Language());
		displayDateFormat = props.getProperty("date.format",displayDateFormat);
		lookAndFeel = props.getProperty("look.and.feel",lookAndFeel);
		defaultAccount = props.getProperty("default.account","");
		String code = props.getProperty("code.unlock","");
		if("godisgood".equals(code)) {
			isDemoVersion = false;
		}
		props.setProperty("default.account",defaultAccount);
		props.setProperty("date.format",displayDateFormat);
		storeProperties();
		setLanguage(languageISO);
		setLookAndFeel(lookAndFeel);	
	}
	
	private void storeProperties() {
		try {	
			URL fileUrl = ClassLoader.getSystemResource("payit.properties");
			FileOutputStream fout = new FileOutputStream(fileUrl.getPath());
			props.store(fout,"payit properties");
			fout.close();
		} catch (FileNotFoundException e1) {
			throw new RuntimeException(e1);
		} catch (IOException e2) {
			throw new RuntimeException(e2);
		}					
	}
	/*
	public static ResourceBundle getResourceBundle() {
		return myResources;
	}
	*/

	public void setLanguage(String languageISO) {
		// update databse
		if(Locale.ENGLISH.getISO3Language().equals(languageISO)) {
			locale = Locale.ENGLISH;
		} else if(Locale.GERMAN.getISO3Language().equals(languageISO)) {
			locale = Locale.GERMAN;
		} else if(Locale.FRENCH.getISO3Language().equals(languageISO)) {
			locale = Locale.FRENCH;
		} else if(Locale.ITALIAN.getISO3Language().equals(languageISO)) {
			locale = Locale.ITALIAN;
		} else {
			locale = Locale.getDefault();
		}
		messages = ResourceBundle.getBundle("messages", locale);
		props.setProperty("language",locale.getISO3Language());
		notifyConfigChangeListeners();
		storeProperties();
	}
	
	public String getLanguage() {
		return locale.getISO3Language();
	}
	

	public void addConfigChangeListener(ConfigChangeListener cfcl) {
		configChangeListenerList.add(cfcl);
	}

	public String getLookAndFeel() {
		return lookAndFeel;
	}

	public void setLookAndFeel(String lnfName) {
		lookAndFeel = lnfName;
		props.setProperty("look.and.feel",lnfName);
		storeProperties();
		// notify the listeners
		Iterator it = configChangeListenerList.iterator();
		while (it.hasNext())
			 ((ConfigChangeListener) it.next()).setLookAndFeel(lnfName);
	}

	public String getDefaultAccount() {
		return defaultAccount;
	}

	public void setDefaultAccount(String account) {
		// update databse
		defaultAccount = account;
		props.setProperty("default.account",account);
		storeProperties();
	}

	public String convertToDbDateString(String s) {
		sdf.applyPattern(displayDateFormat);
		try {
			java.util.Date d = sdf.parse(s);
			sdf.applyPattern(dbDateFormat);
			return sdf.format(d);
		} catch (ParseException e) {
			return "";
		}
	}

	public String convertToDisplayDateString(String s) {
		sdf.applyPattern(dbDateFormat);
		try {
			java.util.Date d = sdf.parse(s);
			sdf.applyPattern(displayDateFormat);
			return sdf.format(d);
		} catch (ParseException e) {
			return "";
		}
	}

	public String getDisplayDateString(java.util.Date d) {
		if (d != null) {
			sdf.applyPattern(displayDateFormat);
			return sdf.format(d);
		} else
			return "";
	}

	public java.util.Date getDateFromDisplayDateString(
		String displayDateString) {
		sdf.applyPattern(displayDateFormat);
		try {
			java.util.Date d = sdf.parse(displayDateString);
			return d;
		} catch (ParseException e) {
			return null;
		}
	}

	public String getDisplayDateFormat() {
		return displayDateFormat;
	}
	
	public String getText(String key){
		try {
			return messages.getString(key);
		} catch (MissingResourceException e){
		}
		return key;
	}

	public void notifyConfigChangeListeners() {
		// go through the configChangeListenerList and notify the Listeners
		Iterator it = configChangeListenerList.iterator();
		while (it.hasNext())
			 ((ConfigChangeListener) it.next()).applyLanguageChange();
	}
	/**
	 * @return
	 */
	public boolean isDemoVersion() {
		return isDemoVersion;
	}

}