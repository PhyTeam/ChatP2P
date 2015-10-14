package peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import protocol.ProtocolInterface;

public class PeerConnection implements Runnable{

	private String host;
	private int port;
	
	public int getPort(){
		return this.port;
	}
	public String getIp(){
		return this.host;
	}
	private Socket socket;
	
	BlockingQueue<ProtocolInterface> queue;
	/**
	 * Init peer connection. This a account will be a client. And <code>ip</code> is a server
	 * @param ip other peer ip
	 * @param port other peer port 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public PeerConnection(String host, int port) throws UnknownHostException, IOException{
		this.host = host;
		this.port = port;
		// Create a socket connect to other peer
		socket = new Socket(this.host, this.port);
		// Init queue
		queue = new LinkedBlockingQueue<ProtocolInterface>();
	}
	
	public PeerConnection(Socket socket){
		this.socket = socket;
		this.host = socket.getRemoteSocketAddress().toString();
		this.port = socket.getPort();
		// Init queue
		queue = new LinkedBlockingQueue<ProtocolInterface>();
	}
	
	/**
	 * Just for testing
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public PeerConnection() throws UnknownHostException, IOException {
		// defaut ip and port
		this("localhost", 5000);
		host = "localhost";
		port = 5000;
		
	}
	
	public InputStream getInputConnection() throws IOException{
		return socket.getInputStream();
	}
	/**
	 * Send a protocol to other peer
	 * @param protocolInterface Method protocol or Return protocol
	 * @throws InterruptedException
	 */
	public void write(ProtocolInterface protocolInterface) throws InterruptedException{
		queue.put(protocolInterface);
	}
	
	@Override
	public void run() {
		try {
			OutputStream os = socket.getOutputStream();
			// Loop check for request sending
			while(true){
				try {
					ProtocolInterface protocol = queue.take();
					byte[] data = protocol.toString().getBytes();
					os.write(data);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
