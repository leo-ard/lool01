import java.awt.Color;
import java.awt.Graphics2D;


public class Explosion {
	
	int x;
	int y;
	int rMax;
	int rPlus;
	int dommageMax;
	int dommageMin;
	int dommageMoins;
	int r;
	int dommage;
	
	public Explosion(int x, int y, int rMax, int rPlus, int dommageMax, int dommageMin, int dommageMoins){
		
		this.x = x;
		this.y = y;
		this.rMax = rMax;
		this.rPlus = rPlus;
		this.dommageMax = dommageMax;
		this.dommageMin = dommageMin;
		this.dommageMoins = dommageMoins;
		this.r = 1;
		this.dommage = dommageMax;
		
	}
	
	public boolean update(){
		r+=rPlus;
		dommageMax-=dommageMoins;
		
		if(r>=rMax){
			return true;
		}
		if(dommage<=dommageMin){
			dommage = dommageMin;
		}
		
		
		return false;
		
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.red);
		g.fillOval(x-r, y-r, r*2, r*2);
		
	}

	public double getX() {return x;}
	public double getY() {return y;}
	public double getR() {return r;}

}
