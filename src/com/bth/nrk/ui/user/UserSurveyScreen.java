/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UsersurveyScreen.java
 *
 * Created on 9 May, 2011, 9:13:33 PM
 */
package com.bth.nrk.ui.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.Timer;

import uk.co.caprica.vlcj.logger.Logger;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.list.MediaList;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

import com.bth.excel.CreateExcelReport;
import com.bth.excel.CreateExcelReportPerVideo;
import com.bth.mos.MosConstants;
import com.bth.mos.manager.CommonUtils;
import com.bth.mos.manager.SurveyDetails;
import com.bth.mos.manager.UserDetailsManager;
import com.bth.mos.persistence.entity.Survey;
import com.bth.mos.persistence.entity.Survey.ScaleType;
import com.bth.mos.persistence.entity.User;
import com.bth.mos.persistence.entity.Video;
import com.sun.jna.NativeLibrary;

/**
 * 
 * @author Kalyan
 */
public class UserSurveyScreen extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DUMP_NATIVE_MEMORY = "false";
	static {
		try {
			LibXUtil.initialise();
			if (null != CommonUtils.NATIVE_LIBRARY_SEARCH_PATH) {
				Logger.info("Explicitly adding JNA native library search path: '{}'", CommonUtils.NATIVE_LIBRARY_SEARCH_PATH);
				NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), CommonUtils.NATIVE_LIBRARY_SEARCH_PATH);
				System.setProperty("jna.dump_memory", DUMP_NATIVE_MEMORY);
			} else {
				throw new IllegalArgumentException("VLC Player location missing.");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(-1);
		}
	}

	private final UserDetailsManager mUserDetailsManager = new UserDetailsManager();
	private final CreateExcelReport createExcelReport = new CreateExcelReport();
	private final CreateExcelReportPerVideo createExcelReportPerVideo = new CreateExcelReportPerVideo();
	private SurveyDetails currentSurveyDetails;
	private MediaPlayerFactory factory;
	private EmbeddedMediaPlayer mediaPlayer;
	private MediaListPlayer mediaListPlayer;
	private MediaList mediaList;
	private CanvasVideoSurface videoSurface;
	private final LinkedList<Video> videoList = new LinkedList<Video>();

	/** Creates new form UsersurveyScreen */

	public UserSurveyScreen() {
		init();
		this.user = null;
	}

	public UserSurveyScreen(User user) {
		init();
		this.user = user;
		startSurvey();
	}

	private void init() {
		// this will not allow user to close the screen
		initComponents(); // Step 1
		getCurrentSurveyDetails(); // Step 2
		initVlcPlayer(); // Step 3
		setSize(MosConstants.xSize, MosConstants.ySize);
		setDefaultCloseOperation(JRootPane.NONE);
		setAlwaysOnTop(true);
		gradePanel.setVisible(false);
		referenceLable.setVisible(true);
	}

	private void initVlcPlayer() {
		factory = new MediaPlayerFactory("--no-video-title-show"); // Step1
		mediaPlayer = factory.newEmbeddedMediaPlayer(); // Step2
		mediaListPlayer = factory.newMediaListPlayer(); // Step3
		videoSurface = factory.newVideoSurface(canvasScreen); // Step4
		mediaList = factory.newMediaList();// Step5

		mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventListner());

		// Load few Static video files
		mediaListPlayer.setMediaPlayer(mediaPlayer);
		mediaListPlayer.setMediaList(mediaList);
		mediaPlayer.setVideoSurface(videoSurface);
		//
	}

	private void getCurrentSurveyDetails() {
		try {
			currentSurveyDetails = mUserDetailsManager.getCurrentSurveyDetails();
			loadScaleTypeAndTimers();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void displayScale(JPanel panel, String title) {
		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(scalePanel);
		scalePanel.setLayout(jPanel1Layout);
		scalePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		//
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(panel).addContainerGap(47, Short.MAX_VALUE)));
		//
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)//
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
				//
						.addContainerGap(68, Short.MAX_VALUE).addComponent(panel).addGap(30, 30, 30)));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		scalePanel = new javax.swing.JPanel();
		gradePanel = new javax.swing.JPanel();
		t2Label = new javax.swing.JLabel();
		gradeLabel = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		canvasScreen = new java.awt.Canvas();
		referenceLable = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(800, 600));
		setResizable(false);
		setUndecorated(true);

		scalePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scale"));
		scalePanel.setPreferredSize(new java.awt.Dimension(220, 300));

		javax.swing.GroupLayout scalePanelLayout = new javax.swing.GroupLayout(scalePanel);
		scalePanel.setLayout(scalePanelLayout);
		scalePanelLayout.setHorizontalGroup(scalePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 228, Short.MAX_VALUE));
		scalePanelLayout.setVerticalGroup(scalePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 324, Short.MAX_VALUE));

		t2Label.setFont(new java.awt.Font("Tahoma", 1, 14));
		t2Label.setText("t2Label");

		gradeLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
		gradeLabel.setText("GRADE WITHIN");

		javax.swing.GroupLayout gradePanelLayout = new javax.swing.GroupLayout(gradePanel);
		gradePanel.setLayout(gradePanelLayout);
		gradePanelLayout.setHorizontalGroup(gradePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				gradePanelLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gradePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(t2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(gradeLabel))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gradePanelLayout.setVerticalGroup(gradePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				gradePanelLayout.createSequentialGroup().addContainerGap().addComponent(gradeLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(t2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Video"));

		canvasScreen.setBackground(new java.awt.Color(153, 153, 153));
		canvasScreen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(canvasScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout.createSequentialGroup().addComponent(canvasScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		referenceLable.setFont(new java.awt.Font("Tahoma", 1, 14));
		referenceLable.setText("REFERENCE");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addGap(228, 228, 228)
						.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(scalePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(
												jPanel1Layout.createSequentialGroup().addGap(10, 10, 10)
														.addComponent(referenceLable, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(gradePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(1245, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel1Layout
						.createSequentialGroup()
						.addGroup(
								jPanel1Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGap(171, 171, 171)
														.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel1Layout
														.createSequentialGroup()
														.addGap(125, 125, 125)
														.addComponent(scalePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(referenceLable, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(gradePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(460, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap(37, Short.MAX_VALUE)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addGap(20, 20, 20)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(142, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void startSurvey() {
		// startSurveyButton.setEnabled(false);
		t3PlayTimer.start();
		t3LabelTimer.start();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private java.awt.Canvas canvasScreen;
	private javax.swing.JLabel gradeLabel;
	private javax.swing.JPanel gradePanel;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JLabel referenceLable;
	private javax.swing.JPanel scalePanel;
	private javax.swing.JLabel t2Label;
	// End of variables declaration//GEN-END:variables

	private int t1;// Max time for the video to play
	private int t2; // Time available with the user to submit the
	private int t3; // Time to wait for the next video to play
	private int tempT2;
	@SuppressWarnings("unused")
	private int tempT3;
	private Timer t2Timer;
	private Timer t3PlayTimer;
	private final Timer t2LabelTimer = new Timer(1000, new T2LabelTimerListner());
	private final Timer t3LabelTimer = new Timer(1000, new T3LabelTimerListner());
	private boolean isFirstVideo = true;
	private final User user;
	private int playListCounter;
	private int counter = -1;
	private Video currentVideo;
	private AbstractScalePanel abstractScalePanel;
	private boolean isUserSubmitedSurvey = false;

	private void loadScaleTypeAndTimers() throws Exception {

		Survey survey = currentSurveyDetails.getSurvey();
		if (survey.getScaleType() == ScaleType.BINARY_SCALE) {
			abstractScalePanel = new BinaryScalePanel();
			displayScale(abstractScalePanel, "Binary Scale");
		} else if (survey.getScaleType() == ScaleType.CUSTOM_SCALE) {
			abstractScalePanel = new CustomScalePanel();
			displayScale(abstractScalePanel, "Custom Scale");
		} else if (survey.getScaleType() == ScaleType.FIVE_POINT_QUALITY_SCALE) {
			abstractScalePanel = new FivePointScalePanel();
			displayScale(abstractScalePanel, "Five Point Quality Scale");
		} else if (survey.getScaleType() == ScaleType.FIVE_POINT_IMPAIRMENT_SCALE) {
			abstractScalePanel = new FivePointImparementScalePanel();
			displayScale(abstractScalePanel, "Five Point Impairment Scale");
		} else if (survey.getScaleType() == ScaleType.CONTINOUS_SCALE) {
			abstractScalePanel = new HundredPointScalePanel();
			displayScale(abstractScalePanel, "Continous Scale");
		}

		enableOrDisableSurveyscalePanel(false);

		com.bth.mos.persistence.entity.Timer timer = currentSurveyDetails.getTimer();

		t1 = timer.getT1();
		t2 = timer.getT2();
		t3 = timer.getT3();

		t2Timer = new Timer(t2 * 1000, new T2TimerListner());
		t3PlayTimer = new Timer(t3 * 1000, new T3PlayTimerListner());

		tempT2 = t2;
		tempT3 = t3 - 1;
		// t2Label.setText("0 Sec");

		videoList.clear();
		// Load Videos from the directory
		Map<String, List<Video>> videoFiles = mUserDetailsManager.getVideoFiles(survey.getId());
		File trainingVideo = CommonUtils.trainingVideo;
		if (trainingVideo != null && trainingVideo.exists()) {
			List<Video> trainingVideos = videoFiles.remove(trainingVideo.getPath());
			System.out.println("--------------------------------------");
			System.out.println("Training Videos in Path = " + trainingVideo.getPath());
			System.out.println(trainingVideos);
			loadVideos(trainingVideos);
			System.out.println("--------------------------------------");

		} else {
			System.out.println("Training videos does not exist " + trainingVideo.getPath());
		}
		for (String dir : videoFiles.keySet()) {
			List<Video> videos = videoFiles.get(dir);
			loadVideos(videos);
		}
	}

	private void loadVideos(List<Video> videos) {
		if (videos != null) {
			for (Video video : videos) {
				File file = new File(video.getUrl());
				if (file.exists()) {
					playListCounter++;
					videoList.add(video);
				}
			}
		}
	}

	private void gradeVideo() {
		try {
			// If the video is not Original Video
			String fileName = currentVideo.getFileName();
			String extension = fileName.substring(fileName.lastIndexOf("."));
			if (!fileName.equalsIgnoreCase("original" + extension)) {
				mUserDetailsManager.gradeVideo(currentVideo, currentSurveyDetails, user, abstractScalePanel.getGrade());
			}
			isUserSubmitedSurvey = true;
			gradePanel.setVisible(false);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void continueButtonActionPerformed() {
		this.setVisible(false);
		new UserDetailsScreen().setVisible(true);
	}

	/*
	 * Event happening after T3
	 */
	class T3PlayTimerListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			abstractScalePanel.reset();

			if (playListCounter > 0) {
				t3PlayTimer.stop();
				// t2Label.setText("0 Sec");
				t3LabelTimer.stop();
				/*
				 * To reduce the playlist
				 */
				playListCounter--;
				/*
				 * To increase the counter of playing video
				 */
				counter++;
				currentVideo = videoList.get(counter);
				System.out.println("Currently Playing :" + currentVideo.getFileName());

				String fileName = currentVideo.getFileName();
				String extension = fileName.substring(fileName.lastIndexOf("."));
				if (fileName.equalsIgnoreCase("original" + extension)) {
					referenceLable.setVisible(true);
					gradePanel.setVisible(false);
				} else {
					referenceLable.setVisible(false);
				}
				/*
				 * To remove the playing media
				 */
				mediaList.removeMedia(0);
				if (t1 > 0) {
					mediaList.insertMedia(0, currentVideo.getUrl(), ":stop-time=" + t1);
				} else {
					mediaList.insertMedia(0, currentVideo.getUrl());
				}
				if (isFirstVideo) {
					mediaListPlayer.play();
				} else {
					if (isUserSubmitedSurvey) {
						mediaListPlayer.playNext();
						isUserSubmitedSurvey = false;
					}
				}
			} else {
				t3PlayTimer.stop();
				t3LabelTimer.stop();
				// Generate Excel Report.
				try {
					createExcelReport.createExcelReportPerUser();
					createExcelReport.createCompleteExcelReport();
					createExcelReportPerVideo.createExcelReportPerVideo();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				JOptionPane.showMessageDialog(null, "Thank you for your time.");
				// continueButton.setEnabled(true);
				// okButton.setEnabled(false);
				// startSurveyButton.setEnabled(false);
				continueButtonActionPerformed();
			}
		}

	}

	/*
	 * event happening after T2
	 */
	class T2TimerListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			t2Timer.stop();
			t2LabelTimer.stop();
			t2Label.setText(--tempT2 + " Sec");
			if (!isUserSubmitedSurvey) {
				gradeVideo();
			}
			enableOrDisableSurveyscalePanel(false);

			isFirstVideo = false;
			tempT3 = t3 - 1;
			t3PlayTimer.restart();
			t3LabelTimer.restart();
		}
	}

	class T2LabelTimerListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (currentVideo != null) {
				String fileName = currentVideo.getFileName();
				String extension = fileName.substring(fileName.lastIndexOf("."));
				if (fileName.equalsIgnoreCase("original" + extension)) {
					gradePanel.setVisible(false);
				} else {
					gradePanel.setVisible(true);
					t2Label.setText(--tempT2 + " Sec");

				}
			}
		}
	}

	class T3LabelTimerListner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// t3Label.setText(--tempT3 + " Sec");
			gradePanel.setVisible(false);
		}

	}

	/*
	 * Event
	 */
	class MediaPlayerEventListner extends MediaPlayerEventAdapter {

		@Override
		public void finished(MediaPlayer mediaPlayer) {
			// JOptionPane.showMessageDialog(null,
			// "Use the scale above for grading and press Submit within " + t2 +
			// " Seconds");
			mediaListPlayer.stop();
			gradePanel.setVisible(true);
			enableOrDisableSurveyscalePanel(true);
			tempT2 = t2;
			t2Label.setText(tempT2 + " Sec");
			if (isFirstVideo) {
				t2Timer.start();
				t2LabelTimer.start();
			} else {
				t2Timer.restart();
				t2LabelTimer.restart();
			}
		}

	}

	public void enableOrDisableSurveyscalePanel(boolean isEnable) {
		if (currentVideo != null) {
			String fileName = currentVideo.getFileName();
			String extension = fileName.substring(fileName.lastIndexOf("."));
			if (fileName.equalsIgnoreCase("original" + extension)) {
				gradePanel.setVisible(false);
				referenceLable.setVisible(true);
				abstractScalePanel.enableOrDisableInputFields(false);
			} else {
				gradePanel.setVisible(true);
				referenceLable.setVisible(false);
				abstractScalePanel.enableOrDisableInputFields(isEnable);
			}
		} else {
			abstractScalePanel.enableOrDisableInputFields(isEnable);
		}
	}

}