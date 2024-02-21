package management_dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

//import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import management_dto.ClientService;

public class ClientServiceDao 
{

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("amit");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction et = em.getTransaction();
    
    
    public ClientService saveClientService(ClientService clientService) 
    {
        if (clientService != null) 
        {
            et.begin();
            em.persist(clientService);
            et.commit();
            return clientService;
        } 
        else 
        {
            return null;
        }
    }
    
    public ClientService findClientService(int id) 
    {
		
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService != null) 
		{
			return exClientService;
		}
		else
		{
			return null;
		}
		
	}
    
    public ClientService deleteClientServiceById(int id ) 
    {
		
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService != null) 
		{
			et.begin();
			em.remove(exClientService);
			et.commit();
			return exClientService;
		}
		else
		{
			return null;
		}
		
	}
    
    public ClientService updateClientService(ClientService clientService ,int id) 
    {
		
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService != null ) 
		{
			exClientService.setClientServiceId(exClientService.getClientServiceId());
			et.begin();
			em.merge(clientService);
			et.commit();
			return clientService;
		}
		else
		{
			return null;
		}
		
	}
}
