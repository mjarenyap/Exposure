package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import beans.Tag;



public class TagService {
	//CRUD
	public static void addTag(Tag tags)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			em.persist(tags);
			trans.commit();
		}catch(Exception e){
			if(trans!=null)
				trans.rollback();
			
			e.printStackTrace();
		} finally{
			em.close();
		}
	}
	
	public static Tag getTag(int id)
	{
		Tag tags = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			tags = em.find(Tag.class, id);
			trans.commit();
		} catch(Exception e){
			if(trans!=null)
				trans.rollback();
			
			e.printStackTrace();
		} finally{
			em.close();
		}
		
		return tags;
		
	}
	
	public static void deleteTag(int id)
	{	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			Tag a = em.find(Tag.class, id);
			em.remove(a);
			trans.commit();
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}
	}
	
	public static boolean updateTag(int id, Tag newinfo)
	{
		boolean success = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			//find album
			Tag a = em.find(Tag.class, id);
			//change info
			a.setName(newinfo.getName());
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
	
	public static List<Tag> getAllTags()
	{
		List<Tag> tags = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<Tag> query = em.createQuery("select tags from tags tags", Tag.class);
			tags = query.getResultList();
			
			trans.commit();
		
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return tags;
		
	}
}
