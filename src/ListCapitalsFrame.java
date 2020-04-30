import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListCapitalsFrame extends JFrame {

	private JPanel contentPane;

	private ArrayList<Capital> capitals;
	private JTable table;
	private JButton btnNewButton;
	DefaultTableModel tableModel;
	private JButton btnNewButton_1;

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

	public void setTableData(ArrayList<Capital> capitals) {
		this.capitals = capitals;
		String[] col = { "id", "name", "lat", "lng" };
		tableModel = new DefaultTableModel(col, 0);
		for (Capital capital : capitals) {
			tableModel.addRow(new Object[] {capital.getId(), capital.getName(), capital.getLat(), capital.getLng()});
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
				capitals.remove(selectedIndex);
				tableModel.removeRow(selectedIndex);	
			}
		});
		btnNewButton.setBounds(535, 13, 97, 25);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCapitalDialog addC = new AddCapitalDialog();
				addC.setVisible(true);
				Capital newCapital = new Capital(capitals.size()+1,addC.name, addC.lat, addC.lng);
				capitals.add(newCapital);	
				tableModel.addRow(new Object[] {newCapital.getId(), newCapital.getName(), newCapital.getLat(), newCapital.getLng()});
			}
		});
		btnNewButton_1.setBounds(535, 51, 97, 25);
		contentPane.add(btnNewButton_1);
	}
}
