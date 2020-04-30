import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ListFlightsFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private ArrayList<Flight> flights;
	private ArrayList<Capital> capitals;
	DefaultTableModel tableModel;

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListFlightsFrame frame = new ListFlightsFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setCapitals(ArrayList<Capital> capitals) {
		this.capitals = capitals;
	}

	public void setTableData(ArrayList<Flight> flights) {
		this.flights = flights;
		String[] col = { "id", "From", "To", "FlightNumber", "Airline", "AircraftModel", "ScheduledArrival",
				"ScheduledDeparture", "Arrival", "Departure" };
		tableModel = new DefaultTableModel(col, 0);
		for (Flight flight : flights) {
			tableModel.addRow(new Object[] { flight.getId(), flight.getFrom().getName(), flight.getTo().getName(),
					flight.getFlightNumber(), flight.getAirline(), flight.getAircraftModel(),
					flight.getScheduledArrivalFormatted(), flight.getScheduledDepartureFormatted(),
					flight.getArrivalFormatted(), flight.getDepartureFormatted() });
		}

		table.setModel(tableModel);
	}

	/**
	 * Create the frame.
	 */
	public ListFlightsFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1017, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 866, 378);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				flights.remove(selectedIndex);
				tableModel.removeRow(selectedIndex);

			}
		});
		btnNewButton.setBounds(890, 13, 97, 25);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddFlightDialog addFlight = new AddFlightDialog();
				addFlight.setComboBoxData(capitals);
				addFlight.setVisible(true);
				addFlight.flight.setId(flights.size() + 1);
				flights.add(addFlight.flight);
				tableModel.addRow(new Object[] { addFlight.flight.getId(), addFlight.flight.getFrom().getName(),
						addFlight.flight.getTo().getName(), addFlight.flight.getFlightNumber(),
						addFlight.flight.getAirline(), addFlight.flight.getAircraftModel(),
						addFlight.flight.getScheduledArrivalFormatted(),
						addFlight.flight.getScheduledDepartureFormatted(), addFlight.flight.getArrivalFormatted(),
						addFlight.flight.getDepartureFormatted() });

			}
		});
		btnNewButton_1.setBounds(890, 51, 97, 25);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Edit");
		btnNewButton_2.setBounds(890, 89, 97, 25);
		contentPane.add(btnNewButton_2);
	}

}
