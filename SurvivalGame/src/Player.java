import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Player {
	
	//FIELDS
	private int x;
	private int y;
	private int WIDTH;
	private int HEIGHT;
	
	private int dx;
	private int dy;
	private int speed;
	
	private boolean w;
	private boolean a;
	private boolean s;
	private boolean d;
	private boolean SHIFT;
	
	private boolean shoting;
	
	private int hp;
	private int maxHP;
	
	private ArrayList<Arme> armes = new ArrayList<Arme>();
	private int nbArme = 0;
	
	private int stamina;
	private int maxStamina;
	private int staminaRegen;
	private Power power;
	
	private long timerStamina;
	
	//CONSTRUCTEUR
	public Player(){
		
		x = GamePane.map.getSpawnX();
		y = GamePane.map.getSpawnY();
		WIDTH = 30;
		HEIGHT = 30;
		
		dx = 0;
		dy = 0;
		speed = 5;
		
		hp = 100;
		maxHP = 100;
		
		stamina = 100;
		maxStamina = 100;
		staminaRegen = 1;
		
		armes.add(Arme.HANDGUN);
		
		armes.add(Arme.ASSAULT_RIFLE);
		/*
		armes.add(Arme.SNIPER);
		armes.add(Arme.BASUKA);
		armes.add(Arme.CARABIN);
		*/
		armes.add(Arme.POING);
		
		armes.get(nbArme).isSelected(true);
		
	}
	
	//FUNCTIONS
	public void update(){
		speed = 5;
		boolean collision = false;
		
		if(stamina < 0){
			stamina = 0;
			SHIFT = false;
		}
		
		if(SHIFT){
			speed+=5;
			stamina--;
		}
		if(w){
			dy+=-speed;
		}
		if(a){
			dx+=-speed;
		}
		if(s){
			dy+=speed;
		}
		if(d){
			dx+=speed;
		}
		
		if(!(w||a||s||d||isShoting())){
			System.out.println(timerStamina);
			timerStamina++;
			if(timerStamina>=40){
				stamina++;
				timerStamina-= 3;
			}
			if(stamina > 100){
				stamina = 100;
			}
		}
		else{
			timerStamina=0;
		}
		
		
		//collision detection enemy-player
		for(int i = 0;i<GamePane.enemies.size();i++){
			int d2 = (int)(((x+dx)-(GamePane.enemies.get(i).getX())) * ((x+dx)-(GamePane.enemies.get(i).getX())) + ((y+dy)-GamePane.enemies.get(i).getY()) * ((y+dy)-GamePane.enemies.get(i).getY()));
			if(!(d2 > (WIDTH/2 + GamePane.enemies.get(i).getWIDTH()/2)*(WIDTH/2 + GamePane.enemies.get(i).getWIDTH()/2))){
				collision = true;
			}
		}
		
		//----collision avec murs----//
		for(int i = 0; i < GamePane.map.getWalls().size();i++){
			if(CollisionCircleRect(new Rectangle(x+dx-this.WIDTH/2,y+dy-this.HEIGHT/2,WIDTH,HEIGHT),(new Rectangle(GamePane.map.getWalls().get(i).x-GamePane.map.getX(),GamePane.map.getWalls().get(i).y-GamePane.map.getY(),GamePane.map.getWalls().get(i).width,GamePane.map.getWalls().get(i).height)))){
				collision = true;
			}
		}
		
		if(!collision){
			x += dx;
			y += dy;	
		}
		
		//Collision murs
		if(x-this.WIDTH/2 < 0){
			x = this.WIDTH/2;
		}
		if(y-this.HEIGHT/2 < 0){
			y = this.HEIGHT/2;
		}
		if(x> GamePane.map.getWidth()-this.WIDTH/2 ){
			x = GamePane.map.getWidth()-this.WIDTH/2;
		}
		if(y>GamePane.map.getHeight()-this.HEIGHT/2){
			y = GamePane.map.getHeight()-this.HEIGHT/2;
		}
		
		
		dx = 0;
		dy = 0;
		
		
		
		if(shoting){
			armes.get(nbArme).attack(x, y);
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.black);
		g.drawImage(new ImageIcon(GamePane.texturePack+"textures/joueur/personnage.png").getImage(), (int)x-WIDTH/2, (int)y-HEIGHT/2,null);
		/*g.setFont(new Font("Impact",20,20));
		g.drawString("HP  :  "+hp, 10 , 20);*/
		
		
		
	}
	
	public void drawForMenu(Graphics2D g, int r){
		g.drawImage(new ImageIcon(GamePane.texturePack+"textures/joueur/personnage.png").getImage(), (int)GamePane.WIDTH/2-(31*r)/2, (int)GamePane.HEIGHT/2-(31*r)/2,31*r,31*r, null);
	}
	
	public void hit(int domage){
		hp -= domage;
		if(hp<=0){
			GamePane.gameOver = true;
		}
	}
	
	public void armePlus(){
		armes.get(nbArme).isSelected(false);
		armes.get(nbArme).getChargeur().stopRecharge();
		nbArme++; 
		
		if(nbArme>armes.size()-1){
			nbArme-=armes.size();
		} 
	
		armes.get(nbArme).isSelected(true);
	}
	public void armeMoins(){
		armes.get(nbArme).isSelected(false);
		armes.get(nbArme).getChargeur().stopRecharge();
		nbArme--; 
		
		if(nbArme<0){
			nbArme=armes.size()-1;
		} 
	
		armes.get(nbArme).isSelected(true);
	}

	public void recharge() {
		this.getEquipedArme().getChargeur().recharge();
	}

	public ArrayList<Arme> getArmes() {
		return armes;
	}
	
	public boolean CollisionCircleRect(Rectangle circle, Rectangle rect){
		if(rect.intersects(circle)){
			if(rect.intersects(new Rectangle(circle.x,circle.y-dy,circle.width,circle.height))){
				x = (dx<0)?rect.x+rect.width+this.WIDTH/2:rect.x-this.WIDTH/2;
				System.out.println(1);
			}
			if(rect.intersects(new Rectangle(circle.x-dx,circle.y,circle.width,circle.height))){
				y = (dy<0)?rect.y+rect.height+this.HEIGHT/2:rect.y-this.HEIGHT/2;
				System.out.println(2);
			}
			
			
			return true;
			
			
			/*
			for(int i = 0;i<=speed;i++){
				if(rect.intersects(new Rectangle((dx!=0)?circle.x+(speed-i):circle.x, (dy!=0)?circle.y+(speed-i):circle.y,circle.width,circle.height))){
					/*
					if(dx!=0){
						dx = (dx<0)?-i:i;
					}
					if(dy!=0){
						dy = (dy<0)?-i:i;
					}
					return true;
				}
			}
			return true;
			*/
		}
		else 
			return false;
		/*
		if (CollisionPointCercle(rect.x,rect.y,circle)==true
				    || CollisionPointCercle(rect.x,rect.y+rect.height,circle)==true
				    || CollisionPointCercle(rect.x+rect.width,rect.y,circle)==true
				    || CollisionPointCercle(rect.x+rect.width,rect.y+rect.height,circle)==true)
			 return true;   // deuxieme test
		if (CollisionPointRect(circle.x,circle.y,rect)==true)
			return true;   // troisieme test
		boolean projvertical = ProjectionSurSegment(circle.x,circle.y,rect.x,rect.y,rect.x,rect.y+rect.height);
		boolean projhorizontal = ProjectionSurSegment(circle.x,circle.y,rect.x,rect.y,rect.x+rect.width,rect.y); 
		if (projvertical==true || projhorizontal==true)
			return true;   // cas E
		return false; */ // cas B   
	}

	public boolean ProjectionSurSegment(int Cx,int Cy,int Ax,int Ay,int Bx,int By)
	{
	   int ACx = Cx-Ax;
	   int ACy = Cy-Ay; 
	   int ABx = Bx-Ax;
	   int ABy = By-Ay; 
	   int BCx = Cx-Bx;
	   int BCy = Cy-By; 
	   int s1 = (ACx*ABx) + (ACy*ABy);
	   int s2 = (BCx*ABx) + (BCy*ABy); 
	   if (s1*s2>0)
	     return false;
	   return true;
	}
	

	public static boolean CollisionPointCercle(int x, int y, Rectangle circle){
		
		int d2 = (x-circle.x)*(x-circle.x) + (y-circle.y)*(y-circle.y);
		if (d2>(circle.width/2)*(circle.height/2))
			return false;
		else
			return true;
	}
	
	public static boolean CollisionPointRect(int curseur_x,int curseur_y,Rectangle box)
	{
	   if (curseur_x >= box.x 
	    && curseur_x < box.x + box.width
	    && curseur_y >= box.y 
	    && curseur_y < box.y + box.height)
	       return true;
	   else
	       return false;
	}
	
	public void cast(){
		power = new Power(Power.DASH);
		power.cast(x, y);
	}
	
	
	public void setW(boolean b){w=b;}
	public void setA(boolean b){a=b;}
	public void setS(boolean b){s=b;}
	public void setD(boolean b){d=b;}
	public void setShift(boolean b){SHIFT = b;};
	public void setShoting(boolean b) {shoting = b;}
	public void setX(double x){this.x=(int) x;}
	public void setY(double y){this.y=(int) y;}
	public void consumeStamina(int x){if(stamina<x){return;}stamina -= x;};
	
	public Arme getEquipedArme(){return armes.get(nbArme);};
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getWIDTH(){return WIDTH;}
	public int getHEIGHT(){return HEIGHT;}
	public int getVie(){return this.hp;};
	public int getHP(){return hp;};
	public int getMaxVie(){return maxHP;};
	public int getMaxHP(){return maxHP;};
	public boolean isShoting(){return shoting;}
	public int getStamina(){return stamina;}
	public int getMaxStamina(){return maxStamina;};
}
