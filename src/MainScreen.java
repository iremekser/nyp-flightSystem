import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import java.awt.SystemColor;

public class MainScreen {
	Font myFont = new Font("Yu Gothic UI ", Font.BOLD, 16);
	private JFrame frame;
	JLabel lblSystemTime = new JLabel("");

	public static Calendar systemTime;
	public ArrayList<Capital> capitals = new ArrayList<>();
	public ArrayList<Flight> flights = new ArrayList<>();
	DefaultListModel<String> listModel = new DefaultListModel<String>();

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	JList<String> list = new JList<String>();

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
	 * @throws IOException
	 */
	public MainScreen() throws ParseException, IOException {
		initialize();
		systemTime = Calendar.getInstance();

		Thread timer = new Thread() {
			public void run() {
				while (true) {
					try {
						systemTime.add(Calendar.MINUTE, 1);
						lblSystemTime.setText(sdf.format(systemTime.getTime()));
						setListData();
						sleep(1000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};
		timer.start();

		try (BufferedReader br = new BufferedReader(new FileReader("src/capitals.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] args = line.split(",");
				capitals.add(new Capital(Integer.parseInt(args[0]), args[1], Float.parseFloat(args[2]),
						Float.parseFloat(args[3])));
			}
		}

		try (BufferedReader br = new BufferedReader(new FileReader("src/flights.txt"))) {
			String line;
			Calendar departure = Calendar.getInstance(), arrival = Calendar.getInstance();
			while ((line = br.readLine()) != null) {
				String[] args = line.split(",");
				departure.setTime(sdf.parse(args[6]));
				arrival.setTime(sdf.parse(args[7]));
				flights.add(new Flight(Integer.parseInt(args[0]), capitals.get(Integer.parseInt(args[1])),
						capitals.get(Integer.parseInt(args[2])), args[3], args[4], args[5], departure, arrival));
			}
		}
		setListData();

	}

	public void setListData() {
		listModel.clear();
		for (Flight flight : flights) {
			listModel.addElement(flight.getInfo());
		}

		list.setModel(listModel);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 979, 448);
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
		btnListCapital.setBounds(12, 13, 120, 25);
		frame.getContentPane().add(btnListCapital);

		JButton btnNewButton = new JButton("List Flights");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListFlightsFrame listFlights = new ListFlightsFrame();
				listFlights.setTableData(flights);
				listFlights.setCapitals(capitals);
				listFlights.setVisible(true);
				setListData();
			}
		});
		btnNewButton.setBounds(12, 51, 120, 25);
		frame.getContentPane().add(btnNewButton);

		list.setBounds(144, 16, 781, 354);
		list.setFont(myFont);
		list.setBorder(new LineBorder(SystemColor.windowBorder, 3));
		list.setCellRenderer(getRenderer());
		frame.getContentPane().add(list);
	}

	private DefaultListCellRenderer getRenderer() {
		return new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

				return listCellRendererComponent;
			}
		};
	}

}
