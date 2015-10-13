package test;

import java.io.IOException;
import java.io.InputStream;


public class MyInputStream extends InputStream {

	String sample = "{\"method\" : \"Hello\"}";
	int i = 0;
	@Override
	public int read() throws IOException {
		byte[] data = sample.getBytes();
		int ret = -1;
		if (i < data.length) ret = (int)data[i++];
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

}
