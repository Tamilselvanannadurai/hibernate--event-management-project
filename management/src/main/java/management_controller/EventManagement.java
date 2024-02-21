package management_controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Persistence;
import javax.persistence.Query;
import management_dao.AdminDao;
import management_dao.ClientDao;
import management_dao.ClientEventDao;
import management_dao.ClientServiceDao;
import management_dao.ServiceDao;
import management_dto.Admin;
import management_dto.Client;
import management_dto.ClientEvent;
import management_dto.ClientService;
import management_dto.EventType;
import management_dto.Service;

public class EventManagement 
{

	AdminDao aDao = new AdminDao();
	ClientDao cDao = new ClientDao();
	ClientEventDao ceDao = new ClientEventDao();
	ClientServiceDao csDao = new ClientServiceDao();
	ServiceDao sDao = new ServiceDao();
	
	public static void main(String[] args) 
	{
	//	EventManagement em = new EventManagement();
		

	}

	
	public Admin saveAdmin() {
		Admin admin = new Admin();
		System.out.println("\tADMIN SIGNUP");
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Admin Name: ");
		admin.setAdminName(s.nextLine());
		System.out.println("Enter Admin Email: ");
		admin.setAdminMail(s.next());
		System.out.println("Enter Admin Password: ");
		admin.setAdminPassword(s.next());
		System.out.println("Enter Admin Conatct: ");
		admin.setAdminContact(s.nextLong());
		s.close();
		return aDao.saveAdmin(admin);
	}
	
	
	public Admin adminLogin() {
		Scanner s = new Scanner(System.in);
		System.out.println("enter admin email");
		String adminMail = s.next();
		System.out.println("enter admin password");
		String adminPassword = s.next();
		s.close();
		javax.persistence.Query query = Persistence.createEntityManagerFactory("amit").createEntityManager()
				.createQuery("select a from Admin a where a.adminMail=?1");
		query.setParameter(1, adminMail);
		Admin exAdmin = (Admin) query.getSingleResult();
		if (exAdmin != null) 
		{
			if (exAdmin.getAdminPassword().equals(adminPassword))
			{
				return exAdmin;
			}
			return null;
		}
		return null;
	}
	
	public Service saveService()
	{
		System.out.println("enter admin credentials to proceed");
		
		Admin exAdmin = adminLogin();
		
		if (exAdmin != null) 
		{
			Service service = new Service();
			
			Scanner s = new Scanner(System.in);
			
			System.out.println("enter service name");
			service.setServiceName(s.next());
			
			System.out.println("enter service cost per day");
			service.setServiceCostPerDay(s.nextDouble());
			
			System.out.println("enter service cost per person");
			service.setServiceCostPerPerson(s.nextDouble());
			s.close();
			Service savedService= sDao.saveService(service);
			
			exAdmin.getServices().add(savedService);
			aDao.updateAdmin(exAdmin, exAdmin.getAdminId());
			return service;
			
		}
		return null;
	}
	
	public List<Service> getAllServices() 
	{
		System.out.println("enter admin credentials to proceed");
		Admin exAdmin = adminLogin();
		
		if (exAdmin != null)
		{
			return exAdmin.getServices();
		}
		return null;
	}
	
	
	public String updateService() 
	{
		Scanner sc = new Scanner(System.in);
		
		List<Service> services = getAllServices();
		
		for (Service s : services) 
		{
			System.out.println("serviceId    " + "serviceName   " + "cost_per_person   " + "cost_per_day");
			System.out.println("     " + s.getServiceId() + "        " + s.getServiceName() + "          "
					+ s.getServiceCostPerPerson() + "           " + s.getServiceCostPerDay());
		}

		System.out.println("*************** choose service id from above to update **************");
		
		int id = sc.nextInt();
		Service tobeUpdated = sDao.findService(id);
		
		System.out.println("enter updated cost per person");
		double costperPerson = sc.nextDouble();
		
		System.out.println("enter updated cost per day");
		double costperday = sc.nextDouble();
		sc.close();
		tobeUpdated.setServiceCostPerDay(costperday);
		tobeUpdated.setServiceCostPerPerson(costperPerson);

		Service updated = sDao.updateService(tobeUpdated, id);
		if (updated != null) 
		{
			return "service update success";
		}
		return "invalid service id";
	}
	
	
	public void deleteService() 
	{
		Scanner sc = new Scanner(System.in);
		Admin exAdmin = adminLogin();

		if (exAdmin != null)
		{
			List<Service> services = exAdmin.getServices();
			System.out.println("************** choose service id from below to delete *************");

			for (Service s : services) 
			{
				System.out.println("serviceId    " + "serviceName   " + "cost_per_person   " + "cost_per_day");
				System.out.println("     " + s.getServiceId() + "        " + s.getServiceName() + "          "
						+ s.getServiceCostPerPerson() + "           " + s.getServiceCostPerDay());
			}
			
			int id = sc.nextInt();
			sc.close();
			List<Service> newList = new ArrayList<Service>();

			for (Service s : services) 
			{
				if (s.getServiceId() != id)
				{
					newList.add(s);
				}
			}

			exAdmin.setServices(newList);
			aDao.updateAdmin(exAdmin, exAdmin.getAdminId());
			sDao.deleteServiceById(id);
		}
	}
	
	public Client saveClient() {
		Scanner sc = new Scanner(System.in);
		
		Client client = new Client();
		
		System.out.println("enter client name");
		client.setClientName(sc.next());
		
		System.out.println("enter client email");
		client.setClientMail(sc.next());
		
		System.out.println("enter client password");
		client.setClientPassword(sc.next());
		
		System.out.println("enter client contact");
		client.setClientContact(sc.nextLong());
		sc.close();
		return cDao.saveClient(client);
	}

	
	public Client clientLogin() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("enter client email");
		String email = sc.next();
		
		System.out.println("enter client password");
		String password = sc.next();
		sc.close();
		Query query = Persistence.createEntityManagerFactory("amit").createEntityManager()
				.createQuery("select c from Client c where c.clientMail=?1");
		
		query.setParameter(1, email);
		
		Client client = (Client) query.getSingleResult();

		if (client != null) 
		{
			if (client.getClientPassword() == password) 
			{
				return client;
			}
			return null;
		}
		return null;
	}
	
	
	// without adding clientService

		public Client createClientEvent() 
		{
			Client client = clientLogin();
			ClientEvent event = new ClientEvent();
			
			if (client != null) 
			{

				System.out.println("enter event type");
				int i = 1;
				for (EventType e : EventType.values()) 
				{
					System.out.println(i + " " + e);
					i++;
				}
				
				Scanner sc= new Scanner(System.in);
				int eventType = sc.nextInt();
				
				switch (eventType) 
				{
				case 1:
					event.setEventType(EventType.Anniversary);
					break;
					
				case 2:
					event.setEventType(EventType.babyShower);
					break;
					
				case 3:
					event.setEventType(EventType.BachelorParty);
					break;
					
				case 4:
					event.setEventType(EventType.BirthDay);
					break;
					
				case 5:
					event.setEventType(EventType.Engagement);
					break;
					
				case 6:
					event.setEventType(EventType.Marriage);
					break;
					
				case 7:
					event.setEventType(EventType.NamingCeremony);
					break;
					
				case 8:
					event.setEventType(EventType.Reunion);
					break;
					
				default:
					event.setEventType(EventType.BirthDay);
					break;
				}
				
				System.out.println("enter the event location");
				String eventLocation = sc.next();
				event.setClientEventLocation(eventLocation);

				System.out.println("enter the " + event.getEventType() + " year");
				int year = sc.nextInt();
				System.out.println("enter the " + event.getEventType() + " month");
				int month = sc.nextInt();
				System.out.println("enter the " + event.getEventType() + " date");
				int day = sc.nextInt();
				event.setStartDate(LocalDate.of(year, month, day));

				System.out.println("enter number of days for the event");
				event.setClientEventNoOfDays(sc.nextInt());

				System.out.println("enter the number of people that will be attending the event");
				event.setclientEventNoOfPeople(sc.nextInt());
				sc.close();
				ClientEvent savedEvent = ceDao.saveClientEvent(event);

				savedEvent.setClient(client);
				
				client.getEvents().add(savedEvent);

				return cDao.updateClient(client, client.getClientId());

			}
			return null;
		}
	
	
		//add services to the client event
		
		public void addEventServices() 
		{
			Client client = clientLogin();
			
			if (client != null) 
			{
				for (ClientEvent event : client.getEvents()) 
				{
					System.out.println("Event Id   " + "Event type   " + "Event location");
					System.out.println(
							event.getClientEventId() + " " + event.getEventType() + " " + event.getClientEventLocation());
				}

				Scanner sc= new Scanner(System.in);
				System.out.println("Enter the event id you want to add services to");
				int eventId = sc.nextInt();
				
				ClientEvent event = ceDao.findClientEvent(eventId);
				
				if (event != null) 
				{
					Admin admin = adminLogin();
					if (admin != null) 
					{
						System.out.println("ener the number of services you want to add to the event");
						int count = sc.nextInt();
						while (count > 0) 
						{

							for (Service cs : admin.getServices()) 
							{
								System.out.println(cs.getServiceId() + " " + cs.getServiceName());
							}
							System.out.println("enter the service id you want to choose");
							int serviceId = sc.nextInt();

							Service exService = sDao.findService(serviceId);
							
							ClientService cs = new ClientService();
							
							cs.setClientServiceCost(exService.getServiceCostPerDay() * exService.getServiceCostPerPerson() * event.getClientEventNoOfDays());
							cs.setClientServiceName(exService.getServiceName());
							cs.setClientServiceNoOfDays(event.getClientEventNoOfDays());
							cs.setClientServiceCostPerPerson(exService.getServiceCostPerPerson());

//							if (exService.getServiceCostPerPerson() == 0) {
//								cs.setClientServiceCost(exService.getServiceCostPerDay() * event.getClientEventNoOfDays());
//							} else {
//								cs.setClientServiceCost(exService.getServiceCostPerDay() * event.getClientEventNoOfDays()
//										* exService.getServiceCostPerPerson());
//
//							}
							event.setClientEventPrice(event.getClientEventPrice() + cs.getClientServiceCost());
							event.getClientService().add(cs);
							ceDao.updateClientEvent(event, event.getClientEventId());
							count--;
							sc.close();
						}
						
					} // admin credentials are wrong to get all the services
					
				} // event does not exist with given event id
				
			} // client login failed
		}
		
		
		
		public ClientEvent deleteEventService() 
		{
			Client client = clientLogin();
			if (client != null)
			{
				for (ClientEvent event : client.getEvents()) 
				{
					System.out.println(event.getClientEventId()+" "+event.getEventType());
				}
				
				
				System.out.println("choose the  event from which you want to remove a service");
				Scanner sc= new Scanner(System.in);
				int eventId = sc.nextInt();
				
				ClientEvent event = ceDao.findClientEvent(eventId);
				
				List<ClientService> services = event.getClientService();
				
				for(ClientService service: services) 
				{
					System.out.println(service.getClientServiceId()+" "+service.getClientServiceName());
				}
				
				System.out.println("enter the service id you want to delete from event");
				int sId = sc.nextInt();
				sc.close();
				for(ClientService service: services) 
				{
					if(service.getClientServiceId()==sId) 
					{
						services.remove(service);
					}
				}
				event.setClientservice(services);
				
				ceDao.updateClientEvent(event,event.getClientEventId());
				return event;
			}
			return null; // wrong client credentials
		}

}
