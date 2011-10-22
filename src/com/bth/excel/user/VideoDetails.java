package com.bth.excel.user;

import java.io.Serializable;

import com.bth.mos.persistence.entity.Video;

public class VideoDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video video;
	private int grade = -1;

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}

	@Override
	public String toString() {
		return "VideoDetails [video=" + video + ", grade=" + grade + "]";
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((video == null) ? 0 : video.hashCode());
	// return result;
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (obj == null)
	// return false;
	// if (getClass() != obj.getClass())
	// return false;
	// VideoDetails other = (VideoDetails) obj;
	// if (video == null) {
	// if (other.video != null)
	// return false;
	// } else if (!video.equals(other.video))
	// return false;
	// return true;
	// }

}
