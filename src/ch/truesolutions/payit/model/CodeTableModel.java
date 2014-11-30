/*
 * Created on 09.05.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * @author Daniel
 */
public class CodeTableModel {

	private String query;
	private String condition = "";
	private List tableData;
	private String[] titles; // row titles
	private String title; // dialog title
	
	private PropertyChangeSupport support;
	/**
	 * 
	 */
	public CodeTableModel(String query, String[] titles, String title) {
		super();
		support = new PropertyChangeSupport(this);
		this.query = query;
		this.titles = titles;
		this.title = title;
		refresh();
	}
	
	public void refresh(){
		tableData = DAO.getInstance().executeQuery(query+condition);
		support.firePropertyChange("codetable","old","new");
	}

	public List getTableData(){
		return tableData;
	}

	public List getTableDataAt(int row){
		return (List)tableData.get(row);
	}
	
	public String[] getTitles(){
		return titles;
	}

	public void setCondition(String field,String value) {
		String newCondition = "WHERE "+field+" LIKE '"+value+"%'";
		if(!newCondition.equals(this.condition)){
			this.condition = newCondition;
			refresh();
		}			
	}
    
	public void noCondition() {
		if(this.condition.length() > 0){
			this.condition = "";
			refresh();
		}
	}

	public String getTitle() {
		return Config.getInstance().getText(title);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		support.removePropertyChangeListener(l);
	}

}
