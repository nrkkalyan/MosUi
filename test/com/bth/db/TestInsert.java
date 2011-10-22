package com.bth.db;

import java.util.List;

import com.bth.excel.CreateExcelReport;
import com.bth.excel.CreateExcelReportPerVideo;
import com.bth.mos.persistence.dao.AdminDao;
import com.bth.mos.persistence.dao.AdminDaoBean;
import com.bth.mos.persistence.dao.BinaryScaleDao;
import com.bth.mos.persistence.dao.BinaryScaleDaoBean;
import com.bth.mos.persistence.dao.GlobalPropertiesDao;
import com.bth.mos.persistence.dao.GlobalPropertiesDaoBean;
import com.bth.mos.persistence.dao.SurveyDao;
import com.bth.mos.persistence.dao.SurveyDaoBean;
import com.bth.mos.persistence.dao.TimerDao;
import com.bth.mos.persistence.dao.TimerDaoBean;
import com.bth.mos.persistence.dao.VideoDao;
import com.bth.mos.persistence.dao.VideoDaoBean;
import com.bth.mos.persistence.entity.Admin;
import com.bth.mos.persistence.entity.BinaryScale;
import com.bth.mos.persistence.entity.GlobalProperties;
import com.bth.mos.persistence.entity.Survey;
import com.bth.mos.persistence.entity.Timer;
import com.bth.mos.persistence.entity.Video;

public class TestInsert {

	protected final AdminDao adminDao = new AdminDaoBean();
	protected final VideoDao videoDao = new VideoDaoBean();
	protected final BinaryScaleDao binaryScaleDao = new BinaryScaleDaoBean();
	protected final SurveyDao surveyDao = new SurveyDaoBean();
	protected final TimerDao timerDao = new TimerDaoBean();
	protected final GlobalPropertiesDao mGlobalPropertiesDao = new GlobalPropertiesDaoBean();
	protected final CreateExcelReport createExcelReport = new CreateExcelReport();
	protected final CreateExcelReportPerVideo createExcelReportPerVideo = new CreateExcelReportPerVideo();

	public static void main(String[] args) {
		TestInsert tester = new TestInsert();
		try {
			tester.loadVideos();

			// createReport(tester);
			// createReportPerVideo(tester);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void loadVideos() throws Exception {
		List<Video> videos = videoDao.findVideosBySurveyId(7);
		for (Video video : videos) {
			System.out.println(video);
		}
	}

	protected static void createReportPerVideo(TestInsert tester) throws Exception {

		tester.createExcelReportPerVideo.createExcelReportPerVideo();
	}

	protected static void createReport(TestInsert tester) throws Exception {
		tester.createExcelReport.createCompleteExcelReport();
		tester.createExcelReport.createExcelReportPerUser();
	}

	protected void testGlobalProperty() throws Exception {

		GlobalProperties entity = new GlobalProperties();

		mGlobalPropertiesDao.createEntity(entity);
		System.out.println("Successfully inserted to Admin table");
	}

	protected void testInsertAdmin() throws Exception {
		Admin entity = new Admin();
		entity.setUsername("root");
		entity.setPassword("password");
		adminDao.createEntity(entity);
		System.out.println("Successfully inserted to Admin table");
	}

	protected void testInsertBinaryScale() throws Exception {
		BinaryScale entity = new BinaryScale();
		entity.setPositiveScale("positiveScale");
		entity.setNegativeScale("negativeScale");
		binaryScaleDao.createEntity(entity);
		System.out.println("Successfully inserted to binaryscale table");

	}

	protected void testInsertSurveyTable() throws Exception {
		Survey entity = new Survey();
		entity.setScaleType(Survey.ScaleType.BINARY_SCALE);
		String surveyName = "Test:" + System.currentTimeMillis();
		entity.setSurveyName(surveyName);
		entity.setVideoLocation("c:/");
		Timer timer = new Timer();
		timerDao.createEntity(timer);
		entity.setFk_timer_id(timer.getId());
		surveyDao.createEntity(entity);
		updateSurveyTable(surveyName);
		System.out.println("Successfully inserted to testInsertSurveyTable table " + entity.getId());

	}

	public void updateSurveyTable(String surveyName) throws Exception {
		Survey survey = surveyDao.findSurveyByName(surveyName);
		survey.setScaleType(Survey.ScaleType.FIVE_POINT_QUALITY_SCALE);
		surveyDao.updateEntity(survey);
		System.out.println(survey.getScaleType().name());
		Survey survey1 = surveyDao.findSurveyByName(surveyName);
		System.out.println(survey1.getScaleType().name());
	}

	protected void testAuthenticate() throws Exception {
		adminDao.authenticate("admin", "admin".toCharArray());
		System.out.println("Successfully Authenticate.");
	}
}
