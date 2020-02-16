package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {
		
	//Größe des Fensters
	public static final int WIDTH = 320;			//Verhältnis 4:3
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	//game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	//image
	private BufferedImage image;
	private Graphics2D g;
	
	//GameStateManager
	private GameStateManager gsm;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); 
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	public void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//Gameloop
		while(running) {
			
			System.out.println("Game running");
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 10000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//Inhalt darstellen und updaten
	private void update() {									//Weiterverarbeitung im GameStateManager
		gsm.update();
	}
	
	private void draw() {									//Weiterverarbeitung im GameStateManager
		gsm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}
	
	//Tasteneingabe und Verarbeitung
	public void keyTyped(KeyEvent key) {
		
	}
	
	public void keyPressed(KeyEvent key) {					//Weiterverarbeitung im GameStateManager
		gsm.keyPressed(key.getKeyCode());
	}
	
	public void keyReleased(KeyEvent key) {					//Weiterverarbeitung im GameStateManager
		gsm.keyReleased(key.getKeyCode());
	}
	
}
