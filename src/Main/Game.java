package Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Cloud Runner");							//Bennenung des Fenster		// !! !!
		window.setContentPane(new GamePanel());								//Inhalt des Fensters = Inhalt GamePanels
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				//Was passiert bei Exit?
		window.setResizable(false);											//Veränderung der Größe nicht möglich
		window.pack();
		window.setVisible(true); 											//Fenster sichtbar
		window.setIconImage(createImage("/HUD/icon.gif").getImage());
		
	}
	
	public static ImageIcon createImage(String path) {
		return new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getClass().getResource(path));
	}
		
}
