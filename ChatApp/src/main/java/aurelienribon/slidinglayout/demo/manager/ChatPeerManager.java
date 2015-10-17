package aurelienribon.slidinglayout.demo.manager;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

import aurelienribon.slidinglayout.demo.ChatEntityPanel;
import controller.PeerService;
import peer.PeerConnection;

public class ChatPeerManager {
	protected Map<String, ChatEntityPanel> chatTabs = new HashMap<String, ChatEntityPanel>();
	
	public void addNewChatTab(String username, String ip, int port){
		// Check exist
		if (chatTabs.containsKey(username))
			return;
		// Init network
		try {
			PeerService service = createPeerService(ip, port);
			//ChatEntityPanel entity = new ChatEntityPanel();
			//entity.OpenChatUI(service);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PeerService createPeerService(String ip, int port) throws UnknownHostException, IOException{
		PeerConnection connection = new PeerConnection(ip, port);
		PeerService service = new PeerService(connection);
		
		return service;
	}
}
