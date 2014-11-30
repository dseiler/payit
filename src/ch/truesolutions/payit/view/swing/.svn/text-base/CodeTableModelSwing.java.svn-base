package ch.truesolutions.payit.view.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import ch.truesolutions.payit.model.CodeTableModel;
import ch.truesolutions.payit.model.Config;

/**
 * Created on 24.06.2004
 * TODO enter class description
 * 
 * @author @author <a href=mailto:daniel.seiler@truesolutions.ch>Daniel Seiler</a>
 * @version 1.00
 * Copyright (c) Daniel Seiler 2004
 */
public class CodeTableModelSwing extends AbstractTableModel implements CodeTableModelInterface {

	private CodeTableModel model;
	/**
	 * 
	 */
	public CodeTableModelSwing(CodeTableModel model) {
		super();
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		return model.getTitles().length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return model.getTableData().size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int col) {
		return ((List)(model.getTableDataAt(row))).get(col);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return Config.getInstance().getText(model.getTitles()[col]);
	}

	/* (non-Javadoc)
	 * @see ch.truesolutions.payit.view.swing.CodeTableModelInterface#getTableDataAt(int)
	 */
	public List getTableDataAt(int row) {
		return model.getTableDataAt(row);
	}

	/* (non-Javadoc)
	 * @see ch.truesolutions.payit.view.swing.CodeTableModelInterface#noCondition()
	 */
	public void noCondition() {
		model.noCondition();
	}

	/* (non-Javadoc)
	 * @see ch.truesolutions.payit.view.swing.CodeTableModelInterface#setCondition(java.lang.String, java.lang.String)
	 */
	public void setCondition(String field, String value) {
		model.setCondition(field,value);
	}

}
