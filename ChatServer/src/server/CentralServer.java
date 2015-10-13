package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

import protocol.ProtocolMethod;
import protocol.ProtocolMethodExcutor;
import protocol.ProtocolParser;
import protocol.ProtocolReturn;
import utils.ChatInputStream;
import utils.ChatOutputStream;


public class CentralServer implements Runnable{
	private int port = 6000;
	
	private InputStream in;
	private OutputStream os;
	
	private boolean IsPaused = false;
	public CentralServer() {
		os = System.out;
		in = System.in;
	}
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			while(!IsPaused)
			{
				Socket socket = server.accept();
				System.out.println("Connected " + socket.getLocalAddress() + ": " + socket.getPort());
				Thread thread = new ServerThread(socket);
				thread.start();	
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Thread t = new Thread(new CentralServer());
		t.start();
	}
}
