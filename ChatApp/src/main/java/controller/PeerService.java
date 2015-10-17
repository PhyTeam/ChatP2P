package controller;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import peer.MethodReceiverInterface;
import peer.PeerConnection;
import peer.PeerConnectionReader;
import protocol.ProtocolInterface;
import protocol.ProtocolMethod;
import protocol.ProtocolMethodExcutor;
import protocol.ProtocolReturn;
import server.ServerConnection;
import server.ServerConnectionReader;

public class PeerService {
	private PeerConnection connection;
	private PeerConnectionReader reader;
	
	int counter = 0;
	public PeerConnection getConnection(){
		return connection;
	}

	/**
	 * Create a peer communicate with peer
	 * @param conn Peer connection
	 * @throws IOException Something went wrong
	 */
	public PeerService(PeerConnection conn) throws IOException {
		connection = conn;
		reader = new PeerConnectionReader(conn);
		// Start connection
		Thread serverThread = new Thread(conn);
		serverThread.start();
		
		Thread readThread = new Thread(reader);
		readThread.start();
	}
	
	public void addMethodListener(MethodReceiverInterface receiverInterface){
		reader.addMethodReceiver(receiverInterface);
	}
	
	public void addMethodDispatch(final PeerMethodController controller){
		reader.addMethodReceiver(new MethodReceiverInterface() {
			
			@Override
			public void onReceive(ProtocolMethod method) {
				controller.receive(method);
				ProtocolMethodExcutor.excute(method, controller);
				
			}
		});
	}
	
	public void send(ProtocolInterface r) throws InterruptedException{
		connection.write(r);
	}
	
	public void accept(CallbackInteface callback) throws InterruptedException{
		ProtocolMethod method = new ProtocolMethod("accept", new Object[0], 0);
		//System.out.println("sended method: ACCEPT");
		connection.write(method);
		reader.addCallback(callback);
	}
	
	public void sendMessage(String text) throws InterruptedException{
		String[] param = new String[2];
		param[0] = "utf-8";
		param[1] = text;
		ProtocolMethod method = new ProtocolMethod("sendMessage", param, 0);
		connection.write(method);
	}
	
	public void sendFile(String filename, int size, CallbackInteface callbackInteface) throws InterruptedException{
		Object[] param = new Object[2];
		param[0] = filename;
		param[1] = size;
		ProtocolMethod method = new ProtocolMethod("sendFile", param, counter++);
		connection.write(method);
		reader.addCallback(callbackInteface);
	}

	public void sendCaro(Point point) throws InterruptedException{
		Object[] param = new Object[2];
		param[0] = point.getX();
		param[1] = point.getY();
		ProtocolMethod method = new ProtocolMethod("sendCaro", param, counter++);
		connection.write(method);
	}
	
	public void invite(CallbackInteface callbackInteface) throws InterruptedException{
		
		ProtocolMethod method = new ProtocolMethod("invite",new Object[0], counter++);
		connection.write(method);
		reader.addCallback(callbackInteface);
	}
	
	public void close() {
		// TODO Auto-generated method stub
		
	}
}
