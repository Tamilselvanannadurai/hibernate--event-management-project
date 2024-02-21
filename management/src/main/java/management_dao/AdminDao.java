package management_dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import management_dto.Admin;

public class AdminDao 
{
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("amit");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction et = em.getTransaction();

    public Admin saveAdmin(Admin admin) 
    {
        if (admin != null) 
        {
            et.begin();
            em.persist(admin);
            et.commit();
            return admin;
        } 
        else 
        {
            return null;
        }
    }

    public Admin findAdmin(int adminId) 
    {
        Admin admin = em.find(Admin.class, adminId);
        if (admin != null)
        {
            return admin;
        } 
        else 
        {
            return null;
        }
    }

    public Admin deleteAdmin(int adminId) 
    {
        Admin admin = em.find(Admin.class, adminId);
        if (admin != null) 
        {
            et.begin();
            em.remove(admin);
            et.commit();
            return admin;
        } 
        else 
        {
            return null;
        }
    }

    public Admin updateAdmin(Admin admin,int adminId) 
    {
        Admin exAdmin= findAdmin(adminId);
        
        if (exAdmin != null)
        {
        	admin.setAdminId(adminId);
            et.begin();
            em.merge(admin);
            et.commit();
            return admin;
        } 
        else 
        {
            return null;
        }
    }

	
    
   
    
}
