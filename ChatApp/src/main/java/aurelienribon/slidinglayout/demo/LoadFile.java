package aurelienribon.slidinglayout.demo;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.CallbackInteface;
import controller.PeerService;
import peer.FileTransferClient;
import protocol.ProtocolInterface;

import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import java.awt.List;
import java.awt.ScrollPane;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JTabbedPane;

public class LoadFile extends ThePanel {
	private JTextField textField;
	private JButton btnBrowse; 
	private JFileChooser fc;
	private File f;
	public JTabbedPane tabbedPane = null;
	public String currentUser;
	public PeerService service;
	protected File uploadFile;
	/**
	 * Create the panel.
	 */
	public LoadFile(final String name, String path) {
		super(name,path);
		setLayout(null);
		
		btnBrowse = new JButton("Browse");
		fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("C:"));
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == btnBrowse)
				{
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						f = fc.getSelectedFile();
						textField.setText(f.getPath());
					}
				}
			}
		});
		btnBrowse.setBounds(386, 26, 89, 23);
		btnBrowse.setBorder(null);
		add(btnBrowse);
		
		JLabel lblFilesName = new JLabel("File's Name:");
		lblFilesName.setBounds(10, 30, 65, 14);
		add(lblFilesName);
		
		textField = new JTextField();
		textField.setBounds(85, 26, 291, 23);
		add(textField);
		textField.setColumns(10);
		JButton btnAttach = new JButton("Attach");
		btnAttach.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(textField.getText());
				uploadFile = file;
				try {
					if(service ==null) System.out.println("Not yet!");
					else service.sendFile(file.getName(), (int)file.length(), sendFileCallback);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CreateProgressTab(f.getName());
			}
		});
		btnAttach.setBounds(187, 59, 89, 23);
		add(btnAttach);		
	}
	
	public void CreateProgressTab(String fileName)
	{
		if(fileName != null)
		if(tabbedPane == null)
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane.setBounds(10, 93, 465, 80);
			add(tabbedPane);
			
			JPanel panel = new JPanel();
			tabbedPane.addTab(fileName, null, panel, fileName);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel(fileName);
			lblNewLabel.setBounds(10, 20, 55, 20);
			panel.add(lblNewLabel);
			
			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(75, 20, 300, 20);
			panel.add(progressBar);
			
			JButton btnStop = new JButton("Stop");
			btnStop.setBounds(385, 20, 65, 20);
			btnStop.setBorder(null);
			panel.add(btnStop);
		}
		else
		{
			JPanel panel = new JPanel();
			tabbedPane.addTab(fileName, null, panel, fileName);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel(fileName);
			lblNewLabel.setBounds(10, 20, 55, 20);
			panel.add(lblNewLabel);
			
			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(75, 20, 300, 20);
			panel.add(progressBar);
			
			JButton btnStop = new JButton("Stop");
			btnStop.setBounds(385, 20, 65, 20);
			btnStop.setBorder(null);
			panel.add(btnStop);
		}
			
	}

	CallbackInteface sendFileCallback = new CallbackInteface() {
		
		@Override
		public void onTimeout() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onSuccess(Object[] result) {
			String host = (String)result[1];
			int port = (int)result[0];
			
			//JOptionPane.showMessageDialog(null, "send file " + port);
			uploadFile(host,port);
		}
		
		@Override
		public void onResponse(ProtocolInterface protocolInterface) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onFail(int errorCode, String message) {
			JOptionPane.showMessageDialog(null, "can not send file");
			
		}
	};
	void uploadFile(String host, int port){
		try {
			FileTransferClient server = new FileTransferClient(uploadFile.getPath(), host, port);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
