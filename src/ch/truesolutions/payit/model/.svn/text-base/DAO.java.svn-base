package ch.truesolutions.payit.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author dseiler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DAO implements DataAccessObject {

	private static DataAccessObject instance = null;
	private static Logger logger = Logger.getLogger(DAO.class.getName());

	private static Connection connection = null;
	static {
		try {
			// Load the HSQL Database Engine JDBC driver
			Class.forName("org.hsqldb.jdbcDriver");
			//client/server
			//connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
			//direct
			if (Config.getInstance().isDemoVersion()) {
				// this is the url for the inmemory database (demo version)
				connection = DriverManager.getConnection("jdbc:hsqldb:.", "sa", "");
			} else {
				connection = DriverManager.getConnection("jdbc:hsqldb:payitdata", "sa", "");
			}

		} catch (ClassNotFoundException e) {
			// Print out the error message
			logger.fatal(e);
			e.printStackTrace();
		} catch (SQLException e) {
			// Print out the error message
			logger.fatal(e);
			e.printStackTrace();
		}
	}

	public static DataAccessObject getInstance() {
		if (instance == null) {
			instance = new DAO();
		}
		return instance;
	}
	
	private DAO(){
		super();		
	}
	
	public PreparedStatement createPreparedStatement(String sql){
		try {
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void setPreparedValue(PreparedStatement p,int parameterIndex,Object x){
		try {
			p.setObject(parameterIndex,x);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	
	/**
	 * @see ch.ts.contactit.DataAccessObject#executeUpdate(String)
	 */
	public void executeUpdate(String sqlString) {
		Statement stmt = null;
		try {
			// Create a statement object
			stmt = connection.prepareStatement(sqlString);
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			// Print out the error message
			logger.error(e);
		} catch (Exception e) {
			// Print out the error message
			logger.error(e);
		} finally {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

	/**
	 * @see ch.ts.contactit.DataAccessObject#executeQuery(String)
	 */
	public List executeQuery(String sqlString) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// Create a statement object
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlString);
			logger.info("executing query :"+sqlString);
			// Moves to the next record until no more records
			return convertResultSet(rs);
		} catch (SQLException e) {
			logger.error(e);
			return null;
		} catch (Exception e) {
			// Print out the error message
			logger.error(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
	}
	
	private List convertResultSet(ResultSet rs) throws SQLException {
		ArrayList result = new ArrayList();
		while (rs.next()) {
			ArrayList row = new ArrayList();
			result.add(row);
			try {
				int i = 1;
				while (true) {
					row.add(rs.getObject(i));
					i++;
				}
			} catch (Exception e) {
				//System.out.println(e);
			}
		}
		return result;
		
	}

	/**
	 * @see ch.ts.contactit.DataAccessObject#getNextId(String, String)
	 */
	public int getNextId(String table, String column) {
		List result = executeQuery("SELECT MAX(" + column + ")+1 FROM " + table);
		List row = null;
		try {
			if ((result != null) && (result.size() == 1)) {
				row = (List) (result.get(0));
			}
			if ((row != null) && (row.size() == 1)) {
				Object id = row.get(0);
				if (id == null){
					return 1;
				}else if (id instanceof Long) {
					return (int) (((Long) (id)).longValue());
				} else if (id instanceof Integer) {
					return ((Integer) (id)).intValue();
				} else {
					logger.error("unknown object type:"+id);
					return -1;
				}
			} else {
				logger.error("query for next id failed");
				return -1;
			}
		} catch (ClassCastException e) {
			logger.error(e);
			return 1;
		}
	}

	/**
	 * @see ch.ts.contactit.DataAccessObject#doesTableExist(String)
	 */
	public boolean doesTableExist(String tableName) {
		Statement stmt = null;
		try {
			// Create a statement object
			stmt = connection.createStatement();
			stmt.executeQuery("SELECT * FROM " + tableName);
			return true;
		} catch (SQLException e) {
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		} finally {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}

}
