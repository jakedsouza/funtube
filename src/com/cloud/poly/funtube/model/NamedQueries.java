package com.cloud.poly.funtube.model;

public class NamedQueries {
	// List
	public static final String listAllVideos = "Video.listAllVideos";
	public static final String listAllVideosByDateDesc = "Video.listAllVideosByDateDesc";
	public static final String listAllVideosByDateAsc = "Video.listAllVideosByDateASC";
	public static final String listAllVideosByRating = "Video.listAllVideosByRating";
	
	// delete
	public static final String deleteAllVideos = "Video.deleteAllVideos";
	public static final String deleteVideoByS3Name = "Video.deleteVideoByS3Name";
	public static final String deleteVideoByS3Url= "Video.deleteVideoByS3Url";
	public static final String deleteVideoByVideoTitle = "Video.deleteVideoByVideoTitle";
	// Find
	public static final String findVideoByS3Name = "Video.findVideoByS3Name";
	public static final String findVideoByVideoTitle = "Video.findVideoByVideoTitle";
	public static final String findVideoInDateRange = "Video.findVideoInDateRange";
	// Update
	public static final String updateVideoScoreByS3Name = "Video.updateVideoScoreByS3Name";
	public static final String updateVideoDescriptionByS3Name = "Video.updateVideoDescriptionByS3Name";

}