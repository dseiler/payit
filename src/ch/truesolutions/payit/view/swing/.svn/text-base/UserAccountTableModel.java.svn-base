/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;

import ch.truesolutions.payit.model.*;
import ch.truesolutions.payit.model.Config;
import ch.truesolutions.payit.model.MainModel;

public class UserAccountTableModel extends AbstractTableModel implements ConfigChangeListener {

    private static UserAccountTableModel uatm = new UserAccountTableModel();
    
    private List tableData;
    
    public static UserAccountTableModel getInstance() {
        //uatm.refresh();
        return uatm;
    }
    
    private UserAccountTableModel() {
        super();
        // register this frame at the Config
        Config.getInstance().addConfigChangeListener(this);
		MainModel.getInstance().getUserAccountsModel().addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent event){
				refreshTableDisplay(event.getPropertyName(),(String)event.getOldValue());
			}
		});

        refresh();
    }
    
    private void refreshTableDisplay(String target, String type) {
		// type: "insert" "update" "remove"
		// target: "accounts"
		if("accounts".equals(target)) {
			refresh();
			if("insert".equals(type)){
				fireTableRowsInserted(0,getRowCount());
			} else if("remove".equals(type)) {
				fireTableRowsDeleted(0,getRowCount());
			} else if("update".equals(type)) {
				fireTableRowsUpdated(0,getRowCount());
			}
		}
    }
    
    /*
    public void tableRowUpdated() {
        refresh();
        fireTableRowsUpdated(0,getRowCount());
    }
    
    public void tableRowDeleted() {
        refresh();
        fireTableRowsDeleted(0,getRowCount());
    }
    
    public void tableRowInserted() {
        refresh();
        fireTableRowsInserted(0,getRowCount());
    }
    */
    
    public void refresh() {
        this.tableData = MainModel.getInstance().getUserAccountsModel().getUserAccounts();
    }
    
    /*
    public Vector getUserAccounts() {
        if(userAccounts.size() == 0)
            userAccounts.add(new UserAccount());
        return userAccounts;
    }
    
    public UserAccount getUserAccount(int row) {
        return (UserAccount)(userAccounts.get(row));
    }
    */
    
    public int getRowCount() {
        if(tableData != null) {
            return tableData.size();
        } else {
            return 0;
        }
    }
    
    public int getColumnCount() {
        return 3;
    }
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0: // from kto
                return ((UserAccount)(tableData.get(row))).getAccountNr();
            case 1: // description
                return ((UserAccount)(tableData.get(row))).getDescription();
            case 2: // owner
                String line1 = ((UserAccount)(tableData.get(row))).getLine1();
                String line2 = ((UserAccount)(tableData.get(row))).getLine2();
                String line3 = ((UserAccount)(tableData.get(row))).getLine3();
                String zipcity = ((UserAccount)(tableData.get(row))).getZip()+" "+
					((UserAccount)(tableData.get(row))).getCity();;
                
                String ktoOwner = line1+
                (line2.equals("") ? "" : ", "+line2)+
                (line3.equals("") ? "" : ", "+line3)+
                (zipcity.equals("") ? "" : ", "+zipcity);
                return ktoOwner;
            default:
                return "";
        }
    }
    
    public String getColumnName(int column) {
        switch(column) {
            case 0:
                return Config.getInstance().getText("account.nr");
            case 1:
                return Config.getInstance().getText("account.description");
            case 2:
                return Config.getInstance().getText("account.owner");
            default:
                return "";
        }
    }
    
        /*
        public Class getColumnClass(int column)
        {
                switch(column)
                {
                        case 0:
                                return ImageIcon.class;
                        case 2:
                                return Number.class;
                        default:
                                return Object.class;
                }
        }
         */
    
    public void applyLanguageChange() {
        fireTableChanged(new TableModelEvent(this,TableModelEvent.HEADER_ROW));
    }
    
    public void setLookAndFeel(String lnfName){}
}