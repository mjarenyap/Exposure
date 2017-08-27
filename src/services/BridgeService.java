package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import beans.PhotoTag;
import beans.Privilege;



public class BridgeService {

		//Create privilege
	
	public static void addPrivilege(Privilege privilege)
	{
		//connect to JDBC driver
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		//add object into db
		try{
			trans.begin();
			em.persist(privilege);
			trans.commit();
		}catch(Exception e){
			if(trans!=null)
				trans.rollback();
			
			e.printStackTrace();
		}
		
		
		//close connections
		em.close();
	}
	
		//Create phototag
	
	public static void addPhotoTag(PhotoTag photoTags)
	{
		//connect to JDBC driver
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		//add object into db
		try{
			trans.begin();
			em.persist(photoTags);
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
	
		//get all privileges
	public static List<Privilege> getAllPrivileges()
	{
		List<Privilege> privilege = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Privilege> query = em.createQuery("select privilege from privilege privilege", Privilege.class);
			privilege = query.getResultList();
			
			trans.commit();
		
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return privilege;
		
	}
	
		//get all phototags
	public static List<PhotoTag> getAllPhotoTags()
	{
		List<PhotoTag> photoTags = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<PhotoTag> query = em.createQuery("select phototags from phototags phototags", PhotoTag.class);
			photoTags = query.getResultList();
			
			trans.commit();
		
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return photoTags;	
	}	
}
