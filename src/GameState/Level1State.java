package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	
	// EVENT
	private boolean blockInput = false;
	private boolean eventDead;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		// title and subtitle
		try {
			hageonText = ImageIO.read(
				getClass().getResourceAsStream("/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 20, 82, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/cloudtileset.gif");
		tileMap.loadMap("/Maps/level1-B.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/mountainbg.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(7030, 215);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
	}
	
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Point[] points = new Point[] {
			new Point(1150, 200),
			new Point(1400, 200),
			new Point(1850, 200),
			new Point(2020, 200),
			new Point(2150, 200),
			new Point(3050, 200),
			new Point(3250, 200),
			new Point(4000, 200),
			new Point(5100, 200),
			new Point(6300, 200),
			new Point(6500, 200)
			
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}
	
	public void update() {
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// finished game
		if((player.getx() == 7050 || player.getx() == 7075) && player.gety() >= 200) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		
		// check if player dead event should start
		if(player.getHealth() == 0 || player.gety() >= 255) {
			eventDead = blockInput = true;
		}
				
		// play events
		if(eventDead) eventDead();
				
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
	}
	
	////////// KEY INPUT //////////
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_UP) player.setJumping(true);
		if(k == KeyEvent.VK_SPACE) player.setGliding(true);
		if(k == KeyEvent.VK_A) player.setScratching();
		if(k == KeyEvent.VK_D) player.setFiring();
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_UP) player.setJumping(false);
		if(k == KeyEvent.VK_SPACE) player.setGliding(false);
	}
	
	
	////////////// EVENT ////////////
	
	// reset level
		private void reset() {
			player.reset();
			populateEnemies();
			blockInput = false;
			if(player.getx() >= 3355) {
				player.setPosition(3355, 100);
			}
			else {
				player.setPosition(255,215);
			}
		}
		
	// player has died
		private void eventDead() {
			player.setDead();
			player.stop();
			eventDead = blockInput = false;
			reset();
		}	
	
}












