package controller;

import java.net.InetAddress;

public class ControllerSession {
	public boolean IsLogin;
	public InetAddress addr;
	public int port;
	private static int session_counter = 0;
	public int sessionCode;
	public String username;
	
	public ControllerSession(InetAddress address, int port){
		this.port = port;
		this.addr = address;
		
		sessionCode = session_counter++;
	}
	
	public int setLogin(String username){
		IsLogin = true;
		this.username = username;
		return sessionCode;
	}
}
