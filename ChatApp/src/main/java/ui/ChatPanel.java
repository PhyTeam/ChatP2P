package ui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
//import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatPanel extends ThePanel {
	private JTextField textField;
	public JTabbedPane tab1 = null;
	
	/**
	 * Create the panel.
	 */
	public void CreateTab(String UserName)
	{
		Boolean check = true;
		if(tab1 != null)
		{
			for(int i = 0; i < tab1.getTabCount();i++)
			{
				if(UserName == tab1.getTitleAt(i))
				check = check && false;
			}
		}
		if(check)
			if(tab1 == null)
			{
				tab1 = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
				tab1.setBounds(10, 11, 480, 450);
				tab1.setForeground(null);
				tab1.setBorder(null);
				add(tab1);
				final JPanel panel = new JPanel();
				panel.setBackground(SystemColor.activeCaptionBorder);
				tab1.addTab(null, null, panel, null);
				tab1.setEnabledAt(0, true);
				panel.setLayout(null);
				final JTextPane message_area = new JTextPane();
				message_area.setBounds(0, 23, 485, 337);
				message_area.setEditable(false);
				message_area.setFont(new Font("Tahoma", Font.PLAIN, 11));
				message_area.setForeground(Color.BLACK);
				message_area.setBackground(new Color(204, 255, 255));
				panel.add(message_area);
				
				textField = new JTextField();
				textField.setBounds(71, 371, 315, 40);
				textField.setBackground(new Color(204, 255, 255));
				textField.setBorder(null);
				panel.add(textField);
				textField.setColumns(10);
				
				JButton btnSend = new JButton("SEND");
				btnSend.setBounds(396, 371, 69, 40);
				btnSend.setBorder(null);
				btnSend.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						message_area.setText(message_area.getText() + "\n" + "You say: " + textField.getText());
					}
				});
				panel.add(btnSend);
				
				JButton AttachBtn = new JButton("@");
				AttachBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						/** 
						 * Go to LoadFile Panel
						 */
				}});
				AttachBtn.setBounds(10, 371, 51, 40);
				AttachBtn.setBorder(null);
				panel.add(AttachBtn);
				
				JButton btnExit = new JButton("Exit");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						 tab1.remove(tab1.getTabRunCount() - 1);
					}
				});
				btnExit.setBounds(425, 0, 60, 23);
				btnExit.setBorder(null);
				panel.add(btnExit);
			}
			else
			{
				final JPanel panel = new JPanel();
				panel.setBackground(SystemColor.activeCaptionBorder);
				tab1.addTab(null, null, panel, null);
				tab1.setEnabledAt(0, true);
				panel.setLayout(null);
				final JTextPane message_area = new JTextPane();
				message_area.setBounds(0, 23, 485, 337);
				message_area.setEditable(false);
				message_area.setFont(new Font("Tahoma", Font.PLAIN, 11));
				message_area.setForeground(Color.BLACK);
				message_area.setBackground(new Color(204, 255, 255));
				panel.add(message_area);
				
				textField = new JTextField();
				textField.setBounds(71, 371, 315, 40);
				textField.setBackground(new Color(204, 255, 255));
				textField.setBorder(null);
				panel.add(textField);
				textField.setColumns(10);
				
				JButton btnSend = new JButton("SEND");
				btnSend.setBounds(396, 371, 69, 40);
				btnSend.setBorder(null);
				btnSend.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						message_area.setText(message_area.getText() + "\n" + "You say: " + textField.getText());
					}
				});
				panel.add(btnSend);
				
				JButton AttachBtn = new JButton("@");
				AttachBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						/** 
						 * Go to LoadFile Panel
						 */
				}});
				AttachBtn.setBounds(10, 371, 51, 40);
				AttachBtn.setBorder(null);
				panel.add(AttachBtn);
				
				JButton btnExit = new JButton("Exit");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						 tab1.remove(tab1.getTabRunCount() - 1);
					}
				});
				btnExit.setBounds(425, 0, 60, 23);
				btnExit.setBorder(null);
				panel.add(btnExit);
			}

	}
	public ChatPanel(String name, String path) {
		super(name,path);
		setLayout(null);
		this.setBorder(null);
	}
}
