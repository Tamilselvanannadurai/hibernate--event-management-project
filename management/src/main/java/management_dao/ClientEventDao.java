package management_dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import management_dto.ClientEvent;

public class ClientEventDao 
{
		private EntityManagerFactory emf = Persistence.createEntityManagerFactory("amit");
	    private EntityManager em = emf.createEntityManager();
	    private EntityTransaction et = em.getTransaction();
	    
	    public ClientEvent saveClientEvent(ClientEvent clientEvent) 
	    {
			et.begin();
			em.persist(clientEvent);
			et.commit();
			return clientEvent;
		}
	    
	    public ClientEvent findClientEvent(int id) {
			
			ClientEvent exClientEvent = em.find(ClientEvent.class, id);
			if(exClientEvent != null) 
			{
				return exClientEvent;
			}
			else
			{
				return null;
			}
			
		}
	    
	    public ClientEvent deleteClientEventById(int id ) 
	    {
			
			ClientEvent exClientEvent = em.find(ClientEvent.class, id);
			if(exClientEvent != null) 
			{
				et.begin();
				em.remove(exClientEvent);
				et.commit();
				return exClientEvent;
			}
			else
			{
				return null;
			}
			
		}
	    
	    public ClientEvent updateClientEvent(ClientEvent clientEvent ,int id) 
	    {
			
			ClientEvent exClientEvent = em.find(ClientEvent.class, id);
			if(exClientEvent != null )
			{
				exClientEvent.setClientEventId(exClientEvent.getClientEventId());
				et.begin();
				em.merge(clientEvent);
				et.commit();
				return clientEvent;
			}
			else
			{
				return null;
			}
			
		}
}
