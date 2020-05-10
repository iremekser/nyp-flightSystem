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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddCapitalDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtLat;
	private JTextField txtLng;

	public String answer = "close";

	public boolean editMode = false;
	public Capital capital = new Capital();
	JButton okButton = new JButton("Add");

	/**
	 * Launch the application.
	 */
	public void setCapital(Capital c) {
		this.capital = c;
		txtName.setText(capital.getName());
		txtLat.setText(capital.getLat() + "");
		txtLng.setText(capital.getLng() + "");
		okButton.setText(editMode ? "Update" : "Add");

	}

	public static void main(String[] args) {
		try {
			AddCapitalDialog dialog = new AddCapitalDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	public AddCapitalDialog() {
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 286, 244);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtName = new JTextField();
			txtName.setColumns(10);
			txtName.setBounds(108, 35, 116, 22);
			contentPanel.add(txtName);
		}
		{
			txtLat = new JTextField();
			txtLat.setColumns(10);
			txtLat.setBounds(108, 70, 116, 22);
			contentPanel.add(txtLat);
		}
		{
			txtLng = new JTextField();
			txtLng.setColumns(10);
			txtLng.setBounds(108, 105, 116, 22);
			contentPanel.add(txtLng);
		}
		{
			JLabel lblNewLabel = new JLabel("Name");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel.setBounds(40, 38, 56, 16);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Latitude");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_1.setBounds(40, 73, 56, 16);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Longitude");
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel_2.setBounds(40, 108, 56, 16);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtName.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Ýsim giriniz!!");
							return;
						}
						if (txtLat.getText().isEmpty() ) {
							JOptionPane.showMessageDialog(null, "Enlemi giriniz!!");
							return;
						}
						if (txtLng.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Boylamý giriniz!!");
							return;
						}
						answer = "ok";
						capital.setName(txtName.getText());
						capital.setLat(Float.parseFloat(txtLat.getText()));
						capital.setLng(Float.parseFloat(txtLng.getText()));
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
					public void actionPerformed(ActionEvent e) {
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
