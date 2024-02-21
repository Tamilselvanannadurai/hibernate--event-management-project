package management_dto;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.*;

@Entity
public class ClientEvent 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientEventId;
	private int clientEventNoOfPeople;
	private LocalDate startDate;
	private int  clientEventNoOfDays;
	private String  clientEventLocation;										
	private double clientEventPrice;
	
	@ManyToOne
	private Client client;
	
	@OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
	private List<ClientService> clientService;
	
	private EventType eventType;

	
	
	public int getClientEventId() {
		return clientEventId;
	}
	public void setClientEventId(int clientEventId) {
		this.clientEventId = clientEventId;
	}
	public int getClientEventNoOfPeople() {
		return clientEventNoOfPeople;
	}
	public void setclientEventNoOfPeople(int clientEventNoOfPeople) {
		this.clientEventNoOfPeople = clientEventNoOfPeople;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public int getClientEventNoOfDays() {
		return clientEventNoOfDays;
	}
	public void setClientEventNoOfDays(int clientEventNoOfDays) {
		this.clientEventNoOfDays = clientEventNoOfDays;
	}
	public String getClientEventLocation() {
		return clientEventLocation;
	}
	public void setClientEventLocation(String clientEventLocation) {
		this.clientEventLocation = clientEventLocation;
	}
	public double getClientEventPrice() {
		return clientEventPrice;
	}
	public void setClientEventPrice(double clientEventPrice) {
		this.clientEventPrice = clientEventPrice;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<ClientService> getClientService() {
		return clientService;
	}
	public void setClientservice(List<ClientService> clientService) {
		this.clientService = clientService;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	@Override
	public String toString() {
		return "ClientEvent [clientEventId=" + clientEventId + ", clientEventNoOfPeople=" + clientEventNoOfPeople + ", startDate="
				+ startDate + ", clientEventNoOfDays=" + clientEventNoOfDays + ", clientEventLocation="
				+ clientEventLocation + ", clientEventPrice=" + clientEventPrice +  ", Client=" + client+ ",  clientService="
				+ clientService + ", eventType=" + eventType + "]";
	}
}
