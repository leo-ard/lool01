import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GamePane extends JPanel implements Runnable, KeyListener, MouseListener, MouseWheelListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// FIELDS
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	private Thread thread;
	public static boolean running;
	
	private BufferedImage image;
	private Graphics2D gUI;
	private Graphics2D gGame;
	
	private int FPS = 30;
	private double averageFPS;
	
	public static Map map;
	
	public static Player player;
	public static ArrayList<Projectile> projectiles;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Explosion> explosions;
	public static ArrayList<Item> items;
	
	public static int mouseX = 0;
	public static int mouseY = 0;
	
	private boolean pauseWave;
	public static int wave;
	private int alphaWave;
	private int waveTimer;
	private int waveDelay;
	
	private boolean spawning;
	private int nbSpawned;
	private int nbToSpawn;
	
	static boolean gameOver = true;
	Boutton replay;
	
	Font f = new Font("Ubuntu", Font.PLAIN, 12);
	private int margeX;
	private int margeY;
	private boolean isInGame = false;
	
	public static boolean restart;
	public static boolean LEFT_CLICK;
	public static boolean RIGHT_CLICK;
	public static boolean CLICK_FOR_BUTTONS;
	public static boolean W;
	public static boolean A;
	public static boolean S;
	public static boolean D;
	
	public static int viewPosX;
	public static int viewPosY;
	
	//menu
	public static boolean IsInPauseMenu;
	
	public static int maxH = 0;
	public static int maxW = 0;
	
	//keybord
	public static boolean isWASDMode;
	
	//main menu fields
	
	public static boolean isMainMenu = true;
	public static GameInterface gi;
	
	//Données de jeu
	public static int POINTS;
	
	public static final String texturePack = "assets/";
	
	public static musicPlayer mp = new musicPlayer(new String[]{"musique1.mp3","MazeOfMayo.mp3"}, true);
	
	
	// CONSTRUCTEUR
	public GamePane(){
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		
		requestFocus();
	}
	
	//FUNCTIONS 
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
	}
	
	public void startGame(){
		running = true;
		
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		//image2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		gUI = (Graphics2D) image.getGraphics();
		gGame = (Graphics2D) image.getGraphics();
		gUI.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gUI.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		gGame.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gGame.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		gi = new GameInterface(f.getName());
		
		Arme.defaultArme();
		
		gameOver = false;
		
		pauseWave = false;
		waveTimer = 0;
		wave = 0;
		waveDelay = 30;
		
		alphaWave = 0;
		
		IsInPauseMenu = false;
		
		//map
		map = new Map();
		Map.searchAndLoadMaps(GamePane.texturePack+"maps/");
		map = Map.MAPS.get(Map.theChosenMap);
		
		player = new Player();
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		explosions = new ArrayList<Explosion>();
		items = new ArrayList<Item>();
		
		//Game.st.startLine();
		
		this.setLayout(null);
		
		this.setBackground(Color.black);
		
		restart = false;
		mp = new musicPlayer(new String[]{"assets/musiques/musique1.mp3","assets/musiques/MazeOfMayo.mp3"}, true);
		mp.play();
	}

	
	public void run() {	
		
		loadGameFields();
		
		startGame();
		//test
		//enemies.add(new Enemy(0, 0));
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000/FPS;
		
		// GAME LOOP
		while(running){
			
			if(restart == true){
				startGame();
			}
			
			try{
			mouseX = this.getMousePosition().x;
			mouseY = this.getMousePosition().y;
			}catch(NullPointerException e){}
			
			startTime = System.nanoTime();
			
			//----fullscreen scan----// 
			if(margeX==0){
				margeX = Game.windows.getSize().width - GamePane.WIDTH;
				margeY = Game.windows.getSize().height - GamePane.HEIGHT;
			}

			if(Game.windows.getSize().height != HEIGHT+margeY||Game.windows.getSize().width != WIDTH+margeX){
				GamePane.HEIGHT = Game.windows.getSize().height - margeY;
				GamePane.WIDTH = Game.windows.getSize().width - margeX;
				gi.setBouttons();
				image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
				//image2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
				gUI = (Graphics2D) image.getGraphics();
				gGame = (Graphics2D) image.getGraphics();
				
				Game.gameSave.changeEntree("height", GamePane.HEIGHT);
				Game.gameSave.changeEntree("width", GamePane.WIDTH);
			}
			
			
			
			if(isMainMenu){
				isInGame = false;
				if(mp.isRunning()){
					mp.stop();
				}
				drawAndUpdateMenu();
			}
			else if(IsInPauseMenu){
				isInGame = false;
				gameRender();
				drawAndUpdateMenu();
			}
			else if(gameOver){
				isInGame = false;
				mp.stop();
				gameRender();
				drawAndUpdateMenu();
			}
			else{
				isInGame = true;
				gameUpdate();
				gameRender();
			}
			
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			
			waitTime = targetTime - URDTimeMillis;
			if(waitTime <0){waitTime = 0;}
			
			
				
			
			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime / frameCount)/1000000);
				frameCount = 0;
				totalTime = 0;
			}
			
		}
	}
	

	private void loadGameFields() {
		GamePane.isWASDMode = (boolean) Game.gameSave.get("wasdMode");
	}

	private void gameUpdate() {	
		//----Map update----//
		map.update();
		
		
		//----position de la sourie----//
		mouseX+=viewPosX;
		mouseY+=viewPosY;
		
		
		//----arme update----//
		for(int i = 0; i<player.getArmes().size();i++){
			player.getArmes().get(i).update();
		}
		
		//----player update----//
		player.update();
		
		
		//----Item-player collision----//
		for(int i = 0; i < items.size();i++){
			
			Item it = items.get(i);
			
			int d2 = (int)(((player.getX())-(it.getX())) * ((player.getX())-(it.getX())) + ((player.getY())-it.getY()) * ((player.getY())-it.getY()));
			
			if(!(d2 > (player.getWIDTH()/2 + it.getR())*(player.getWIDTH()/2 + it.getR()))){
				items.get(i).use();
				items.remove(i);
				i--;
			}
			
		}
		
		//----projectiles update----//
		for(int i = 0; i < projectiles.size();i++){
			boolean remove = projectiles.get(i).update();
			if(remove){
				projectiles.remove(i);
				i--;
			}
		}
		
		
		
		
		//----projectile-enemy collision----//
		for(int i = 0; i < projectiles.size();i++){
			
			Projectile p = projectiles.get(i); 
			double px = p.getX();
			double py = p.getY();
			double pdx = p.getDx();
			double pdy = p.getDy();
			
			for(int j = 0; j < enemies.size();j++){
				
				Enemy e = enemies.get(j);
				
				double ex = e.getX();
				double ey = e.getY();
				double ew = e.getWIDTH();
				double eh = e.getHEIGHT();
				
				//double dist = Math.sqrt(px*px+py*py);
				if(new Rectangle((int)(ex-ew/2),(int)(ey-eh/2),(int)ew,(int)eh).intersectsLine(px-pdx,py-pdy,px,py)){
					
					if(p.isExplosif()){
						explosions.add(new Explosion((int)(p.getX()),(int)(p.getY()),100,10,10000,10,1000));
					}
					
					boolean im = false;
					e.hit(p.getDomage());
					e.knockback(p.getKnockback(), p.getDx(), p.getDy());
					if(e.isDead()){
						if(Math.random()*100<=1){
							items.add(new Item((int)e.getX(),(int)e.getY(),0));
						}
						else if(Math.random()*100<=5){
							items.add(new Item((int)e.getX(),(int)e.getY(),1,Arme.getRandomArme()));
						}
						enemies.remove(j);
						p.setDurabilite(p.getDurabilite()-1);
						im=true;
					}
					else{
						p.setDurabilite(0);
					}
					
					if(p.getDurabilite() == 0){
						projectiles.remove(i);
						i--;
						im = false;
					}
					if(im){
						i--;
						im=false;
					}
					break;
				}
			}
		}
		
		
		
		
		//----Explosion Update----//
		for(int i = 0; i < explosions.size();i++){
			boolean remove = explosions.get(i).update();
			if(remove){
				explosions.remove(i);
				i--;
			}
		}
		
		//----Explosion-enemy collision----//
		for(int i = 0; i < enemies.size();i++){
			
			Enemy en = enemies.get(i); 
			
			for(int j = 0; j < explosions.size();j++){
				
				Explosion e = explosions.get(j);

				int d2 = (int)(((en.getX())-(e.getX())) * ((en.getX())-(e.getX())) + ((en.getY())-e.getY()) * ((en.getY())-e.getY()));
				
				if(!(d2 > (en.getWIDTH()/2 + e.getR())*(en.getWIDTH()/2 + e.getR()))){
					enemies.get(i).hit(e.dommage);
				}
			}
		}
		
		
		
		//----remove the dead enemies----//
		for(int i = 0; i < enemies.size();i++){
			enemies.get(i).update();
			if(enemies.get(i).isDead()){
				if(Math.random()*100<=3){
					items.add(new Item((int)enemies.get(i).getX(),(int)enemies.get(i).getY(),0));
				}
				else if(Math.random()*100<=10){
					items.add(new Item((int)enemies.get(i).getX(),(int)enemies.get(i).getY(),1,Arme.getRandomArme()));
				}
				
				
				enemies.remove(i);
				i--;
			}
		}
		
		//----check if all the enemies are dead----//
		if(enemies.size() == 0&&!(spawning||pauseWave)){
			//start new wave
			wave++;
			System.out.println("--WAVE "+wave+" --");
			nbToSpawn = (wave*wave)+3;
			pauseWave = true;
			
		}
		
		//----spawning mode----//
		if(spawning){
			
			if(!(nbToSpawn==nbSpawned)){
				enemies.add(new Enemy((int)(Math.random()*2),0));
				nbSpawned++;
			}
			
			else{
				spawning = false;
				nbSpawned = 0;
			}
			
		}
		
		
		
	}
	
	@SuppressWarnings("unused")
	private void gameRender() {	
		gUI.setColor(Color.gray);
		gUI.fillRect(0, 0, WIDTH, HEIGHT);

		
		//gGAME = (Graphics2D) (new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB)).getGraphics();
		//gGAME.setBackground(new Color(0,0,0,255));
		
		
		
		gGame.translate(-viewPosX, -viewPosY);
		
		//----Map----//
		map.draw(gGame);
		
		//----Player----//
		player.draw(gGame);
		
		//----projectiles----//
		for(int i = 0; i < projectiles.size();i++){
			projectiles.get(i).draw(gGame);
		}
		
		//----explosions----//
		for(int i = 0; i < explosions.size();i++){
			explosions.get(i).draw(gGame);
		}
		
		
		//----enemies----//
		for(int i = 0; i < enemies.size();i++){
			enemies.get(i).draw(gGame);
		}
		
		if(pauseWave){
			//             TIME
			if(waveTimer >= waveDelay){
				alphaWave-=6;
			}
			alphaWave+=3;
			
			if(alphaWave<0){alphaWave = 0;};
			if(alphaWave>255){alphaWave = 255; waveTimer++; };
			
			
			gUI.setColor(new Color(0,0,0,alphaWave));
			gUI.setFont(new Font("Impact",20,20));
			gUI.drawString("-  M  A  N  C  H  E    "+wave+"  -", GamePane.WIDTH/2-getFontMetrics(new Font("Impact",20,20)).stringWidth("-  M  A  N  C  H  E    "+wave+"  -")/2, GamePane.HEIGHT/2);
			gUI.setFont(new Font("Arial",12,12));
			
			if(waveTimer==waveDelay&&alphaWave==0){
				waveTimer = 0;
				pauseWave = false;
				spawning = true;
			}
			
		}
		//----Walls----//
		GamePane.map.drawWalls(gGame);
		
		//----arme----//
		player.getEquipedArme().draw(gUI);
		
		
		//----Items----//
		for(int i = 0; items.size()>i;i++){
			items.get(i).draw(gGame);
		}
		
		//----test----//
		gGame.fillRect(mouseX, mouseY, 4, 4);
		
		//----Draw GUI----//
		drawGUI(gUI);
		
		//----draw the hitboxes in red----//
		if(false){
		for(int i = 0; i < GamePane.map.getWalls().size();i++){
			gGame.setColor(Color.red);
			gGame.drawRect(GamePane.map.getWalls().get(i).x-GamePane.map.getX(),GamePane.map.getWalls().get(i).y-GamePane.map.getY(), GamePane.map.getWalls().get(i).width, GamePane.map.getWalls().get(i).height);
		}}
		
		gGame.translate(-gGame.getTransform().getTranslateX(), -gGame.getTransform().getTranslateY());
		//System.out.println(this.projectiles.size());

		gUI.setColor(Color.blue);
		gUI.setFont(new Font("Arial",10,10));
		gUI.drawString(""+ ((int)averageFPS), WIDTH-15, HEIGHT-10);
	}

	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0,0, null);
		//g2.drawImage(image2, viewPosX-WIDTH, viewPosY-HEIGHT, null);
		
		g2.dispose();
	
	}
	
	private void drawAndUpdateMenu(){
		gi.draw(gUI);
	}
	
	//draw the around of game
	private void drawGUI(Graphics2D g){
		
		//----Les armes----//
		
		int w = 100;
		int h = 50;
		int arc = 15;
		int alpha = 50;
		
		//boites
		Color color1 = Color.black;
		//textes
		Color color2 = Color.black;
		
		//distance acumulee
		int hAccumulation = 0;
		//int wAccumulation = 0;
		
		FontMetrics fm;
		
		for(int i = 0; i<GamePane.player.getArmes().size();i++){
			
			Arme a = GamePane.player.getArmes().get(i);
			
			int x = GamePane.WIDTH-w-10-hAccumulation-a.getAnimationW();
			int y = 10;
			
			//Font f = new Font("Impact", Font.PLAIN, 10);
			
			//rebors
			g.setColor(color1);
			g.drawRoundRect(x, y, w+a.getAnimationW(), h+a.getAnimationH(), arc, arc);
			
			//interieur
			g.setColor(new Color(color1.getRed(),color1.getGreen(), color1.getBlue(), alpha));
			g.fillRoundRect(x,y, w+a.getAnimationW(), h+a.getAnimationH(), arc, arc);
			
			//nom
			g.setColor(color2);
			g.setFont(new Font("Impact",Font.PLAIN,12+(int)((double)a.getAnimationW()/(double)(a.getMaxW()/2))));
			fm = getFontMetrics(new Font("Impact", Font.PLAIN, 12+(int)((double)a.getAnimationW()/(double)(a.getMaxW()/2))));
			g.drawString(a.getName(), x+w-fm.stringWidth(a.getName())-4+a.getAnimationW(), y+15);
			
			//type
			g.setFont(new Font("Impact",Font.PLAIN,10+(int)((double)a.getAnimationW()/(double)(a.getMaxW()/2))));
			fm = getFontMetrics(new Font("Impact", Font.PLAIN, 10+(int)((double)a.getAnimationW()/(double)(a.getMaxW()/2))));
			g.drawString(a.getType(), x+w-fm.stringWidth(a.getType())-4+a.getAnimationW(), y+30);
			
			//draw l'amo d'une arme a distance
			if(a.isSelected()&&!a.isMele()){
				g.setColor(new Color(color2.getRed(),color2.getGreen(), color2.getBlue(),(int)(((double)a.getAnimationH()/(double)(a.getMaxH()))*255.0)));
				g.setFont(new Font("Impact",Font.PLAIN,20));
				fm = getFontMetrics(new Font("Impact", Font.PLAIN, 20));
				if(a.isInfiniteAmo()){
					g.drawString(""+a.getBalles()+"/ Inf.", x+(w+a.getAnimationW())/2-fm.stringWidth(""+a.getBalles()+"/ Inf.")/2, y+65);
				}
				else{
					g.drawString(""+a.getBalles()+"/"+a.getMunition(), x+(w+a.getAnimationW())/2-fm.stringWidth(""+a.getBalles()+"/"+a.getMunition())/2, y+65);
				}
			}
			
			//draw la durabilite d'une arme mele
			if(a.isSelected()&&a.isMele()){
				g.setColor(new Color(color2.getRed(),color2.getGreen(), color2.getBlue(),(int)(((double)a.getAnimationH()/(double)(a.getMaxH()))*255.0)));
				g.setFont(new Font("Impact",Font.PLAIN,20));
				fm = getFontMetrics(new Font("Impact", Font.PLAIN, 20));
				if(!a.isInfiniteAmo()){
					g.drawString(""+a.getDurabilite(), x+(w+a.getAnimationW())/2-fm.stringWidth(""+a.getDurabilite())/2, y+65);
				}
			}
			
			hAccumulation+=w+10+a.getAnimationW();
			
		}
		
		//----Barre de vies----//
		
		int x = 20;
		int y = 10;
		int lon = 150;
		int haut = 10;
		int bordure = 2;
		
		//en arri�re de la barre
		Color c1 = Color.LIGHT_GRAY;
		//couleur de la barre (vie cest rouge)
		Color c2 = Color.red;
		//couleur des contours (estetique)
		Color c3 = Color.black;
		
		//bordure
		g.setColor(new Color(c3.getRed(),c3.getGreen(),c3.getBlue(), 150));
		g.setStroke(new BasicStroke(bordure));
		g.drawRect(x, y, lon, haut);
		
		//background de la barre de vie
		g.setColor(new Color(c1.getRed(),c1.getGreen(),c1.getBlue(), 150));
		g.fillRect(x, y, lon, haut);
		
		//barre de vie
		g.setColor(new Color(c2.getRed(),c2.getGreen(),c2.getBlue(), 150));
		g.fillRect(x, y, (int)(((double)player.getVie()/(double)player.getMaxVie())*(double)lon), haut);
		
		//texte de la barre
		g.setFont(new Font(f.getName(), 10,10));
		fm = getFontMetrics(new Font(f.getName(), 10,10));
		g.setColor(Color.black);
		if((lon+y)-((int)(((double)player.getVie()/(double)player.getMaxVie())*(double)lon)+x)<fm.stringWidth(player.getVie()+"/"+player.getMaxVie()))
		g.drawString(player.getVie()+"/"+player.getMaxVie(), (int)(((double)player.getVie()/(double)player.getMaxVie())*(double)lon)-fm.stringWidth(player.getVie()+"/"+player.getMaxVie())+x, haut+y);
		else
			g.drawString(player.getVie()+"/"+player.getMaxVie(), (int)(((double)player.getVie()/(double)player.getMaxVie())*(double)lon)+x, haut+y);
		//normalize
		g.setStroke(new BasicStroke(1));
		
		//----barre de stamina----//
		
		x = 200;
		y = 10;
		lon = 150;
		haut = 10;
		bordure = 2;
		
		c2 = Color.green;
		
		
		
		
		//en arri�re de la barre
		c1 = Color.LIGHT_GRAY;
		//couleur de la barre (vie cest rouge)
		//couleur des contours (estetique)
		c3 = Color.black;
		
		//bordure
		g.setColor(new Color(c3.getRed(),c3.getGreen(),c3.getBlue(), 150));
		g.setStroke(new BasicStroke(bordure));
		g.drawRect(x, y, lon, haut);
		
		//background de la barre de vie
		g.setColor(new Color(c1.getRed(),c1.getGreen(),c1.getBlue(), 150));
		g.fillRect(x, y, lon, haut);
		
		//barre de vie
		g.setColor(new Color(c2.getRed(),c2.getGreen(),c2.getBlue(), 150));
		g.fillRect(x, y, (int)(((double)player.getStamina()/(double)player.getMaxStamina())*(double)lon), haut);
		
		//texte de la barre
		g.setFont(new Font(f.getName(), 10,10));
		fm = getFontMetrics(new Font(f.getName(), 10,10));
		g.setColor(Color.black);
		if((lon+y)-((int)(((double)player.getStamina()/(double)player.getMaxStamina())*(double)lon)+x)<fm.stringWidth(player.getStamina()+"/"+player.getMaxStamina()))
		g.drawString(player.getStamina()+"/"+player.getMaxStamina(), (int)(((double)player.getStamina()/(double)player.getMaxStamina())*(double)lon)-fm.stringWidth(player.getStamina()+"/"+player.getMaxStamina())+x, haut+y);
		else
			g.drawString(player.getStamina()+"/"+player.getMaxStamina(), (int)(((double)player.getStamina()/(double)player.getMaxStamina())*(double)lon)+x, haut+y);
		//normalize
		g.setStroke(new BasicStroke(1));
		
	}

	
	//KEY LISTENER
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		if(isInGame){
			if(isWASDMode){
				if(e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_UP){
					player.setW(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_A||e.getKeyCode() == KeyEvent.VK_LEFT){
					player.setA(true);
				}
			}
			else{
				if(e.getKeyCode() == KeyEvent.VK_Z||e.getKeyCode() == KeyEvent.VK_UP){
					player.setW(true);
				}
				if(e.getKeyCode() == KeyEvent.VK_Q||e.getKeyCode() == KeyEvent.VK_LEFT){
					player.setA(true);
				}
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S||e.getKeyCode() == KeyEvent.VK_DOWN){
				player.setS(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_D||e.getKeyCode() == KeyEvent.VK_RIGHT){
				player.setD(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				player.recharge();
			}
			if(e.getKeyCode() == KeyEvent.VK_K){
				player.hit(100000);
			}
			if(e.getKeyCode() == KeyEvent.VK_SHIFT){
				player.setShift(true);
			}
			if(e.getKeyCode() == KeyEvent.VK_M){
				//gi.setPopUp((byte)1);
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				if(GamePane.IsInPauseMenu == true){
					GamePane.IsInPauseMenu = false;
				}
				else
					GamePane.IsInPauseMenu = true;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(isWASDMode){
			if(e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_UP){
				player.setW(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_A||e.getKeyCode() == KeyEvent.VK_LEFT){
				player.setA(false);
			}
		}
		else{
			if(e.getKeyCode() == KeyEvent.VK_Z||e.getKeyCode() == KeyEvent.VK_UP){
				player.setW(false);
			}
			if(e.getKeyCode() == KeyEvent.VK_Q||e.getKeyCode() == KeyEvent.VK_LEFT){
				player.setA(false);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_S||e.getKeyCode() == KeyEvent.VK_DOWN){
			player.setS(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_D||e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.setD(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT){
			player.setShift(false);
		}
	}

	//Mouse Listerner
	
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		
		if(e.getButton() == MouseEvent.BUTTON1){
			System.out.println("alloh");
			mouseX = e.getPoint().x;
			mouseY = e.getPoint().y;
			player.setShoting(true);
			RIGHT_CLICK = true;
			CLICK_FOR_BUTTONS = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			player.cast();
			LEFT_CLICK = true;
		}
		
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			player.setShoting(false);
			RIGHT_CLICK = false;
			CLICK_FOR_BUTTONS = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON3){
			LEFT_CLICK = false;
		}
		
		
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()<0&&isInGame){
			player.armePlus();
		}
		else if(e.getWheelRotation()>0&&isInGame){
			player.armeMoins();
		}
		
	}
}
