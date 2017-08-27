package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import beans.Photo;


public class PhotoService {
	//CRUD operators
	
	//createAlbum
	public static void addPhoto(Photo photos)
	{
		//connect to JDBC driver
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		//add object into db
		try{
			trans.begin();
			em.persist(photos);
			trans.commit();
		}catch(Exception e){
			if(trans!=null)
				trans.rollback();
			
			e.printStackTrace();
		} finally{
			em.close();
		}
		
		
		//close connections
	}
	
	//retrieveSingleAlbum
	public static Photo getPhoto(int id)
	{
		Photo photos = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try{
			trans.begin();
			photos = em.find(Photo.class, id);
			//album.setName("otherrr");
			trans.commit();
		} catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		} finally{
			em.close();
		}
		
		return photos;
	}
	//deleteAlbum
	public static void deletePhoto(int id)
	{	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			//find the album
			Photo a = em.find(Photo.class, id);
			//remove album a from em
			em.remove(a);
			trans.commit();
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}
	}
	
	//retrieveAllAlbums
	public static List<Photo> getAllPhotos()
	{
		List<Photo> photos = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Photo> query = em.createQuery("select photos from photos photos", Photo.class);
			photos = query.getResultList();
			
			trans.commit();
		
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return photos;
		
	}
	
	//updateAlbum
	public static boolean updatePhoto(int id, Photo newinfo)
	{
		boolean success = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			//find album
			Photo a = em.find(Photo.class, id);
			//change info
			a.setTitle(newinfo.getTitle());
			a.setDescription(newinfo.getDescription());
			a.setPrivacy(newinfo.isPrivacy());
			trans.commit();
			
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return success;
	}
}
