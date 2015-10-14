package peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

public class PeerServerSocket extends Thread {

	ServerSocket serverSocket;
	List<PeerListenerInterface> listener;
	int port;
	
	public void addListener(PeerListenerInterface listenerInterface){
		listener.add(listenerInterface);
	}
	private PeerServerSocket() throws IOException {
		peerServerSocket = this;
		int i = 0;
		for(i = 0; serverSocket == null && i < 20; i++){
			try{
				port = 5000 + i;
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
		
			}
		}
		if(serverSocket == null) throw new IOException();
		
		System.out.println("Port " + serverSocket.getLocalPort());
		listener = new LinkedList<PeerListenerInterface>();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket socket = serverSocket.accept();
				for (PeerListenerInterface peerListenerInterface : listener) {
					peerListenerInterface.accept(socket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	public static PeerServerSocket peerServerSocket = null;
	
	public static PeerServerSocket getPeerServerSocket() throws IOException{
		if(peerServerSocket == null)
			peerServerSocket = new PeerServerSocket();
		return peerServerSocket;
	}
	
	public int getPort() {
		return port;
	}

}
