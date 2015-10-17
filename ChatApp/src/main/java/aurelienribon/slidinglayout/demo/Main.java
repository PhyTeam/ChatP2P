package aurelienribon.slidinglayout.demo;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import global.GlobalEnv;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Main {
	public static void main(String[] args) {
		if(args.length > 0){
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		GlobalEnv.ip = ip;
		GlobalEnv.port = port;
		}
		else{
			GlobalEnv.ip = "localhost";
			GlobalEnv.port = 6000;
		}
		Tween.registerAccessor(ThePanel.class, new ThePanel.Accessor());
		SLAnimator.start();

		TheFrame frame = new TheFrame();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
