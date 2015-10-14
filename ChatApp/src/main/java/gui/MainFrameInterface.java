package gui;

import javax.swing.JPanel;

public interface MainFrameInterface {
	public void showLoginPanel();

	public void showSignup();
	
	public JPanel showPanel(String panelname);
}
