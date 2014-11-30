/**
 * Title:        PayIT<p>
 * Description:  Payment Software<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.5
 *
 * Releasehistory:
 * v1.1
 * Historytables, bugfixes, improved calendar
 * v1.2 - 14.Juli 2002
 * replace existing db backup function with backup in xml format,
 * including nano xml parser, fixing some bugs, defining the account nr
 * in the useraccounts table as primary key
 * v1.3 - 9.September 2002
 * fixing table sorting and table header. improving desing
 * of bank payments. preparing ipi. adding dta viewer.
 * v1.4 - 27.Oktober 2002
 * making table selections stay after sorting. adding IPI.
 * adding monthly reporting graphs
 */

package ch.truesolutions.payit.model;

import java.io.*;
import java.util.StringTokenizer;

//import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PayIT {

	private static Logger logger = Logger.getLogger(DAO.class.getName());

	public PayIT(boolean isSwt) {
		databaseInit();
		
		if(isSwt) {
			ch.truesolutions.payit.view.swt.MainFrame w = new ch.truesolutions.payit.view.swt.MainFrame();
			w.open();
			System.exit(0);
		} else {
			ch.truesolutions.payit.view.swing.MainFrame w = new ch.truesolutions.payit.view.swing.MainFrame();
			w.setVisible(true);
		}
	}

	private void databaseInit() {
		//initConfigTable();
		initZipCodesTable();
		initCurrencyTable();
		initBkstamTable();
		initPaymentsTable();
		initUserAccountsTable();
		initPostToHistoryTable();
		initPostForHistoryTable();
		initBankHistoryTable();
	}

	private void initZipCodesTable() {
		if (!DAO.getInstance().doesTableExist("ZipCodes")) {
			DAO.getInstance().executeUpdate(
				"CREATE TABLE ZipCodes (zip VARCHAR(10),city VARCHAR(50),region VARCHAR(10))");
			logger.info("table ZipCodes created");
			try {
				// read the data from the text file
				InputStreamReader isr =
					new InputStreamReader(
						ClassLoader.getSystemResourceAsStream("zipcodes.txt"));
				BufferedReader br = new BufferedReader(isr);
				String line;
				String zip = "";
				String city = "";
				String region = "";
				while (br.ready()) {
					line = br.readLine();
					if (line != null) {
						StringTokenizer st = new StringTokenizer(line, ",");
						if (st.hasMoreTokens())
							zip = st.nextToken();
						if (st.hasMoreTokens())
							city = st.nextToken();
						if (st.hasMoreTokens())
							region = st.nextToken();
						DAO.getInstance().executeUpdate(
							"INSERT INTO ZipCodes (zip,city,region) VALUES('"
								+ zip
								+ "','"
								+ city
								+ "','"
								+ region
								+ "')");
					}
				}
				isr.close();
				br.close();
			} catch (EOFException e) {
				logger.warn("unexpected end of file reached...");
			} catch (IOException e) {
				logger.error("error: " + e);
			}
		}
	}

	private void initCurrencyTable() {
		//DAO.getInstance().executeUpdate("DROP TABLE Currencies");
		if (!DAO.getInstance().doesTableExist("Currencies")) {
			DAO.getInstance().executeUpdate(
				"CREATE TABLE Currencies (symbol VARCHAR(3),country VARCHAR(50),name VARCHAR(30),prio INTEGER)");
			logger.info("table Currencies created");
			try {
				// read the data from the text file
				InputStreamReader isr =
					new InputStreamReader(
						ClassLoader.getSystemResourceAsStream(
							"currencies.txt"));
				BufferedReader br = new BufferedReader(isr);
				String line;
				String symbol = "";
				String country = "";
				String name = "";
				short prio = 3;
				while (br.ready()) {
					line = br.readLine();
					if (line != null) {
						symbol = "";
						country = "";
						name = "";
						prio = 3;
						StringTokenizer st = new StringTokenizer(line, ",");
						if (st.hasMoreTokens())
							symbol = st.nextToken().trim();
						if (st.hasMoreTokens())
							country = st.nextToken().trim();
						if (st.hasMoreTokens())
							name = st.nextToken().trim();
						if (st.hasMoreTokens()) {
							try {
								prio =
									new Short(st.nextToken().trim())
										.shortValue();
							} catch (NumberFormatException e) {
								logger.error(e);
							}
						}
						DAO.getInstance().executeUpdate(
							"INSERT INTO Currencies (symbol,country,name,prio) VALUES('"
								+ symbol
								+ "','"
								+ country
								+ "','"
								+ name
								+ "',"
								+ prio
								+ ")");
					}
				}
				isr.close();
				br.close();
			} catch (EOFException e) {
				logger.warn("unexpected end of file reached...");
			} catch (IOException e) {
				logger.error("error: " + e);
			}
		}
	}

	private void initBkstamTable() {
		//DatabaseObject.executeUpdate("DROP TABLE Bkstam");
		if (!DAO.getInstance().doesTableExist("Bkstam")) {
			DAO.getInstance().executeUpdate(
				"CREATE TABLE Bkstam (type VARCHAR(2),clearing VARCHAR(5),language VARCHAR(1),shortName VARCHAR(15),fullName VARCHAR(60),"
					+ "address VARCHAR(35),postAddr VARCHAR(35),zip VARCHAR(10),city VARCHAR(35),phone VARCHAR(18),fax VARCHAR(18),countryPrefix VARCHAR(5),"
					+ "countryCode VARCHAR(2),postKto VARCHAR(12),swift VARCHAR(14))");
			logger.info("table Bkstam created");
			try {
				// read the data from the text file
				InputStreamReader isr =
					new InputStreamReader(
						ClassLoader.getSystemResourceAsStream("bkstamm.txt"));
				BufferedReader br = new BufferedReader(isr);
				String line;
				String type = "";
				String clearing = "";
				//String clearingNew = "";
				String language = "";
				String shortName = "";
				String fullName = "";
				String address = "";
				String postAddr = "";
				String zip = "";
				String city = "";
				String phone = "";
				String fax = "";
				String countryPrefix = "";
				String countryCode = "";
				String postKto = "";
				String swift = "";

				while (br.ready()) {
					//clearingNew = "";
					line = br.readLine();
					if (line != null) {

						type = line.substring(0, 2).trim();
						clearing = line.substring(2, 7).trim();
						//clearingNew = line.substring(11, 16).trim();
						language = line.substring(38, 39).trim();
						shortName = line.substring(39, 44).trim();
						fullName = line.substring(54, 114).trim();
						address = line.substring(114, 149).trim();
						postAddr = line.substring(149, 184).trim();
						zip = line.substring(184, 194).trim();
						city = line.substring(194, 229).trim();
						phone = line.substring(229, 247).trim();
						fax = line.substring(247, 265).trim();
						countryPrefix = line.substring(265, 270).trim();
						countryCode = line.substring(270, 272).trim();
						postKto = line.substring(272, 284).trim();
						swift = line.substring(284, 298).trim();

						/*
						if(clearingNew != "")
							clearing = clearingNew;
						*/

						DAO.getInstance().executeUpdate(
							"INSERT INTO Bkstam (type,clearing,language,shortName,fullName,"
								+ "address,postAddr,zip,city,phone,fax,countryPrefix,countryCode,"
								+ "postKto,swift) VALUES('"
								+ type
								+ "','"
								+ clearing
								+ "','"
								+ language
								+ "','"
								+ shortName
								+ "','"
								+ fullName
								+ "','"
								+ address
								+ "','"
								+ postAddr
								+ "','"
								+ zip
								+ "','"
								+ city
								+ "','"
								+ phone
								+ "','"
								+ fax
								+ "','"
								+ countryPrefix
								+ "','"
								+ countryCode
								+ "','"
								+ postKto
								+ "','"
								+ swift
								+ "')");
					}
				}
				isr.close();
				br.close();
			} catch (EOFException e) {
				logger.warn("unexpected end of file reached...");
			} catch (IOException e) {
				logger.error("error: " + e);
			}
		}
	}

	private void initPaymentsTable() {
		//DatabaseObject.executeUpdate("DROP TABLE Payments");
		if (!DAO.getInstance().doesTableExist("Payments")) {
			DAO.getInstance().executeUpdate(
				"CREATE TABLE Payments ("
					+ "id INTEGER NOT NULL PRIMARY KEY, paymentType INTEGER NOT NULL,"
					+ "isPending BIT NOT NULL, execDate DATE, amount FLOAT,"
					+ "fromKto VARCHAR(40), comment VARCHAR(100),"
					+ "forLine1 VARCHAR(35), forLine2 VARCHAR(35), forLine3 VARCHAR(35),"
					+ "forZip VARCHAR(10), forCity VARCHAR(30), forKtoBank VARCHAR(40), forClearing VARCHAR(10),"
					+ "toLine1 VARCHAR(35), toLine2 VARCHAR(35), toLine3 VARCHAR(35),"
					+ "toZip VARCHAR(10), toCity VARCHAR(30), toKtoPost VARCHAR(20), refNr VARCHAR(50),"
					+ "reason VARCHAR(150), isSalary BIT, currency VARCHAR(3), blz VARCHAR(30),"
					+ "swift VARCHAR(40), instruction VARCHAR(210), costs VARCHAR(3), form VARCHAR(2)"
					+ ")");
			logger.info("table Payments created");
		}
	}

	private void initUserAccountsTable() {
		//DatabaseObject.executeUpdate("DROP TABLE UserAccounts");
		if (!DAO.getInstance().doesTableExist("UserAccounts")) {
			DAO.getInstance().executeUpdate(
				"CREATE TABLE UserAccounts"
					+ "(accountNr VARCHAR(30) NOT NULL PRIMARY KEY,clearing VARCHAR(6), iban VARCHAR(40), currency VARCHAR(3), description VARCHAR(30), line1 VARCHAR(30),line2 VARCHAR(30),line3 VARCHAR(30),zip VARCHAR(6),city VARCHAR(30))");
			logger.info("table UserAccounts created");
		}
	}

	// init payments history tables
	private void initPostToHistoryTable() {
		//DatabaseObject.executeUpdate("DROP TABLE PostToHistory");
		if (!DAO.getInstance().doesTableExist("PostToHistory")) {
			// Payment Type: 1=Red,2=Blue,3=Bank
			DAO.getInstance().executeUpdate(
				"CREATE TABLE PostToHistory ("
					+ "ktoPost VARCHAR(20) NOT NULL PRIMARY KEY,"
					+ "line1 VARCHAR(30), line2 VARCHAR(30), line3 VARCHAR(30),"
					+ "zip VARCHAR(10), city VARCHAR(20)"
					+ ")");
			logger.info("table PostToHistory created");
		}
	}

	private void initPostForHistoryTable() {
		//DatabaseObject.executeUpdate("DROP TABLE PostForHistory");
		if (!DAO.getInstance().doesTableExist("PostForHistory")) {
			// Payment Type: 1=Red,2=Blue,3=Bank
			DAO
				.getInstance()
				.executeUpdate(
					"CREATE TABLE PostForHistory ("
					+ "ktoPost VARCHAR(20) NOT NULL,"
					+ "line1 VARCHAR(30) NOT NULL," // line1 and ktoPost form the key !!
			+"line2 VARCHAR(30), line3 VARCHAR(30),"
				+ "zip VARCHAR(10), city VARCHAR(20)"
				+ ")");
			logger.info("table PostForHistory created");
		}
	}

	private void initBankHistoryTable() {
		//DatabaseObject.executeUpdate("DROP TABLE BankHistory");
		if (!DAO.getInstance().doesTableExist("BankHistory")) {
			// Payment Type: 1=Red,2=Blue,3=Bank
			DAO.getInstance().executeUpdate(
				"CREATE TABLE BankHistory ("
					+ "ktoBank VARCHAR(20)  NOT NULL PRIMARY KEY,"
					+ "clearing VARCHAR(10) NOT NULL,"
					+ "line1 VARCHAR(30), line2 VARCHAR(30), line3 VARCHAR(30),"
					+ "zip VARCHAR(10), city VARCHAR(20), ktoPost VARCHAR(20)"
					+ ")");
			logger.info("table BankHistory created");
		}
	}

	//Main method
	public static void main(String[] args) {

		PropertyConfigurator.configure(
					ClassLoader.getSystemResource("logger.properties"));
		
		boolean isSwt = false;
		if(args.length == 1) {
			if("swt".equals(args[0])) {
				isSwt = true;
			}
		}
		//		try {
		//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		new PayIT(isSwt);
	}
}
