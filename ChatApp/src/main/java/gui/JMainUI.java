package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aurelienribon.slidinglayout.SLPanel;
import controller.ServerService;
import controller.SessionListener;
import ui.ChatPanel;
import ui.JLoginPanel;
import ui.ListFriendPanel;
import ui.SearchOnServer;
import ui.ThePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class JMainUI implements MainFrameInterface {

	private JFrame frame;	
	JPanel login_Panel;
	JPanel panel;
	
	Map<String, JPanel> container;
	JPanel currentPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JMainUI window = new JMainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JMainUI() {
		container = new HashMap<String, JPanel>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createServerConnection();
		frame = new JFrame();
		frame.setBounds(100, 100, 499, 439);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login_Panel = new gui.JLoginPanel(this);
		
		container.put("loginPanel", login_Panel);
		panel = new JSignupPanel(this);
		container.put("signupPanel", panel);
		// Create list friend panel
		JPanel listFriend = new JListFriendPanel(this);
		container.put("listFriendPanel", listFriend);
		try {
			ServerService.GetResource().session.addSessionListener((SessionListener)listFriend);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		panel.setPreferredSize(new Dimension(450, 450));
		login_Panel.setPreferredSize(new Dimension(450, 450));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		currentPanel = panel;
	}
	public void createServerConnection(){
		try {
			ServerService.GetResource();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Error: Can'nt to server");
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error: Can'nt to server");
			System.exit(0);
		}
	}
	
	public void showLoginPanel(){
		frame.getContentPane().remove(panel);
		frame.getContentPane().add(login_Panel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void showSignup() {
		frame.getContentPane().remove(login_Panel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
		
	}

	@Override
	public JPanel showPanel(String panelname) {
		JPanel panel = container.get(panelname);
		if(panel == null) return currentPanel;
		frame.getContentPane().remove(currentPanel);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		currentPanel = panel;
		frame.revalidate();
		frame.repaint();
		return panel;
	}
}
