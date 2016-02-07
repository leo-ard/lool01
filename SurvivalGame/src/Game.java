import java.awt.Dimension;

import javax.swing.JFrame;

public class Game{
	public static JFrame windows;
	public static GameSave gameSave;
	
	public static void main(String[] args){
		windows = new JFrame();
		windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windows.setResizable(true);
		windows.setMinimumSize(new Dimension(760,650));
		
		gameSave = new GameSave();
		gameSave.load();
		GamePane.WIDTH = (int) gameSave.get("width");
		GamePane.HEIGHT = (int) gameSave.get("height");
		
		windows.setContentPane(new GamePane());
		
		windows.pack();
		windows.setLocationRelativeTo(null);
		windows.setVisible(true);
	}

}
