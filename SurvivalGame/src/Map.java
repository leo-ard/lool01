import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	
	public static ArrayList<Map> MAPS = new ArrayList<Map>();
	public static int theChosenMap = 1;
	
	private int viewPosX;
	private int viewPosY;
	
	private BufferedImage imageBackground;

	
	
	private ArrayList<Rectangle> murs;
	private ArrayList<Rectangle> enemiesSpawns;
	private int playerSpawnX;
	private int playerSpawnY;
	
	private String name;
	private int x;
	private int y;
	private int WIDTH;
	private int HEIGHT;
	
	boolean isEnable = false;
	
	
	/**
	 * must use the function importMap before utilisation
	 * 
	 * 
	 */
	public Map(){
		
		murs = new ArrayList<Rectangle>();
		enemiesSpawns = new ArrayList<Rectangle>();
	}
	
	public static Map getTheChosenOne(){
		return MAPS.get(theChosenMap);
	}
	
	public static void searchAndLoadMaps(String path){
		BufferedReader in;
		
		try {
			in = new BufferedReader(new FileReader(path+"maps.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le fichier "+path+"map.txt"+" n'existe pas.");
			return;
		}
		
		String line;
		
		try {
			while ((line = in.readLine()) != null) {
				MAPS.add(new Map());
				MAPS.get(MAPS.size()-1).importMap(path+line+"/");
			}
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		
		
		
		
	}
	
	public void importMap(String path){
		BufferedReader in;
		
		//load du fichier .txt
		try {
			in = new BufferedReader(new FileReader(path+"map.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le fichier "+path+"map.txt"+" n'existe pas.");
			return;
		}
		
		//load de l'image
		try {
			imageBackground = ImageIO.read(new File(path+"map.png"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le fichier "+path+"map.png"+" n'existe pas.");
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Il y a eu une erreur lors de l'ouverture de ce fichier : "+path+"map.txt"+".");
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		String line;
		
		//DEFAULT STTINGS
		name = "DEFAULT";
		x = 0;
		y = 0;
		WIDTH = GamePane.WIDTH;
		HEIGHT = GamePane.HEIGHT;
		playerSpawnX = 0;
		playerSpawnY = 0;
		
		
		try {
			while ((line = in.readLine()) != null) {
				
				if(line.startsWith("name:")){
					name = line.replace("name:", "");
				}
				else if(line.startsWith("x:")){
					x = (int) Double.parseDouble(line.replace("x:", ""));
				}
				else if(line.startsWith("y:")){
					y =(int) Double.parseDouble(line.replace("y:", ""));
				}
				else if(line.startsWith("width:")){
					WIDTH = (int) Double.parseDouble(line.replace("width:", ""));
				}
				else if(line.startsWith("height:")){
					HEIGHT = (int) Double.parseDouble(line.replace("height:", ""));
				}
				else if(line.startsWith("Player spawn x:")){
					playerSpawnX = (int) Double.parseDouble(line.replace("Player spawn x:", ""));
				}
				else if(line.startsWith("Player spawn y:")){
					playerSpawnY = (int) Double.parseDouble(line.replace("Player spawn y:", ""));
				}
				else if(line.startsWith("mur:")){
					
					int[] mur = new int[4];
					String[] str = line.replace("mur:", "").split(",");
					
					for(int i = 0;i<4;i++){
						mur[i] = (int) Double.parseDouble(str[i]);
					}
					murs.add(new Rectangle(mur[0],mur[1],mur[2],mur[3]));
				}
				else if(line.startsWith("Enemy spawn:")){
					
					int[] enemySPawn = new int[4];
					String[] str = line.replace("Enemy spawn:", "").split(",");
					
					for(int i = 0;i<4;i++){
						enemySPawn[i] = (int) Double.parseDouble(str[i]);
					}
					enemiesSpawns.add(new Rectangle(enemySPawn[0],enemySPawn[1],enemySPawn[2],enemySPawn[3]));
				}
				
				
			}
			imageBackground= imageBackground.getSubimage(x,y, WIDTH, HEIGHT);
			isEnable = true;
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		
		
	}
	
	public void update(){
		
		viewPosX = GamePane.player.getX()-GamePane.WIDTH/2;
		viewPosY = GamePane.player.getY()-GamePane.HEIGHT/2;
		
		GamePane.viewPosX = viewPosX;
		GamePane.viewPosY = viewPosY;
		
	}
	
	
	public void draw(Graphics2D g){
		
		if(isEnable){
			g.drawImage(imageBackground, 0, 0, null);
			
		}
		
	}
	
	public void drawWalls(Graphics2D g){
		g.setColor(Color.gray);
		for(int i = 0; i < this.enemiesSpawns.size(); i++){
			g.fillRect((int)this.enemiesSpawns.get(i).getX()-this.x, (int)this.enemiesSpawns.get(i).getY()-this.y, (int)this.enemiesSpawns.get(i).getWidth(), (int)this.enemiesSpawns.get(i).getHeight());
		}
	}
	
	public BufferedImage getImage(){return imageBackground;};
	public int getViewPosX(){return viewPosX;};
	public int getViewPosY(){return viewPosY;};
	public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return WIDTH;}
	public int getHeight(){return HEIGHT;}
	public int getSpawnX(){return playerSpawnX;}
	public int getSpawnY(){return playerSpawnY;}
	public ArrayList<Rectangle> getEnemiesSpawn(){return enemiesSpawns;}
	public ArrayList<Rectangle> getWalls(){return murs;}
	public String getName(){return name;}
	public String getDificulty(){return "Facile";}
}