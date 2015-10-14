package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.PeerMethodController;
import controller.PeerService;
import controller.ServerService;
import peer.PeerListenerInterface;
import protocol.ProtocolError;
import protocol.ProtocolReturn;

import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

public class JChatPanel extends JFrame implements PeerListenerInterface {

	private JPanel contentPane;
	private JTextField tb_msg;
	JTextArea tb_log;
	private PeerService peerService;
	public PeerListener listener = new PeerListener();
	
	public class PeerListener extends PeerMethodController{
		
		@Override
		public void onAcceptedConnect() {
			ProtocolError error = new ProtocolError(0, "Success");
			Object[] result = null;
			try {
				result = new Object[]{ServerService.GetResource().session.username};
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("<<Accepted>>");
			ProtocolReturn ret = new ProtocolReturn("accept", result, method.id, error);
			try {
				peerService.send(ret);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			log("<<Accepted>>");
		}
		
		@Override
		public void onReceiveMessage(String encode, String message) {
			log(message);
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JChatPanel frame = new JChatPanel();
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
	public JChatPanel() {
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
	
	public JChatPanel(PeerService service){
		this();
		peerService = service;
		peerService.addMethodDispatch(listener);
	}
	
	public void log(String text){
		tb_log.setText(tb_log.getText() + '\n' + text);
		System.out.println("logged");
	}

	@Override
	public void accept(Socket socket) {
		int ans = JOptionPane.showConfirmDialog(null, "Accept?", "Window", JOptionPane.OK_CANCEL_OPTION);
		if(ans == JOptionPane.OK_OPTION){
			System.out.println("OK");
		}
		
	}
}
