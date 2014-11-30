/*
 * Created on 15.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;
import java.util.zip.*;

import ch.truesolutions.payit.exceptions.InvalidDataException;

import net.n3.nanoxml.*;

/**
 * @author Daniel
 */
public class MainModel {

	private static MainModel instance;
	
	public static MainModel getInstance() {
		if (instance == null) {
			instance = new MainModel();
		}
		return instance;
	}

	private PaymentsModel pendingPaymentsModel;
	private PaymentsModel archivedPaymentsModel;
	private UserAccountsModel userAccountsModel;
	private PropertyChangeSupport support;
	
	// code table models
	private CodeTableModel zipCityCodeTableModel;
	private CodeTableModel currencyCodeTableModel;
	private CodeTableModel bankHistoryCodeTableModel;
	private CodeTableModel postToHistoryCodeTableModel;
	private CodeTableModel postForHistoryCodeTableModel;
	private CodeTableModel bkstamCodeTableModel;

	/**
	 * 
	 */
	private MainModel() {
		super();
		support = new PropertyChangeSupport(this);
		// SQL queries for the pending and archived payments
		StringBuffer constraint = new StringBuffer("SELECT ");
		constraint.append(
			"id,paymentType,isPending,execDate,amount,fromKto,comment,");
		constraint.append(
			"forLine1,forLine2,forLine3,forZip,forCity,forKtoBank,forClearing,");
		constraint.append(
			"toLine1,toLine2,toLine3,toZip,toCity,toKtoPost,refNr,reason,isSalary,");
		constraint.append("currency,blz,swift,instruction,costs,form ");
		constraint.append("FROM Payments ");

		StringBuffer pendingConstraint =
			new StringBuffer(constraint.toString());
		pendingConstraint.append(
			"WHERE isPending = true ORDER BY execDate,amount");
		StringBuffer archivedConstraint =
			new StringBuffer(constraint.toString());
		archivedConstraint.append(
			"WHERE isPending = false ORDER BY execDate,amount");

		pendingPaymentsModel = new PaymentsModel(pendingConstraint.toString());
		archivedPaymentsModel =
			new PaymentsModel(archivedConstraint.toString());
		userAccountsModel = new UserAccountsModel();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		support.removePropertyChangeListener(l);
	}

	/**
	 * @return
	 */
	public void addPayment(Payment p){
		if(p.getIsPending().booleanValue()){		
			List payments = pendingPaymentsModel.getPayments();
			int newIndex = getInsertingIndexOfNewPayment(payments,p);
			p.insert();
			payments.add(newIndex,p);
			support.firePropertyChange("pending_payments","insert", "new");
			
			int updateType = p.updateHistory(true);
			refreshHistoryTable(updateType);
		} 
		// archived payments should only be insert directly through the
		// restore databse command!!
		else {
			List payments = archivedPaymentsModel.getPayments();
			int newIndex = getInsertingIndexOfNewPayment(payments,p);
			p.insert();
			payments.add(newIndex,p);
			support.firePropertyChange("archived_payments","insert", "new");
			
			int updateType = p.updateHistory(true);
			refreshHistoryTable(updateType);
		}
	}
	
	private void refreshHistoryTable(int updateType){
		switch(updateType){
			case Payment.BANK_HISTORY_UPDATE:
				getBankHistoryCodeTableModel().refresh();
				break;
			case Payment.POSTTO_HISTORY_UPDATE:
				getPostToHistoryCodeTableModel().refresh();
				break;
			case Payment.POSTTO_HISTORY_AND_BANKHISTORY_UPDATE:
				getPostToHistoryCodeTableModel().refresh();
				getBankHistoryCodeTableModel().refresh();
				break;
			case Payment.POSTTO_HISTORY_AND_POSTFOR_HISTORY_UPDATE:
				getPostToHistoryCodeTableModel().refresh();
				getPostForHistoryCodeTableModel().refresh();
				break;
		}		
	}
	
	private int getInsertingIndexOfNewPayment(List payments,Payment p) {
		int index = payments.size();
		for(int i=0;i<payments.size();i++){
			int result = p.compareTo(payments.get(i));
			if(result == -1){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void updatePayment(Payment p){
		if(p.getIsPending().booleanValue()){		
			p.update();
			support.firePropertyChange("pending_payments","update", "new");
			int updateType = p.updateHistory(true);
			refreshHistoryTable(updateType);
		} else {
			throw new RuntimeException("cannot update archived payments!");
		}
	}
	
	public void removePayments(List payments){
		if(pendingPaymentsModel.getPayments().containsAll(payments)){
			Iterator it = payments.iterator();
			while(it.hasNext()){
				Payment p = (Payment)it.next();
				p.delete();
				pendingPaymentsModel.getPayments().remove(p);
			}
			support.firePropertyChange("pending_payments","remove", "new");
		} else {
			throw new RuntimeException("only pending payment can be removed !");
		}		
	}
		
	public void reactivatePayments(List ps){
		if(archivedPaymentsModel.getPayments().containsAll(ps)){
			Iterator it = ps.iterator();
			while(it.hasNext()){
					Payment p = (Payment)it.next();
					p.setIsPending(new Boolean(true));
					p.update();
					archivedPaymentsModel.getPayments().remove(p);
					List payments = pendingPaymentsModel.getPayments();
					int newIndex = getInsertingIndexOfNewPayment(payments,p);
					payments.add(newIndex,p);
			}
			support.firePropertyChange("archived_payments","remove", "new");
			support.firePropertyChange("pending_payments","insert", "new");
		} else {
			throw new RuntimeException("pending payment cannot be reactivated !");
		}					
	}
	
	public void archivePayments(List ps){
		if(pendingPaymentsModel.getPayments().containsAll(ps)){
			Iterator it = ps.iterator();
			while(it.hasNext()){
				Payment p = (Payment)it.next();
				p.setIsPending(new Boolean(false));
				p.update();
				pendingPaymentsModel.getPayments().remove(p);
				List payments = archivedPaymentsModel.getPayments();
				int newIndex = getInsertingIndexOfNewPayment(payments,p);
				payments.add(newIndex,p);
			}
			support.firePropertyChange("archived_payments","insert", "new");
			support.firePropertyChange("pending_payments","remove", "new");
		} else {
			throw new RuntimeException("archived payment cannot be archived !");
		}		
	}
	
	public void backupDatabase(File backupFile) {
		BufferedWriter bw;

		// new
		FileOutputStream fos;
		ZipOutputStream zos;
		OutputStreamWriter osw;

		try {
			fos = new FileOutputStream(backupFile);
			zos = new ZipOutputStream(fos);
			zos.putNextEntry(new ZipEntry("payitdata.xml"));
			zos.setComment("payit database");
			osw = new OutputStreamWriter(zos);
			bw = new BufferedWriter(osw);

			bw.write("<payit-data>", 0, "<payit-data>".length());
			bw.newLine();

			List allPayments = new ArrayList();
			allPayments.addAll(getPendingPaymentsModel().getPayments());
			allPayments.addAll(getArchivedPaymentsModel().getPayments());
			
			for (Iterator it = allPayments.iterator();it.hasNext();) {
				String xmlPayment = ((Payment)it.next()).toXmlString();
				bw.write(xmlPayment, 0, xmlPayment.length());
				bw.newLine();
			}
			
			List userAccounts = getUserAccountsModel().getUserAccounts();
			for (Iterator it = userAccounts.iterator();it.hasNext();) {
				String xmlUseraccount = ((UserAccount)it.next()).toXmlString();
				bw.write(xmlUseraccount, 0, xmlUseraccount.length());
				bw.newLine();
			}
			
			bw.write("</payit-data>", 0, "</payit-data>".length());
			bw.newLine();

			bw.flush();
			zos.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}

	public void restoreDatabase(File f) {
		IXMLElement xml = null;
		try{	
			FileInputStream fis = new FileInputStream(f);
			ZipInputStream zis = new ZipInputStream(fis);
			zis.getNextEntry();
			InputStreamReader isr = new InputStreamReader(zis);

			// parsing a la dom with nanoxml java
			IXMLParser parser = XMLParserFactory.createDefaultXMLParser();
			IXMLReader reader = new StdXMLReader(isr);
			parser.setReader(reader);
			xml = (IXMLElement) parser.parse();

			isr.close();
			zis.close();
			fis.close();
		}catch(FileNotFoundException e){} catch (IOException e) {
			throw new RuntimeException(e);	
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);	
		} catch (InstantiationException e) {
			throw new RuntimeException(e);	
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);	
		} catch (XMLException e) {
			throw new RuntimeException(e);		
		}
		if (xml != null && xml.getName().equals("payit-data")) {
			Vector children = xml.getChildren();
			// go through the children vector and insert
			// either a payment or a useraccount
			Iterator it = children.iterator();
			int count = 0;
			XMLElement child = null;
			while (it.hasNext()) {
				count++;
				child = (XMLElement) (it.next());
				if (child != null && child.getName().equals("payment")) {
					Payment p = new Payment();
					p.fill(child);
					addPayment(p);
				} else if (
					child != null
						&& child.getName().equals("useraccount")) {
					UserAccount ua = new UserAccount(child);
					try {
						getUserAccountsModel().addUserAccount(ua);
					} catch (InvalidDataException ie) {
						throw new RuntimeException(ie);
					}
				}
			}
		} else {
			throw new RuntimeException(
				"couldn't restore db! unknown tag name :" + xml.getName());
		}
	}
	
	public PaymentsModel getArchivedPaymentsModel() {
		return archivedPaymentsModel;
	}

	public PaymentsModel getPendingPaymentsModel() {
		return pendingPaymentsModel;
	}
	
	public UserAccountsModel getUserAccountsModel() {
		return userAccountsModel;
	}
	
	/**
	 * Methods for getting all the code table models
	 * as singletons
	 */
	public CodeTableModel getZipCityCodeTableModel() {
		if(zipCityCodeTableModel == null){
			String[] titles = {"table.zip", "table.city"};
			zipCityCodeTableModel = new CodeTableModel("SELECT zip,city FROM ZipCodes ",titles,"zipcity.title");
		}
		return zipCityCodeTableModel;
	}

	public CodeTableModel getCurrencyCodeTableModel() {
		if(currencyCodeTableModel == null){
			String[] titles = {"table.symbol", "table.country", "table.name"};
			currencyCodeTableModel = new CodeTableModel("SELECT symbol,country,name FROM Currencies ",titles,"currency.title");
		}
		return currencyCodeTableModel;
	}


	public CodeTableModel getBankHistoryCodeTableModel() {
		String query =
			"SELECT bankhistory.ktoBank,bankhistory.clearing,bankhistory.line1,"+
			"bankhistory.line2,bankhistory.line3,bankhistory.zip,bankhistory.city,"+
			"bankhistory.ktoPost,posttohistory.line1,posttohistory.line2,posttohistory.line3,"+
			"posttohistory.zip,posttohistory.city "+
			"FROM BankHistory LEFT JOIN posttohistory ON bankhistory.ktopost = posttohistory.ktopost ";

		if(bankHistoryCodeTableModel == null){
			String[] titles = {"table.kto", "table.clearing","table.adress","table.adress","table.adress", "table.zip","table.city", "table.postaccount"};
			bankHistoryCodeTableModel = new CodeTableModel(query,titles,"bankhistorypanel.title");
		}
		return bankHistoryCodeTableModel;
	}

	public CodeTableModel getPostToHistoryCodeTableModel() {
		if(postToHistoryCodeTableModel == null){
			String[] titles = {"table.postaccount","table.address1","table.address2","table.address3","table.zip","table.city"};
			postToHistoryCodeTableModel = new CodeTableModel("SELECT ktopost,line1,line2,line3,zip,city FROM PostToHistory ",titles,"posttohistorypanel.title");
		}
		return postToHistoryCodeTableModel;
	}

	public CodeTableModel getPostForHistoryCodeTableModel() {
		String query =
			"SELECT postforhistory.ktopost,postforhistory.line1,"+
			"postforhistory.line2,postforhistory.line3,postforhistory.zip,postforhistory.city,"+
			"posttohistory.line1,posttohistory.line2,posttohistory.line3,"+
			"posttohistory.zip,posttohistory.city "+
			"FROM postforhistory LEFT JOIN posttohistory ON postforhistory.ktopost = posttohistory.ktopost ";

		if(postForHistoryCodeTableModel == null){
			String[] titles = {"table.postaccount","table.address1","table.address2","table.address3","table.zip","table.city","table.address1","table.address2","table.address3","table.zip","table.city"};
			postForHistoryCodeTableModel = new CodeTableModel(query,titles,"postforhistorypanel.title");
		}
		return postForHistoryCodeTableModel;
	}

	public CodeTableModel getBkstamCodeTableModel() {
		if(bkstamCodeTableModel == null){
			String[] titles = {"table.clearing","table.bkname","table.adress","table.zip", "table.city","table.postaccount"};
			bkstamCodeTableModel = new CodeTableModel("SELECT clearing,fullname,postaddr,zip,city,postkto FROM bkstam ",titles,"bkstam.title");
		}
		return bkstamCodeTableModel;
	}
}
