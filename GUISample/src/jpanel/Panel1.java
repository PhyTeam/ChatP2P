package jpanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Canvas;

public class Panel1 extends JPanel {

	/**
	 * Create the panel.
	 */
	public Panel1() {
		setForeground(Color.PINK);
		
		Canvas canvas = new Canvas();
		add(canvas);
	}

}
