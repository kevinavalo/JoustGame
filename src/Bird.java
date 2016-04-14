import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird {

	// / imgs: default storage for the pictures of the bird
	private BufferedImage[] imgs;

	// TODO: add your own fields here
	private double x = 0;
	private double y = 0;
	private double vx = 0;
	private double vy = 0;
	private double fx = 0;
	private double fy = 0;
	private int timer = 0;
	private double gravity = 30;
	private int pos = 0;
	private int currentTime = this.timer;
	private int previousTime = 0;
	private double dt = 0;
	private boolean movingRight = false;
	private boolean movingLeft = false;

	/**
	 * Creates a bird object with the given image set
	 *
	 * @param basename
	 *            should be "birdg" or "birdr" (assuming you use the provided
	 *            images)
	 */
	public Bird(String basename, double x, double y, int pos) {
		// You may change this method if you wish, including adding
		// parameters if you want; however, the existing image code works as is.
		this.imgs = new BufferedImage[6];
		try {
			// 0-2: right-facing (folded, back, and forward wings)
			this.imgs[0] = ImageIO.read(new File(basename + ".png"));
			this.imgs[1] = ImageIO.read(new File(basename + "f.png"));
			this.imgs[2] = ImageIO.read(new File(basename + "b.png"));
			// 3-5: left-facing (folded, back, and forward wings)
			this.imgs[3] = Bird.makeFlipped(this.imgs[0]);
			this.imgs[4] = Bird.makeFlipped(this.imgs[1]);
			this.imgs[5] = Bird.makeFlipped(this.imgs[2]);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.x = x;
		this.y = y;
		this.pos = pos;
		this.fx = 1;
		this.fy = 1;

	}

	/**
	 * A helper method for flipping in image left-to-right into a mirror image.
	 * There is no reason to change this method.
	 *
	 * @param original
	 *            The image to flip
	 * @return A left-right mirrored copy of the original image
	 */
	private static BufferedImage makeFlipped(BufferedImage original) {
		AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
		af.translate(-original.getWidth(), 0);
		BufferedImage ans = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		Graphics2D g = (Graphics2D) ans.getGraphics();
		g.drawImage(original, af, null);
		return ans;
	}

	/**
	 * Draws this bird
	 *
	 * @param g
	 *            the paintbrush to use for the drawing
	 */
	public int getPos(){
		return this.pos;
	}
	public void setPos(int x) {
		this.pos = x;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVX() {
		return this.vx;
	}

	public void setVX(double vx) {
		this.vx = vx;
	}

	public void draw(Graphics g) {

		int i = this.pos; // between 0 and 6, depending on facing and wing state
		double x = this.x; // where to center the picture
		double y = this.y;

		// TODO: find the right x, y, and i instead of the examples given here

		g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
				(int) y - this.imgs[i].getHeight() / 2, null);
	}

	public void movePosition() {
		this.x += this.vx * dt;
		this.y += this.vy * dt;
	}
	public void movePositionAI() {
		this.x += this.vx *dt;
		this.y += this.vy *.15/5;
	}

	public void moveRight() {
		this.vx += 9.0;
		this.vy = -30;
		// if (this.y < 579.57 && this.y > 100 && this.x < 755)
		// this.x = this.x + this.vx * dt;
		// this.y = this.y - this.vy * dt;
		this.movingRight = true;
	}
	
	public void moveRightAI() {
		this.vx += 9.0;
		this.vy = -30;
		this.movingRight = true;
	}
	
	public void flipRightAI () {
		this.vx += 9.0;
		this.movingRight = true;
	}
	
	public void moveLeftAI() {
		this.vx -= 9.0;
		this.vy = -30;
		this.movingRight = false;
	}
	
	public void flipLeftAI () {
		this.vx -= 9.0;
		this.movingRight = false;
	}

	public void moveLeft() {
		this.vx -= 9.0;
		this.vy = -30;
		// if(this.y < 590 && this.y > 100 && this.x > 25){
		// this.x = this.x - this.vx * dt;
		// this.y = this.y - this.vy * dt;
		// this.movingLeft = true;
		// }
	}

	public void elapsedTime() {
		this.previousTime = this.currentTime;
		this.currentTime = this.timer;
		this.dt = this.currentTime - this.previousTime;
		if (dt > 0.15) {
			this.dt = 0.15;
		}

	}

	/*
	 * public void movePosition() { x += vx * dt; y += vy * dt; }
	 * 
	 * public void moveLeft() { vx -= 5.0;
	 * 
	 * vy = -10; }
	 * 
	 * public void moveRight() { vx += 5.0; vy = -10; }
	 */
	public void applyGravity() {
		vy += gravity * dt;
		/*
		 * if (this.y < 579.57) { vy += .5 * dt; } if (movingRight && this.y <
		 * 579.57) { double dvx = .5; dvx = dvx + this.gravity * dt; this.x +=
		 * dvx; } if (movingLeft && this.y < 579.57) { double dvx = .5; dvx =
		 * dvx + this.gravity * dt; this.x -= dvx; }
		 */
	}

	public Rectangle getBounds() {
		Rectangle rect = new Rectangle((int) this.x - 20, (int) this.y - 20,
				40, 40);
		return rect;

	}

	public boolean collidesWith(Bird b) {
		boolean collision = false;
		if (b.getBounds().intersects(this.getBounds())) {
			collision = true;
		}

		return collision;
	}

	public boolean collidesWith2(Rectangle b) {
		boolean collision = false;
		if (b.getBounds().intersects(this.getBounds())) {
			collision = true;
		}
		return collision;
	}

	public void bounceIfOutsideOf(Rectangle r, double bounciness) {
		if (this.x - 30 < r.getMinX()) {
			this.x = r.getMinX() + 30;
			this.vx = Math.abs(this.vx * bounciness);
		}
		if (this.x + 25 > r.getMaxX()) {
			this.x = r.getMaxX() - 25;
			this.vx = (-1) * Math.abs(this.vx * bounciness);
		}
		if (this.y - 30 < r.getMinY()) {
			this.y = r.getMinY() + 30;
			this.vy = Math.abs(this.vy * bounciness);
		}
		if (this.y + 20 > r.getMaxY()) {
			this.y = r.getMaxY() - 20;
			this.vy = (-1) * Math.abs(this.vy * bounciness);
		}

	}

	public void bounceIfInsideOf(Rectangle r, double bounciness) {
		if ((this.x) < r.getX()) {
			this.x = r.getX() - 25;
			this.vx = -Math.abs(vx * bounciness);
		}
		if ((this.x) > (r.getX() + r.getWidth())) {
			this.x = r.getX() + r.getWidth() + 25;
			this.vx = Math.abs(vx * bounciness);
		}
		if ((this.y) < r.getY()) {
			this.y = r.getY() - 25;
			this.vy = -Math.abs(vy * bounciness);
		}
		if ((this.y) > (r.getY() + r.getHeight())) {
			this.y = r.getY() + r.getHeight() + 25;
			this.vy = Math.abs(vy * bounciness);
		}
	}

	public double getSpeed() {
		double speed = 0;
		speed = Math.sqrt(Math.pow(this.vx, 2) + Math.pow(this.vy, 2));
		return speed;
	}

	public void applyDrag(double drag) {
		double dvx = (this.getSpeed() * -1.0 * this.vx * drag * dt) / 1;
		double dvy = (this.getSpeed() * -1.0 * this.vy * drag * dt) / 1;
		this.vx += dvx;
		this.vy += dvy;
	}

	public void tick() {
		this.timer++;
	}
	
	public int getTick() {
		return this.timer;
	}
	
	public void moveBirdLeft(boolean birdCollide){
		if(this.getPos() != 3){
			System.out.println("!3" + " " + this.getPos());
			if(birdCollide){
				this.setPos(3);
				this.flipLeftAI();
			} else {
				this.setPos(3);
				this.moveLeftAI();
			}
			
		} else if(this.getPos() == 3){
			if(birdCollide){
				this.setPos(4);
				this.flipLeftAI();
			} else {
				this.setPos(4);
				this.moveLeftAI();
			}
			System.out.println("3");
		} else if(this.getPos() == 4){
			if(birdCollide){
				this.setPos(5);
				this.flipLeftAI();
			} else {
				this.setPos(5);
				this.moveLeftAI();
			}
			System.out.println("4");
		} else if(this.getPos() == 5){
			if(birdCollide){
				this.setPos(3);
				this.flipLeftAI();
			} else {
				this.setPos(3);
				this.moveLeftAI();
			}
			System.out.println("5");
		}
	}
	public void moveBirdRight(boolean birdCollide) {
		 if(this.getPos() != 0){
				System.out.println("!0" + " " + this.getPos());
				if(birdCollide){
					this.setPos(0);
					this.flipRightAI();
				} else {
					this.setPos(0);
					this.moveRightAI();
				}
				
			}else if(this.getPos() == 0){
				if(birdCollide){
					this.setPos(1);
					this.flipRightAI();
				} else {
					this.setPos(1);
					this.moveRightAI();
				}
				System.out.println("0");
			}else if(this.getPos() == 1){
				if(birdCollide){
					this.setPos(2);
					this.flipRightAI();
				} else {
					this.setPos(2);
					this.moveRightAI();
				}
				System.out.println("1");
			}else if(this.getPos() == 2){
				if(birdCollide){
					this.setPos(0);
					this.flipRightAI();
				} else {
					this.setPos(0);
					this.moveRightAI();
				}
				System.out.println("2");
			}

	}
	
	public void eliminateBird() {
		
	}
}

// import java.awt.Graphics;
// import java.awt.Graphics2D;
// import java.awt.Rectangle;
// import java.awt.Window;
// import java.awt.geom.AffineTransform;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
//
// import javax.imageio.ImageIO;
//
// public class Bird {
//
// // / imgs: default storage for the pictures of the bird
// private BufferedImage[] imgs;
//
// // TODO: add your own fields here
// private double x = 0;
// private double y = 0;
// private double vx = 0;
// private double vy = 0;
// private double fx = 0;
// private double fy = 0;
// private int timer = 0;
// private double gravity = 30;
// private int pos = 0;
// private int currentTime = this.timer;
// private int previousTime = 0;
// private double dt = 0;
// private boolean movingRight = false;
// private boolean movingLeft = false;
//
// /**
// * Creates a bird object with the given image set
// *
// * @param basename
// * should be "birdg" or "birdr" (assuming you use the provided
// * images)
// */
// public Bird(String basename, double x, double y, int pos) {
// // You may change this method if you wish, including adding
// // parameters if you want; however, the existing image code works as is.
// this.imgs = new BufferedImage[6];
// try {
// // 0-2: right-facing (folded, back, and forward wings)
// this.imgs[0] = ImageIO.read(new File(basename + ".png"));
// this.imgs[1] = ImageIO.read(new File(basename + "f.png"));
// this.imgs[2] = ImageIO.read(new File(basename + "b.png"));
// // 3-5: left-facing (folded, back, and forward wings)
// this.imgs[3] = Bird.makeFlipped(this.imgs[0]);
// this.imgs[4] = Bird.makeFlipped(this.imgs[1]);
// this.imgs[5] = Bird.makeFlipped(this.imgs[2]);
// } catch (IOException e) {
// throw new RuntimeException(e);
// }
// this.x = x;
// this.y = y;
// this.pos = pos;
// this.fx = 1;
// this.fy = 1;
//
// }
//
// /**
// * A helper method for flipping in image left-to-right into a mirror image.
// * There is no reason to change this method.
// *
// * @param original
// * The image to flip
// * @return A left-right mirrored copy of the original image
// */
// private static BufferedImage makeFlipped(BufferedImage original) {
// AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
// af.translate(-original.getWidth(), 0);
// BufferedImage ans = new BufferedImage(original.getWidth(),
// original.getHeight(), original.getType());
// Graphics2D g = (Graphics2D) ans.getGraphics();
// g.drawImage(original, af, null);
// return ans;
// }
//
// /**
// * Draws this bird
// *
// * @param g
// * the paintbrush to use for the drawing
// */
// public void setPos(int x) {
// this.pos = x;
// }
//
// public double getX() {
// return this.x;
// }
//
// public double getY() {
// return this.y;
// }
//
// public void setX(double x) {
// this.x = x;
// }
//
// public void setY(double y) {
// this.y = y;
// }
//
// public double getVX() {
// return this.vx;
// }
//
// public void setVX(double vx) {
// this.vx = vx;
// }
//
// public void draw(Graphics g) {
//
// int i = this.pos; // between 0 and 6, depending on facing and wing state
// double x = this.x; // where to center the picture
// double y = this.y;
//
// // TODO: find the right x, y, and i instead of the examples given here
//
// g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
// (int) y - this.imgs[i].getHeight() / 2, null);
// }
//
// public void movePosition() {
// this.x += this.vx * dt;
// this.y += this.vy * dt;
// }
//
// public void moveRight() {
// this.vx += 5.0;
// this.vy = -15;
// // if (this.y < 579.57 && this.y > 100 && this.x < 755)
// // this.x = this.x + this.vx * dt;
// // this.y = this.y - this.vy * dt;
// this.movingRight = true;
// }
//
// public void moveLeft() {
// this.vx -= 5.0;
// this.vy = -15;
// // if(this.y < 590 && this.y > 100 && this.x > 25){
// // this.x = this.x - this.vx * dt;
// // this.y = this.y - this.vy * dt;
// // this.movingLeft = true;
// // }
// }
//
// public void elapsedTime() {
// this.previousTime = this.currentTime;
// this.currentTime = this.timer;
// this.dt = this.currentTime - this.previousTime;
// if (dt > 0.15) {
// this.dt = 0.15;
// }
//
// System.out.println(x);
// }
//
// /*
// * public void movePosition() { x += vx * dt; y += vy * dt; }
// *
// * public void moveLeft() { vx -= 5.0;
// *
// * vy = -10; }
// *
// * public void moveRight() { vx += 5.0; vy = -10; }
// */
// public void applyGravity() {
// vy += 5 * dt;
// /*
// * if (this.y < 579.57) { vy += .5 * dt; } if (movingRight && this.y <
// * 579.57) { double dvx = .5; dvx = dvx + this.gravity * dt; this.x +=
// * dvx; } if (movingLeft && this.y < 579.57) { double dvx = .5; dvx =
// * dvx + this.gravity * dt; this.x -= dvx; }
// */
// }
//
// public Rectangle getBounds() {
// Rectangle rect = new Rectangle((int) this.x, (int) this.y, 40, 40);
// return rect;
//
// }
//
// public boolean collidesWith(Bird b) {
// boolean collision = false;
// if (b.getBounds().intersects(this.getBounds())) {
// collision = true;
// }
//
// return collision;
// }
// public boolean collidesWith2(Rectangle b){
// boolean collision = false;
// if (b.getBounds().intersects(this.getBounds())){
// collision = true;
// }
// return collision;
// }
// public void bounceIfOutsideOf(Rectangle r, double bounciness) {
// if (this.x - 30 < r.getMinX()) {
// this.x = r.getMinX() + 30;
// this.vx = Math.abs(this.vx * bounciness);
// }
// if (this.x + 25 > r.getMaxX()) {
// this.x = r.getMaxX() - 25;
// this.vx = (-1) * Math.abs(this.vx * bounciness);
// }
// if (this.y - 30 < r.getMinY()) {
// this.y = r.getMinY() + 30;
// this.vy = Math.abs(this.vy * bounciness);
// }
// if (this.y + 20 > r.getMaxY()) {
// this.y = r.getMaxY() - 20;
// this.vy = (-1) * Math.abs(this.vy * bounciness);
// }
//
// }
//
// public void bounceIfInsideOf(Rectangle r, double bounciness) {
// if ((this.x - 35) < r.getX()) {
// this.x = r.getX() - 35;
// this.vx = -Math.abs(this.vx * bounciness);
// }
// if ((this.x + 35) > (r.getX() + r.getWidth())) {
// this.x = r.getX() + r.getWidth() + 35;
// this.vx = Math.abs(this.vx * bounciness);
// }
// if ((this.y - 35) < r.getY()) {
// this.y = r.getY() - 35;
// this.vy = -Math.abs(this.vy * bounciness);
// }
// if((this.y + 35) > (r.getY() + r.getHeight())){
// this.y = r.getY() + r.getHeight() + 35;
// this.vy = Math.abs(vy * bounciness);
// }
// }
//
// public double getSpeed() {
// double speed = 0;
// speed = Math.sqrt(Math.pow(this.vx, 2) + Math.pow(this.vy, 2));
// return speed;
// }
//
// public void applyDrag(double drag) {
// double dvx = (this.getSpeed() * -1.0 * this.vx * drag * dt) / 1;
// double dvy = (this.getSpeed() * -1.0 * this.vy * drag * dt) / 1;
// this.vx += dvx;
// this.vy += dvy;
// }
//
// public void tick() {
// this.timer++;
// }
// }
