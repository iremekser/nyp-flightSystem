import java.util.Calendar;

public class Flight {
	private int id;
	private Capital from;
	private Capital to;
	private String flightNumber;
	private String airline;
	private String aircraftModel;
	private Calendar scheduledDeparture;
	private Calendar scheduledArrival;
	private Calendar departure;
	private Calendar arrival;
	
	public Flight(int id, Capital from, Capital to, String flightNumber, String airline, String aircraftModel,
			Calendar scheduledDeparture, Calendar scheduledArrival) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.flightNumber = flightNumber;
		this.airline = airline;
		this.aircraftModel = aircraftModel;
		this.scheduledDeparture = scheduledDeparture;
		this.scheduledArrival = scheduledArrival;
		this.departure = scheduledDeparture;
		this.arrival = scheduledArrival;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Capital getFrom() {
		return from;
	}
	public void setFrom(Capital from) {
		this.from = from;
	}
	public Capital getTo() {
		return to;
	}
	public void setTo(Capital to) {
		this.to = to;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getAircraftModel() {
		return aircraftModel;
	}
	public void setAircraftModel(String aircraftModel) {
		this.aircraftModel = aircraftModel;
	}
	public Calendar getScheduledDeparture() {
		return scheduledDeparture;
	}
	public void setScheduledDeparture(Calendar scheduledDeparture) {
		this.scheduledDeparture = scheduledDeparture;
	}
	public Calendar getScheduledArrival() {
		return scheduledArrival;
	}
	public void setScheduledArrival(Calendar scheduledArrival) {
		this.scheduledArrival = scheduledArrival;
	}
	public Calendar getDeparture() {
		return departure;
	}
	public void setDeparture(Calendar departure) {
		this.departure = departure;
	}
	public Calendar getArrival() {
		return arrival;
	}
	public void setArrival(Calendar arrival) {
		this.arrival = arrival;
	}
	
	
}
