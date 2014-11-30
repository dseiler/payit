package ch.truesolutions.payit.model;

import java.util.List;

/**
 * @author dseiler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface DataAccessObject {
	
	/**
	 * executes an sql update command
	 */
	public void executeUpdate(String sqlString);
	
	/**
	 * returns a List of Lists
	 */
	public List executeQuery(String sqlString);
	
	/**
	 * returns the next valid db id
	 */
	public int getNextId(String table, String column);
	
	/**
	 * returns a boolean indicating if the table with
	 * the table name tablName exists allready or not
	 */
	public boolean doesTableExist(String tableName);
}
