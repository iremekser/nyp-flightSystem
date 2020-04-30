import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class AddFlightDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtFlightNumber;
	private JTextField txtAirline;
	private JTextField txtAircraftModel;
	private JTextField txtDeparture;
	private JTextField txtArrival;
	private JComboBox<ComboItem> comboBox;
	private JComboBox<ComboItem> comboBox_1;
	private ArrayList<Capital> capitals;
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddFlightDialog dialog = new AddFlightDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setComboBoxData(ArrayList<Capital> capitals) {
		this.capitals = capitals;
		for (Capital capital : capitals) {
			comboBox.addItem(new ComboItem(capital.getName(),capital.getId()));
			comboBox_1.addItem(new ComboItem(capital.getName(),capital.getId()));
		}
	}

	public Flight flight = new Flight();

	/**
	 * Create the dialog.
	 */
	public AddFlightDialog() {
		setModal(true);
		setBounds(100, 100, 502, 464);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(240, 13, 113, 22);
		contentPanel.add(txtName);

		txtFlightNumber = new JTextField();
		txtFlightNumber.setColumns(10);
		txtFlightNumber.setBounds(240, 157, 113, 22);
		contentPanel.add(txtFlightNumber);

		txtAirline = new JTextField();
		txtAirline.setColumns(10);
		txtAirline.setBounds(239, 205, 113, 22);
		contentPanel.add(txtAirline);

		txtAircraftModel = new JTextField();
		txtAircraftModel.setColumns(10);
		txtAircraftModel.setBounds(239, 253, 113, 22);
		contentPanel.add(txtAircraftModel);

		txtDeparture = new JTextField();
		txtDeparture.setColumns(10);
		txtDeparture.setBounds(240, 301, 113, 22);
		contentPanel.add(txtDeparture);

		txtArrival = new JTextField();
		txtArrival.setColumns(10);
		txtArrival.setBounds(238, 349, 113, 22);
		contentPanel.add(txtArrival);

		JLabel lblNewLabel_3 = new JLabel("Flight Number");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(128, 160, 100, 16);
		contentPanel.add(lblNewLabel_3);

		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(172, 16, 56, 16);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("From");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(172, 64, 56, 16);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("To");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(172, 112, 56, 16);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("AirLine");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(172, 208, 56, 16);
		contentPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Aircraft Model");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(128, 256, 99, 16);
		contentPanel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Departure");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(140, 304, 88, 16);
		contentPanel.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Arrival");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_7.setBounds(170, 352, 56, 16);
		contentPanel.add(lblNewLabel_7);

		comboBox = new JComboBox();
		comboBox.setBounds(240, 61, 113, 22);
		contentPanel.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(240, 109, 113, 22);
		contentPanel.add(comboBox_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JLabel lblNewLabel_8 = new JLabel("Date Format: yyyy/MM/dd HH:mm:ss");
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
			buttonPane.add(lblNewLabel_8);
			{
				JButton okButton = new JButton("Add");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Calendar arrival = Calendar.getInstance(), departure = Calendar.getInstance();
						try {
							arrival.setTime(sdf.parse(txtArrival.getText()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							departure.setTime(sdf.parse(txtDeparture.getText()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						flight.setFrom(capitals.get(((ComboItem)comboBox.getSelectedItem()).getValue()-1));
						flight.setTo(capitals.get(((ComboItem)comboBox_1.getSelectedItem()).getValue()-1));
						
						flight.setFlightNumber(txtFlightNumber.getText());
						flight.setAircraftModel(txtAircraftModel.getText());
						flight.setAirline(txtAirline.getText());
						flight.setScheduledDeparture(departure);
						flight.setScheduledArrival(arrival);
						flight.setDeparture(departure);
						flight.setArrival(arrival);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}