package management_dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import management_dto.*;

public class ClientDao 
{
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("amit");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction et = em.getTransaction();

    public Client saveClient(Client client)
    {
        if (client != null)
        {
            et.begin();
            em.persist(client);
            et.commit();
            return client;
        } 
        else 
        {
            return null;
        }
    }

    public Client findClient(int clientId) 
    {
    	Client exClient = em.find(Client.class, clientId);
		if(exClient != null) {
			return exClient;
		}
		else
		{
			return null;
		}
		
    }

    public Client deleteClient(int clientId) 
    {
    	Client exClient = em.find(Client.class, clientId);
		if(exClient != null) {
			et.begin();
			em.remove(exClient);
			et.commit();
			return exClient;
		}
		else
		{
			return null;
		}
		
    }

    public Client updateClient(Client client, int clientId) 
    {
    	 client = em.find(Client.class, clientId);
         if (client != null) 
         {
             et.begin();
             Client updatedClient = em.merge(client);
             et.commit();
             return updatedClient;
         } 
         else
         {
             return null;
         }
    }
}