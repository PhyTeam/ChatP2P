package ui;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.*;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


public class JListFriendPanel extends ThePanel {
	private JTextField txtSearch;
	private JList list_fri;
	private java.util.List<String> list = new java.util.ArrayList<String>();
	private java.util.List<String> list_view = new java.util.ArrayList<String>();
	ChatPanel temp;
	/**
	 * Create the panel.
	 */
	
	public JListFriendPanel(String name, String path, ChatPanel chatpanel) {
		super(name, path);
		temp = chatpanel;
		
		setLayout(null);
		txtSearch = new JTextField();
		txtSearch.setBounds(10, 11, 151, 29);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 *  Tìm kiếm keyword trong danh sách bạn bè
			 */
			public void mouseClicked(MouseEvent arg0) {
				String keyword = txtSearch.getText();
				if ( keyword.isEmpty()) copy2view();
				else {
					list_view.clear();
					for (int i = 0; i < list.size(); i++)
						if (list.get(i).contains(keyword)){
							list_view.add(list.get(i));
						}
				}
				list_fri.setListData(list_view.toArray());
			}
		});
		btnSearch.setBounds(171, 11, 69, 29);
		add(btnSearch);
		list_fri = new JList();
		list_fri.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_fri.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				
			}
		});
		list_fri.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int n = list_fri.getSelectedIndex();
				txtSearch.setText(list_view.get(n));
			}
		});
		// Khởi tạo danh sách bạn
		copy2view();
		// Cap nhat danh sach ban
		ScrollPane scrollPane = new ScrollPane();
		list_fri.setListData(list.toArray());
		list_fri.setSelectedIndex(0);
		scrollPane.setBounds(10, 76, 230, 305);
		scrollPane.add(list_fri);
		add(scrollPane);
		
		JLabel lblTrcTuyn = new JLabel("Danh sách bạn bè");
		lblTrcTuyn.setForeground(new Color(144, 238, 144));
		lblTrcTuyn.setBounds(10, 51, 123, 14);
		add(lblTrcTuyn);
		
		JButton btnSearchServer = new JButton("Tìm kiếm tất cả");
		btnSearchServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				change2searchserver();
			}
		});
		btnSearchServer.setBounds(127, 387, 113, 29);
		add(btnSearchServer);
		
		JButton btnChat = new JButton("Trò chuyện");
		btnChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				temp.CreateTab(list_view.get(list_fri.getSelectedIndex()));
			}
		});
		btnChat.setBounds(10, 387, 113, 29);
		add(btnChat);
	}
	public void addFriend2List(String name){
		for (int i = 0; i < list.size(); )
		{
			if (list.get(i).toString().equals(name)) return;
			else i++;
		}
		list.add(name);
		copy2view();
		list_fri.setListData(list_view.toArray());
	}
	public void removeFriendFromList(String name){
		for (int i = 0; i < list.size(); )
		{
			if (list.get(i).toString().equals(name)) 
				list.remove(i);
			else i++;
		}
		copy2view();
		list_fri.setListData(list_view.toArray());
	}
	public void setFriendList(java.util.List<String> list){
		this.list = list;
		copy2view();
		list_fri.setListData(this.list_view.toArray());
	}
	private void copy2view(){
		list_view.clear();
		for (int i = 0; i < list.size(); i++)
		{
			list_view.add(list.get(i));
		}
	}
	private void change2searchserver(){

	}
}
