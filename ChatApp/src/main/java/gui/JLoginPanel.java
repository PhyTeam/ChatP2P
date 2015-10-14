package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import controller.CallbackInteface;
import controller.ServerService;
import peer.PeerServerSocket;
import protocol.ProtocolInterface;
import ui.JListFriendPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class JLoginPanel extends JPanel {
	private JTextField tb_username;
	private JTextField tb_password;
	MainFrameInterface parent;
	/**
	 * Create the panel.
	 */
	public JLoginPanel(MainFrameInterface parent) {
		setLayout(null);
		this.parent = parent;
		JButton button = new JButton("Login");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerService service = null;
				try {
					service = ServerService.GetResource();
					String username = tb_username.getText();
					String password = tb_password.getText();
					service.login(username, password, PeerServerSocket.getPeerServerSocket().getPort(), new CallbackInteface() {
						
						@Override
						public void onTimeout() {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onSuccess(Object[] result) {
							try {
								ServerService.GetResource().session.setSession(username);
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							JPanel ret = parent.showPanel("listFriendPanel");
							//((Object) panel).loadListFriend();
							System.out.println(ret);
							try {
								((gui.JListFriendPanel)ret).loadListFriend();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
						@Override
						public void onResponse(ProtocolInterface protocolInterface) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onFail(int errorCode, String message) {
							JOptionPane.showMessageDialog(null, "Error : " + message);
							
						}
					});
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 19));
		button.setBounds(105, 207, 335, 61);
		add(button);
		
		tb_username = new JTextField();
		tb_username.setBounds(98, 70, 314, 42);
		tb_username.setColumns(10);
		add(tb_username);
		
		tb_password = new JTextField();
		tb_password.setBounds(98, 123, 314, 42);
		tb_password.setColumns(10);
		add(tb_password);
		
		JLabel label = new JLabel("User name : ");
		label.setBounds(10, 137, 61, 14);
		add(label);
		
		JLabel label_1 = new JLabel("Password :");
		label_1.setBounds(10, 84, 53, 14);
		add(label_1);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.showPanel("signupPanel");
			}
		});
		btnBack.setBounds(10, 21, 89, 23);
		add(btnBack);

	}

}
