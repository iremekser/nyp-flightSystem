import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ListCapitalsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private ArrayList<Capital> capitals;
	private JTable table;
	private JButton btnNewButton;
	DefaultTableModel tableModel;
	private JButton btnNewButton_1;

	public ArrayList<Flight> flights = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListCapitalsFrame frame = new ListCapitalsFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void setFlights(ArrayList<Flight> f) {
		this.flights = f;
	}
	public void rewrite() {
		try {
			FileWriter writer = new FileWriter("src/capitals.txt");
			for (Capital str : capitals) {
				writer.write(str.txtRow());
			}
			writer.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

	public void rewriteFlight() {
		try {
			FileWriter writer = new FileWriter("src/flights.txt");
			for (Flight str : flights) {
				writer.write(str.txtRow());
			}
			writer.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}

	public void setTableData(ArrayList<Capital> capitals) {
		this.capitals = capitals;
		String[] col = { "id", "name", "lat", "lng" };
		tableModel = new DefaultTableModel(col, 0);
		for (Capital capital : capitals) {
			tableModel.addRow(new Object[] { capital.getId(), capital.getName(), capital.getLat(), capital.getLng() });
		}

		table.setModel(tableModel);
	}

	/**
	 * Create the frame.
	 */
	public ListCapitalsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 520, 314);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex == -1)
					JOptionPane.showMessageDialog(null, "Silmek istediginiz sehri seciniz!!");
				else {
					int result = JOptionPane.showConfirmDialog((Component) null,
							"Eğer şehiri silerseniz, şehre ait tüm uçuşlar silinecektir!! Silmek istediğinize emin misiniz?",
							"alert", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						for(int i = 0;i<flights.size();) {
							if (flights.get(i).getFrom().getId() == capitals.get(selectedIndex).getId()
									|| flights.get(i).getTo().getId() == capitals.get(selectedIndex).getId()) {
								flights.remove(i);
								rewriteFlight();
							}
							else i++;
						}
						capitals.remove(selectedIndex);
						tableModel.removeRow(selectedIndex);
						rewrite();
					}
				}
			}
		});
		btnNewButton.setBounds(535, 13, 97, 25);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddCapitalDialog addCapital = new AddCapitalDialog();
					addCapital.setVisible(true);
					if (addCapital.answer == "ok") {
						addCapital.capital.setId(capitals.size() + 1);
						addCapital.capital.appendText();
						capitals.add(addCapital.capital);
						tableModel.addRow(new Object[] { addCapital.capital.getId(), addCapital.capital.getName(),
								addCapital.capital.getLat(), addCapital.capital.getLng() });
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(535, 51, 97, 25);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Edit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				if (selectedIndex == -1)
					JOptionPane.showMessageDialog(null, "Düzenlemek istediğiniz şehiri seçiniz!!");
				else {
					AddCapitalDialog addCapital = new AddCapitalDialog();
					addCapital.editMode = true;
					addCapital.setCapital((Capital) capitals.get(selectedIndex));

					addCapital.setVisible(true);
					capitals.set(selectedIndex, addCapital.capital);
					setTableData(capitals);
					rewrite();
				}
			}
		});
		btnNewButton_2.setBounds(535, 89, 97, 25);
		contentPane.add(btnNewButton_2);
	}
}
