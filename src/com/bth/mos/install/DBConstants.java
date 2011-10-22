/**
 * This package meant to setup the database with concerned tables
 * (ADMIN_TABLE,VIDEO_TABLE,USER_TABLE,VIDOE_GRADING_TABLE,CUSTOM_SCALE_TABLE)
 * (BINARY_SCALE_TABLE,GLOBAL_PROPERTIES_TABLE,SURVEY_TABLE,TIMER_SETTINGS_TABLE)
 */
package com.bth.mos.install;

/**
 * @author Kalyan
 * 
 */
public final class DBConstants {
	/**
	 * SQLitedriver is the driver of sqlite database
	 * 
	 */

	public static final String SQLiteDriver = "org.sqlite.JDBC";
	/**
	 * We name the Database as mosdatabase.db. this is the database created
	 * whenever usage of tool initiated.
	 */
	public static final String DB_FILE = "mosdatabase.db";
	/**
	 * We declare the connection of the required database with the connector
	 * "jdbc:sqlite:" and database file DB_FILE
	 */
	public static final String DB_CONNECTION = "jdbc:sqlite:" + DB_FILE;
	/**
	 * We create ADMIN_TABLE with by declaring SQL statements as strings
	 * ADMIN_TABLE consists of 3 attributes ID,USERNAME and PASSWORD ID as
	 * primary key of the ADMIN_TABLE
	 * 
	 */
	public static String ADMIN_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"ADMIN_TABLE " + //
			"( ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + //
			"USERNAME VARCHAR NOT NULL UNIQUE, " + //
			"PASSWORD VARCHAR NOT NULL );";
	/**
	 * We create VIDEO_TABLE with ID primary key. FILENAME gives the name of the
	 * video{eg. video.avi} and URL declares the location of the videos.We keep
	 * constraint as URL, FK_SURVEY_ID to be Unique the location of the
	 * video(URL) and the Survey ID (FK_SURVEY_ID) should be unique
	 */
	public static String VIDEO_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"VIDEO_TABLE " + //
			"( ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + //
			"FILENAME VARCHAR NOT NULL, " + //
			"URL VARCHAR NOT NULL," + //
			"FK_SURVEY_ID INTEGER NOT NULL," + //
			"CONSTRAINT VIDEO_UNQ UNIQUE (URL, FK_SURVEY_ID) );";
	/**
	 * We create USER_TABLE in the database for dynamically storing the subject
	 * details of the survey subject details include NAME,AGE,GENDER,SKILL ID as
	 * primary key of the table
	 */

	public static String USER_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"USER_TABLE " + //
			"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + //
			"NAME VARCHAR NOT NULL, " + //
			"AGE INTEGER NOT NULL, " + //
			"GENDER VARCHAR NOT NULL," + //
			"SKILL VARCHAR NOT NULL );";//

	/**
	 * We construct VIDEO_GRADING_TABLE ID as primary key FK_USER_ID,
	 * FK_VIDOE_ID and FK_SURVEY_ID as foreign keys from the USER_TABLE,
	 * VIDEO_TABLE and SURVEY_TABLE respectively. We keep the constraint
	 * FK_USER_ID, FK_VIDOE_ID and FK_SURVEY_ID to be unique.
	 * 
	 */

	public static String VIDEO_GRADING_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"VIDEO_GRADING_TABLE " + //
			"( ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + //
			"FK_USER_ID INTEGER NOT NULL, " + //
			"FK_VIDEO_ID INTEGER NOT NULL, " + //
			"FK_SURVEY_ID INTEGER NOT NULL, " + //
			"GRADE INTEGER NOT NULL, " + //
			"FOREIGN KEY(FK_USER_ID) REFERENCES USER_TABLE(ID), " + //
			"FOREIGN KEY(FK_VIDEO_ID) REFERENCES VIDEO_TABLE(ID), " + //
			"FOREIGN KEY(FK_SURVEY_ID) REFERENCES SURVEY_TABLE(ID), " + //
			"CONSTRAINT VIDEO_GRADING_UNQ UNIQUE (FK_USER_ID, FK_VIDEO_ID, FK_SURVEY_ID) );";
	/**
	 * CUSTOM_SCALE TABLE is created for the custom scale, which has three
	 * levels for grading ( e.g BEST, GOOD,BAD) MINSCALE,MIDSCALE and MAXSCALE
	 * are the levels for the concerning levels We declare the FK_SURVEY_ID as
	 * the foreign key with reference SURVEY_TABLE
	 */

	public static String CUSTOM_SCALE_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"CUSTOM_SCALE_TABLE (" + //
			"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " + //
			"FK_SURVEY_ID INTEGER NOT NULL, " + //
			"MINSCALE VARCHAR NOT NULL , " + //
			"MIDSCALE VARCHAR NOT NULL , " + //
			"MAXSCALE VARCHAR NOT NULL , " + //
			"FOREIGN KEY(FK_SURVEY_ID) REFERENCES SURVEY_TABLE(ID) )";
	/**
	 * BINARY_SCALE_TABLE is created for the binary scale IT has two levels (e.g
	 * ACCEPTABLE and UN-ACCEPTABLE) We declare the FK_SURVEY_ID as the foreign
	 * key with reference SURVEY_TABLE
	 */
	public static String BINARY_SCALE_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"BINARY_SCALE_TABLE (" + //
			"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " + //
			"FK_SURVEY_ID INTEGER NOT NULL, " + //
			"POSITIVE_SCALE VARCHAR NOT NULL , " + //
			"NEGATIVE_SCALE VARCHAR NOT NULL," + //
			"FOREIGN KEY(FK_SURVEY_ID) REFERENCES SURVEY_TABLE(ID))";
	/**
	 * In this GLOBAL_PROPERTIES_TABLE we declare KEY and VALUE as two attribute
	 * to save th key and value in the table.
	 */

	public static String GLOBAL_PROPERTIES_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"GLOBAL_PROPERTIES_TABLE (" + //
			"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + //
			"KEY VARCHAR NOT NULL UNIQUE , " + //
			"VALUE VARCHAR NOT NULL)";

	/**
	 * SURVEY_TABLE shows the details of the SURVEY It saves the name of the
	 * survey, type of the scale and video location We keep FK_TIMER_ID as the
	 * FOREIGN KEY of TIMMER_SETTINGS_TABLE
	 */
	public static String SURVEY_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"SURVEY_TABLE (" + //
			"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + //
			"SURVEYNAME VARCHAR NOT NULL UNIQUE , " + //
			"SCALETYPE VARCHAR NOT NULL , " + //
			"FK_TIMER_ID INTEGER NOT NULL UNIQUE, " + //
			"VIDEOLOCATION VARCHAR NOT NULL," + //
			"FOREIGN KEY(FK_TIMER_ID) REFERENCES TIMER_SETTINGS_TABLE(ID))";
	/**
	 * We construct TIMER_SETTINGS_TABLE to store the values of timers this
	 * table helps to save the timer values given for each unique survey
	 */
	public static String TIMER_SETTINGS_TABLE = "CREATE TABLE IF NOT EXISTS " + //
			"TIMER_SETTINGS_TABLE  (" + //
			"ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + // \
			"TIMERTYPE VARCHAR NOT NULL," + //
			"T1 INTEGER, " + //
			"T2 INTEGER NOT NULL, " + //
			"T3 INTEGER NOT NULL)"; //

}
