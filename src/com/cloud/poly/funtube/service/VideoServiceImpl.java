package com.cloud.poly.funtube.service;

import java.util.Date;
import java.util.List;

import com.cloud.poly.funtube.dao.VideoDAO;
import com.cloud.poly.funtube.dao.impl.VideoDaoImpl;
import com.cloud.poly.funtube.model.Video;

public class VideoServiceImpl implements VideoService {

	private VideoDAO dao;

	public VideoServiceImpl() {
		if (dao == null) {
			dao = new VideoDaoImpl();
		}
	}

	@Override
	public void createVideo(String videoTitle, String s3Name, String s3url,String s3urlThumbnail,
			String description,String etag) {
		Video v = new Video();
		v.setDescription(description);
		v.setS3Name(s3Name);
		v.setS3URL(s3url);
		v.setS3URLThumbnail(s3urlThumbnail);
		v.setUploadDate(new Date());
		v.setVideoTitle(videoTitle);
		v.setTotalScore(1);
		v.setTotalScoreCount(1);
		v.setViewCount(1);
		v.setEtag(etag);
		createVideo(v);
	}

	@Override
	public void createVideo(Video v) {
		dao.createVideo(v);
	}

	@Override
	public int deleteAllVideos() {
		return dao.deleteAllVideos();
	}

	@Override
	public int deleteVideoByS3Name(String s3Name) {
		return dao.deleteVideoByS3Name(s3Name);
	}
	@Override
	public int deleteVideoByS3Url(String s3Url) {
		return dao.deleteVideoByS3Url(s3Url);
	}
	@Override
	public int deleteVideoByVideoTitle(String videoTitle) {
		return dao.deleteVideoByVideoTitle(videoTitle);
	}

	@Override
	public List<Video> findVideoByS3Name(String s3Name) {
		return dao.findVideoByS3Name(s3Name);
	}

	@Override
	public List<Video> findVideoByVideoTitle(String videoTitle) {

		return dao.findVideoByVideoTitle(videoTitle);
	}

	@Override
	public List<Video> findVideoInDateRange(Date start, Date end) {
		return dao.findVideoInDateRange(start, end);

	}

	@Override
	public List<Video> listAllVideos() {

		return dao.listAllVideos();
	}

	@Override
	public List<Video> listAllVideosByDateDesc(int start , int length) {
		return dao.listAllVideosByDateDesc(start ,length);
	}

	@Override
	public List<Video> listAllVideosByDateAsc(int start , int length) {
		return dao.listAllVideosByDateAsc(start ,length);
	}

	@Override
	public List<Video> listAllVideosByRating(int start , int length) {
		return dao.listAllVideosByRating(start ,length);
	}

	@Override
	public int updateVideoDescriptionByS3Name(String s3Name,
			String newDescription) {

		return dao.updateVideoDescriptionByS3Name(s3Name, newDescription);
	}

	@Override
	public int updateVideoScoreByS3Name(String s3Name, int score) {
		return dao.updateVideoScoreByS3Name(s3Name, score);
	}

	@Override
	public long getTotalVideos() {
		long count = dao.countAll(null);
		return count;
	}

	
}