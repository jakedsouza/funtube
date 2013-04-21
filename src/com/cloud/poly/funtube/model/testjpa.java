package com.cloud.poly.funtube.model;

import javax.persistence.EntityManagerFactory;
import com.cloud.poly.funtube.service.VideoService;
import com.cloud.poly.funtube.service.VideoServiceImpl;

public class testjpa {
	private static final String PERSISTENCE_UNIT_NAME = "Video";
	private static EntityManagerFactory factory;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		VideoService v = new VideoServiceImpl();
		//v.updateVideoScoreByS3Name("b", 5);
		System.out.println(v.listAllVideosByRating(1, 2));
		//v.createVideo("b", "b", "b", "b", "b", "b");
//		System.out
//				.println("Deleting database entries : " + v.deleteAllVideos());
		// testing create new video
		/*
		 * for (int i = 0; i < 100; i++) { v.createVideo(String.valueOf(i),
		 * String.valueOf(i), String.valueOf(i), String.valueOf(i));
		 * Thread.sleep(10000); }
		 */
		// deleting 20 to 25 by s3 name
//		for (int i = 20; i < 25; i++) {
//			System.out.println(v.deleteVideoByS3Name(String.valueOf(i)));
//		}

		// deleting 30 to 35 by title name
//		for (int i = 30; i < 35; i++) {
//			System.out.println(v.deleteVideoByVideoTitle(String.valueOf(i)));
//		}
		//v.createVideo("40","yy", "yy", "yy");
		//int out = v.updateVideoScoreByS3Name("yy", 3);
		//System.out.println(out);
//		System.out.println(v.findVideoByS3Name(String.valueOf(35)));
//		System.out.println(v.findVideoByVideoTitle(String.valueOf(41)));
//		System.out.println(v.findVideoByVideoTitle(String.valueOf(35)));		
//		System.out.println(v.findVideoInDateRange(null, null));
//		System.out.println(v.listAllVideos());
		
	}

}

