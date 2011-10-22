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

import com.bth.excel.video.UserGradeDetails;
import com.bth.excel.video.VideoSurveyDetails;
import com.bth.excel.video.VideosPerUser;
import com.bth.mos.manager.SurveyManager;
import com.bth.mos.persistence.entity.Survey.ScaleType;
import com.bth.mos.persistence.entity.Timer;
import com.bth.mos.persistence.entity.Timer.TimerType;
import com.bth.mos.persistence.entity.User;
import com.bth.mos.persistence.entity.Video;

public class CreateExcelReportPerVideo {

	private final SurveyManager mSurveyManager = new SurveyManager();
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String fileName;
	private final String reportFolder = "c:/temp/";

	public CreateExcelReportPerVideo() {
		if (reportFolder == null || !reportFolder.endsWith("/")) {
			throw new IllegalArgumentException("Please not report folder should end with a /. i.e c:/temp/");
		}
	}

	public void write(WritableWorkbook workbook, String sheetName, int sheetNo, VideoSurveyDetails videoSurveyDetails) throws IOException, WriteException {
		workbook.createSheet(sheetName, sheetNo);
		WritableSheet excelSheet = workbook.getSheet(sheetNo);
		createLabel(excelSheet, videoSurveyDetails);
		createContent(excelSheet, videoSurveyDetails);

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

	private void createLabel(WritableSheet sheet, VideoSurveyDetails videoSurveyDetails) throws WriteException {
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
		String surveyName = videoSurveyDetails.getSurveyDetails().getSurvey().getSurveyName();
		addLabel(sheet, 0, 1, surveyName);
		//
		addCaption(sheet, 1, 0, "Scale Type");
		ScaleType scaleType = videoSurveyDetails.getSurveyDetails().getSurvey().getScaleType();
		addLabel(sheet, 1, 1, scaleType.name());
		// Timer
		addCaption(sheet, 2, 0, "Timer Type");
		Timer timer = videoSurveyDetails.getSurveyDetails().getTimer();
		addLabel(sheet, 2, 1, timer.getTimerType().getTimerName());
		// T1
		addCaption(sheet, 3, 0, "T1");
		int t1 = videoSurveyDetails.getSurveyDetails().getTimer().getT1();
		if (timer.getTimerType() == TimerType.DEFAULTTIMER) {
			addLabel(sheet, 3, 1, "Not Applicable");
		} else {
			addNumber(sheet, 3, 1, t1);
		}
		// T2
		addCaption(sheet, 4, 0, "T2");
		int t2 = videoSurveyDetails.getSurveyDetails().getTimer().getT2();
		addNumber(sheet, 4, 1, t2);
		// T3
		addCaption(sheet, 5, 0, "T3");
		int t3 = videoSurveyDetails.getSurveyDetails().getTimer().getT3();
		addNumber(sheet, 5, 1, t3);
		//
		addCaption(sheet, 6, 0, "UserName/VideoName");

	}

	private void createContent(WritableSheet sheet, VideoSurveyDetails videoSurveyDetails) throws WriteException, RowsExceededException {
		// Now a bit of text
		int i = 7;
		final LinkedList<VideosPerUser> videosPerUser = videoSurveyDetails.getVideosPerUserList();
		final LinkedList<UserGradeDetails> userGradeDetails = videosPerUser.get(0).getUserGradeDetails();
		for (UserGradeDetails ugd : userGradeDetails) {
			// List of videos
			User user = ugd.getUser();
			addLabel(sheet, i++, 0, "Name: " + user.getName() + " ,Age:" + user.getAge() + ", skill:" + user.getSkill());
		}
		int j = 1;
		for (VideosPerUser vpu : videosPerUser) {
			Video user = vpu.getVideo();
			addLabel(sheet, 6, j, user.getFileName()); // Video name
			//
			int k = 7;
			LinkedList<UserGradeDetails> videoList = vpu.getUserGradeDetails();
			for (UserGradeDetails vd : videoList) {
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

	public String createExcelReportPerVideo() throws Exception {
		VideoSurveyDetails videoSurveyDetails = mSurveyManager.getSurveyResultsPerVideo();
		String surveyName = videoSurveyDetails.getSurveyDetails().getSurvey().getSurveyName();
		fileName = surveyName + "-ReportPerVideo.xls";
		WritableWorkbook workBook = null;
		try {
			LinkedList<VideosPerUser> userVideoList = videoSurveyDetails.getVideosPerUserList();
			if (userVideoList.isEmpty()) {
				throw new UnsupportedOperationException("No survey conducted for the " + surveyName + " Survey name. So report can't be generated");
			}
			workBook = createWorkBook(fileName);
			int i = 0;
			for (VideosPerUser vpu : userVideoList) {
				VideoSurveyDetails tempVideoSurveyDetails = new VideoSurveyDetails();
				tempVideoSurveyDetails.setSurveyDetails(videoSurveyDetails.getSurveyDetails());
				VideosPerUser tempVideosPerUser = new VideosPerUser();
				tempVideosPerUser.setVideo(vpu.getVideo());
				tempVideosPerUser.addAllUserVideoList(vpu.getUserGradeDetails());
				tempVideoSurveyDetails.addToVideosPerUserList(tempVideosPerUser);
				write(workBook, vpu.getVideo().getFileName(), i++, tempVideoSurveyDetails);
			}
		} finally {
			closeWorkBook(workBook);
		}
		return "Please check the report file under " + reportFolder + fileName;
	}

}
