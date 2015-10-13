package gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class MyInputStream extends InputStream {

	Queue<Integer> queue;
	
	public MyInputStream() {
		queue = new LinkedList<Integer>();
	}
	
	public void sendText(String string){
		//System.out.println(string);
		char[] arr = string.toCharArray();
		synchronized (this) {
			for (char c : arr) {
				queue.add((int)c);
				//System.out.print((char)queue.peek().intValue() + "-");
			}
			queue.add(new Integer('\n'));
		}
	}
	@Override
	public int read() throws IOException {
		while(true){
			if(queue.isEmpty())
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else break;
		}
		int ret= queue.poll();
		//System.out.print((char)ret);
		return ret;
	}

}