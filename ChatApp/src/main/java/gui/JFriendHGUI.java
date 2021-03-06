package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.PeerService;
import controller.ServerService;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JFriendHGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tb_msg;
	JTextArea tb_log;
	private PeerService peerService;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFriendHGUI frame = new JFriendHGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFriendHGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, BorderLayout.SOUTH);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox.add(horizontalBox_2);
		
		tb_msg = new JTextField();
		horizontalBox_2.add(tb_msg);
		tb_msg.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					peerService.sendMessage(tb_msg.getText());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		horizontalBox_2.add(btnNewButton);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(7);
		contentPane.add(horizontalStrut_1, BorderLayout.EAST);
		
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.CENTER);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut_1);
		
		tb_log = new JTextArea();
		tb_log.setEditable(false);
		verticalBox.add(tb_log);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		verticalBox.add(verticalStrut);
		
		Component horizontalStrut = Box.createHorizontalStrut(7);
		contentPane.add(horizontalStrut, BorderLayout.WEST);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		contentPane.add(horizontalBox_1, BorderLayout.NORTH);
		
		JLabel lblChatPp = new JLabel("chat P2P");
		horizontalBox_1.add(lblChatPp);
	}
	
	public JFriendHGUI(PeerService service){
		this();
		peerService = service;
	}
	
	public void log(String text){
		tb_log.setText(tb_log.getText() + '\n' + text);
		System.out.println("logged");
	}
}
