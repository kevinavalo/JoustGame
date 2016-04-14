import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class MenuPanel {
	
	private BufferedImage img;
	
	public MenuPanel(String img) {
		try {
			this.img = ImageIO.read(new File(img + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.img, 0, 0, null);
	}
	
}
