package caro;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MyImage {
	private String urlImage = "caro_data/image/";
	public Image imgCross;
	public Image imgNought;

	public MyImage() {
		int size = CaroGraphics.sizeCell - 2;
		imgCross = reSizeImage(getMyImageIcon("cross.gif"), size, size);
		imgNought = reSizeImage(getMyImageIcon("nought.gif"), size, size);
	}

	public Image reSizeImage(Image image, int width, int height) {
		image = new ImageIcon(image.getScaledInstance(width, height,
				imgCross.SCALE_SMOOTH)).getImage();
		return image;
	}

	public Image getMyImageIcon(String nameImageIcon) {
		System.out.println(urlImage + nameImageIcon);
		// Loading image
		Image ii = new ImageIcon(getClass().getResource(
				urlImage + nameImageIcon)).getImage();
		return ii;
	}
}
