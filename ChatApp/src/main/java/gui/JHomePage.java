package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.CallbackInteface;
import controller.PeerMethodController;
import controller.PeerService;
import peer.MethodReceiverInterface;
import peer.PeerConnection;
import peer.PeerListenerInterface;
import peer.PeerServerSocket;
import protocol.ProtocolInterface;
import protocol.ProtocolMethod;
import protocol.ProtocolMethodExcutor;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JHomePage extends JFrame implements PeerListenerInterface {

	private JPanel contentPane;
	private JTextField tb_Ip;
	private JTextField tn_port;

	private PeerServerSocket peerServerSocket;
	private PeerConnection connection;
	private PeerService service;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JHomePage frame = new JHomePage();
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
	public JHomePage() {
		
		// Create server socket
		try {
			peerServerSocket = PeerServerSocket.getPeerServerSocket();
			peerServerSocket.addListener(this);
			peerServerSocket.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tb_Ip = new JTextField();
		tb_Ip.setBounds(178, 24, 196, 20);
		contentPane.add(tb_Ip);
		tb_Ip.setColumns(10);
		
		tn_port = new JTextField();
		tn_port.setBounds(178, 58, 197, 20);
		contentPane.add(tn_port);
		tn_port.setColumns(10);
		
		JLabel lblIp = new JLabel("Ip");
		lblIp.setBounds(62, 27, 46, 14);
		contentPane.add(lblIp);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(62, 61, 46, 14);
		contentPane.add(lblPort);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					PeerConnection connection = new PeerConnection(tb_Ip.getText(), Integer.parseInt(tn_port.getText()));
					PeerService service = new PeerService(connection);
					service.addMethodDispatch(new ChatBoxManager(service));
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnConnect.setBounds(178, 94, 196, 23);
		contentPane.add(btnConnect);
		
		JLabel lb_status = new JLabel("New label");
		lb_status.setBounds(10, 396, 46, 14);
		contentPane.add(lb_status);
	}

	@Override
	public void accept(Socket socket) {
		
		try {
			connection = new PeerConnection(socket);
			service = new PeerService(connection);
			
			int confirm = JOptionPane.showConfirmDialog(null, "Accept connect!",
					"Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(confirm == JOptionPane.YES_OPTION){
				service.accept(new CallbackInteface() {
					
					@Override
					public void onTimeout() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Object[] result) {
						System.out.println("OK");
						
					}
					
					@Override
					public void onResponse(ProtocolInterface protocolInterface) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onFail(int errorCode, String message) {
						// TODO Auto-generated method stub
						
					}
				});
				ChatBoxManager controller = new ChatBoxManager(service);
				service.addMethodDispatch(controller);
				controller.createChatWindown();
			}
			else service.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
