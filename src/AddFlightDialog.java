import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

public class AddFlightDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtFlightNumber;
	private JTextField txtAirline;
	private JTextField txtAircraftModel;
	private JFormattedTextField txtDeparture;
	private JFormattedTextField txtArrival;
	private JComboBox<ComboItem> comboBox;
	private JComboBox<ComboItem> comboBox_1;
	private ArrayList<Capital> capitals;
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public String answer = "close";

	public boolean editMode = false;
	public Flight flight = new Flight();
	JButton okButton = new JButton("Add");

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

	public void setFlight(Flight f) {
		this.flight = f;

		comboBox.getModel().setSelectedItem(new ComboItem(flight.getFrom().getName(), flight.getFrom().getId()));
		comboBox_1.getModel().setSelectedItem(new ComboItem(flight.getTo().getName(), flight.getTo().getId()));
		txtFlightNumber.setText(flight.getFlightNumber());
		txtAirline.setText(flight.getAirline());
		txtAircraftModel.setText(flight.getAircraftModel());
		txtDeparture.setText(flight.getDepartureFormatted());
		txtArrival.setText(flight.getArrivalFormatted());
		okButton.setText(editMode ? "Update" : "Add");
	}

	public void setComboBoxData(ArrayList<Capital> capitals) {
		this.capitals = capitals;
		for (Capital capital : capitals) {
			comboBox.addItem(new ComboItem(capital.getName(), capital.getId()));
			comboBox_1.addItem(new ComboItem(capital.getName(), capital.getId()));
		}
	}

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

		txtFlightNumber = new JTextField();
		txtFlightNumber.setColumns(10);
		txtFlightNumber.setBounds(242, 109, 159, 22);
		contentPanel.add(txtFlightNumber);

		txtAirline = new JTextField();
		txtAirline.setColumns(10);
		txtAirline.setBounds(241, 157, 160, 22);
		contentPanel.add(txtAirline);

		txtAircraftModel = new JTextField();
		txtAircraftModel.setColumns(10);
		txtAircraftModel.setBounds(241, 205, 160, 22);
		contentPanel.add(txtAircraftModel);

		txtDeparture = new JFormattedTextField(sdf);
		txtDeparture.setBounds(242, 253, 159, 22);
		
		txtDeparture.setText(sdf.format(MainScreen.systemTime.getTime()));
		contentPanel.add(txtDeparture);

		txtArrival = new JFormattedTextField(sdf);
	
		txtArrival.setBounds(240, 301, 161, 22);
		txtArrival.setText(sdf.format(MainScreen.systemTime.getTime()));
		contentPanel.add(txtArrival);

		JLabel lblNewLabel_3 = new JLabel("Flight Number");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(130, 112, 100, 16);
		contentPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_1 = new JLabel("From");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(174, 16, 56, 16);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("To");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(174, 64, 56, 16);
		contentPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("AirLine");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(174, 160, 56, 16);
		contentPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Aircraft Model");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_5.setBounds(130, 208, 99, 16);
		contentPanel.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Departure");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6.setBounds(142, 256, 88, 16);
		contentPanel.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Arrival");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_7.setBounds(172, 304, 56, 16);
		contentPanel.add(lblNewLabel_7);

		comboBox = new JComboBox();
		comboBox.setBounds(242, 13, 159, 22);
		contentPanel.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(242, 61, 159, 22);
		contentPanel.add(comboBox_1);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JLabel lblNewLabel_8 = new JLabel("Date Format: yyyy/MM/dd HH:mm:ss");
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
			buttonPane.add(lblNewLabel_8);
			{

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (txtFlightNumber.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Uçuþ numarasýný giriniz!!");
							return;
						}
						if (txtAirline.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Havayolu þirketini giriniz!!");
							return;
						}
						if (txtAircraftModel.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Uçak modelini giriniz!!");
							return;
						}
						if ((((ComboItem) comboBox.getSelectedItem()).getValue()
								- 1) == ((ComboItem) comboBox_1.getSelectedItem()).getValue() - 1) {
							JOptionPane.showMessageDialog(null, "Farklý þehirler seçiniz!!");
							return;
						}
						Calendar arrival = Calendar.getInstance(), departure = Calendar.getInstance();
						
						try {
							arrival.setTime(sdf.parse(txtArrival.getText()));
							departure.setTime(sdf.parse(txtDeparture.getText()));
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Formata uygun tarih giriniz!!");
							return;
						}
						answer = "ok";
						Capital cFrom = new Capital(), cTo = new Capital();
						for (Capital capital : capitals) {
							if (capital.getId() == ((ComboItem) comboBox.getSelectedItem()).getValue())
								cFrom = capital;
							if (capital.getId() == ((ComboItem) comboBox_1.getSelectedItem()).getValue())
								cTo = capital;

						}
						
						flight.setFrom(cFrom);
						flight.setTo(cTo);
						flight.setFlightNumber(txtFlightNumber.getText());
						flight.setAircraftModel(txtAircraftModel.getText());
						flight.setAirline(txtAirline.getText());
						flight.setScheduledDeparture(departure);
						flight.setScheduledArrival(arrival);
						flight.setDeparture(departure);
						flight.setArrival(arrival);

						if (!editMode) {

							flight.startThread();
						}
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
						answer = "cancel";
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
