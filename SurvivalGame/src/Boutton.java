import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;


public class Boutton extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isOn,
					isEnable;
	
	private int x,
				y,
				xText,
				yText,
				WIDTH,
				HEIGHT,
				r,
				type,
				position,
				popup,
				shape;
	
	
	private Color normal = Color.red,
				  enable = Color.red.brighter(),
				  pass = Color.red.brighter();
	
	private String text;
	
	private Font f;

	private Color textColor;
	
	
	public Boutton(int x, int y,int WIDTH, int HEIGHT,int r, int type, String text, Font f,Color textColor, Color normal, int shape, int cor){
		
		FontMetrics fm = this.getFontMetrics(f);
		
		this.f = f;
		this.x = x-WIDTH/2;
		this.y = y-HEIGHT/2;
		
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.type = type;
		
		if(shape == 1)
			this.xText = x-WIDTH/2+(WIDTH - fm.stringWidth(text))/2+10;
		else 
			this.xText = x-WIDTH/2+(WIDTH - fm.stringWidth(text))/2;
		
		this.yText = y+(HEIGHT/2 - fm.getAscent()/2)+cor; 
		
		this.text = text;
		
		this.normal = normal;
		if(this.normal.equals(Color.white)){
			this.pass = new Color(220,220,220);
			this.enable = new Color(200,200,200);
		}
		
		this.r = r;
		
		this.textColor = textColor;
		
		this.shape = shape;
		
	}
	
	public Boutton(int x, int y,int WIDTH, int HEIGHT,int r, int type, String text, Font f,Color textColor, Color normal, int position, int popup,int shape, int cor){
		
		FontMetrics fm = this.getFontMetrics(f);
		
		this.f = f;
		this.x = x-WIDTH/2;
		this.y = y-HEIGHT/2;
		
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.type = type;
		
		this.xText = x-WIDTH/2+(WIDTH - fm.stringWidth(text))/2;
		this.yText = y+(HEIGHT/2 - fm.getAscent()/2)+cor; 
		
		this.text = text;
		
		this.normal = normal;
		if(this.normal.equals(Color.white)){
			this.pass = new Color(220,220,220);
			this.enable = new Color(200,200,200);
		}
		
		this.r = r;
		
		this.textColor = textColor;
		
		this.position = position;
		this.popup = popup;
		
		this.shape = shape;
		
	}
	
	
	/**
	 * new types:
	 * 0: start game
	 * 1: change place in menu
	 * 2: quit game
	 * 3: popUp++
	 * 4: popUp--
	 * 5: change WASD ZQSD mode
	 * 6: go to last position
	 * 7: resume game
	 * 8: go to menu
	 */
	public void click(){
		//start the game
		if(type == 0){
			GamePane.restart = true;
			GamePane.isMainMenu = false;
		}
		//change place in menu
		else if(type == 1){
			if(position != -1){
				GamePane.gi.setPosition((byte)position);
			}
			
			if(popup != -1){
				GamePane.gi.setPopUp((byte)popup);
			}
		}
		//quit game
		else if(type == 2){
			Game.gameSave.save();
			Game.windows.dispatchEvent(new WindowEvent(Game.windows, WindowEvent.WINDOW_CLOSING));
		}
		//ajoute 1 au popup jusqua 2
		else if(type == 3){
			GamePane.gi.setPopUp((byte) ((GamePane.gi.getPopUp()+1<=2)? GamePane.gi.getPopUp()+1:0));
		}
		//enleve 1 au popup jusqua 0
		else if(type == 4){
			GamePane.gi.setPopUp((byte) ((GamePane.gi.getPopUp()-1>=0)? GamePane.gi.getPopUp()-1:2));
		}
		//Change wasd zqsd mode
		else if(type == 5){
			if(GamePane.isWASDMode){
				GamePane.isWASDMode = false;
				
				this.text = "Mode du clavier : ZQSD";
			}
			else{
				GamePane.isWASDMode = true;
				this.text = "Mode du clavier : WASD";
			}
			Game.gameSave.changeEntree("wasdMode", GamePane.isWASDMode);
		}
		//va a la derniere position
		else if(type == 6){
			if(GamePane.gi.getPosition() == 1){
				Game.gameSave.save();
			}
			GamePane.gi.setPosition(GameInterface.LAST_POSITION);

		}
		// depause
		else if(type == 7){
			GamePane.IsInPauseMenu = false;
		}
		// va au menu
		else if(type == 8){
			GamePane.gi.setPosition((byte)0);
			GamePane.isMainMenu = true;
		}
		//rejouer
		else if(type == 9){
			GamePane.gi.setPosition((byte)0);
			GamePane.restart = true;
		}
		//i for map ++
		else if(type == 10){
			Map.theChosenMap++;
			if(Map.theChosenMap>Map.MAPS.size()-1){
				Map.theChosenMap = 0;
			}
		}
		//i for map --
		else if(type == 11){
			Map.theChosenMap--;
			if(Map.theChosenMap < 0){
				Map.theChosenMap = Map.MAPS.size()-1;
			}
		}
		
		
		
		
		/*
		if(type == 0){
			GamePane.running = false;
		}
		if(type == 1){
			GamePane.IsInPauseMenu = false;
		}
		if(type == 2){
			GamePane.maxH = 0;
			GamePane.maxW = 0;
			GamePane.UsedButton = GamePane.OptionBoutton;
		}
		if(type == 3){
			GamePane.stop();
			GamePane.running = false;
			Game.windows.dispatchEvent(new WindowEvent(Game.windows, WindowEvent.WINDOW_CLOSING));
		}
		if(type == 4){
			if(GamePane.isWASDMode){
				GamePane.isWASDMode = false;
				this.text = "Keyborard mode: ZQSD";
			}
			else{
				GamePane.isWASDMode = true;
				this.text = "Keyborard mode: WASD";
			}
		}
		if(type == 5){
			GamePane.maxH = 0;
			GamePane.maxW = 0;
			GamePane.UsedButton = GamePane.MenuBoutton;

		}
		*/
		GamePane.CLICK_FOR_BUTTONS = false;
		
	}
	
	@SuppressWarnings("unused")
	public void draw(Graphics2D g){
		
		g.setFont(f);
		
		if(isOn&&isEnable){
			g.setColor(this.enable);
		}
		else{
			if(isOn){
				g.setColor(this.pass);
			}
			else{
				g.setColor(this.normal);
			}
			
		}
		
		if(shape == 0){
			g.fillRoundRect(x, y, WIDTH, HEIGHT, r, r);
		}
		else if(shape == 1){
			Polygon p = new Polygon();
			p.addPoint(x, y+HEIGHT/2);
			p.addPoint(x+WIDTH, y);
			p.addPoint(x+WIDTH, y+HEIGHT);
			g.fillPolygon(p);;
		}
		else if(shape == 2){
			Polygon p = new Polygon();
			p.addPoint(x+WIDTH, y+HEIGHT/2);
			p.addPoint(x, y);
			p.addPoint(x, y+HEIGHT);
			g.fillPolygon(p);;
		}
		else if(shape == 3){
			Polygon p = new Polygon();
			p.addPoint(x, y+HEIGHT);
			p.addPoint(x+WIDTH, y+HEIGHT);
			p.addPoint(x+WIDTH/2, y);
			g.fillPolygon(p);;
		}
		else if(shape == 4){
			Polygon p = new Polygon();
			p.addPoint(x, y);
			p.addPoint(x+WIDTH, y);
			p.addPoint(x+WIDTH/2, y+HEIGHT);
			g.fillPolygon(p);;
		}
		
		
		g.setColor(textColor);
		if(xText==0&&yText==0){ g.drawString(text, x, y+HEIGHT);}else  g.drawString(text, xText, yText);
		
		//see box in black if true
		if(false){
			g.setColor(Color.black);
			g.drawRect(x,y,WIDTH,HEIGHT);
		}
		
	}
	
	public void update(int curseur_x, int curseur_y, boolean clicked){
		
		if (curseur_x >= x 
		    && curseur_x < x + WIDTH
		    && curseur_y >= y 
		    && curseur_y < y + HEIGHT){
			if(clicked){
				isEnable = true;
				isOn = true;
				if(GamePane.CLICK_FOR_BUTTONS)
					click();
			}
			else{
				isEnable = false;
				isOn = true;
			}
		}
		else{
			isEnable = false;
			isOn = false;
		}
		
	}
	
	
	public void setXNY(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public void setTextXNY(int tx, int ty){
		this.xText = tx;
		this.yText = ty;
	}
	
	public int getTextX(){
		return this.xText;
	}
	public int getTextY(){
		return this.yText;
	}
	public int getHeight(){return this.HEIGHT;};
	public int getWidth(){return this.WIDTH;};
	
	@Override
	public int getX(){return this.x;};
	@Override
	public int getY(){return this.y;};
}
