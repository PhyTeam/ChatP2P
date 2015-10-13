package controller;

import protocol.ProtocolReturn;

public interface ControllerInterface {
	public ProtocolReturn login();
	public ProtocolReturn signup();
	public ProtocolReturn getProfile();
	public ProtocolReturn getContacts();
	public ProtocolReturn logout();
	public ProtocolReturn online();
	
}
