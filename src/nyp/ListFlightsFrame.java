package nyp;
import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class ListFlightsFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public void rewrite() {
		try {
			FileWriter writer = new FileWriter("src/flights.txt");
			for (Flight str : flights) {
				writer.write(str.txtRow());
			}
			writer.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
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
		setModal(true);

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
				if (selectedIndex == -1)
					JOptionPane.showMessageDialog(null, "Silmek istediginiz ucusu seciniz!!");
				else {
					flights.remove(selectedIndex);
					tableModel.removeRow(selectedIndex);
					rewrite();
				}
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
				if (addFlight.answer == "ok") {
					addFlight.flight.setId(flights.size() + 1);
					try {
						addFlight.flight.appendText();
					} catch (IOException e) {
						e.printStackTrace();
					}
					flights.add(addFlight.flight);
					tableModel.addRow(new Object[] { addFlight.flight.getId(), addFlight.flight.getFrom().getName(),
							addFlight.flight.getTo().getName(), addFlight.flight.getFlightNumber(),
							addFlight.flight.getAirline(), addFlight.flight.getAircraftModel(),
							addFlight.flight.getScheduledArrivalFormatted(),
							addFlight.flight.getScheduledDepartureFormatted(), addFlight.flight.getArrivalFormatted(),
							addFlight.flight.getDepartureFormatted() });
				}
			}
		});
		btnNewButton_1.setBounds(890, 51, 97, 25);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Edit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex == -1)
					JOptionPane.showMessageDialog(null, "Duzenlemek istediginiz ucusu seciniz!!");
				else {
					AddFlightDialog addFlight = new AddFlightDialog();
					addFlight.setComboBoxData(capitals);
					addFlight.editMode = true;
					addFlight.setFlight((Flight) flights.get(selectedIndex));
					addFlight.setVisible(true);
					flights.set(selectedIndex, addFlight.flight);
					setTableData(flights);
					rewrite();
				}
			}
		});
		btnNewButton_2.setBounds(890, 89, 97, 25);
		contentPane.add(btnNewButton_2);
	}

}
