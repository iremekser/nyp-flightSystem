import java.awt.EventQueue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainScreen {

	private JFrame frame;
	JLabel lblSystemTime = new JLabel("");

	private Calendar systemTime;
	public ArrayList<Capital> capitals = new ArrayList<>();
	public ArrayList<Flight> flights = new ArrayList<>();

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws ParseException
	 */
	public MainScreen() throws ParseException {
		initialize();
		systemTime = Calendar.getInstance();

		Thread timer = new Thread() {
			public void run() {
				while (true) {
					try {
						sleep(1000);
						systemTime.add(Calendar.MINUTE, 1);
						lblSystemTime.setText(sdf.format(systemTime.getTime()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
		timer.start();
		capitals.add(new Capital(1, "Ankara", 34.5f, 54.3f));
		capitals.add(new Capital(2, "New York", 51.2f, 845.2f));
		capitals.add(new Capital(3, "Sydney", 8451.2f, 185.1f));
		Calendar arrival = Calendar.getInstance(), departure = Calendar.getInstance();
		arrival.setTime(sdf.parse("2020/12/11 20:33:12"));
		departure.setTime(sdf.parse("2020/12/12 00:33:12"));
		flights.add(new Flight(1, capitals.get(0), capitals.get(1), "223WV", "turkis", "ankakusu", arrival, departure));
		flights.add(new Flight(1, capitals.get(1), capitals.get(2), "12", "vd", "df", arrival, departure));
		flights.add(new Flight(1, capitals.get(2), capitals.get(1), "223213WV", "df", "db", arrival, departure));
		flights.add(new Flight(1, capitals.get(0), capitals.get(1), "2334", "sfg", "dgbf", arrival, departure));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 443, 284);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblSystemTime.setBounds(12, 174, 156, 25);
		frame.getContentPane().add(lblSystemTime);

		JButton btnListCapital = new JButton("List Capitals");
		btnListCapital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListCapitalsFrame listCapitals = new ListCapitalsFrame();
				listCapitals.setTableData(capitals);
				listCapitals.setVisible(true);

			}
		});
		btnListCapital.setBounds(12, 36, 97, 25);
		frame.getContentPane().add(btnListCapital);
		
		JButton btnNewButton = new JButton("List Flights");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListFlightsFrame listFlights = new ListFlightsFrame();
				listFlights.setTableData(flights);
				listFlights.setCapitals(capitals);
				listFlights.setVisible(true);
			}
		});
		btnNewButton.setBounds(12, 74, 97, 25);
		frame.getContentPane().add(btnNewButton);
	}
}
