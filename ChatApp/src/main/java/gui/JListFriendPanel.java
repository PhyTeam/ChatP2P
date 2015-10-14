package gui;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import java.awt.Font;

public class JListFriendPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public JListFriendPanel() {
		setLayout(null);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Online", "Test"},
			},
			new String[] {
				"Friends", "Status"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setBounds(10, 51, 430, 327);
		add(table);

	}
}
