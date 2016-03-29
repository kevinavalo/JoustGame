//Kevin Avalo (kwa6nf) & Oludare (ooo6hy)
//Nikhil Gupta (ng3br) helped us out.

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class JoustScreen extends KeyAdapter implements ActionListener {
	/**
	 * A simple method to make the game runnable. You should not modify this
	 * main method: it should print out a list of extras you added and then say
	 * "new JoustScreen();" -- nothing more than that.
	 */
	public static void main(String[] args) {
		// add a list of all extras you did, such as
		// System.out.println("Moving obstacles");
		// System.out.println("Birds leave trails of glowing faerie dust");
		// System.out.println("Press left-right-left-left-down to open a game of Mahjong");
		new JoustScreen();
	}

	// DO NOT CHANGE the next four fields (the window and timer)
	private JFrame window; // the window itself
	private BufferedImage content; // the current game graphics
	private Graphics2D paintbrush; // for drawing things in the window
	private Timer gameTimer; // for keeping track of time passing
	// DO NOT CHANGE the previous four fields (the window and timer)

	// TODO: add your own fields here
	private Bird b1 = new Bird("birdr", 100, 204.89, 0);
	private Bird b2 = new Bird("birdg", 200, 204.89, 3);
	private Rectangle rect = new Rectangle(0, 0, 800, 600);
	private Rectangle wall = new Rectangle(150, 150, 300, 30);
	private Rectangle wall2 = new Rectangle(500, 400, 30, 300);
	private Scoreboard score = new Scoreboard();
	private String msg = "";

	public JoustScreen() {
		// DO NOT CHANGE the window, content, and paintbrush lines below
		this.window = new JFrame("Joust Clone");
		this.content = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		this.paintbrush = (Graphics2D) this.content.getGraphics();
		this.window.setContentPane(new JLabel(new ImageIcon(this.content)));
		this.window.pack();
		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.addKeyListener(this);
		// DO NOT CHANGE the window, content, and paintbrush lines above
		// TODO: add anything else you might need (e.g., a couple of Bird
		// objects, some walls)

		// DO NOT CHANGE the next two lines nor add lines after them
		this.gameTimer = new Timer(20, this); // tick at 1000/20 fps
		this.gameTimer.start(); // and start ticking now
		// DO NOT CHANGE the previous two lines nor add lines after them
	}

	/**
	 * This method gets called each time a player presses a key. You can find
	 * out what key the pressed by comparing event.getKeyCode() with
	 * KeyEvent.VK_...
	 */
	public void keyPressed(KeyEvent event) {

		// TODO: handle the keys you want to use to run your game
		if (event.getKeyCode() == KeyEvent.VK_S) { // example
			System.out.println("The 's' key was pressed");
			if(b1.getPos() != 0){
				b1.setPos(0);
				b1.moveRight();
			}else if(b1.getPos() == 0){
				b1.setPos(1);
				b1.moveRight();
			}else if(b1.getPos() == 1){
				b1.setPos(2);
				b1.moveRight();
			}else if(b1.getPos() == 2){
				b1.setPos(0);
				b1.moveRight();
			}
		
		}
		if (event.getKeyCode() == KeyEvent.VK_A) { // example
			System.out.println("The 'a' key was pressed");
			if(b1.getPos() != 3){
				b1.setPos(3);
				b1.moveLeft();
			} else if(b1.getPos() == 3){
				b1.setPos(4);
				b1.moveLeft();
			} else if(b1.getPos() == 4){
				b1.setPos(5);
				b1.moveLeft();
			} else if(b1.getPos() == 5){
				b1.setPos(3);
				b1.moveLeft();
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_K) { // example
			System.out.println("The 'k' key was pressed");
			if(b2.getPos() != 3){
				b2.setPos(3);
				b2.moveLeft();
			} else if(b2.getPos() == 3){
				b2.setPos(4);
				b2.moveLeft();
			} else if(b2.getPos() == 4){
				b2.setPos(5);
				b2.moveLeft();
			} else if(b2.getPos() == 5){
				b2.setPos(3);
				b2.moveLeft();
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_L) { // example
			System.out.println("The 'l' key was pressed");
			if(b2.getPos() != 0){
				b2.setPos(0);
				b2.moveRight();
			}else if(b2.getPos() == 0){
				b2.setPos(1);
				b2.moveRight();
			}else if(b2.getPos() == 1){
				b2.setPos(2);
				b2.moveRight();
			}else if(b2.getPos() == 2){
				b2.setPos(0);
				b2.moveRight();
			}
		
		}
	}

	/**
	 * Java will call this every time the gameTimer ticks (50 times a second).
	 * If you want to stop the game, invoke this.gameTimer.stop() in this
	 * method.
	 */
	public void actionPerformed(ActionEvent event) {
		// DO NOT CHANGE the next four lines, and add nothing above them
		if (!this.window.isValid()) { // the "close window" button
			this.gameTimer.stop(); // should stop the timer
			return; // and stop doing anything else
		}
		// DO NOT CHANGE the previous four lines

		// TODO: add every-frame logic in here (gravity, momentum, collisions,
		// etc)
		
		b1.elapsedTime();
		b1.tick();
		b1.applyGravity();
		b1.applyDrag(.005);
		b2.elapsedTime();
		b2.tick();
		b2.applyGravity();
		b2.applyDrag(.005);
		b1.movePosition();
		b2.movePosition();
		b1.bounceIfOutsideOf(rect, 1);
		b2.bounceIfOutsideOf(rect, 1);
		b1.collidesWith(b2);
		b2.collidesWith(b1);

//		if (b1.getX() > b2.getX()) {
//			b1.setPos(3);
//			b2.setPos(0);
//		} else if (b1.getX() < b2.getX()) {
//			b1.setPos(0);
//			b2.setPos(3);
//		}

		Random randX = new Random();
		int newX = randX.nextInt(800);
		// Make spawn locations random
		if (b1.collidesWith(b2)) {
			double coordinates = Math.abs(b1.getY() - b2.getY());
			if (coordinates < 35) {
				double repel1 = b1.getVX() * -1;
				double repel2 = b2.getVX() * -1;
				b1.setVX(repel1);
				b2.setVX(repel2);
			} else if (b1.getY() > b2.getY()) {
				b1.setX(newX);
				b1.setY(10);
				score.incrementScore2();
			} else if (b1.getY() < b2.getY()) {
				b2.setX(newX);
				b2.setY(10);
				score.incrementScore1();
			}

		}
		if (b1.collidesWith2(wall)) {
			b1.bounceIfInsideOf(wall, .5);
		}
		if (b1.collidesWith2(wall2)) {
			b1.bounceIfInsideOf(wall2, .5);
		}
		if (b2.collidesWith2(wall)) {
			b2.bounceIfInsideOf(wall, .5);
		}
		if (b2.collidesWith2(wall2)) {
			b2.bounceIfInsideOf(wall2, .5);
		}
		
		//Ends game if a certain score is reached.
		
		// DO NOT CHANGE the next line; it must be last in this method
		try {
			this.refreshScreen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // redraws the screen after things move
		// DO NOT CHANGE the above line; it must be last in this method
	}

	/**
	 * Re-draws the screen. You should erase the old image and draw a new one,
	 * but you should not change anything in this method (use actionPerformed
	 * instead if you need something to change).
	 * @throws Exception 
	 */
	private void refreshScreen() throws Exception {
		this.paintbrush.fill(rect);
		this.paintbrush.setColor(new Color(150, 210, 255)); // pale blue
		this.paintbrush.fillRect(0, 0, this.content.getWidth(),
				this.content.getHeight()); // erases the previous frame

		// TODO: replace the following example code with code that does
		// the right thing (i.e., draw the birds, walls, and score)

		// example bird drawing; replace with something sensible instead of
		// making a new bird every frame
		b1.draw(this.paintbrush);
		b2.draw(this.paintbrush);
		// example wall drawing; replace with something sensible instead of
		// making a new wall every frame

		this.paintbrush.setColor(Color.orange);
		this.paintbrush.fill(wall);
		this.paintbrush.setColor(Color.black);
		this.paintbrush.fill(wall2);

		// example text drawing, for scores and/or other messages
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(new Color(127, 0, 0)); // dark red
		this.paintbrush.drawString("" + score.getScore1(), 30, 30);
		this.paintbrush.setColor(new Color(0, 127, 0)); // dark green
		this.paintbrush.drawString("" + score.getScore2(), 760, 30);
		this.msg = "JOUST";
		f = new Font(Font.SANS_SERIF, Font.BOLD, 90);
		Rectangle2D r = f.getStringBounds(msg, this.paintbrush.getFontRenderContext());
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(Color.WHITE);
		String listOfHighScores = "";
		if((score.getScore1() >= 10 || score.getScore2() >= 10) && Math.abs(score.getScore1() - score.getScore2()) >= 2){
			this.gameTimer.stop();
			try {
				score.loadHighScores("highscores");
			} catch(FileNotFoundException e) {
			}
			
			if(score.getScore1() > score.getScore2()){
				this.msg = "Game Over. Player 1 wins!";
			} else {
				this.msg = "Game Over. Player 2 wins!";
			}
				score.addHighScore(score.getScore1());
				score.addHighScore(score.getScore2());
				score.saveHighScores("highscores");
			f = new Font(Font.SANS_SERIF, Font.BOLD, 50);
			r = f.getStringBounds(msg, this.paintbrush.getFontRenderContext());
			this.paintbrush.setFont(f);
			this.paintbrush.setColor(Color.RED);
		}
		
		
		this.paintbrush.drawString(msg, 400 - (int) r.getWidth() / 2, 300);
		
		ArrayList<Integer> highScores = score.getHighScores();
		int curHeight = 400;
		int place = 1;
		for(int n : highScores){
			listOfHighScores += ""+n + "\n";
			this.paintbrush.setColor(Color.MAGENTA);
			this.paintbrush.drawString("High Scores:", (int) r.getWidth() / 2 - 90, 350);
			this.paintbrush.drawString(""+place +"." + "   " + n, (int) r.getWidth() / 2 - 25, curHeight);
			
			place ++;
			curHeight += 50;
		}

		// DO NOT CHANGE the next line; it must be last in this method
		this.window.repaint(); // displays the frame to the screen
		// DO NOT CHANGE the above line; it must be last in this method
	}
}

// import java.awt.Color;
// import java.awt.Font;
// import java.awt.Graphics2D;
// import java.awt.Rectangle;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.KeyEvent;
// import java.awt.event.KeyAdapter;
// import java.awt.geom.Rectangle2D;
// import java.awt.image.BufferedImage;
// import java.util.ArrayList;
// import java.util.Random;
//
// import javax.swing.ImageIcon;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.Timer;
//
// public class JoustScreen extends KeyAdapter implements ActionListener {
// /**
// * A simple method to make the game runnable. You should not modify this
// * main method: it should print out a list of extras you added and then say
// * "new JoustScreen();" -- nothing more than that.
// */
// public static void main(String[] args) {
// // add a list of all extras you did, such as
// // System.out.println("Moving obstacles");
// // System.out.println("Birds leave trails of glowing faerie dust");
// //
// System.out.println("Press left-right-left-left-down to open a game of Mahjong");
// new JoustScreen();
// }
//
// // DO NOT CHANGE the next four fields (the window and timer)
// private JFrame window; // the window itself
// private BufferedImage content; // the current game graphics
// private Graphics2D paintbrush; // for drawing things in the window
// private Timer gameTimer; // for keeping track of time passing
// // DO NOT CHANGE the previous four fields (the window and timer)
//
// // TODO: add your own fields here
// private Bird b1 = new Bird("birdr", 100, 204.89, 0);
// private Bird b2 = new Bird("birdg", 200, 204.89, 3);
// private Rectangle rect = new Rectangle(0, 0, 800, 600);
// private Rectangle wall = new Rectangle(200, 150, 300, 10);
// private Rectangle wall2 = new Rectangle(400, 400, 300, 10);
// private Scoreboard score = new Scoreboard();
//
// public JoustScreen() {
// // DO NOT CHANGE the window, content, and paintbrush lines below
// this.window = new JFrame("Joust Clone");
// this.content = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
// this.paintbrush = (Graphics2D) this.content.getGraphics();
// this.window.setContentPane(new JLabel(new ImageIcon(this.content)));
// this.window.pack();
// this.window.setVisible(true);
// this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
// this.window.addKeyListener(this);
// // DO NOT CHANGE the window, content, and paintbrush lines above
// // TODO: add anything else you might need (e.g., a couple of Bird
// // objects, some walls)
//
// // DO NOT CHANGE the next two lines nor add lines after them
// this.gameTimer = new Timer(20, this); // tick at 1000/20 fps
// this.gameTimer.start(); // and start ticking now
// // DO NOT CHANGE the previous two lines nor add lines after them
// }
//
// /**
// * This method gets called each time a player presses a key. You can find
// * out what key the pressed by comparing event.getKeyCode() with
// * KeyEvent.VK_...
// */
// public void keyPressed(KeyEvent event) {
//
// // TODO: handle the keys you want to use to run your game
// if (event.getKeyCode() == KeyEvent.VK_S) { // example
// System.out.println("The 's' key was pressed");
// b1.moveRight();
// }
// if (event.getKeyCode() == KeyEvent.VK_A) { // example
// System.out.println("The 'a' key was pressed");
// b1.moveLeft();
// }
// if (event.getKeyCode() == KeyEvent.VK_K) { // example
// System.out.println("The 'k' key was pressed");
// b2.moveLeft();
// }
// if (event.getKeyCode() == KeyEvent.VK_L) { // example
// System.out.println("The 'l' key was pressed");
// b2.moveRight();
// }
// }
//
// /**
// * Java will call this every time the gameTimer ticks (50 times a second).
// * If you want to stop the game, invoke this.gameTimer.stop() in this
// * method.
// */
// public void actionPerformed(ActionEvent event) {
// // DO NOT CHANGE the next four lines, and add nothing above them
// if (!this.window.isValid()) { // the "close window" button
// this.gameTimer.stop(); // should stop the timer
// return; // and stop doing anything else
// }
// // DO NOT CHANGE the previous four lines
//
// // TODO: add every-frame logic in here (gravity, momentum, collisions,
// // etc)
// b1.elapsedTime();
// b1.tick();
// b1.applyGravity();
// b1.applyDrag(.005);
// b2.elapsedTime();
// b2.tick();
// b2.applyGravity();
// b2.applyDrag(.005);
// b1.movePosition();
// b2.movePosition();
// b1.bounceIfOutsideOf(rect, .5);
// b2.bounceIfOutsideOf(rect, .5);
// b1.collidesWith(b2);
// b2.collidesWith(b1);
//
// if (b1.getX() > b2.getX()) {
// b1.setPos(3);
// b2.setPos(0);
// } else if (b1.getX() < b2.getX()) {
// b1.setPos(0);
// b2.setPos(3);
// }
//
// Random randX = new Random();
// int newX = randX.nextInt(800);
// // Make spawn locations random
// if (b1.collidesWith(b2)) {
// double coordinates = Math.abs(b1.getY() - b2.getY());
// if (coordinates < 35) {
// double repel1 = b1.getVX() * -1;
// double repel2 = b2.getVX() * -1;
// b1.setVX(repel1);
// b2.setVX(repel2);
// } else if (b1.getY() > b2.getY()) {
// b1.setX(newX);
// b1.setY(10);
// score.incrementScore2();
// } else if (b1.getY() < b2.getY()) {
// b2.setX(newX);
// b2.setY(10);
// score.incrementScore1();
// }
// }
//
// if (b1.collidesWith2(wall)) {
// b1.bounceIfInsideOf(wall, .5);
// }
// if (b1.collidesWith2(wall2)) {
// b1.bounceIfInsideOf(wall2, .5);
// }
// if (b2.collidesWith2(wall)) {
// b2.bounceIfInsideOf(wall, .5);
// }
// if (b2.collidesWith2(wall2)) {
// b2.bounceIfInsideOf(wall2, .5);
// }
// // DO NOT CHANGE the next line; it must be last in this method
// this.refreshScreen(); // redraws the screen after things move
// // DO NOT CHANGE the above line; it must be last in this method
// }
//
// /**
// * Re-draws the screen. You should erase the old image and draw a new one,
// * but you should not change anything in this method (use actionPerformed
// * instead if you need something to change).
// */
// private void refreshScreen() {
// this.paintbrush.fill(rect);
// this.paintbrush.setColor(new Color(150, 210, 255)); // pale blue
// this.paintbrush.fillRect(0, 0, this.content.getWidth(),
// this.content.getHeight()); // erases the previous frame
//
// // TODO: replace the following example code with code that does
// // the right thing (i.e., draw the birds, walls, and score)
//
// // example bird drawing; replace with something sensible instead of
// // making a new bird every frame
// b1.draw(this.paintbrush);
// b2.draw(this.paintbrush);
// // example wall drawing; replace with something sensible instead of
// // making a new wall every frame
//
// this.paintbrush.setColor(Color.orange);
// this.paintbrush.fill(wall);
// this.paintbrush.setColor(Color.black);
// this.paintbrush.fill(wall2);
//
// // example text drawing, for scores and/or other messages
// Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
// this.paintbrush.setFont(f);
// this.paintbrush.setColor(new Color(127, 0, 0)); // dark red
// this.paintbrush.drawString("" + score.getScore1(), 30, 30);
// this.paintbrush.setColor(new Color(0, 127, 0)); // dark green
// this.paintbrush.drawString("" + score.getScore2(), 760, 30);
// String msg = "boi";
// f = new Font(Font.SANS_SERIF, Font.BOLD, 90);
// Rectangle2D r = f.getStringBounds(msg,
// this.paintbrush.getFontRenderContext());
// this.paintbrush.setFont(f);
// this.paintbrush.setColor(Color.WHITE);
// this.paintbrush.drawString(msg, 400 - (int) r.getWidth() / 2, 300);
//
// // DO NOT CHANGE the next line; it must be last in this method
// this.window.repaint(); // displays the frame to the screen
// // DO NOT CHANGE the above line; it must be last in this method
// }
// }
//
