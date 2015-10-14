package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import controller.CallbackInteface;
import controller.PeerService;
import controller.ServerService;
import controller.SessionListener;
import peer.PeerConnection;
import peer.PeerListenerInterface;
import peer.PeerServerSocket;
import protocol.ProtocolInterface;

import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class JListFriendPanel extends JPanel implements SessionListener, PeerListenerInterface{
	private static final long serialVersionUID = 1L;
	private JTable table;
	
	private Timer timer = new Timer();
	private long delayTime = 15000;
	private TimerTask timerTask = new OnlineCheckerTimerTask();
	
	private Map<String, JFrame> chatBox;
	
	class OnlineCheckerTimerTask extends TimerTask{
		
		@Override
		public void run() {
			ServerService serverService;
			try {
				serverService = ServerService.GetResource();
				serverService.online(serverService.session.username, new CallbackInteface() {
					@Override
					public void onTimeout() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Object[] result) {
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						
						for (Object object : result) {
							//System.out.println(object);
							JSONObject user = (JSONObject)object;
							Object[] row = new Object[]{
									user.get("username"),
									user.get("Ip"),
									user.get("Port"),
									user.get("Status")
								};
								model.addRow(row);
						}
						timer.schedule(new OnlineCheckerTimerTask(), delayTime);
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
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e){
				
			}
			
			
		}
	};
	MainFrameInterface parent;
	/**
	 * Create the panel.
	 */
	public JListFriendPanel(MainFrameInterface parent) {
		// Set up map
		chatBox = new HashMap<String, JFrame>();
		// Setup layout
		setLayout(null);
		// Set up socket
		this.parent = parent;
		try {
			PeerServerSocket.getPeerServerSocket().addListener(this);
			PeerServerSocket.getPeerServerSocket().start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Online", null, null, "Test"},
			},
			new String[] {
				"Friends", "Ip", "Port", "Status"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(183);
		table.getColumnModel().getColumn(1).setPreferredWidth(101);
		table.getColumnModel().getColumn(2).setPreferredWidth(37);
		table.getColumnModel().getColumn(3).setPreferredWidth(46);
		table.setBounds(10, 51, 430, 327);
		
		add(table);
		
		ListSelectionModel model = table.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				ListSelectionModel listSelectionModel = (ListSelectionModel)e.getSource();
				int index = listSelectionModel.getMinSelectionIndex();
				String fullname = (String)table.getModel().getValueAt(index, 0);
				String host = (String)table.getModel().getValueAt(index, 1);
				int port = (int) table.getModel().getValueAt(index, 2);
				// Check has exist chat box
				if(chatBox.containsKey(fullname)) return;
				
				Thread thread = new Thread(new Runnable() {
					public void run() {
						try {
							
							
							// create peer connection
							PeerConnection peerConnection = new PeerConnection(host, port);
							System.out.println("Connecting to peer " + host);
							PeerService service = new PeerService(peerConnection);
							JChatPanel frame = new JChatPanel(service);
							chatBox.put(fullname, frame);
							
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
				thread.start();
			}
		});
		
	}
	
	public void loadListFriend() throws InterruptedException{
		try {
			ServerService service = ServerService.GetResource();
			String username = service.session.username;
			
			service.getContacts(username, new CallbackInteface() {
				
				@Override
				public void onTimeout() {
					
				}
				
				@Override
				public void onSuccess(Object[] result) {
					// Add list print
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					for (int i = 0; i < table.getRowCount(); i++) {
						((DefaultTableModel)table.getModel()).removeRow(0);
						
					}
					for (Object object : result) {
						JSONObject user = (JSONObject)object;
						System.out.println(object);
						
						Object[] row = new Object[]{
							user.get("username"),
							user.get("Ip"),
							user.get("Port"),
							user.get("Status")
						};
						model.addRow(row);
					}
				}
				
				@Override
				public void onResponse(ProtocolInterface protocolInterface) {
					
				}
				
				@Override
				public void onFail(int errorCode, String message) {
					
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onLogin(String username) {
		timer.schedule(timerTask, delayTime);
		System.out.println("Test 1: Login");
	}

	@Override
	public void onLogout(String username) {
		
		
	}

	@Override
	public void accept(Socket socket) {
		try {
			PeerConnection connection = new PeerConnection(socket);
		
			PeerService service = new PeerService(connection);
			service.accept(new CallbackInteface() {
				
				@Override
				public void onTimeout() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSuccess(Object[] result) {
					System.out.println("onSuccess Accept");
					String peerusername = (String)result[0];
					JChatPanel chatPanel = new JChatPanel(service);
					chatBox.put(peerusername, chatPanel);
					Thread thread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							chatPanel.setVisible(true);
						}
					});
					thread.start();
					System.out.println("Started");
					service.addMethodDispatch(chatPanel.listener);
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
			
			
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
