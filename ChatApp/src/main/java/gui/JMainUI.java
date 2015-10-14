package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import aurelienribon.slidinglayout.SLPanel;
import ui.ChatPanel;
import ui.JLoginPanel;
import ui.ListFriendPanel;
import ui.SearchOnServer;
import ui.ThePanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JMainUI implements MainFrameInterface {

	private JFrame frame;	
	JPanel login_Panel;
	JPanel panel;
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 499, 439);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login_Panel = new gui.JLoginPanel(this);
		panel = new JSignupPanel(this);
		panel.setPreferredSize(new Dimension(450, 450));
		login_Panel.setPreferredSize(new Dimension(450, 450));
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		frame.getContentPane().add(panel, BorderLayout.CENTER);
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
}
