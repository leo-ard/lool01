import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComponent;

public class GameInterface extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static byte LAST_POSITION;

	/**
	 * in MainMenu
	 * 
	 * 0:mainMenu
	 * 1:voulez vous vraiment quitter
	 * 
	 * in options
	 * 
	 * 0:commande
	 * 1:interface
	 * 2:son
	 * 
	 * in personnalisation
	 * 
	 * 0: personnalisation
	 * 1:pas assez de point pop up
	 * 2:am√©liorations
	 * 3:explication points
	 */
	private byte popUp;
	
	/**
	 * 0: main menu
	 * 1: options
	 * 2: Personalisation
	 * 
	 */
	private byte position;
	private int r = 30;
	
	//MAIN MENU
	ArrayList<Boutton> MainMenuBoutton;
	ArrayList<Boutton> ouiNonMainMenu;
	ArrayList<Boutton> ouiNonMenu;
	private ArrayList<Boutton> commingSoon;
	
	//OPTION
	private ArrayList<Boutton> optionBoutton;
	private ArrayList<Boutton> optionCommand;
	private ArrayList<Boutton> optionAffichage;
	
	//PERSONNALISATION
	private ArrayList<Boutton> personnalisationBoutton;
	
	//GAME OVER
	private ArrayList<Boutton> gameOverBouttons;
	
	private String  FontName;
	
	private Color color1 = Color.red;
	private Color color2 = new Color(255, 75, 75);
	
	FontMetrics fm;

	private ArrayList<Boutton> pauseBouttons;
	
	public static boolean isInAnimation = false;
	private int alphaAnimation = 0;
	public static boolean changed = true;
	
	public GameInterface(String FN){
		FontName = FN;
		
		setBouttons();
	}
	
	public void setBouttons(){
		//MAIN MENU
		MainMenuBoutton = new ArrayList<Boutton>();
		MainMenuBoutton.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2+150, 200, 30,r,1, "Personnaliser", new Font(FontName, 20,20), Color.white, color1, 0, 2, 0, 0));
		MainMenuBoutton.add(new Boutton(GamePane.WIDTH-210/2, GamePane.HEIGHT-40/2, 200, 30,r,1, "Option", new Font(FontName, 20,20), Color.white, color1, 1, 0, 0, 0));
		MainMenuBoutton.add(new Boutton(210/2, GamePane.HEIGHT-40/2, 200, 30,r,500000, "Mode : Survie", new Font(FontName, 20,20), Color.white, color1, 0, 0));
		MainMenuBoutton.add(new Boutton(210/2, 40/2, 200, 30,r,1, "Quitter le jeu", new Font(FontName, 20,20), Color.white, color1, 0, 1, 0, 0));
		MainMenuBoutton.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2, 180, 180,r,0, "Commencer", new Font(FontName, 20,20), Color.white, color1, 50, -75));
		MainMenuBoutton.add(new Boutton(GamePane.WIDTH/4*3, GamePane.HEIGHT/2+60, 140, 20,r,10, "", new Font(FontName, 20,20), Color.white, Color.white, 4, 0));
		MainMenuBoutton.add(new Boutton(GamePane.WIDTH/4*3, GamePane.HEIGHT/2-60, 140, 20,r,11, "", new Font(FontName, 20,20), Color.white, Color.white, 3, 0));
		
		ouiNonMainMenu = new ArrayList<Boutton>();
		ouiNonMainMenu.add(new Boutton(GamePane.WIDTH/2-720/4, GamePane.HEIGHT/2+25, 720/2-5, 40,r-15,2, "Oui", new Font(FontName, 20,20), Color.black, color2, 0, 0));
		ouiNonMainMenu.add(new Boutton(GamePane.WIDTH/2+720/4, GamePane.HEIGHT/2+25, 720/2-5, 40,r-15,1, "non", new Font(FontName, 20,20), Color.black, color2, 0, 0, 0, 0));
		
		commingSoon = new ArrayList<Boutton>();
		commingSoon.add(new Boutton(GamePane.WIDTH/2+720/2-720/8, GamePane.HEIGHT/2+50, 720/4, 30,r-15,1, "ok", new Font(FontName, 20,20), Color.black, color2, 0, 0, 0, 0));
		
		//OPTION MENU
		optionBoutton = new ArrayList<Boutton>();
		optionBoutton.add(new Boutton(GamePane.WIDTH/2+330, GamePane.HEIGHT/2-215, 40, 30,r-15,3, "", new Font(FontName, 20,20), Color.black, Color.white, 2, 0));
		optionBoutton.add(new Boutton(GamePane.WIDTH/2-330, GamePane.HEIGHT/2-215, 40, 30,r-15,4, "", new Font(FontName, 20,20), Color.black, Color.white, 1, 0));
		optionBoutton.add(new Boutton(GamePane.WIDTH/2-330, GamePane.HEIGHT/2-275, 50, 40,r-15,6, "Retour", new Font(FontName, Font.PLAIN, 8), Color.black, Color.white, 1, -13));
		
		optionCommand = new ArrayList<Boutton>();
		optionCommand.add(new Boutton(GamePane.WIDTH/2, 0, getFontMetrics(new Font(FontName, Font.PLAIN, 17)).stringWidth("Mode du clavier : WASD"), getFontMetrics(new Font(FontName, Font.PLAIN, 17)).getHeight(),r, 5, (GamePane.isWASDMode)?"Mode du clavier : WASD":"Mode du clavier : ZQSD", new Font(FontName, Font.PLAIN, 17), Color.black, Color.black, 50 , 0));
		
		optionAffichage = new ArrayList<Boutton>();
		optionAffichage.add(new Boutton(GamePane.WIDTH/2, 0, getFontMetrics(new Font(FontName, Font.PLAIN, 17)).stringWidth("R√©solutions : "+GamePane.WIDTH+"x"+GamePane.HEIGHT), getFontMetrics(new Font(FontName, Font.PLAIN, 17)).getHeight(),r, 5, (GamePane.isWASDMode)?"Mode du clavier : WASD":"Mode du clavier : ZQSD", new Font(FontName, Font.PLAIN, 17), Color.black, Color.black, 50 , 0));
		
		//PERSONNALISATION MENU
		personnalisationBoutton = new ArrayList<Boutton>();
		personnalisationBoutton.add(new Boutton(GamePane.WIDTH/2-500, GamePane.HEIGHT/2-275, 50, 40,r-15,6, "Retour", new Font(FontName, Font.PLAIN, 8), Color.black, Color.white, 1, -13));
		
		//PAUSE MENU
		pauseBouttons = new ArrayList<Boutton>();
		pauseBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2-45, 720, 35,r-15,7, "Retour au jeu", new Font(FontName, 20,20), Color.black, color2, 0, 0));
		pauseBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2, 720, 35,r-15,1, "Option", new Font(FontName, 20,20), Color.black, color2, 1, 0, 0, 0));
		pauseBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2+45, 720, 35,r-15,1, "Retour au menu", new Font(FontName, 20,20), Color.black, color2, 0, 2, 0, 0));
		pauseBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2+90, 720, 35,r-15,1, "Quitter le jeu", new Font(FontName, 20,20), Color.black, color2, 0, 1, 0, 0));
	
		//GAME OVER MENU
		gameOverBouttons = new ArrayList<Boutton>();
		gameOverBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2, 720, 35,r-15,9, "Rejouer", new Font(FontName, 20,20), Color.black, color2, 0, 0));
		gameOverBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2+45, 720, 35,r-15,1, "Retour au menu", new Font(FontName, 20,20), Color.black, color2, 0, 2, 0, 0));
		gameOverBouttons.add(new Boutton(GamePane.WIDTH/2, GamePane.HEIGHT/2+90, 720, 35,r-15,1, "Quitter le jeu", new Font(FontName, 20,20), Color.black, color2, 0, 1, 0, 0));
	
		//Verrifit menu
		ouiNonMenu = new ArrayList<Boutton>();
		ouiNonMenu.add(new Boutton(GamePane.WIDTH/2-720/4, GamePane.HEIGHT/2+25, 720/2-5, 40,r-15,8, "Oui", new Font(FontName, 20,20), Color.black, color2, 0, 0));
		ouiNonMenu.add(new Boutton(GamePane.WIDTH/2+720/4, GamePane.HEIGHT/2+25, 720/2-5, 40,r-15,1, "non", new Font(FontName, 20,20), Color.black, color2, 0, 0, 0, 0));
		
	}
	
	
	/**
	 * 0:MainMenu
	 * 1:option
	 * 2:personnalisation
	 * 
	 * @param num
	 */
	public void draw(Graphics2D g){
		if(GamePane.isMainMenu == true){
			g.setColor(Color.white);
			g.fillRect(0, 0, GamePane.WIDTH, GamePane.HEIGHT);
			
			
			if(position==0){
				drawMainMenu(g);
				if(popUp == 1){
					//this.startAnimation(175, 10);
					g.setColor(new Color(255,255,255,175));
					g.fillRect(0, 0, GamePane.WIDTH, GamePane.HEIGHT);
					g.setFont(new Font(FontName, 50,50));
					fm = getFontMetrics(new Font(FontName, 50,50));
					this.drawVerifitQuitte(g, false);
				}
				if(popUp == 2){
					//this.startAnimation(175, 10);
					g.setColor(new Color(255,255,255,175));
					g.fillRect(0, 0, GamePane.WIDTH, GamePane.HEIGHT);
					g.setFont(new Font(FontName, 50,50));
					fm = getFontMetrics(new Font(FontName, 50,50));
					this.drawCommingSoon(g);
				}
			}
			else if(position == 1){
				drawOptionMenu(g);
			}
			else{
				drawPersonnalisationMenu(g);
			}
		}
		else if(GamePane.gameOver){
			g.setColor(new Color(0,0,0,175));
			g.fillRect(0, 0, GamePane.WIDTH, GamePane.HEIGHT);
			if(position == 0){
				if(popUp == 1){
					g.setFont(new Font(FontName, 50, 50));
					fm =  getFontMetrics(new Font(FontName, 50, 50));
					this.drawVerifitQuitte(g, false);
				}
				else if(popUp == 2){
					g.setFont(new Font(FontName, 50, 50));
					fm =  getFontMetrics(new Font(FontName, 50, 50));
					this.drawVerifitMenu(g, false);
				}
				else
					drawGameOverMenu(g);
			}
			
		}
		else if(GamePane.IsInPauseMenu){
			g.setColor(new Color(255,255,255,175));
			g.fillRect(0, 0, GamePane.WIDTH, GamePane.HEIGHT);
			if(position == 0){
				if(popUp == 1){
					g.setFont(new Font(FontName, 50, 50));
					fm =  getFontMetrics(new Font(FontName, 50, 50));
					this.drawVerifitQuitte(g, true);
				}
				else if(popUp == 2){
					g.setFont(new Font(FontName, 50, 50));
					fm =  getFontMetrics(new Font(FontName, 50, 50));
					this.drawVerifitMenu(g, true);
				}
				else
					this.drawPauseMenu(g);
			}
			else if(position == 1){
				this.drawOptionMenu(g);
			}
		}
	}
	
	public void startAnimation(int alpha, int rapidity){

		if(!GameInterface.isInAnimation&&changed){
			alphaAnimation = 0;
		}
		if(changed){
			isInAnimation = true;
			alphaAnimation += rapidity;
			
			if(alphaAnimation>=alpha){
				alphaAnimation = alpha;
				changed = false;
				isInAnimation = false;
			}
		}
	}
	
	private void drawMapsBox(Graphics2D g){
		BufferedImage img = Map.getTheChosenOne().getImage();
		
		double ratio = ((double)(img.getHeight())/(double)img.getWidth());
		int w = 140;
		int h = (int)((double)(140.0)*(double)(ratio));
		
		this.drawFenetre(g, GamePane.WIDTH/4*3, GamePane.HEIGHT/2, 160, h+80, "", Color.white, color1, fm, 0);
		this.MainMenuBoutton.get(5).setXNY(GamePane.WIDTH/4*3-this.MainMenuBoutton.get(5).getWidth()/2, GamePane.HEIGHT/2+(h/2+40-30));
		this.MainMenuBoutton.get(6).setXNY(GamePane.WIDTH/4*3-this.MainMenuBoutton.get(6).getWidth()/2, GamePane.HEIGHT/2-(h/2+40-10));
		
		g.drawImage(img, GamePane.WIDTH/4*3-w/2, GamePane.HEIGHT/2-h/2, w, h, null);
		g.setColor(new Color(255, 255, 255, 175));
		g.fillRect(GamePane.WIDTH/4*3-w/2, GamePane.HEIGHT/2-h/2, w, h);
		g.setFont(new Font(FontName,10,10));
		fm = getFontMetrics(new Font(FontName,10,10));
		g.setColor(Color.black);
		g.drawString(Map.getTheChosenOne().getName()+" "+Map.getTheChosenOne().getDificulty(),GamePane.WIDTH/4*3-fm.stringWidth(Map.getTheChosenOne().getName()+" "+Map.getTheChosenOne().getDificulty())/2, GamePane.HEIGHT/2-fm.getHeight()/2);
	}
	
	private void drawCommingSoon(Graphics2D g) {
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-20, 720, 90, "¿ venir", Color.BLACK,color1, fm, 10);
		for(int i = 0; i<commingSoon.size();i++){
			commingSoon.get(i).draw(g);
		}
		
		//UPDATE
		for(int i = 0; i<commingSoon.size();i++){
			commingSoon.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
		}
		
	}
	
	private void drawPersonnalisationMenu(Graphics2D g) {
		// TODO Personnalisation Menu
		//DRAW

		g.setFont(new Font(FontName, 50,50));
		fm = this.getFontMetrics(new Font(FontName, 50,50));
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-275, 1060, 60, "Personnalisation", Color.black, color1, fm,0);
		
		g.setFont(new Font(FontName, 30,30));
		fm = this.getFontMetrics(new Font(FontName, 30,30));
		this.drawFenetre(g, GamePane.WIDTH/2-396, GamePane.HEIGHT/2-175, 265, 40, "AbiletÈ", Color.white, color2, fm, 0);
		this.drawFenetre(g, GamePane.WIDTH/2+396, GamePane.HEIGHT/2-175, 265, 40, "Points", Color.white, color2, fm, 0);
		this.drawFenetre(g, GamePane.WIDTH/2+396, GamePane.HEIGHT/2+100, 265, 60, "AmÈlioration", Color.white, color2, fm, 10, -25);
		
		
		g.setFont(new Font(FontName, 20,20));
		fm = this.getFontMetrics(new Font(FontName, 20,20));
		this.drawFenetre(g, GamePane.WIDTH/2-396, GamePane.HEIGHT/2-50, 265, 190, "Tir FixÈ", Color.white, color2, fm, 0);
		this.drawFenetre(g, GamePane.WIDTH/2+396, GamePane.HEIGHT/2-130, 265, 30, ""+GamePane.POINTS, Color.white, color2, fm, 2);
		
		GamePane.player.drawForMenu(g, 10);
		
		for(int i = 0; i < personnalisationBoutton.size(); i++){
			//DRAW 
			personnalisationBoutton.get(i).draw(g);
			
			//UPDATE
			personnalisationBoutton.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
		}
	}


	private void drawOptionMenu(Graphics2D g) {
		//DRAW
		g.setFont(new Font(FontName, 50,50));
		fm = this.getFontMetrics(new Font(FontName, 50,50));
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-275, 720, 60, "Option", Color.black, color1, fm,0);
		//DRAW
		if(popUp != -1){ //TODO change this was == 0
			fm = getFontMetrics(new Font(FontName, Font.PLAIN, 30));
			g.setFont(new Font(FontName, Font.PLAIN, 30));
			
			this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-215, 720, 40, "Commandes", Color.black, color2, fm,0);
			this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2+55, 720, 480, "", Color.black, color2, fm,0);
			
			int acc = 0;
			int yDep = GamePane.HEIGHT/2-155;
			for(int i = 0; i < this.optionCommand.size(); i++){
				optionCommand.get(i).setTextXNY(optionCommand.get(i).getTextX(), yDep+acc);
				optionCommand.get(i).setXNY(optionCommand.get(i).getX(), yDep+acc-optionCommand.get(i).getHeight());
				acc += optionCommand.get(i).getHeight()+10;
				optionCommand.get(i).draw(g);
				
				//UPDATE
				optionCommand.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
			}
		}
		else if(popUp == 1){
			fm = getFontMetrics(new Font(FontName, Font.PLAIN, 30));
			g.setFont(new Font(FontName, Font.PLAIN, 30));
			
			this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-215, 720, 40, "Interface", Color.black, color2, fm,0);
			this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2+55, 720, 480, "", Color.black, color2, fm,0);
			
			int acc = 0;
			int yDep = GamePane.HEIGHT/2-155;
			for(int i = 0; i < this.optionAffichage.size(); i++){
				optionAffichage.get(i).setTextXNY(optionAffichage.get(i).getTextX(), yDep+acc);
				optionAffichage.get(i).setXNY(optionAffichage.get(i).getX(), yDep+acc-optionAffichage.get(i).getHeight());
				acc += optionAffichage.get(i).getHeight()+10;
				optionAffichage.get(i).draw(g);
				
				//UPDATE
				optionAffichage.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
			}
		}
		else if(popUp == 2){
			fm = getFontMetrics(new Font(FontName, Font.PLAIN, 30));
			g.setFont(new Font(FontName, Font.PLAIN, 30));
			
			this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-215, 720, 40, "Son", Color.black, color2, fm,0);
			this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2+55, 720, 480, "", Color.black, color2, fm,0);
			//TODO change this
		}
		for(int i = 0; i < optionBoutton.size();i++){
			optionBoutton.get(i).draw(g);
		}
		//UPDATE
		for(int i = 0; i < optionBoutton.size();i++){
			optionBoutton.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
		}
	}


	//PS: it's draw and update
	private void drawMainMenu(Graphics2D g){
		//DRAW
		GamePane.player.drawForMenu(g, 6);
		
		
		
		fm = this.getFontMetrics(new Font(FontName, 50, 50));
		g.setFont(new Font(FontName, 50, 50));
		g.setColor(Color.black);
		
		this.drawMapsBox(g);
		
		for(int i = 0; i<MainMenuBoutton.size();i++){
			MainMenuBoutton.get(i).draw(g);
		}
		//UPDATE
		if(popUp == 0){
			for(int i = 0; i<MainMenuBoutton.size();i++){
				MainMenuBoutton.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
			}
		}
	}
	
	private void drawGameOverMenu(Graphics2D g){
		//DRAW
		g.setFont(new Font(FontName, Font.PLAIN, 50));
		fm = getFontMetrics(new Font(FontName, Font.PLAIN, 50));
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-80, 720, 100, "Game Over", Color.black, color1, fm, 0);
		
		g.setColor(Color.black);
		g.setFont(new Font(FontName, Font.PLAIN, 15));
		fm = getFontMetrics(new Font(FontName, Font.PLAIN, 15)); 
		g.drawString("Vous vous Ítes rendu ‡† la manche "+GamePane.wave, GamePane.WIDTH/2-fm.stringWidth("Vous vous Ítes rendu ‡† la manche "+GamePane.wave)/2, GamePane.HEIGHT/2-45);
		
		for(int i = 0; i < gameOverBouttons.size();i++){
			//DRAW
			gameOverBouttons.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
			
			//UPDATE
			gameOverBouttons.get(i).draw(g);
		}
	}
	
	private void drawPauseMenu(Graphics2D g){
		g.setFont(new Font(FontName, Font.PLAIN, 50));
		fm = getFontMetrics(new Font(FontName, Font.PLAIN, 50));
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-105, 720, 60, "Pause", Color.black, color1, fm, 0);
		
		for(int i = 0; i < pauseBouttons.size(); i++){
			//DRAW
			pauseBouttons.get(i).draw(g);
			
			//UPDATE
			pauseBouttons.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
		}
	}
	
	private void drawVerifitQuitte(Graphics2D g, boolean show){
		//DRAW
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-50, 720, (show)?110:90, "Voulez-vous quitter le jeu?", Color.BLACK,color1, fm, 10);
		g.setFont(new Font(FontName, 15, 15));
		fm = getFontMetrics(new Font(FontName, 15, 15));
		if(show)
		g.drawString("Toute progression non sauvegard√© sera perdu", GamePane.WIDTH/2-fm.stringWidth("Toute progression non sauvegardÈ sera perdu")/2, GamePane.HEIGHT/2-20);
		
		
		for(int i = 0; i<ouiNonMainMenu.size();i++){
			//DRAW
			if(show){
				ouiNonMainMenu.get(i).setXNY(ouiNonMainMenu.get(i).getX(), ouiNonMainMenu.get(i).getY()+20);
				ouiNonMainMenu.get(i).setTextXNY(ouiNonMainMenu.get(i).getTextX(), ouiNonMainMenu.get(i).getTextY()+20);
			}
			ouiNonMainMenu.get(i).draw(g);
			
			//UPDATE
			ouiNonMainMenu.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
			
			if(show){
				ouiNonMainMenu.get(i).setTextXNY(ouiNonMainMenu.get(i).getTextX(), ouiNonMainMenu.get(i).getTextY()-20);
				ouiNonMainMenu.get(i).setXNY(ouiNonMainMenu.get(i).getX(), ouiNonMainMenu.get(i).getY()-20);
			}
		}	
			
	}
	private void drawVerifitMenu(Graphics2D g, boolean show){
		//DRAW
		this.drawFenetre(g, GamePane.WIDTH/2, GamePane.HEIGHT/2-50, 720, (show)?110:90, "Voulez-vous aller au menu ?", Color.BLACK,color1, fm, 10);
		g.setFont(new Font(FontName, 15, 15));
		fm = getFontMetrics(new Font(FontName, 15, 15));
		if(show)
		g.drawString("Toute progression non sauvegard√© sera perdu", GamePane.WIDTH/2-fm.stringWidth("Toute progression non sauvegardÈ sera perdu")/2, GamePane.HEIGHT/2-20);
		
		
		for(int i = 0; i<ouiNonMenu.size();i++){
			//DRAW
			if(show){
				ouiNonMenu.get(i).setXNY(ouiNonMenu.get(i).getX(), ouiNonMenu.get(i).getY()+20);
				ouiNonMenu.get(i).setTextXNY(ouiNonMenu.get(i).getTextX(), ouiNonMenu.get(i).getTextY()+20);
			}
			ouiNonMenu.get(i).draw(g);
			
			//UPDATE
			ouiNonMenu.get(i).update(GamePane.mouseX, GamePane.mouseY, GamePane.RIGHT_CLICK);
			
			if(show){
				ouiNonMenu.get(i).setTextXNY(ouiNonMenu.get(i).getTextX(), ouiNonMenu.get(i).getTextY()-20);
				ouiNonMenu.get(i).setXNY(ouiNonMenu.get(i).getX(), ouiNonMenu.get(i).getY()-20);
			}
		}	
	}
	
	public void drawFenetre(Graphics2D g,int x, int y, int w, int h, String t, Color tc, Color c, FontMetrics fm, int cor){
		int xPos = x-w/2;
		int yPos = y-h/2;
		
		g.setColor(c);
		g.fillRoundRect(xPos, yPos, w, h, r, r);
		g.setColor(tc);
		
		g.drawString(t, xPos+w/2-fm.stringWidth(t)/2, yPos+h-(h-fm.getHeight())-fm.getDescent()+cor); ;//+h/2-fm.getHeight()+h);
		
	}
	
	public void drawFenetre(Graphics2D g,int x, int y, int w, int h, String t, Color tc, Color c, FontMetrics fm, int cor, int corX){
		int xPos = x-w/2;
		int yPos = y-h/2;
		
		g.setColor(c);
		g.fillRoundRect(xPos, yPos, w, h, r, r);
		g.setColor(tc);
		
		g.drawString(t, xPos+w/2-fm.stringWidth(t)/2+corX, yPos+h-(h-fm.getHeight())-fm.getDescent()+cor); ;//+h/2-fm.getHeight()+h);
		
	}
	
	
	public void setPopUp(byte b){popUp = b;GameInterface.changed = true;};
	public void setPosition(byte b){
		
		popUp = 0;
		LAST_POSITION = position;
		position = b;
	};
	
	public byte getPosition(){return position;};
	public byte getPopUp(){return popUp;};

}
