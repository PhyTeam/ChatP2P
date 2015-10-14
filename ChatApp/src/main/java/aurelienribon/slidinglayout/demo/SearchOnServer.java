package aurelienribon.slidinglayout.demo;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchOnServer extends ThePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtSearch;
	@SuppressWarnings("rawtypes")
	private JList list_result;
	private java.util.List<String> list = new java.util.ArrayList<String>();
	private java.util.List<String> liststt = new java.util.ArrayList<String>();
	private boolean blogout;
	/**
	 * Create the panel.
	 */
	public SearchOnServer(String name, String path,  ThePanel listFriendPanel, TheFrame frame) {
		super(name, path);
		setLayout(null);
		
		blogout = false;
		
		txtSearch = new JTextField();
		txtSearch.setBounds(10, 11, 143, 29);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Get Data from Server
			 * param: Keyword
			 * Result: List<String>, List<String>
			 */
			public void mouseClicked(MouseEvent arg0) {
				String keyword = txtSearch.getText();
				// TODO : Server search
				
				

			}
		});
		btnSearch.setBounds(163, 11, 77, 29);
		add(btnSearch);
		list_result = new JList();
		list_result.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_result.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		// Khởi tạo list
		list.add("a1111111111111111111");	liststt.add("online");
		list.add("ab"); liststt.add("online");
		list.add("abc"); liststt.add("online");
		list.add("bmmmm"); liststt.add("online");
		list.add("d"); liststt.add("online");
		list.add("c"); liststt.add("online");
		list.add("e"); liststt.add("online");
		list.add("f"); liststt.add("online");
		list.add("g"); liststt.add("online");
		list.add("h"); liststt.add("online");
		list.add("ai"); liststt.add("online");
		list.add("j"); liststt.add("offline");
		list.add("all"); liststt.add("offline");
		list.add("al1"); liststt.add("offline");
		list.add("al2"); liststt.add("offline");
		list.add("al3"); liststt.add("offline");
		
		// Tao Scroll Pane
		ScrollPane scrollPane = new ScrollPane();
		list_result.setListData(list.toArray());
		list_result.setSelectedIndex(0);
		scrollPane.setBounds(10, 76, 230, 400);
		scrollPane.add(list_result);
		add(scrollPane);
		
		JLabel lblTrcTuyn = new JLabel("Results");
		lblTrcTuyn.setForeground(new Color(144, 238, 144));
		lblTrcTuyn.setBounds(10, 51, 123, 14);
		add(lblTrcTuyn);
		
		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Add Friend to ListFriend
			 */
			public void mouseClicked(MouseEvent arg0) {
				((ListFriendPanel)listFriendPanel).addFriend2List(list.get(list_result.getSelectedIndex()),
																liststt.get(list_result.getSelectedIndex()));
			}
		});
		btnAddFriend.setBounds(128, 506, 112, 23);
		add(btnAddFriend);
		
		
		JButton btnChat = new JButton("Chat");
		btnChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((ChatPanel)frame.chatpanel).CreateTab(list.get(list_result.getSelectedIndex()));
			}
		});
		btnChat.setBounds(10, 506, 108, 23);
		add(btnChat);
		
	}
	/**
	 * 
	 * @list: List<String> List User tim duoc
	 * Result: Hien thi List ra giao dien
	 */
	public void setFriendList(java.util.List<String> list, java.util.List<String> list1){
		this.list = list;
		this.liststt = list1;
		list_result.setListData(this.list.toArray());
		
	}
	public boolean getlogout(){
		return this.blogout;
	}
}
