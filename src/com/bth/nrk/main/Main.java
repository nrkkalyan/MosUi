/*
 * naming the package as com.bth.nrk.main
 * 
 * we name the this package as com.bth.nrk.main for public access * 
 * 
 */
package com.bth.nrk.main;

/*
 *we import the following packages for initiation of database 
 * java.io.File package is to execute input output operations
 * packages [com.bth.mos.install.DBConstants;com.bth.mos.install.InitialDBSetup;] is establish database for each survey
 * 
 * importing required library package in concerned packages
 * DBconstants and InitialDBSetup are present in com.bth.mos.install package
 */
import java.io.File;

import com.bth.mos.install.DBConstants;
import com.bth.mos.install.InitialDBSetup;
import com.bth.mos.manager.CommonUtils;

/**
 * 
 * @author Kalyan
 * 
 * 
 * 
 */

public class Main {

	public static void main(String... args) {

		try {
			if (!new File(DBConstants.DB_FILE).exists())
			// IF condition is to verify whether DB is exist are not
			// It will generated a new database to each survey which we set
			{
				System.out.println("Database file not found. Installing database.");
				// An update to show whether Db is established are not
				InitialDBSetup.main(args);
				// setup the database
			}
			CommonUtils.loadSettings();
			/**
			 * Loading the settings of the survey which include VLC player
			 * location ,location of report directory , formats that are used
			 * for conducting the survey condition to shuffle the play list and
			 * skill level of the users
			 */

		} catch (Exception e) {
			/*
			 * Running the exceptions for any errors or misleads
			 */
			e.printStackTrace();
			throw new RuntimeException(e);
			/*
			 * throwing runtime exceptions
			 */
		}
		/*
		 * Calling the Mainscreen GUI to visible to the Admin(Researcher)
		 */

		MainScreen mainScreen = new MainScreen();
		mainScreen.setVisible(true);
	}

}
