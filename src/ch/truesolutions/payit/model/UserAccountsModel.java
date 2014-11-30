/*
 * Created on 05.07.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import ch.truesolutions.payit.exceptions.InvalidDataException;

/**
 * @author Daniel
 */
public class UserAccountsModel {
	private List accounts;
	private String sql =
		"SELECT DISTINCT UserAccounts.accountNr,UserAccounts.clearing,UserAccounts.iban,"+
		"Bkstam.fullName,UserAccounts.currency,UserAccounts.description,UserAccounts.line1,"+
		"UserAccounts.line2,UserAccounts.line3,UserAccounts.zip,UserAccounts.city "+
		"FROM UserAccounts "+
		"LEFT JOIN Bkstam ON UserAccounts.clearing = Bkstam.clearing";
	

	private String[] titles =  new String[]{"account.nr","account.description","account.owner"};

	private PropertyChangeSupport support;

	/**
	 * 
	 */
	public UserAccountsModel() {
		super();
		support = new PropertyChangeSupport(this);
		reload();
	}

	/**
	 * @return
	 */
	public List getUserAccounts() {
		return accounts;
	}

	/**
	 * @param list
	 */
	public void setUserAccounts(List accounts) {
		this.accounts = accounts;
	}
	
	public void reload() {
		// query the database
		List al = new ArrayList();
		List l = DAO.getInstance().executeQuery(sql);
		for(Iterator i = l.iterator();i.hasNext();){
			UserAccount account = new UserAccount((List)i.next());
			// p.register(this);
			al.add(account);
		}
		setUserAccounts(al);
	}

	public UserAccount getUserAccount(int row) {
		return (UserAccount)(accounts.get(row));
	}
	
	public void addUserAccount(UserAccount userAccount) throws InvalidDataException {
		// check if new user account nr doesn't exist yes
		// if it exists throw an exception else
		// do the insert
		List l = DAO.getInstance().executeQuery(
								"SELECT accountNr FROM UserAccounts "
									+ "WHERE accountNr='"
									+ userAccount.getNewAccountNr()
									+ "'");
		if (l != null && l.size() == 0) {
			userAccount.insert();
			accounts.add(userAccount);
			support.firePropertyChange("accounts","insert","new");
		} else {
			throw new InvalidDataException("validate_msg.accountexists");
		}
	}
	
	public void removeUserAccount(UserAccount userAccount) throws InvalidDataException {
		// validate that this user account is not used anymore in any payment
		List l = DAO.getInstance().executeQuery("SELECT id FROM Payments WHERE fromKto='"+userAccount.getAccountNr()+"'");
		if(l != null) {
			throw new InvalidDataException("warn_msg.accountinuse");
		}
		userAccount.delete();
		reload();
		support.firePropertyChange("accounts","remove","new");
	}

	public void updateUserAccount(UserAccount userAccount) throws InvalidDataException{
		// if the accountNr was changed we have to make sure that
		// 1) it doesn't allready exist and
		// 2) there are no existing payments using the old account nr
		List l = null;
		if(!userAccount.getNewAccountNr().equals(userAccount.getAccountNr())){
			l = DAO.getInstance().executeQuery(
									"SELECT accountNr FROM UserAccounts "
										+ "WHERE accountNr='"
										+ userAccount.getNewAccountNr()
										+ "'");
			if (l == null || l.size() > 0) {
				throw new InvalidDataException("validate_msg.accountexists");
			}
		
			l = DAO.getInstance().executeQuery(
					"SELECT id FROM Payments WHERE fromKto='"+
					userAccount.getAccountNr()+"'");
	
			if (l == null || l.size() > 0) {
				throw new InvalidDataException("validate_msg.accountinuse");
			}
		}

		userAccount.update();
		reload();
		support.firePropertyChange("accounts","update","new");
	}
	
	public String[] getTitles(){
		return titles;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		support.removePropertyChangeListener(l);
	}


}
