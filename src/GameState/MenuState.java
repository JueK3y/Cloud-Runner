package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

import TileMap.Background;

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0;
	//Inhalt des Menüs
	private String[] options = {
			
			"Start",
			"Quit"
	};
	
	//Design Einstellungen
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font creditFont;
	private Color creditColor;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menumountainbg.gif", 1);				// Pfad, Scale
			
			titleColor = new Color(128, 0, 0);								//Hintergrundfarbe
			titleFont = new Font("Times New Roman", Font.PLAIN, 29);		//Menü-Font
			
			font = new Font("Arial", Font.PLAIN, 14);
			creditFont = new Font("Century Gothic", Font.PLAIN, 10);
			
		}
		catch(Exception e) {
			e.printStackTrace();			
		}		
	}
	
	public void init() {
		
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(Graphics2D g) {
		
		//draw bg
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Cloud Runner", 80, 55);
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], 144, 134 + i * 19);
		}
		
		//draw credits
		g.setFont(creditFont);
		g.setColor(creditColor = new Color(170, 170, 170));
		g.drawString("© 2020  J. Kennedy", 10, 260);
		
		
	}
	
	private void select() {
		if(currentChoice == 0) {										//Start
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {										//Beenden
			System.exit(0);
		}
	}
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}

}
