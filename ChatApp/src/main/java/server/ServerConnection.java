package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import gui.ConsumerInterface;
import peer.ProtocolConsummer;
import protocol.ProtocolInterface;
import protocol.ProtocolMethod;

public class ServerConnection implements Runnable, ProtocolConsummer {

	String host = "localhost";
	int port = 6000;
	ServerConnectionReader ireader;
	BlockingQueue<ProtocolInterface> method_queue;
	Socket socket;
	public ServerConnection() throws UnknownHostException, IOException {
		method_queue = new LinkedBlockingQueue<ProtocolInterface>();
		socket = new Socket(host, port);
	}
	
	public void write(ProtocolInterface p) throws InterruptedException{
		method_queue.put(p);
	}
	public InputStream getInputStream() throws IOException{
		return socket.getInputStream();
	}
	@Override
	public void run() {
		try {
			
			OutputStream os = socket.getOutputStream();
			while(true){
				try {
					ProtocolInterface method = method_queue.take();
					byte[] data = method.toString().getBytes();
					os.write(data);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		} catch (UnknownHostException e) {
			// Can not connecto to server
			System.out.println("Can not connect with server");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		ServerConnection sClient = new ServerConnection();
		ServerConnectionReader reader = new ServerConnectionReader(sClient);
		reader.addConsummer(sClient);
		String[] param = new String[]{"buibaphuc95@gmail.com","23"};
		ProtocolMethod p = new ProtocolMethod("login", param, 100);
		System.out.println(p.toString());
		
		Thread t2 = new Thread(reader);
		Thread t = new Thread(sClient);
		t.start();
		t2.start();
		//sClient.getReader().addConsummer(sClient);
		sClient.write(p);
		t.join();
	}

	@Override
	public void consume(ProtocolInterface p) {
		System.out.println(p.toString());
		
	}

	public void logout() {
		// TODO Logout
		
	}

}
