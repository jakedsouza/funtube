package com.cloud.poly.funtube.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.Grant;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.SetBucketAclRequest;
import com.cloud.poly.funtube.FuntubeController;
import com.cloud.poly.funtube.service.VideoServiceImpl;

public class WebServiceUtils {
	private static AWSCredentials credentials;
	private static AmazonS3Client client;
	private static String BUCKET_NAME = "funtube";
	private static String Image_BUCKET_NAME = "funtubethumb";
	private static void initS3() {
		if (client != null) {
			return;
		}
		try {
			credentials = new PropertiesCredentials(
					FuntubeController.class
							.getResourceAsStream("/AwsCredentials.properties"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		client = new AmazonS3Client(credentials);
	}
	
	

	public static String uploadRequestToS3(File videoFile, String key,String videoExt) {
		createBucket(BUCKET_NAME);
		createBucket(Image_BUCKET_NAME);
		String videoFileKey = key + videoExt;
		String thumbnailKey = key+ ".jpg";
		System.out.println("Generating thumbnail");
		File thumbNailFile = null;
		try {
			thumbNailFile =	generateThumbnail(videoFile.getCanonicalPath(),thumbnailKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done generating thumbnail");
		
		System.out.println("uploading video to s3 " + new Date().toString());
		String etag = uploadVideoToS3(videoFile, videoFileKey);
		if(thumbNailFile != null){
			uploadThumbnailToS3(thumbNailFile, thumbnailKey);	
		}	
		return etag;
	}
	
	
	public static String uploadThumbnailToS3(File file , String key){
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/*");		
		String etag = uploadFileToS3(Image_BUCKET_NAME, file, key, metadata);
		return etag;
	}

	public static String uploadVideoToS3(File file , String key){
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("video/*");		
		String etag = uploadFileToS3(BUCKET_NAME, file, key, metadata);
		return etag;
	}
	
	public static String uploadFileToS3(String bucketName ,File file ,String key, ObjectMetadata metadata ){
		initS3();
		long currentTime = System.currentTimeMillis();
		System.out.println("uploading file " + file.getAbsolutePath() + " " + file.getName() +"  to s3 " + new Date().toString());	
		PutObjectRequest request = new PutObjectRequest(bucketName, key,file);
		request.setMetadata(metadata);
		request.setCannedAcl(CannedAccessControlList.PublicReadWrite);

		PutObjectResult result = client.putObject(request);
		System.out.println("done uploading file " + file.getAbsolutePath() +" " + file.getName() + "  to s3 " + new Date().toString());
		System.out.println("Total time taken : " + (System.currentTimeMillis() - currentTime));
		return result.getETag();
	}
	
	
	
	
	public static String generateKey() {
		String out =RandomStringUtils.randomAlphanumeric(11);
		VideoServiceImpl v = new VideoServiceImpl();
		int i = v.findVideoByS3Name(out).size()  ;
		while(i != 0 ){
			out =RandomStringUtils.randomAlphanumeric(11);
			i = v.findVideoByS3Name(out).size()  ;
		}
		return out ;
	}
	
	public static File generateThumbnail(String filePath,String fileName){
	//	String outputFileName  = fileName.split("\\.(?=[^\\.]+$)")[0] + ".jpg" ;
		fileName = "/tmp/"+fileName ;
		 String command = "ffmpegthumbnailer -i "+ filePath + " -o " + fileName +" -q 10 -s 256 ";
		//String command = "ipconfig" ;
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
			return null ;
		}
		File file = new File(fileName);		
		return file;		
	}
	public static void deleteVideoByS3Url(String s3Url) {
		initS3();
		String key ="";
		String split [] =s3Url.split("/");
		key = split[4];
		DeleteObjectRequest videoRequest = new DeleteObjectRequest(BUCKET_NAME, key);
		client.deleteObject(videoRequest);
		key = key.replaceFirst(".mp4",".jpg");
		//split = key.split(" .");
	//	key = split[0] + ".jpg";
		DeleteObjectRequest thumbNailRequest = new DeleteObjectRequest(Image_BUCKET_NAME, key);
		System.out.println(key);
		client.deleteObject(thumbNailRequest);
		
	}
	
	public static boolean createBucket(String bucketName){
		initS3();
		CreateBucketRequest request = new CreateBucketRequest(bucketName);
		//SetBucketAclRequest setBucketAclRequest = new SetBucketAclRequest(bucketName, );
		request.setCannedAcl(CannedAccessControlList.PublicReadWrite);
		try{
			//client.setBucketAcl(setBucketAclRequest);
		Bucket bucket=	client.createBucket(request);
			return true;
		}catch(AmazonClientException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;		
	}
	

	public static void main(String args[]) {
		
		createBucket("badsfasdfas");
	}

}
