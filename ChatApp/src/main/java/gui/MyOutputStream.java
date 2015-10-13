package gui;
import java.io.IOException;
import java.io.OutputStream;

import gui.ConsumerInterface;

/**
 * 
 */

/**
 * @author Phuc
 *
 */
public class MyOutputStream extends OutputStream {

	private ConsumerInterface consumer;
	private StringBuffer stringBuffer;
	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	public MyOutputStream(ConsumerInterface consumer) {
		stringBuffer = new StringBuffer();
		this.consumer = consumer;
	}
	@Override
	public void write(int arg0) throws IOException {
		char c = (char)arg0;
		stringBuffer.append(c);
		if(c == '\n'){
			consumer.getText(stringBuffer.toString());
			stringBuffer.delete(0, stringBuffer.length());
		}
			
	}

}
