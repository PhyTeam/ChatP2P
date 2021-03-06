package peer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import utils.ChatInputStream;
import utils.ChatOutputStream;

public class ChatClient implements Runnable {

	String[] args = null;
	private String hostname;
	private int port = 5000;
	private OutputStream os;
	private InputStream inputStream;
	
	public void setInputStream(InputStream in){
		inputStream = in;
	}
	public ChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}
	public ChatClient(OutputStream os, String h, int p) {
		this(h, p);
		this.os = os;
	}
	@Override
	public void run() {
	    try {
	    	System.out.println("Connecting to " + hostname);      
	    	Socket theSocket = new Socket(hostname, port);
	    	
	    	InputStream timeStream = theSocket.getInputStream();
	    	PrintStream printStream = new PrintStream(theSocket.getOutputStream());
	    	StringBuffer time = new StringBuffer();
	    	System.out.println("Connected.");
	    	
	    	ChatInputStream in = new ChatInputStream(theSocket.getInputStream(), os);
	    	ChatOutputStream out  = new ChatOutputStream(theSocket.getOutputStream(), inputStream);
	    	
	    	Thread t1 = new Thread(in);
	    	Thread t2 = new Thread(out);
	    	t1.start();
	    	t2.start();
	    	
	    	try {
				t1.join();t2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	//theSocket.close();
	    }  // end try    
	    catch (UnknownHostException ex) 
	    {      
	    	System.err.println(ex);    
	    }    
	    catch (IOException ex) 
	    {      
	    	System.err.println(ex);    
	    }
	}

	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient("localhost", 5000);
		Thread thread = new Thread(chatClient);
		thread.start();

	}

}
