package controller;

import protocol.ProtocolMethod;

public interface PeerMethodInterface {
	public void sendMessage();
	public void sendFile();
	public void accept();
	public void sendCaro();
	public void invite();
}
