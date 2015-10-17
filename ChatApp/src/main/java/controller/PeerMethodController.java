package controller;

import java.awt.Point;
import java.io.IOException;
import java.lang.reflect.Parameter;

import protocol.ProtocolError;
import protocol.ProtocolMethod;
import protocol.ProtocolReturn;

/**
 * Extends this class to receive method sent
 * @author Phuc
 *
 */
public class PeerMethodController implements PeerMethodInterface{

	protected ProtocolMethod method;
	public Object[] getParam(){
		return method.method_param;
	}
	
	public void receive(ProtocolMethod method){
		this.method = method;
	}

	public PeerMethodController(ProtocolMethod method) {
		this.method = method;
	}
	
	public PeerMethodController() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void sendMessage() {
		Object[] param = getParam();
		String encode = (String)param[0];
		String message = (String)param[1];
		onReceiveMessage(encode, message);
	}

	@Override
	public void accept() {
		System.out.println("Accepted called");
		onAcceptedConnect();		
	}
	
	public void onReceiveMessage(String encode, String message){
		
	}
	public void onAcceptedConnect(){
		
	}

	@Override
	public void sendFile() {
		Object[] parem = getParam();
		String filename = (String)parem[0];
		int size = (int)parem[1];
		onReceiveFile(filename, size);
		
	}
	
	protected void onReceiveFile(String filename, int size) {
		
		
	}

	@Override
	public void sendCaro() {
		Object[] ret = getParam();
		int x = (int) ret[0];
		int y = (int) ret[1];
		onCaroMessage(new Point(x, y));
		
	}
	
	protected void onCaroMessage(Point point) {
		
	}

	@Override
	public void invite() {
		
	}
}
