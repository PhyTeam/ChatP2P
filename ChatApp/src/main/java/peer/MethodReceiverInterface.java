package peer;

import protocol.ProtocolMethod;

public interface MethodReceiverInterface {
	public void onReceive(ProtocolMethod method);
}
