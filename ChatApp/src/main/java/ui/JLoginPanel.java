package ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class JLoginPanel extends ThePanel {
	private JTextField txtTxt;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public JLoginPanel(String name, String path) {
		super(name, path);
		setLayout(null);
		
		txtTxt = new JTextField();
		txtTxt.setBounds(124, 48, 316, 34);
		txtTxt.setText("Txt");
		add(txtTxt);
		txtTxt.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(88, 203, 248, 52);
		add(btnLogin);
		
		textField = new JTextField();
		textField.setBounds(124, 94, 316, 34);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setBounds(10, 58, 46, 14);
		add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 104, 46, 14);
		add(lblPassword);
	}
}
