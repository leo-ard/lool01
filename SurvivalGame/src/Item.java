import java.awt.Color;
import java.awt.Graphics2D;


public class Item {
	
	int x;
	int y;
	int r;
	/**
	 * 0 = munition
	 * 1 = arme
	 * 
	 */
	int type;
	Arme arme;
	boolean isUsed;
	
	public Item(int x, int y, int type, Arme arme){
		
		this.x = x;
		this.y = y;
		this.type = type;
		this.arme = arme;
		this.r = 4;
		
	}
	
	public Item(int x, int y, int type){
		
		this.x = x;
		this.y = y;
		this.type = type;
		this.r = 4;
		
	}
	
	public void use(){
		
		if(this.type == 0){
			for(int i = 0;GamePane.player.getArmes().size()>i;i++){
				 GamePane.player.getArmes().get(i).amoFull();
			}
			
		}
		if(this.type == 1){
			boolean haveArme = false;
			for(int i = 0; i<GamePane.player.getArmes().size();i++){
				if(this.arme.getName().equals(GamePane.player.getArmes().get(i).getName())){
					haveArme = true;
					GamePane.player.getArmes().get(i).amoFull();
					return;
				}
			}
			
			if(!haveArme){
				GamePane.player.getArmes().add(arme);
			}	
			
				
			
		}
		
		isUsed = true;
		
	}
	
	public void draw(Graphics2D g){
		
		if(type == 0){
			g.setColor(Color.orange);
		}
		if(type == 1){
			g.setColor(Color.red);
		}
		
		g.fillOval(x, y, r*2, r*2);
		
	}

	public int getX() {return x;}
	public int getY() {return y;}
	public int getR() {return r;}

}
