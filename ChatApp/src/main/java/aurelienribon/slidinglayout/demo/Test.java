package aurelienribon.slidinglayout.demo;

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
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;


public class Test extends ThePanel {
	private JTextField txtSearch;
	private JList list_fri = new JList();
	private java.util.List<String> list = new java.util.ArrayList<String>();
	private java.util.List<String> liststt = new java.util.ArrayList<String>();
	private java.util.List<String> list_view = new java.util.ArrayList<String>();
	
	/**
	 * Create the panel.
	 */
	
	public Test(String name, String path, ChatPanel chatpanel) {
		super(name, path);
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				
			}
		});
		setLayout(null);
		txtSearch = new JTextField();
		txtSearch.setBounds(10, 11, 151, 29);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 *  Tìm kiếm keyword trong danh sách bạn bè
			 */
			public void mouseClicked(MouseEvent arg0) {
				String keyword = txtSearch.getText();
				if (keyword.equals(".online")){	// search friend online
					list_view.clear();
					for (int i = 0; i < list.size(); i++)
						if (list.get(i).equals("online")){
							list_view.add(list.get(i));
						}
				}
				else if( keyword.isEmpty()) copy2view();
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
		list.add("a");
		list.add("Online :" + "	ab");
		list.add("Offline:" + "	abc");
		list.add("Offline: 	bmmmm");
		list.add("Offline: 	d");
		list.add("Offline: 	c");
		list.add("Offline: 	e");
		list.add("Offline: 	f");
		list.add("Offline:	g");
		list.add("Offline:	h");
		list.add("Offline:	ai");
		list.add("Offline: 	j");
		list.add("Offline: 	ak");
		list.add("Offline: 	all");
		list.add("Offline: 	al1");
		list.add("Offline: 	al2");
		list.add("Offline: 	al3");
		// Cap nhat danh sach ban
		ScrollPane scrollPane = new ScrollPane();
		list_fri.setListData(list.toArray());
		list_fri.setSelectedIndex(0);
		//list_stt.setListData(list.toArray());
		
		scrollPane.setBounds(10, 76, 230, 400);
		scrollPane.add(list_fri);
		//scrollPane.add(list_stt);//////////////////
		add(scrollPane);
		
		JLabel lblTrcTuyn = new JLabel("List Friend");
		lblTrcTuyn.setForeground(new Color(144, 238, 144));
		lblTrcTuyn.setBounds(10, 51, 123, 14);
		add(lblTrcTuyn);
		
		JButton btnSearchServer = new JButton("Search All");
		btnSearchServer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
		
			}
		});
		btnSearchServer.setBounds(127, 500, 113, 29);
		add(btnSearchServer);
		
		JButton btnChat = new JButton("Chat");
		btnChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chatpanel.CreateTab(list_view.get(list_fri.getSelectedIndex()));
			}
		});
		btnChat.setBounds(10, 500, 113, 29);
		add(btnChat);
	}
	public void addFriend2List(String name, String stt){
		for (int i = 0; i < list.size(); )
		{
			if (list.get(i).toString().equals(name)) return;
			else i++;
		}
		list.add(name);
		liststt.add(stt);
		copy2view();
		list_fri.setListData(list_view.toArray());
	}
	public void removeFriendFromList(String name){
		for (int i = 0; i < list.size(); )
		{
			if (list.get(i).toString().equals(name)) 
				{
					list.remove(i);
					liststt.remove(i);
				}
			else i++;
		}
		copy2view();
		list_fri.setListData(list_view.toArray());
	}
	public void setFriendList(java.util.List<String> list, java.util.List<String> list2){
		this.list = list;
		this.liststt = list2;
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

	
}
