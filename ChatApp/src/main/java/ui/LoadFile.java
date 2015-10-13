package ui;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import java.awt.List;
import java.awt.ScrollPane;
import javax.swing.JMenu;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JTabbedPane;

public class LoadFile extends ThePanel {
	private JTextField textField;
	private JButton btnBrowse; 
	private JFileChooser fc;
	private File f;
	public JTabbedPane tabbedPane = null;
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
}
