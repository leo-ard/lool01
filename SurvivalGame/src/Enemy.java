import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Enemy {
	
	//FIELDS
	private double x;
	private double y;
	private int WIDTH;
	private int HEIGHT;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private int health;
	private int maxHealth;
	private int domage;
	//0=normal 1=speed 2=tank 3=cracheur
	private int type;
	private int value;
	
	Color color;
	
	private boolean ready;
	private boolean dead;
	
	private boolean attackMode;
	private int attackDelay;
	private int attackTimer = 0;
	
	private boolean collisionX;
	private boolean collisionY;
	
	private boolean isKnokBack;
	
	//tests
	int test = 0;
	
	//CONSTRUCTEUR
	public Enemy(int type, int rank) {
		
		this.type = type;
		
		if(type == 0){
			this.health = 100;
			this.maxHealth = 100;
			this.WIDTH = 30;
			this.HEIGHT = 30;
			this.color = Color.BLUE;
			this.speed = 3;
			this.domage = 10;
			this.attackDelay = 10;
			this.value = 1;
		}
		if(type == 1){
			this.health = 50;
			this.maxHealth = 50;
			this.WIDTH = 20;
			this.HEIGHT = 20;
			this.color = Color.BLUE;
			this.speed = 4;
			this.domage = 5;
			this.attackDelay = 7;
			this.value = 1;
		}
		
		boolean isOk = true;
		int d2;
		
		while(isOk){
			isOk = false;
			//spawning areas 0=haut 1=droite 2=bas 3=gauche
			Rectangle spawnRect = GamePane.map.getEnemiesSpawn().get((int) ((double)Math.random()*(double)GamePane.map.getEnemiesSpawn().size()));
			x = spawnRect.x+this.WIDTH/2+((double)Math.random()*(double)(spawnRect.width-this.WIDTH))-GamePane.map.getX();
			y = spawnRect.y+this.HEIGHT/2+((double)Math.random()*(double)(spawnRect.height-this.HEIGHT))-GamePane.map.getY();
			
			for(int i = 0;i<GamePane.enemies.size();i++){
				
				Enemy e1 = GamePane.enemies.get(i);
				
				d2 = (int)(((x)-(e1.getX())) * ((x)-(e1.getX())) + ((y)-e1.getY()) * ((y)-e1.getY()));
				if(!(d2 > (WIDTH/2 + e1.getWIDTH()/2)*(WIDTH/2 + e1.getWIDTH()/2))){
					//isOk = true;isOk = false;
					isOk = true;
				}
				else{
					
					//System.out.println("CORRECTION "+test+" : "+x+":"+y+" "+e1.getX()+":"+e1.getY());
				}
			}
			
		}
		test++;
		
		int AC = (int) (GamePane.player.getX() - x);
		int AB = (int) (GamePane.player.getY() - y);
		
		double angle = 90-(Math.toDegrees(Math.atan2(AC,AB)));
		rad = Math.toRadians(angle);
		
		dx = Math.cos(rad)*speed;
		dy = Math.sin(rad)*speed;
		
		ready = false;
		dead = false;
		
	}
	

	
	public void hit(int domage){
		
		this.health -= domage;
		if(this.health <= 0){
			dead = true;
		}
		
	}
	
	public void update(){
		//the enemy goes to the player
		
		collisionX = false;
		collisionY = false;
		
		if(!this.isKnokBack){
			int AC = (int) (GamePane.player.getX() - x);
			int AB = (int) (GamePane.player.getY() - y);
		
			double angle = 90-(Math.toDegrees(Math.atan2(AC,AB)));
			rad = Math.toRadians(angle);
		
			dx = Math.cos(rad)*speed;
			dy = Math.sin(rad)*speed;
		}
		else{
			isKnokBack=false;
		}
		int d2 = (int)(((x+dx)-(GamePane.player.getX())) * ((x+dx)-(GamePane.player.getX())) + ((y+dy)-GamePane.player.getY()) * ((y+dy)-GamePane.player.getY()));
		
		if(attackMode){
			collisionX = true;
			collisionY = true;
			Attack();
		}
		else if(!(d2 > (WIDTH/2 + GamePane.player.getWIDTH()/2)*(WIDTH/2 + GamePane.player.getWIDTH()/2))){
			collisionX = true;
			collisionY = true;
			attackMode = true;
			Attack();
		}
		
		//x n y verrifi
		for(int i = 0;i<GamePane.enemies.size();i++){
			Enemy e1 = this;
			Enemy e2 = GamePane.enemies.get(i);
			
			boolean collision = false;
			
			if(!e1.equals(e2)){
				
				d2 = (int)(((e1.getX()+dx)-(e2.getX())) * ((e1.getX()+dx)-(e2.getX())) + ((e1.getY()+dy)-e2.getY()) * ((e1.getY()+dy)-e2.getY()));
				if(!(d2 > (e1.getWIDTH()/2 + e2.getWIDTH()/2)*(e1.getWIDTH()/2 + e2.getWIDTH()/2)))
					collision = true;
				
				if(collision){
					//X
					d2 = (int)(((e1.getX()+dx)-(e2.getX())) * ((e1.getX()+dx)-(e2.getX())) + ((e1.getY())-e2.getY()) * ((e1.getY())-e2.getY()));
					if(!(d2 > (e1.getWIDTH()/2 + e2.getWIDTH()/2)*(e1.getWIDTH()/2 + e2.getWIDTH()/2))){
						collisionX = true;
						//collisionY = true;
					}
					//y
					d2 = (int)(((e1.getX())-(e2.getX())) * ((e1.getX())-(e2.getX())) + ((e1.getY()+dy)-e2.getY()) * ((e1.getY()+dy)-e2.getY()));
				
					if(!(d2 > (e1.getWIDTH()/2 + e2.getWIDTH()/2)*(e1.getWIDTH()/2 + e2.getWIDTH()/2))){
						collisionY=true;
					}
				}
			}
		}
		
		//----collision avec murs----//
		for(int i = 0; i < GamePane.map.getWalls().size();i++){
			Rectangle rect;
			if(new Rectangle((int)(x+dx-this.WIDTH/2),(int)(y+dy-this.HEIGHT/2),WIDTH,HEIGHT).intersects(rect = new Rectangle(GamePane.map.getWalls().get(i).x-GamePane.map.getX(),GamePane.map.getWalls().get(i).y-GamePane.map.getY(),GamePane.map.getWalls().get(i).width,GamePane.map.getWalls().get(i).height))){
				if(new Rectangle((int)(x+dx-this.WIDTH/2),(int)(y-this.HEIGHT/2),WIDTH,HEIGHT).intersects(new Rectangle(GamePane.map.getWalls().get(i).x-GamePane.map.getX(),GamePane.map.getWalls().get(i).y-GamePane.map.getY(),GamePane.map.getWalls().get(i).width,GamePane.map.getWalls().get(i).height))){
					x = (dx<0)?rect.x+rect.width+this.WIDTH/2:rect.x-this.WIDTH/2;
					collisionX = true;
				}
				if(new Rectangle((int)(x-this.WIDTH/2),(int)(y+dy-this.HEIGHT/2),WIDTH,HEIGHT).intersects(new Rectangle(GamePane.map.getWalls().get(i).x-GamePane.map.getX(),GamePane.map.getWalls().get(i).y-GamePane.map.getY(),GamePane.map.getWalls().get(i).width,GamePane.map.getWalls().get(i).height))){
					y = (dy<0)?rect.y+rect.height+this.HEIGHT/2:rect.y-this.HEIGHT/2;
					collisionY = true;
				}
			}
		}
		
		if(!collisionX){
			x+=dx;	
		}
		if(!collisionY){
			y+=dy;
		}
		
		if(!ready){
			if(x>0&&x<GamePane.WIDTH&&y>0&&y<GamePane.HEIGHT){
				ready = true;
			}
		}
		
		
		
	}
	
	private void Attack() {
		
		attackTimer++;
		if(attackTimer>=attackDelay){
			attackMode = false;
			//boster un peu pour plus de range
			if(new Rectangle((int)(x+dx)-5, (int)(y+dy)-5,WIDTH+10,HEIGHT+10).intersects(GamePane.player.getX(),GamePane.player.getY(),GamePane.player.getWIDTH(),GamePane.player.getHEIGHT())){
				GamePane.player.hit(domage);
				attackTimer = 0;
				color = Color.red;
				
			}
		}
	}

	public void draw(Graphics2D g){
		
		if(type == 0){
			g.setColor(color);
			g.drawImage(new ImageIcon(GamePane.texturePack+"textures/zombies/zombie.png").getImage(), (int)x-WIDTH/2, (int)y-HEIGHT/2,null);
		}
		if(type == 1){
			g.drawImage(new ImageIcon(GamePane.texturePack+"textures/zombies/fastzombie.png").getImage(), (int)x-WIDTH/2, (int)y-HEIGHT/2,null);
		}
		
		
		if(health != maxHealth){
			int X = (int)x-this.HEIGHT/2; 
			int Y = (int)y-this.HEIGHT/2-7;
			int gros = 5;
			double longe = this.HEIGHT;
			
			/*g.setColor(Color.BLACK);
			g.fillOval(X, Y, gros, gros);
			g.fillRect(X+gros/2, Y, (int)(longe+10), gros);
			g.fillOval((int)(X+longe+10), Y, gros, gros);*/
			if(maxHealth*.25>=health){
				g.setColor(Color.red);
			}
			else if(maxHealth*.5>=health){
				g.setColor(Color.orange);
				
			}
			else{
				g.setColor(Color.green);
			}
			g.fillRect(X, Y, (int)((longe*(((double)this.health)/((double)this.maxHealth)))), gros);
		}
		
	}

	public void knockback(double knokBack, double dx, double dy) {
		isKnokBack = true;
		this.dx = dx*knokBack;
		this.dy = dy*knokBack;
	}
	
	//FUNCTIONS
	
	public double getX(){return x;};
	public double getY(){return y;};
	public double getWIDTH(){return WIDTH;};
	public double getHEIGHT(){return HEIGHT;};
	public int getValue(){return value;};
	
	public boolean isDead(){return dead;};

}
