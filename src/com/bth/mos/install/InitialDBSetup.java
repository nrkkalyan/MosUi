package com.bth.mos.install;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.bth.mos.MosConstants;
import com.bth.mos.persistence.dao.AdminDao;
import com.bth.mos.persistence.dao.AdminDaoBean;
import com.bth.mos.persistence.dao.GlobalPropertiesDao;
import com.bth.mos.persistence.dao.GlobalPropertiesDaoBean;
import com.bth.mos.persistence.entity.Admin;
import com.bth.mos.persistence.entity.GlobalProperties;

public class InitialDBSetup {

	private static Connection mConnection;
	private final AdminDao adminDao = new AdminDaoBean();
	private final GlobalPropertiesDao globalPropertiesDao = new GlobalPropertiesDaoBean();

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		InitialDBSetup initialDBSetup = new InitialDBSetup();
		try {
			initialDBSetup.testCreateTable();
			System.out.println("Succussfully created the tables..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This testCreateTable()will executes following methods after instancing
	 * InitialDbSetup dropAndCreateTables(); closeConnection();
	 * insertDefaultValuesToAdminTable();
	 * insertDefaultValuesToGlobalPropertiesTable();
	 * 
	 */

	private void testCreateTable() throws Exception {
		dropAndCreateTables();
		closeConnection();
		insertDefaultValuesToAdminTable();
		insertDefaultValuesToGlobalPropertiesTable();
	}

	/**
	 * Exception to close the connection which is a SQLconnection
	 */
	private void closeConnection() throws SQLException {
		mConnection.close();
	}

	/**
	 * Exception to get the connection
	 */
	private Connection getConnection() throws Exception {
		if (mConnection == null) {
			Class.forName(DBConstants.SQLiteDriver);
			mConnection = DriverManager.getConnection(DBConstants.DB_CONNECTION);
		}
		return mConnection;
	}

	/**
	 * To drop and create the Database tables required for the initiating the
	 * whole process
	 */
	private void dropAndCreateTables() throws Exception {
		Statement stat = getConnection().createStatement();
		stat.executeUpdate(DBConstants.ADMIN_TABLE);
		stat.executeUpdate(DBConstants.USER_TABLE);
		stat.executeUpdate(DBConstants.VIDEO_TABLE);
		stat.executeUpdate(DBConstants.VIDEO_GRADING_TABLE);
		stat.executeUpdate(DBConstants.CUSTOM_SCALE_TABLE);
		stat.executeUpdate(DBConstants.BINARY_SCALE_TABLE);
		stat.executeUpdate(DBConstants.GLOBAL_PROPERTIES_TABLE);
		stat.executeUpdate(DBConstants.TIMER_SETTINGS_TABLE);
		stat.executeUpdate(DBConstants.SURVEY_TABLE);

	}

	private void insertDefaultValuesToAdminTable() throws Exception {
		Admin entity = new Admin();
		entity.setUsername("admin");
		entity.setPassword("admin");
		adminDao.createEntity(entity);
	}

	private void insertDefaultValuesToGlobalPropertiesTable() throws Exception {
		GlobalProperties entity1 = new GlobalProperties();
		entity1.setKey(MosConstants.CURRENT_SURVEY_NAME);
		entity1.setValue("Not Present");
		globalPropertiesDao.createEntity(entity1);
	}

}
