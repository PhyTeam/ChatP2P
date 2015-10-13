package controller;

import protocol.ProtocolMethod;

/**
 * Extends this class to receive method sent
 * @author Phuc
 *
 */
public class PeerMethodController implements PeerMethodInterface{

	private ProtocolMethod method;
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
		onAcceptedConnect();		
	}
	
	public void onReceiveMessage(String encode, String message){
		
	}
	public void onAcceptedConnect(){
		
	}
}
