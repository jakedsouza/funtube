package com.cloud.poly.funtube;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.cloud.poly.funtube.model.Video;
import com.cloud.poly.funtube.service.VideoServiceImpl;
import com.cloud.poly.funtube.utils.WebServiceUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/video")
public class FuntubeController{
	@Context
	 protected HttpServletResponse response;
	 @Context
	 protected HttpServletRequest request;
	 VideoServiceImpl v = new VideoServiceImpl();
	 
	@POST
	@Consumes("multipart/form-data")
	@Path("/upload")
	public String uploadVideo(@FormDataParam("videoTitle") String videoTitle,@FormDataParam("videoDescription") String videoDescription,
            @FormDataParam("videoFile") FormDataContentDisposition contentDisposition,@FormDataParam("videoFile") File videoFile,@Context HttpServletRequest request) throws IOException {
		String ext = contentDisposition.getFileName().split("\\.(?=[^\\.]+$)")[1];
		String key = WebServiceUtils.generateKey();
		//key = key ;
		//String key = System.currentTimeMillis()+contentDisposition.getFileName();
		String s3Url = "https://s3.amazonaws.com/funtube/"+key+"."+ext;
		String s3UrlThumbnail = "https://s3.amazonaws.com/funtubethumb/"+key+".jpg";
		String etag = WebServiceUtils.uploadRequestToS3(videoFile, key, "."+ext);
		//String etag = "";
		v.createVideo(videoTitle, key, s3Url,s3UrlThumbnail, videoDescription,etag);
		return "upload successfull";
		
				
	//	uploadToS3(videoFile, title);
//
	}
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Path("/getTotalVideosCount")
	public 	long getTotalVideosCount(){
		long count = v.getTotalVideos();
		JSONObject totalCountJSON = new JSONObject();
		try {
			totalCountJSON.put("totalVideosCount", count);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}

	@POST
    @Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getLatestVideos")
	public List<Video> getLatestVideos(@FormParam("start") String start,@FormParam("length") String length){
		List<Video> latestVideos = v.listAllVideosByDateDesc(Integer.valueOf(start), Integer.valueOf(length));
		return latestVideos;		
	}
	
	@POST
    @Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/getTopVideos")
	public List<Video> getTopVideos(@FormParam("start") String start,@FormParam("length") String length){
		List<Video> topVideos = v.listAllVideosByRating(Integer.valueOf(start), Integer.valueOf(length));
		return topVideos ;
		
	}
	
	@POST
    @Produces( MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/deleteVideoByS3Url")
	public String deleteVideoByS3Url(@FormParam("s3Url") String s3Url,@FormParam("delVideoTitle") String delVideoTitle){
		WebServiceUtils.deleteVideoByS3Url(s3Url);
		int count = v.deleteVideoByS3Url(s3Url);
		return delVideoTitle;		
	}
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/updateScoreByS3Name")
	public int updateScoreByS3Name(@FormParam("s3Name") String s3Name,@FormParam("score") String score,@FormParam("title") String title){
		 int newScore = v.updateVideoScoreByS3Name(s3Name, Integer.parseInt(score));
		
		return newScore;		
	}
	
	


}
