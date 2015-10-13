package controller;

import java.io.IOException;

import protocol.ProtocolMethod;
import server.ServerConnectionReader;
import server.ServerConnection;

public class ServerService {
	private ServerConnection connection;
	private ServerConnectionReader reader;
	public int method_ip_counter = 1;
	
	private int getTransactionId(){
		return method_ip_counter++;
	}
	
	/**
	 * Create a server communicate with server
	 * @param conn Server connection
	 * @throws IOException Something went wrong
	 */
	public ServerService(ServerConnection conn) throws IOException {
		connection = conn;
		reader = new ServerConnectionReader(conn);
		// Start connection
		Thread serverThread = new Thread(conn);
		serverThread.start();
		
		Thread readThread = new Thread(reader);
		readThread.start();
	}
	/**
	 * Login exist account
	 * @param username User name
	 * @param password Password 
	 * @param callback which called when server has return
	 * @throws InterruptedException
	 */
	public void login(String username, String password, CallbackInteface callback) throws InterruptedException{
		String[] param = new String[2];
		param[0] = username;
		param[1] = password;
		int transactionId = getTransactionId();
		ProtocolMethod p = new ProtocolMethod("login", param, transactionId);
		System.out.println(p.toString());
		reader.addCallback(callback, transactionId);	
		connection.write(p);
			
	}

	/**
	 * Create a new account
	 * @param username User name
	 * @param password Password
	 * @param fullname Full name
	 * @param callback which called when server has response
	 * @throws InterruptedException 
	 */
	public void signup(String username, String password, String fullname, CallbackInteface callback) throws InterruptedException{
		String[] param = new String[3];
		param[0] = username;
		param[1] = password;
		param[2] = fullname;
		int transactionId = getTransactionId();
		ProtocolMethod p = new ProtocolMethod("login", param, transactionId);
		System.out.println(p.toString());
		reader.addCallback(callback, transactionId);	
		connection.write(p);
	}
	
	/**
	 * Request get friend by keyword. By default, if keyword null it will load all friends.
	 * @param keyword Friend user name. Nullable.
	 * @param callbackwhich called when server has response
	 */
	public void getContacts(String keyword, CallbackInteface callback){
		
	}
	
	/**
	 * Get user profiles
	 * @param username user name.
	 * @param callback which called when server has response
	 * @throws InterruptedException 
	 */
	public void getProfile(String username, CallbackInteface callback) throws InterruptedException{
		String[] param = new String[1];
		param[0] = username;
		int transactionId = getTransactionId();
		ProtocolMethod p = new ProtocolMethod("getProfile", param, transactionId);
		System.out.println(p.toString());
		reader.addCallback(callback, transactionId);	
		connection.write(p);
	}
	
	/**
	 * Confirm this client still online. And request update new online list.
	 * @param callback server return new online list
	 * @throws InterruptedException 
	 */
	public void online(String current_user_name, CallbackInteface callback) throws InterruptedException{
		String[] param = new String[1];
		param[0] = current_user_name;
		int transactionId = getTransactionId();
		ProtocolMethod p = new ProtocolMethod("getProfile", param, transactionId);
		System.out.println(p.toString());
		reader.addCallback(callback, transactionId);	
		connection.write(p);
	}
	
	/**
	 * Log out
	 * @param callback
	 */
	public void logout(CallbackInteface callback){
		connection.logout();
	}
}
