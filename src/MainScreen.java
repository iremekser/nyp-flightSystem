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
	Font myFont = new Font("Yu Gothic UI ", Font.PLAIN, 20);
	private JFrame frame;
	JLabel lblSystemTime = new JLabel("2019/11/22 17:30:00");

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

		systemTime = Calendar.getInstance();

		initialize();

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

			while ((line = br.readLine()) != null) {
				Calendar departure = Calendar.getInstance(), arrival = Calendar.getInstance();
				String[] args = line.split(",");
				departure.setTime(sdf.parse(args[6]));
				arrival.setTime(sdf.parse(args[7]));
				Capital cFrom = new Capital(), cTo = new Capital();
				for (Capital capital : capitals) {
					if (capital.getId() == Integer.parseInt(args[1]))
						cFrom = capital;
					if (capital.getId() == Integer.parseInt(args[2]))
						cTo = capital;

				}
				flights.add(new Flight(Integer.parseInt(args[0]), cFrom, cTo, args[3], args[4], args[5], departure,
						arrival));
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
				listCapitals.setFlights(flights);
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

		JButton btnNewButton_1 = new JButton("pause");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Flight flight : flights) {
					flight.pauseThread();
				}
				timer.suspend();
			}
		});
		btnNewButton_1.setBounds(12, 212, 97, 25);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("resume");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Flight flight : flights) {
					flight.resumeThread();
				}
				timer.resume();
			}
		});
		btnNewButton_2.setBounds(12, 250, 97, 25);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("stop");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Flight flight : flights) {
					flight.stopThread();
				}
				timer.stop();
			}
		});
		btnNewButton_3.setBounds(12, 288, 97, 25);
		frame.getContentPane().add(btnNewButton_3);
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
