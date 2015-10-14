package controller;

public interface SessionListener {
	public void onLogin(String username);
	public void onLogout(String username);
}
