/**
 * 
 */
package com.cloud.poly.funtube.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author jake
 * 
 */
@XmlRootElement
@Entity
@Table(name="Video",schema="funtube2")
@NamedQueries({
// List 
@NamedQuery(name="Video.listAllVideos", query="SELECT v FROM Video v"),
@NamedQuery(name="Video.listAllVideosByDateDesc", query="SELECT v FROM Video v ORDER BY v.uploadDate DESC"),
@NamedQuery(name="Video.listAllVideosByDateASC", query="SELECT v FROM Video v ORDER BY v.uploadDate"),
//@NamedQuery(name="Video.listAllVideosByRating", query="SELECT v FROM Video v ORDER BY (v.totalscore/v.totalscorecount) DESC"),

//delete
@NamedQuery(name="Video.deleteAllVideos", query="DELETE FROM Video v "),
@NamedQuery(name="Video.deleteVideoByS3Name", query="DELETE FROM Video v WHERE v.s3Name = :s3Name"),
@NamedQuery(name="Video.deleteVideoByS3Url", query="DELETE FROM Video v WHERE v.s3URL = :s3Url"),
@NamedQuery(name="Video.deleteVideoByVideoTitle", query="DELETE FROM Video v WHERE v.videoTitle = :videoTitle"),
// Find
@NamedQuery(name="Video.findVideoByS3Name", query="SELECT v FROM Video v WHERE v.s3Name = :s3Name"),
@NamedQuery(name="Video.findVideoByVideoTitle", query="SELECT v FROM Video v WHERE v.videoTitle = :videoTitle"),
@NamedQuery(name="Video.findVideoInDateRange", query="SELECT v FROM Video v WHERE v.uploadDate BETWEEN :startDate AND :endDate"),
// Update
@NamedQuery(name="Video.updateVideoScoreByS3Name", query="UPDATE Video v SET v.totalScore = v.totalScore + :score , v.totalScoreCount = v.totalScoreCount + 1 WHERE v.s3Name= :s3Name"),
@NamedQuery(name="Video.updateVideoDescriptionByS3Name", query="UPDATE Video v SET v.description =:description WHERE v.s3Name = :s3Name"),
})
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long totalScore;
	private long totalScoreCount;
	@Column(length = 1000)
	private String description;
	@Column(length = 1000)
	private String s3Name;
	@Column(length = 1000)
	private String s3URL;
	@Column(length =1000)
	private String s3URLThumbnail;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date uploadDate;
	
	@Column(length = 1000)
	private String videoTitle;
	
	
	
	private String etag;
	
	private int viewCount;
	//private long avgRating =totalScore/totalScoreCount;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the totalScore
	 */
	public long getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore
	 *            the totalScore to set
	 */
	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the s3Name
	 */
	public String getS3Name() {
		return s3Name;
	}

	/**
	 * @param s3Name
	 *            the s3Name to set
	 */
	public void setS3Name(String s3Name) {
		this.s3Name = s3Name;
	}

	/**
	 * @return the s3URL
	 */
	public String getS3URL() {
		return s3URL;
	}

	/**
	 * @param s3url
	 *            the s3URL to set
	 */
	public void setS3URL(String s3url) {
		s3URL = s3url;
	}

	/**
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * @param uploadDate
	 *            the uploadDate to set
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * @return the videoTitle
	 */
	public String getVideoTitle() {
		return videoTitle;
	}

	/**
	 * @param videoTitle
	 *            the videoTitle to set
	 */
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	/**
	 * @return the viewCount
	 */
	public int getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount
	 *            the viewCount to set
	 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public long getTotalScoreCount() {
		return totalScoreCount;
	}

	public void setTotalScoreCount(long totalScoreCount) {
		this.totalScoreCount = totalScoreCount;
	}
	
	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}
//
//	public long getAvgRating() {
//		return avgRating;
//	}
//
//	public void setAvgRating(long avgRating) {
//		this.avgRating = avgRating;
//	}

	public String getS3URLThumbnail() {
		return s3URLThumbnail;
	}

	public void setS3URLThumbnail(String s3urlThumbnail) {
		s3URLThumbnail = s3urlThumbnail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		long avg = this.totalScore/this.totalScoreCount ;
		StringBuilder sb= new StringBuilder();
		sb.append("{ id : "  + this.id+ ",");
		sb.append("videoTitle : "  + this.videoTitle + ",");
		sb.append(" s3Name : "  + this.s3Name+ ",");
		sb.append(" totalScore : "  + this.totalScore+ ",");
		sb.append(" totalScoreCount : "  + this.totalScoreCount+ ",");
		sb.append(" avg : "  + avg + ",");
		sb.append(" description : "  + this.description+ ",");
		sb.append(" s3URL : "  + this.s3URL+ ",");		
		sb.append(" viewCount : "  + this.viewCount+ ",");	
		sb.append(" uploadDate : "  + this.uploadDate.toString()+ " }");
		return sb.toString();
	}
}
