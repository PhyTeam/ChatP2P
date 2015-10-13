package peer;

import java.net.Socket;

public interface PeerListenerInterface {
	public void accept(Socket socket);
}
