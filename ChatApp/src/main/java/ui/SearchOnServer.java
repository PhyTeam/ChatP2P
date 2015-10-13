package ui;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchOnServer extends ThePanel {
	private JTextField txtSearch;
	JList list_result;
	java.util.List<String> list = new java.util.ArrayList<String>();
	ListFriendPanel temp;
	/**
	 * Create the panel.
	 */
	public SearchOnServer(String name, String path, ListFriendPanel listFriendPanel) {
		super(name, path);
		setLayout(null);
		temp = listFriendPanel;
		txtSearch = new JTextField();
		txtSearch.setBounds(10, 11, 151, 29);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Get Data from Server
			 * param: Keyword
			 * Result: List<String>
			 */
			public void mouseClicked(MouseEvent arg0) {
				String keyword = txtSearch.getText();
				
				
				

			}
		});
		btnSearch.setBounds(171, 11, 69, 29);
		add(btnSearch);
		list_result = new JList();
		list_result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_result.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		// Khởi tạo list
		list.add("a");
		list.add("ab");
		list.add("abc");
		list.add("bmmmm");
		list.add("d");
		list.add("c");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("ai");
		list.add("j");
		list.add("ak");
		list.add("all");
		list.add("al1");
		list.add("al2");
		list.add("al3");
		
		// Tao Scroll Pane
		ScrollPane scrollPane = new ScrollPane();
		list_result.setListData(list.toArray());
		list_result.setSelectedIndex(0);
		scrollPane.setBounds(10, 76, 230, 305);
		scrollPane.add(list_result);
		add(scrollPane);
		
		JLabel lblTrcTuyn = new JLabel("Kết quả tìm kiếm");
		lblTrcTuyn.setForeground(new Color(144, 238, 144));
		lblTrcTuyn.setBounds(10, 51, 123, 14);
		add(lblTrcTuyn);
		
		JButton btnAddFriend = new JButton("Thêm bạn");
		btnAddFriend.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Add Friend to ListFriend
			 */
			public void mouseClicked(MouseEvent arg0) {
				temp.addFriend2List(list.get(list_result.getSelectedIndex()));
			}
		});
		btnAddFriend.setBounds(10, 387, 113, 23);
		add(btnAddFriend);
		
		
		JButton btnBack = new JButton("Quay lại");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel();
			}
		});
		btnBack.setBounds(127, 387, 113, 23);
		add(btnBack);
		
	}
	/**
	 * 
	 * @list: List<String> List User tim duoc
	 * Result: Hien thi List ra giao dien
	 */
	public void setFriendList(java.util.List<String> list){
		this.list = list;
		list_result.setListData(this.list.toArray());
		
	}
	private void changePanel(){
		this.hide();
	}
}
