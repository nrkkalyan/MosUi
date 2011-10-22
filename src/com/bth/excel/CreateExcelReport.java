package com.bth.excel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.bth.excel.user.UserSurveyDetails;
import com.bth.excel.user.UserVideo;
import com.bth.excel.user.VideoDetails;
import com.bth.mos.manager.CommonUtils;
import com.bth.mos.manager.SurveyManager;
import com.bth.mos.persistence.entity.Survey.ScaleType;
import com.bth.mos.persistence.entity.Timer;
import com.bth.mos.persistence.entity.Timer.TimerType;
import com.bth.mos.persistence.entity.User;

/**
 * This code is to generate excel sheets
 * 
 */
public class CreateExcelReport {

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String fileName;
	private final String reportFolder = CommonUtils.reportDir;
	private final SurveyManager mSurveyManager = new SurveyManager();

	public CreateExcelReport() {
		if (reportFolder == null || !reportFolder.endsWith("/")) {
			throw new IllegalArgumentException("Please not report folder should end with a /. i.e c:/temp/");
		}
	}

	private void write(WritableWorkbook workbook, String sheetName, int sheetNo, UserSurveyDetails userSurveyDetails) throws IOException, WriteException {
		workbook.createSheet(sheetName, sheetNo);
		WritableSheet excelSheet = workbook.getSheet(sheetNo);
		createLabel(excelSheet, userSurveyDetails);
		createContent(excelSheet, userSurveyDetails);

	}

	private void closeWorkBook(WritableWorkbook workBook) throws IOException, WriteException {
		if (workBook != null) {
			workBook.write();
			workBook.close();
		}
	}

	private WritableWorkbook createWorkBook(String fileName) throws Exception {
		File file = new File(reportFolder + fileName);
		if (file.exists()) {
			boolean isDeleteed = file.delete();
			if (!isDeleteed) {
				throw new UnsupportedOperationException("There is already a report file exist in the " + reportFolder + " folder. Please delete it or rename it.");
			}
		}

		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		return workbook;
	}

	private void createLabel(WritableSheet sheet, UserSurveyDetails userSurveyDetails) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, 0, 0, "Survey Name");
		String surveyName = userSurveyDetails.getSurveyDetails().getSurvey().getSurveyName();
		addLabel(sheet, 0, 1, surveyName);
		// Scale
		addCaption(sheet, 1, 0, "Scale Type");
		ScaleType scaleType = userSurveyDetails.getSurveyDetails().getSurvey().getScaleType();
		addLabel(sheet, 1, 1, scaleType.name());
		// Timer
		addCaption(sheet, 2, 0, "Timer Type");
		Timer timer = userSurveyDetails.getSurveyDetails().getTimer();
		addLabel(sheet, 2, 1, timer.getTimerType().getTimerName());
		// T1
		addCaption(sheet, 3, 0, "T1");
		int t1 = userSurveyDetails.getSurveyDetails().getTimer().getT1();
		if (timer.getTimerType() == TimerType.DEFAULTTIMER) {
			addLabel(sheet, 3, 1, "Not Applicable");
		} else {
			addNumber(sheet, 3, 1, t1);
		}
		// T2
		addCaption(sheet, 4, 0, "T2");
		int t2 = userSurveyDetails.getSurveyDetails().getTimer().getT2();
		addNumber(sheet, 4, 1, t2);
		// T3
		addCaption(sheet, 5, 0, "T3");
		int t3 = userSurveyDetails.getSurveyDetails().getTimer().getT3();
		addNumber(sheet, 5, 1, t3);
		//
		addCaption(sheet, 6, 0, "VideoName/UserName");

	}

	private void createContent(WritableSheet sheet, UserSurveyDetails userSurveyDetails) throws WriteException, RowsExceededException {
		// Now a bit of text
		int i = 7;
		final LinkedList<UserVideo> userVideos = userSurveyDetails.getUserVideoList();
		final LinkedList<VideoDetails> videoDetails = userVideos.get(0).getVideoList();
		for (VideoDetails video : videoDetails) {
			// List of videos
			addLabel(sheet, i++, 0, video.getVideo().getFileName());
		}
		int j = 1;
		for (UserVideo userVideo : userVideos) {
			User user = userVideo.getUser();
			addLabel(sheet, 6, j, "Name: " + user.getName() + " ,Age:" + user.getAge() + ",skill:" + user.getSkill()); // User
			// name
			//
			int k = 7;
			LinkedList<VideoDetails> videoList = userVideo.getVideoList();
			for (VideoDetails vd : videoList) {
				addNumber(sheet, k++, j, vd.getGrade());
			}
			j++;
		}
	}

	private void addCaption(WritableSheet sheet, int row, int column, String s) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int row, int column, Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int row, int column, String s) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	public String createCompleteExcelReport() throws Exception {
		UserSurveyDetails userSurveyDetails = mSurveyManager.getCurrentSurveyResults();
		String surveyName = userSurveyDetails.getSurveyDetails().getSurvey().getSurveyName();
		fileName = surveyName + ".xls";
		WritableWorkbook workBook = null;
		if (userSurveyDetails.getUserVideoList().isEmpty()) {
			throw new UnsupportedOperationException("No survey conducted for the " + surveyName + " Survey name. So report can't be generated");
		}
		try {
			workBook = createWorkBook(fileName);
			write(workBook, fileName, 0, userSurveyDetails);
		} finally {
			closeWorkBook(workBook);
		}
		return "Please check the report file under " + reportFolder + fileName;
	}

	public String createExcelReportPerUser() throws Exception {
		UserSurveyDetails userSurveyDetails = mSurveyManager.getCurrentSurveyResults();
		String surveyName = userSurveyDetails.getSurveyDetails().getSurvey().getSurveyName();
		fileName = surveyName + "-ReportPerUser.xls";
		WritableWorkbook workBook = null;

		try {
			LinkedList<UserVideo> userVideoList = userSurveyDetails.getUserVideoList();
			if (userVideoList.isEmpty()) {
				throw new UnsupportedOperationException("No survey conducted for the " + surveyName + " Survey name. So report can't be generated");
			}
			workBook = createWorkBook(fileName);
			int i = 0;
			for (UserVideo uv : userVideoList) {
				UserSurveyDetails tempUserSurveyDetails = new UserSurveyDetails();
				tempUserSurveyDetails.setSurveyDetails(userSurveyDetails.getSurveyDetails());
				UserVideo tempUserVideo = new UserVideo();
				tempUserVideo.setUser(uv.getUser());
				tempUserVideo.addAllVideo(uv.getVideoList());
				tempUserSurveyDetails.addToUserVideoList(tempUserVideo);
				write(workBook, uv.getUser().getName(), i++, tempUserSurveyDetails);
			}
		} finally {
			closeWorkBook(workBook);
		}
		return "Please check the report file under " + reportFolder + fileName;
	}

}
