package Main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Prototype Game");							//Bennenung des Fenster		// !! !!
		window.setContentPane(new GamePanel());								//Inhalt des Fensters = Inhalt GamePanels
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				//Was passiert bei Exit?
		window.setResizable(false);											//Veränderung der Größe nicht möglich
		window.pack();
		window.setVisible(true); 											//Fenster sichtbar
		
	}
}
