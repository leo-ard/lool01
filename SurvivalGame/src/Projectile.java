import java.awt.Color;
import java.awt.Graphics2D;


public class Projectile {
	
	//FIELDS
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private int domage;
	private int durabilite;
	private boolean explosif;
	private double knockback;
	
	
	//CONSTRUCTEUR
	public Projectile(double angle, int x, int y, int speed, int domage, int durabilite, boolean explosif, double knockback){
		
		this.x = x;
		this.y = y;
		r = 2;
		
		this.domage = domage;
		
		rad = Math.toRadians(angle);
		dx = Math.cos(rad)*speed;
		dy = Math.sin(rad)*speed;
		this.durabilite = durabilite;
		
		this.explosif = explosif;
		
		this.knockback = knockback;
		
	}
	
	//FUNCTIONS
	public boolean update(){
		
		
		x+=dx;
		y+=dy;
		
		if(durabilite==0){
			return true;
			
		}
		
		for(int i = 0; i < GamePane.map.getWalls().size();i++){
			if(GamePane.map.getWalls().get(i).intersectsLine(x-dx+GamePane.map.getX(), y-dy+GamePane.map.getY(),x+GamePane.map.getX(), y+GamePane.map.getY())){
				return true;
			}
		}
		
		//touche les bords return true
		if(x<0||y<0||x>GamePane.map.getWidth()||y>GamePane.map.getHeight()){
			return true;
		}
		
		
		return false;
		
	}
	
	public void draw(Graphics2D g){
		
		g.setColor(Color.red);
		g.fillOval((int) ( x - r ) , (int) ( y - r ),r*2,2*r);
		
	}
	
	public double getX(){return x;};
	public double getY(){return y;};
	public double getR(){return r;};
	public double getDx(){return dx;};
	public double getDy(){return dy;}
	public int getDomage() {return domage;}
	public int getDurabilite() {return durabilite;};
	public boolean isExplosif() {return explosif;}
	public double getKnockback() {return this.knockback;}
	
	public void setDurabilite(int dur) { this.durabilite = dur;}
	
}
