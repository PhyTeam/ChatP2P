package aurelienribon.slidinglayout.demo;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.CallbackInteface;
import controller.ServerService;
import gui.MainFrameInterface;
import protocol.ProtocolInterface;
import server.ServerConnection;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class JSignupPanel extends ThePanel {
	private JTextField tb_username;
	private JTextField tb_password;
	private JTextField tb_repassword;
	private JLabel lblNhpLiMt;
	private JTextField tb_fullname;
	private JLabel lblTny;
	private JButton btn_Signup;
	private JLabel lblngKTi;
	ServerService service;
	
	TheFrame parent;

	/**
	 * Create the panel.
	 */
	public JSignupPanel(TheFrame frame, String name, String path) {
		super(name, path);
		parent = frame;
		setLayout(null);
		try {
			service = ServerService.GetResource();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		lblngKTi = new JLabel("\u0110\u0102NG K\u00CD T\u00C0I KHO\u1EA2N");
		lblngKTi.setForeground(Color.YELLOW);
		lblngKTi.setBounds(104, 28, 452, 44);
		lblngKTi.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblngKTi.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblngKTi);
		
		JLabel lblNewLabel = new JLabel("T\u00EAn t\u00E0i kho\u1EA3n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(26, 84, 123, 14);
		add(lblNewLabel);
		
		tb_username = new JTextField();
		tb_username.setBounds(159, 84, 361, 20);
		add(tb_username);
		tb_username.setColumns(10);
		
		btn_Signup = new JButton("\u0110\u0103ng k\u00ED");
		btn_Signup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_Signup.setBounds(159, 243, 361, 44);
		btn_Signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "failed");
				try {
					service.signup(tb_username.getText(), tb_password.getText(),
							tb_fullname.getText(), new CallbackInteface() {
								
								@Override
								public void onTimeout() {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onSuccess(Object[] result) {
									JOptionPane.showMessageDialog(null, "Success");
								}
								
								@Override
								public void onResponse(ProtocolInterface protocolInterface) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onFail(int errorCode, String message) {
									JOptionPane.showMessageDialog(null, "Failed " + message);
								}
							});
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("M\u1EADt kh\u1EA9u");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(47, 110, 85, 14);
		add(lblNewLabel_1);
		
		tb_password = new JTextField();
		tb_password.setBounds(159, 110, 361, 20);
		add(tb_password);
		tb_password.setColumns(10);
		
		lblNhpLiMt = new JLabel("Nh\u1EADp l\u1EA1i m\u1EADt kh\u1EA9u");
		lblNhpLiMt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNhpLiMt.setBounds(6, 135, 143, 21);
		add(lblNhpLiMt);
		
		tb_repassword = new JTextField();
		tb_repassword.setBounds(159, 136, 361, 20);
		add(tb_repassword);
		tb_repassword.setColumns(10);
		
		lblTny = new JLabel("T\u00EAn \u0111\u1EA7y \u0111\u1EE7");
		lblTny.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTny.setBounds(47, 161, 95, 25);
		add(lblTny);
		
		tb_fullname = new JTextField();
		tb_fullname.setBounds(159, 167, 361, 20);
		add(tb_fullname);
		tb_fullname.setColumns(10);
		add(btn_Signup);
		
	}
}
