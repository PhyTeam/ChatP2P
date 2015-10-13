package controller;

import protocol.ProtocolInterface;

public interface CallbackInteface {
	public void onResponse(ProtocolInterface protocolInterface);
	public void onSuccess(Object[] result);
	public void onFail(int errorCode, String message);
	public void onTimeout();
}
