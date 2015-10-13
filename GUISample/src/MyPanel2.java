import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class MyPanel2 extends JPanel {

	/**
	 * Create the panel.
	 */
	public MyPanel2() {
		setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(110, 93, 212, 20);
		add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(149, 145, 4, 22);
		add(textArea);

	}
}
