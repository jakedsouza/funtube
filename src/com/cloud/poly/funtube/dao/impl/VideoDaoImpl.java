package com.cloud.poly.funtube.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Component;

import com.cloud.poly.funtube.dao.VideoDAO;
import com.cloud.poly.funtube.model.NamedQueries;
import com.cloud.poly.funtube.model.Video;

@Component("videoDao")
public class VideoDaoImpl extends GenericDaoImpl< Video> implements VideoDAO{


	@Override
	public void createVideo(Video v) {
		super.create(v);		
	}

	@Override
	public int deleteAllVideos() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNamedQuery(NamedQueries.deleteAllVideos);
		int count = query.executeUpdate();
		tx.commit();
		return count ;
	}

	@Override
	public int deleteVideoByS3Name(String s3Name) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNamedQuery(NamedQueries.deleteVideoByS3Name);
		query.setParameter("s3Name", s3Name);
		int count = query.executeUpdate();
		tx.commit();
		return count;
	}
	
	@Override
	public int deleteVideoByS3Url(String s3Url) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNamedQuery(NamedQueries.deleteVideoByS3Url);
		query.setParameter("s3Url", s3Url);
		int count = query.executeUpdate();
		tx.commit();
		return count;
	}

	@Override
	public int deleteVideoByVideoTitle(String videoTitle) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNamedQuery(NamedQueries.deleteVideoByVideoTitle);
		query.setParameter("videoTitle", videoTitle);
		int count = query.executeUpdate();
		tx.commit();
		return count ;		
	}

	@Override
	public List<Video> findVideoByS3Name(String s3Name) {
		Query query = em.createNamedQuery(NamedQueries.findVideoByS3Name);
		query.setParameter("s3Name", s3Name);
		return query.getResultList();
	}

	@Override
	public List<Video> findVideoByVideoTitle(String videoTitle) {
		Query query = em.createNamedQuery(NamedQueries.findVideoByVideoTitle);
		query.setParameter("videoTitle", videoTitle);
		return query.getResultList();
	}

	@Override
	public List<Video> findVideoInDateRange(Date start, Date end) {
		Query query = em.createNamedQuery(NamedQueries.findVideoInDateRange);
		query.setParameter("startDate", start);
		query.setParameter("endDate", end);
		return query.getResultList();
	}

	@Override
	public List<Video> listAllVideos() {
		Query query = em.createNamedQuery(NamedQueries.listAllVideos);
		return query.getResultList();
	}

	@Override
	public List<Video> listAllVideosByDateDesc(int start , int length) {
		Query query = em.createNamedQuery(NamedQueries.listAllVideosByDateDesc);
		if(start != -1 || length != -1){
			query.setFirstResult(start);
			query.setMaxResults(length);
		}
		return query.getResultList();
	}

	@Override
	public List<Video> listAllVideosByDateAsc(int start , int length) {
		Query query = em.createNamedQuery(NamedQueries.listAllVideosByDateAsc);
		if(start != -1 || length != -1){
			query.setFirstResult(start);
			query.setMaxResults(length);
		}
		return query.getResultList();
	}

	@Override
	public List<Video> listAllVideosByRating(int start , int length) {
	//	Query query = em.createNamedQuery(NamedQueries.listAllVideosByRating);
		//Query query = em.createQuery("SELECT  v   FROM Video v ");
		Query query = em.createNativeQuery("SELECT * FROM Video order by (totalscore/totalscorecount) desc, uploaddate desc ;",Video.class);
		
		if(start != -1 || length != -1){
			query.setFirstResult(start);
			query.setMaxResults(length);
		}
		return query.getResultList();
	}


	@Override
	public int updateVideoDescriptionByS3Name(String s3Name,
			String newDescription) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNamedQuery(NamedQueries.updateVideoDescriptionByS3Name);
		query.setParameter("s3Name", s3Name);
		query.setParameter("description", newDescription);
		int count = query.executeUpdate();
		tx.commit();
		return count ;		
	}

	@Override
	public int updateVideoScoreByS3Name(String s3Name, int score) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createNamedQuery(NamedQueries.updateVideoScoreByS3Name);
		query.setParameter("s3Name", s3Name);
		query.setParameter("score", score);
		int count = query.executeUpdate();
		tx.commit();
		return count ;		
	}



	

   
}