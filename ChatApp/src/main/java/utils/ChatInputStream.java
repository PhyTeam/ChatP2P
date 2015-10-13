package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author Phuc
 *
 */
public class ChatInputStream implements Runnable {

	private InputStream inputStream;
	private OutputStream oStream;
	public ChatInputStream(InputStream outputStream, OutputStream os){
		this.inputStream = outputStream;
		this.oStream = os;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		StringBuffer line = new StringBuffer();
		PrintWriter writer = new PrintWriter(oStream);
		try {
			while(true){
			int c = inputStream.read();
			if(c == -1) break;
			line.append((char)c);
			System.out.print(c);
			if(c == '\n'){
					//System.out.println(line);
					writer.print(line.toString());
					writer.flush();
					line.delete(0, line.length());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}