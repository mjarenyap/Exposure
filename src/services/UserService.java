package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import beans.User;

public class UserService {
	//CRUD
	public static void addUser(User users)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			em.persist(users);
			trans.commit();
		}catch(Exception e){
			if(trans!=null)
				trans.rollback();
			
			e.printStackTrace();
		} finally{
			em.close();
		}
	}
	
	public static User getUser(int id)
	{
		User users = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			users = em.find(User.class, id);
			trans.commit();
		} catch(Exception e){
			if(trans!=null)
				trans.rollback();
			
			e.printStackTrace();
		} finally{
			em.close();
		}
		
		return users;
		
	}
	
	public static void deleteUser(int id)
	{	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			User a = em.find(User.class, id);
			em.remove(a);
			trans.commit();
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}
	}
	
	public static boolean updateUser(int id, User newinfo)
	{
		boolean success = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			//find album
			User a = em.find(User.class, id);
			//change info
			a.setName(newinfo.getName());
			a.setUsername(newinfo.getUsername());
			a.setPassword(newinfo.getPassword());
			a.setDescription(newinfo.getDescription());
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
	
	public static List<User> getAllUsers()
	{
		List<User> users = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqldb");
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		
		try{
			trans.begin();
			
			TypedQuery<User> query = em.createQuery("select users from users users", User.class);
			users = query.getResultList();
			
			trans.commit();
		
		}catch(Exception e){
			if(trans != null)
				trans.rollback();
			
			e.printStackTrace();
		}finally{
			em.close();
		}
		
		return users;	
	}
}
