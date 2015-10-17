package aurelienribon.slidinglayout.demo;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.TabExpander;

import caro.CaroFrame;
import controller.CallbackInteface;
import controller.PeerService;
import controller.ServerService;
import peer.PeerConnection;
import peer.PeerListenerInterface;
import peer.PeerServerSocket;
import protocol.ProtocolInterface;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class ChatPanel extends ThePanel implements PeerListenerInterface {
	public JTabbedPane tab1 = null;
	public LoadFile loadFile = null;
	public String currentUser()
	{
		/**
		 * Tra ve userName hien dang chat
		 */
		return tab1.getTitleAt((this.tab1.getSelectedIndex()));
	}
	public void receiveMess(String mess,String userName)
	{	
		/**
		 * nhan tin nhan tu user
		 */
		int i = -1;
		for(i = 0; i < tab1.getTabCount();i++)
		{
			System.out.println(tab1.getTitleAt(i));
			if(userName == tab1.getTitleAt(i))
				break;
		}
		
		JPanel p = (JPanel) tab1.getComponentAt(i);
		Component[] cl = p.getComponents();
		for(int j = 0; j < cl.length;j++)
		{
			//System.out.println(cl[j].getClass().toString() + "   " + j);
			if(cl[j] instanceof JScrollPane)
			{
				JScrollPane scrollpane = (JScrollPane) cl[j];
				Component[] cl1 = scrollpane.getComponents();
				for(int h = 0; h < cl1.length;h++)
				{
					System.out.println(cl1[h].getClass().toString() + "  h " + h);
					if(cl1[h] instanceof JViewport)
					{
						JViewport vp = (JViewport) cl1[h];
						Component[] cl2 = vp.getComponents();
						JTextPane tp = (JTextPane)cl2[0];
						String message ;
						if(userName.length() > 7)
							message = userName.substring(0,7) + " say:\n" + mess;
						else
							message = userName + " say:\n" + mess;
						tp.setText(tp.getText() + "\n" + message);
					}
				}
			}
		}
	}
	public ChatEntityPanel CreateTab(String UserName)
	{
		ChatEntityPanel ret = null;
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
				tab1 = new JTabbedPane(JTabbedPane.TOP);
				tab1.setBounds(10, 11, 480, 450);
				tab1.setForeground(null);
				tab1.setBorder(null);
				add(tab1);
				tab1.addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						if(loadFile != null){
							loadFile.currentUser = currentUser();
							ChatEntityPanel entityPanel = (ChatEntityPanel) tab1.getComponentAt(tab1.getSelectedIndex());
							loadFile.service = entityPanel.peerService;
						}
					}
				});
				tab1.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						if(loadFile != null){
							loadFile.currentUser = currentUser();
							ChatEntityPanel entityPanel = (ChatEntityPanel) tab1.getComponentAt(tab1.getSelectedIndex());
							loadFile.service = entityPanel.peerService;
						}
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
					}
				});
				final JPanel panel = new ChatEntityPanel();
				ret = (ChatEntityPanel) panel;
				panel.setBackground(SystemColor.activeCaptionBorder);
				tab1.addTab(UserName, null, panel, UserName);
				tab1.setEnabledAt(0, true);
				panel.setLayout(null);
				
				final JTextField textField = new JTextField();
				textField.setBounds(71, 371, 315, 40);
				textField.setBackground(new Color(204, 255, 255));
				textField.setBorder(null);
				panel.add(textField);
				textField.setColumns(10);
				final JTextPane message_area = new JTextPane();
				message_area.setBounds(10, 21, 455, 339);
				message_area.setEditable(false);
				message_area.setFont(new Font("Tahoma", Font.PLAIN, 11));
				message_area.setForeground(Color.BLACK);
				message_area.setBackground(new Color(204, 255, 255));
				//panel.add(message_area);
				this.setBorder(null);
				JButton btnSend = new JButton("SEND");
				btnSend.setBounds(396, 371, 69, 40);
				btnSend.setBorder(null);
				btnSend.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(textField.getText() != null)
							message_area.setText(message_area.getText() + "\n" + "You say: \n" + textField.getText());
						((ChatEntityPanel)panel).sendMessage(textField.getText());
						textField.setText(null);
					}
				});
				panel.add(btnSend);
				
				JButton AttachBtn = new JButton("@");
				AttachBtn.setBounds(10, 371, 51, 40);
				AttachBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//LoadFile((ChatEntityPanel) panel);
						caroInit((ChatEntityPanel)panel);
				}});
				AttachBtn.setBorder(null);
				panel.add(AttachBtn);
				
				JButton btnExit = new JButton("Exit");
				btnExit.setBounds(425, 0, 60, 23);
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						 tab1.remove(tab1.getSelectedIndex());
					}
				});
				btnExit.setBorder(null);
				panel.add(btnExit);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(0, 21, 475, 339);
				scrollPane.setBorder(null);
				panel.add(scrollPane);

				scrollPane.setViewportView(message_area);
				
			}
			else
			{
				final JPanel panel = new ChatEntityPanel();
				ret = (ChatEntityPanel) panel;
				panel.setBackground(SystemColor.activeCaptionBorder);
				tab1.addTab(UserName, null, panel, UserName);
				tab1.setEnabledAt(0, true);
				panel.setLayout(null);
				
				final JTextField textField = new JTextField();
				textField.setBounds(71, 371, 315, 40);
				textField.setBackground(new Color(204, 255, 255));
				textField.setBorder(null);
				panel.add(textField);
				textField.setColumns(10);
				final JTextPane message_area = new JTextPane();
				message_area.setBounds(10, 21, 455, 339);
				message_area.setEditable(false);
				message_area.setFont(new Font("Tahoma", Font.PLAIN, 11));
				message_area.setForeground(Color.BLACK);
				message_area.setBackground(new Color(204, 255, 255));
				//panel.add(message_area);
				this.setBorder(null);
				JButton btnSend = new JButton("SEND");
				btnSend.setBounds(396, 371, 69, 40);
				btnSend.setBorder(null);
				btnSend.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(textField.getText() != null)
							message_area.setText(message_area.getText() + "\n" + "You say: " + textField.getText());
						
						((ChatEntityPanel)panel).sendMessage(textField.getText());
						textField.setText(null);
					}
				});
				panel.add(btnSend);
				
				JButton AttachBtn = new JButton("@");
				AttachBtn.setBounds(10, 371, 51, 40);
				AttachBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						LoadFile((ChatEntityPanel) panel);
				}});
				AttachBtn.setBorder(null);
				panel.add(AttachBtn);
				
				JButton btnExit = new JButton("Exit");
				btnExit.setBounds(425, 0, 60, 23);
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						 tab1.remove(tab1.getSelectedIndex());
					}
				});
				btnExit.setBorder(null);
				panel.add(btnExit);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(0, 21, 475, 339);
				scrollPane.setBorder(null);
				panel.add(scrollPane);

				scrollPane.setViewportView(message_area);
			}
		ret.setParent(this);
		return ret;
	}
	/*
	 * Da xong
	 */
	public void ReceiveFile(File f,String UserName)
	{
		final JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(119, 144, 283, 91);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblUserXSent = new JLabel(UserName + "sent a file to you!");
		lblUserXSent.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserXSent.setBounds(36, 23, 213, 23);
		panel.add(lblUserXSent);
		
		final JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if(e.getSource() == saveBtn)
				{
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						File f = fc.getSelectedFile();
					}
				}
			}
		});
		saveBtn.setBounds(100, 57, 89, 23);
		saveBtn.setBorder(null);
		panel.add(saveBtn);
		
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBorder(null);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll();
				remove(panel);
			}
		});
		exitBtn.setBounds(232, 0, 51, 23);
		panel.add(exitBtn);
	}
	public ChatPanel(String name, String path) {
		super(name,path);
		setLayout(null);
		// Add peer listener
		attachPeerListener();
	}
	private void attachPeerListener() {
		try {
			PeerServerSocket socket = PeerServerSocket.getPeerServerSocket();
			socket.addListener(this);
			socket.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void accept(Socket socket) {
		// Accept a connection
		System.out.println("Get user name ...");
		PeerConnection connection = new PeerConnection(socket);
		
		try {
			PeerService service = new PeerService(connection);
			service.accept(new CallbackInteface() {
				
				@Override
				public void onTimeout() {
					
				}
				
				@Override
				public void onSuccess(Object[] result) {
					String username = (String) result[0];
					ChatEntityPanel entityPanel = CreateTab(username);
					entityPanel.setPeerService(service);
					entityPanel.setUsername(username);
				}
				
				@Override
				public void onResponse(ProtocolInterface protocolInterface) {
					
				}
				
				@Override
				public void onFail(int errorCode, String message) {
					System.out.println("Error" + message);
				}
			});
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
	}
	private void LoadFile(ChatEntityPanel panel){
		try {
			panel.peerService.sendFile("ff.pdf", 1000, null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void caroInit(ChatEntityPanel panel) {
		try {
			panel.peerService.invite(new CallbackInteface() {
				
				@Override
				public void onTimeout() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSuccess(Object[] result) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							new CaroFrame(panel.peerService);
							
						}
					}).start();
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
