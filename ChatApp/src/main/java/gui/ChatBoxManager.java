package gui;

import java.awt.EventQueue;
import java.io.IOException;

import controller.PeerMethodController;
import controller.PeerService;
import controller.ServerService;
import protocol.ProtocolError;
import protocol.ProtocolMethod;
import protocol.ProtocolReturn;

public class ChatBoxManager extends PeerMethodController {

	PeerService service;
	public ChatBoxManager(ProtocolMethod method) {
		super(method);
		// TODO Auto-generated constructor stub
	}
	
	public ChatBoxManager(ProtocolMethod method, PeerService service) {
		super(method);
		this.service = service;
	}
	
	public ChatBoxManager(PeerService service){
		super();
		this.service = service;
	}
	JChatPanel frame;
	
	public void createChatWindown(){
		new Thread(new Runnable() {
			public void run() {
				try {
					frame = new JChatPanel(service);
					frame.setVisible(true);
					System.out.print("HAs been create windown");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	@Override
	public void onAcceptedConnect() {
		ProtocolError error = new ProtocolError(0, "Success");
		Object[] result = null;
		try {
			result = new Object[]{ServerService.GetResource().session.username};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("<<Accepted>>");
		ProtocolReturn ret = new ProtocolReturn("accept", result, method.id, error);
		try {
			service.send(ret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createChatWindown();
	}
	
	@Override
	public void onReceiveMessage(String encode, String message) {
		if(frame != null) frame.log(message);
		System.out.println("Message : " + message);
	}
}
