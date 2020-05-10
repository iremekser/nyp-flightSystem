import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

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
	private boolean accepted;
	private boolean delayed;
	private boolean canceled;
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Thread flightThread;

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

		startThread();
	}

	public String getInfo() {
		return "#" + this.getId() + "  " + this.getAirline() + "  " + this.getFrom().getName() + "->"
				+ this.getTo().getName() + "   " + this.getFlightNumber() + "  "
				+ (canceled ? "uçuþ iptal edildi"
						: (isLanded() ? "iniþ yapýldý"
								: ((isTakeOff() ? "     iniþe " : "    kalkýþa ") + minuteLeft() + " dakika kaldý "
										+ (accepted ? " -- iniþ izni verildi"
												: (delayed ? " -- uçuþ geciktirildi" : "")))));
	}

	public void appendText() throws IOException {
		Files.write(Paths.get("src/flights.txt"), txtRow().getBytes(), StandardOpenOption.APPEND);

	}

	public String txtRow() {
		return this.getId() + "," + this.getFrom().getId() + "," + this.getTo().getId() + "," + this.getFlightNumber()
				+ "," + this.getAirline() + "," + this.getAircraftModel() + "," + this.getDepartureFormatted() + ","
				+ this.getArrivalFormatted() + "\n";
	}

	public float minuteLeft() {
		if (isTakeOff())
			return (this.getArrival().getTimeInMillis() - MainScreen.systemTime.getTimeInMillis()) / 1000 / 60;
		else
			return (this.departure.getTimeInMillis() - MainScreen.systemTime.getTimeInMillis()) / 1000 / 60;
	}

	public boolean isTakeOff() {
		return this.departure.getTimeInMillis() < MainScreen.systemTime.getTimeInMillis();
	}

	public boolean isLanded() {
		return isTakeOff() && minuteLeft() <= 0;
	}

	public void addMinuteToArrival(int minute) {
		this.arrival.add(Calendar.MINUTE, minute);
	}

	public Flight() {
		// startThread();
	}

	public void startThread() {
		
		flightThread = new Thread() {
			public void run() {
				while (true) {
					Random r = new Random();
					try {
						sleep(1000);
						if (isLanded()) {
							stop();
							return;
						}
						if (!isTakeOff() && minuteLeft() == 15) {
							int random = r.nextInt(100);
							System.out.println(random);
							if (random < 15) {
								canceled = true;
								accepted = false;
								delayed = false;
								stop();
							}
						}
						if (isTakeOff() && minuteLeft() < 15 && accepted == false) {
							int random = r.nextInt(100);
							if (random < 50) {
								accepted = true;
								canceled = false;
							} else {
								addMinuteToArrival(10);
								delayed = true;
							}
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
		if (!isLanded())
			flightThread.start();

	}

	public void stopThread() {
		this.flightThread.stop();
	}

	public void pauseThread() {
		this.flightThread.suspend();
	}

	public void resumeThread() {
		this.flightThread.resume();
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

	public String getScheduledDepartureFormatted() {
		return sdf.format(scheduledDeparture.getTime());
	}

	public void setScheduledDeparture(Calendar scheduledDeparture) {
		this.scheduledDeparture = scheduledDeparture;
	}

	public Calendar getScheduledArrival() {
		return scheduledArrival;
	}

	public String getScheduledArrivalFormatted() {
		return sdf.format(scheduledArrival.getTime());
	}

	public void setScheduledArrival(Calendar scheduledArrival) {
		this.scheduledArrival = scheduledArrival;
	}

	public Calendar getDeparture() {
		return departure;
	}

	public String getDepartureFormatted() {
		return sdf.format(departure.getTime());
	}

	public void setDeparture(Calendar departure) {
		this.departure = departure;
	}

	public Calendar getArrival() {
		return arrival;
	}

	public String getArrivalFormatted() {
		return sdf.format(arrival.getTime());
	}

	public void setArrival(Calendar arrival) {
		this.arrival = arrival;
	}

}
