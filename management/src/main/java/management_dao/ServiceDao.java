package management_dao;

//import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import management_dto.Service;
//import com.mysql.cj.Query;

public class ServiceDao 
{
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("amit");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction et = em.getTransaction();
    
    
    public Service saveService(Service service) 
    {
        if (service != null) 
        {
            et.begin();
            em.persist(service);
            et.commit();
            return service;
        } 
        else 
        {
            return null;
        }
    }
    
    public Service findService(int id)
    {
    	Service exService = em.find(Service.class, id);
		if(exService != null) 
		{
			return exService;
		}
		else
		{
			return null;
		}
		
    }
    
    public Service deleteServiceById(int id ) 
    {
		
		Service exService = em.find(Service.class, id);
		if(exService != null) 
		{
			et.begin();
			em.remove(exService);
			et.commit();
			return exService;
		}
		else
		{
			return null;
		}
	
	}
    
    public Service updateService(Service service ,int id)
    {
		
    	 if (service != null) 
    	 {
	            et.begin();
	            Service updatedService = em.merge(service);
	            et.commit();
	            return updatedService;
	     } 
    	 else
    	 {
	            return null;
	     }
		
	}
}
