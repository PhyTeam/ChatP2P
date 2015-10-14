package aurelienribon.slidinglayout.demo;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.*;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import controller.CallbackInteface;
import controller.PeerMethodController;
import controller.PeerService;
import controller.ServerService;
import controller.SessionListener;
import gui.JChatPanel;
import model.Friend;
import peer.PeerConnection;
import peer.PeerListenerInterface;
import protocol.ProtocolError;
import protocol.ProtocolInterface;
import protocol.ProtocolReturn;

import javax.swing.event.ListSelectionEvent;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.beans.PropertyChangeEvent;


public class ListFriendPanel extends ThePanel implements SessionListener, PeerListenerInterface{
	private JTextField txtSearch;
	private JList list_fri = new JList();
	private java.util.List<String> list = new java.util.ArrayList<String>();
	private java.util.List<String> liststt = new java.util.ArrayList<String>();
	private java.util.List<String> list_view = new java.util.ArrayList<String>();
	private java.util.List<String> list_viewstt = new java.util.ArrayList<String>();
	ChatPanel chatPanel;
	
	public Map<String, model.Friend> listFriend;
	/**
	 * Create the panel.
	 */
	
	public ListFriendPanel(String name, String path, ChatPanel chatpanel) {
		//this.chatpanel = chatpanel;
		
		super(name, path);
		
		listFriend = new HashMap<String, model.Friend>();
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		setLayout(null);
		txtSearch = new JTextField();
		txtSearch.setBounds(10, 11, 139, 29);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 *  Tìm kiếm keyword trong danh sách bạn bè
			 */
			public void mouseClicked(MouseEvent arg0) {
				String keyword = txtSearch.getText();
				search(keyword);
				list_fri.setListData(list_view.toArray());
			}
		});
		btnSearch.setBounds(159, 11, 81, 29);
		add(btnSearch);
		list_fri.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fri.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
		});
		list_fri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int n = list_fri.getSelectedIndex();
				txtSearch.setText(list_view.get(n));
			}
		});
		copy2view();
		// Cap nhat danh sach ban
		ScrollPane scrollPane = new ScrollPane();
		list_fri.setListData(list_view.toArray());
		list_fri.setSelectedIndex(0);
		
		scrollPane.setBounds(10, 76, 230, 400);
		scrollPane.add(list_fri);
		add(scrollPane);
		
		JLabel lblTrcTuyn = new JLabel("List Friend");
		lblTrcTuyn.setForeground(new Color(144, 238, 144));
		lblTrcTuyn.setBounds(10, 51, 123, 14);
		add(lblTrcTuyn);
		
		JButton btnChat = new JButton("Chat");
		btnChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (list_viewstt.get(list_fri.getSelectedIndex()).equals("online"))	// neu user online thi tao tab chat	
				{
					String selectedUsername = list_view.get(list_fri.getSelectedIndex());
					// Create peer connect
					System.out.println(listFriend.toString());
					Friend friend = listFriend.get(selectedUsername);
					System.out.println(friend);
					String host  = friend.ip;
					int port = friend.port;
					try {
						PeerConnection connection = new PeerConnection(host, port);
						PeerService service = new PeerService(connection);
						System.out.println("Create connect to " + host + ":" + port);
						ChatEntityPanel chatEntityPanel = chatpanel.CreateTab(list_view.get(list_fri.getSelectedIndex()));
						chatEntityPanel.setPeerService(service);
						chatEntityPanel.setUsername(friend.username);
					
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnChat.setBounds(10, 500, 230, 29);
		add(btnChat);
	}
	public void addFriend2List(String name,String stt){
		for (int i = 0; i < list.size(); )
		{
			if (list.get(i).toString().equals(name)) return;
			else i++;
		}
		list.add(name);
		liststt.add(stt);
		copy2view();
		list_fri.setListData(list_view.toArray());
	}
	public void removeFriendFromList(String name){
		for (int i = 0; i < list.size(); )
		{
			if (list.get(i).toString().equals(name)) 
				{
					list.remove(i);
					liststt.remove(i);
				}
			else i++;
		}
		copy2view();
		list_fri.setListData(list_view.toArray());
	}
	@SuppressWarnings("unchecked")
	public void setFriendList(java.util.List<String> list,java.util.List<String> list1){
		this.list = list;
		this.liststt = list1;
		copy2view();
		//list_fri.setListData(this.list_view.toArray());
		Object[] data = new Object[listFriend.size()];
		int i = 0;
		for (Friend friend : listFriend.values()) {
			data[i++] = friend.username + " - " + friend.status;
		}
		list_fri.setListData(data);
	}
	private void copy2view(){
		list_view.clear();
		list_viewstt.clear();
		for (int i = 0; i < list.size(); i++)
		{
			list_view.add(list.get(i));
			list_viewstt.add(liststt.get(i));
		}
	}
	/**
	 * Search keyword in List Friend
	 * @param keyword
	 * Result: list_view contain users found
	 */
	private void search(String keyword){
		if (keyword.equals(".online")){	// search friend online
			list_view.clear();
			list_viewstt.clear();
			for (int i = 0; i < list.size(); i++)
				if (liststt.get(i).equals("online")){
					list_view.add(list.get(i));
					list_viewstt.add(liststt.get(i));
				}
		}
		else if ( keyword.isEmpty()) copy2view();
		else {
			list_view.clear();
			list_viewstt.clear();
			for (int i = 0; i < list.size(); i++)
				if (list.get(i).contains(keyword)){
					list_view.add(list.get(i));
					list_viewstt.add(liststt.get(i));
				}
		}
	}
	
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
						resolveListFriend(result);
						
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
					resolveListFriend(result);
					timer.schedule(new OnlineCheckerTimerTask(), delayTime);
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
		try {
			loadListFriend();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.schedule(timerTask, delayTime);
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
					
				}
				
				@Override
				public void onFail(int errorCode, String message) {
					
				}
			});
			
			
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void resolveListFriend(Object[] result){
		java.util.List<String> usernames = new LinkedList<String>();
		java.util.List<String> stt = new LinkedList<String>();
		for (Object object : result) {
			//System.out.println(object);
			JSONObject user = (JSONObject)object;
			String username  = (String) user.get("username");
			String ip = (String)user.get("Ip");
			int port = (int)user.get("Port");
			String status = ((boolean) user.get("Status"))? "offline" : "online";
			listFriend.put(username, new Friend(username, ip, port, status));
			usernames.add(username);
			stt.add(status);
			
		}
		setFriendList(usernames, stt);
	}

}
