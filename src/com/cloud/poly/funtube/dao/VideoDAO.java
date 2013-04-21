package com.cloud.poly.funtube.dao;

import java.util.Date;
import java.util.List;
import com.cloud.poly.funtube.model.Video;

public interface VideoDAO extends GenericDao<Video> {
	
	void createVideo(Video v);

	int deleteAllVideos();

	int deleteVideoByS3Name(String s3Name);
	int deleteVideoByS3Url(String s3Url);

	int deleteVideoByVideoTitle(String videoTitle);

	List<Video> findVideoByS3Name(String s3Name);

	List<Video> findVideoByVideoTitle(String videoTitle);

	List<Video> findVideoInDateRange(Date start, Date end);

	List<Video> listAllVideos();

	List<Video> listAllVideosByDateDesc(int start , int length);

	List<Video> listAllVideosByDateAsc(int start , int length);

	List<Video> listAllVideosByRating(int start , int length);

   // List<Video> listAllVideos(int count);

	// List<Video> listAllVideosByDateDesc(int count);

	 //List<Video> listAllVideosByDateAsc(int count);

	// List<Video> listAllVideosByRating(int count);

	int updateVideoDescriptionByS3Name(String s3Name, String newDescription);

	int updateVideoScoreByS3Name(String s3Name, int score);
}
