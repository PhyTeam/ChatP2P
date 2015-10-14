package peer;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.LinkedList;

import controller.CallbackInteface;
import controller.ServerService;
import protocol.ProtocolError;
import protocol.ProtocolInterface;
import protocol.ProtocolMethod;
import protocol.ProtocolParser;
import protocol.ProtocolReturn;

public class PeerConnectionReader implements Runnable {

	LinkedList<CallbackInteface> callbacks;
	LinkedList<MethodReceiverInterface> receivers;
	PeerConnection connection;
	InputStream inputStream;
	public void addMethodReceiver(MethodReceiverInterface receiverInterface){
		synchronized (receivers) {
			receivers.add(receiverInterface);
		}
	}
	public void addCallback(CallbackInteface callback){
		synchronized (callbacks) {
			callbacks.add(callback);
		}
	}
	
	public PeerConnectionReader(PeerConnection connection) throws IOException {
		this.connection = connection;
		inputStream = connection.getInputConnection();
		callbacks = new LinkedList<CallbackInteface>();
		receivers = new LinkedList<MethodReceiverInterface>();
	}
	@Override
	public void run() {
		// Create protocol parser
		ProtocolParser parser = new ProtocolParser(inputStream);
		while(true){
			ProtocolInterface protocol = parser.read();
			// Show me
			System.out.println("Fuck");
			// Accept method
			if(protocol.getType().equals("method")){
				ProtocolMethod method = (ProtocolMethod)protocol;
				if(method.method_name.equals("accept")){
					ProtocolError error = new ProtocolError(0, "Success");
					Object[] result = null;
					try {
						result = new Object[]{ServerService.GetResource().session.username};
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					ProtocolReturn ret = new ProtocolReturn(method.method_name, result, method.id, error);
					System.out.println(ret);
					try {
						connection.write(ret);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			}
			// Send to return listener
			for (CallbackInteface callbackInteface : callbacks) {
				callbackInteface.onResponse(protocol);
				if(protocol.getType() == "return") {
					ProtocolReturn ret = (ProtocolReturn) protocol;
					int code  = ((ProtocolReturn) protocol ).error.code;
					if(code >= 0) callbackInteface.onSuccess(ret.result);
					else callbackInteface.onFail(code, ret.error.message);
				}
				
			}
			// Send to method listener
			for (MethodReceiverInterface methodReceiverInterface : receivers) {
				System.out.println(methodReceiverInterface.toString());
				if(protocol.getType().equals("method")){
					methodReceiverInterface.onReceive((ProtocolMethod)protocol);
				}
			}
		}

	}

}
