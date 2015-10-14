package aurelienribon.slidinglayout.demo;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class MyPanel extends ThePanel {
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the panel.
	 */
	public MyPanel(String name, String path)  {
		super(name, path);
		setLayout(null);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.setBounds(156, 152, 89, 23);
		add(btnSignUp);
		
		textField = new JTextField();
		textField.setBounds(202, 42, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(202, 88, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(91, 45, 46, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(91, 91, 46, 14);
		add(lblNewLabel_1);

	}
}
