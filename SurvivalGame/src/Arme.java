import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;


public class Arme {
	
	public static Arme SHOTGUN ;
	public static Arme HANDGUN ;
	public static Arme SNIPER;
	public static Arme MACHINE_GUN;
	public static Arme ASSAULT_RIFLE;
	public static Arme BASUKA;
	public static Arme CARABIN;
	
	public static Arme POING;
	
	public static ArrayList<Arme> DEFAULT_ARMES = new ArrayList<Arme>();
	
	public static void defaultArme(){
		
		SHOTGUN = new Arme("RZK 97","Shotgun",1000,50,4,70,10,30,false,false,20,1,2500, false, 0.5);              
		HANDGUN = new Arme("GIK .45","Handgun",0,0,10,95,1,30,false,true,10,1,1000, false, .1);        //0->10. .7->.1          
		SNIPER = new Arme("FDR 916","Sniper",1000,60,5,99,1,100,false,false,100,4,2000, false, 0.05);              
		MACHINE_GUN = new Arme("FAL 85","Machine Gun",60,400,100,85,1,20,true,false,10,1,2000, false, 0.05);     
		ASSAULT_RIFLE = new Arme("AMK 2.5","Assault Riffle",60,400,40,90,1,20,true,false,10,1,1000, false, 0.07); 
		BASUKA = new Arme("XBD 52","BASUKA",2000,10,1,95,1,15,false,false,10000,1,3000,true, 0);                      
		CARABIN = new Arme("ANG 015","Carabin",1500,0,15,95,1,30,false,false,50,2,3000,false, 0.3); 
		
		//mele : Str nom, Str type, int speed, int durabilite, int range, int dommage, boo infini, dou knockBack
		POING = new Arme("POING","Mele", 100, 1, 15, 5,true, 4);
		
		DEFAULT_ARMES.add(SHOTGUN);
		DEFAULT_ARMES.add(HANDGUN);
		DEFAULT_ARMES.add(SNIPER);
		DEFAULT_ARMES.add(MACHINE_GUN);
		DEFAULT_ARMES.add(ASSAULT_RIFLE);
		DEFAULT_ARMES.add(BASUKA);
		DEFAULT_ARMES.add(CARABIN);
		DEFAULT_ARMES.add(POING);
	}
	
	
	private int domage;
	
	private String nom;
	private String type;
	
	private int vitesse;
	private int vitesseDeTir;
	
	private int precision;
	private int nbDeBalle;
	private int speed;
	private int durabiliteBalles;
	private double knockback;
	
	private int munitionMax;
	private int ballesMax;
	private int balles;
	private int munition;
	private int rechargeTime;
	private int rechargeChargeur = 0;
	//private boolean recharged = true;
	
	private boolean auto;
	
	private Chargeur chargeur;
	
	private boolean infiniteAmo;

	private long shotingTimer;
	private long meleTimer = 0;
	
	long elapsed;
	
	boolean explosif;
	
	//mele fields
	private boolean mele;
	private int durabilite;
	private int xx;
	private int yy;
	private int range;
	
	//gui fields
	private boolean selected;
	private int animationW;
	private int animationH;
	private int maxW;
	private int maxH;
	
	
	public final static int DEFAULT_MAXW = 10;
	public final static int DEFAULT_MAXH = 30;
	
	
	/**
	 * 
	 * The gun Constructor
	 * 
	 * 
	 * @param nom
	 * @param type
	 * @param vitesseDeTir
	 * @param munitionMax
	 * @param chargeurMax
	 * @param precision
	 * @param nbDeBalle
	 * @param speed
	 * @param auto
	 * @param infiniteAmo
	 * @param domage
	 * @param dirabiliteBalles
	 * @param rechargeTime
	 * @param explosif
	 */
	public Arme(String nom, String type,int vitesseDeTir, int munitionMax, int chargeurMax, int precision, int nbDeBalle, int speed, boolean auto, boolean infiniteAmo, int domage, int dirabiliteBalles, int rechargeTime, boolean explosif, double knockback) {
		
		this.vitesseDeTir = vitesseDeTir;
		this.vitesse = vitesseDeTir;
		this.rechargeTime = rechargeTime;
		
		this.munitionMax = munitionMax;
		this.ballesMax = chargeurMax;
		this.balles = chargeurMax;
		this.munition = munitionMax;
		
		this.precision = precision;
		this.nbDeBalle = nbDeBalle;
		
		this.speed = speed;
		this.auto = auto;
		this.domage = domage;
		this.durabiliteBalles = dirabiliteBalles;
		
		this.infiniteAmo = infiniteAmo;
		
		this.nom = nom;
		this.type = type;
		
		this.explosif = explosif;
		
		this.maxH = DEFAULT_MAXH;
		this.maxW = DEFAULT_MAXW;
		
		this.mele = false;
		
		this.knockback = knockback;
		
	}
	
	
	public Arme(String nom, String type, int speed, int durabilite, int range, int dommage, boolean infini, double knockBack){
		
		this.nom = nom;
		this.type = type;
		this.speed = speed;
		this.durabilite = durabilite;
		
		this.range = range;
		this.mele = true;
		this.infiniteAmo = infini;
		this.domage = dommage;
		
		this.maxH = DEFAULT_MAXH;
		this.maxW = DEFAULT_MAXW;
		
		this.knockback = knockBack;
		
	}
	

	public void attack(int x, int y) {
		
		
		//Shoot
		if(mele == false){
			boolean shoot = true;
			vitesse = vitesseDeTir;
			elapsed = (System.nanoTime() - shotingTimer)/1000000;
			if(elapsed > vitesse&&!this.chargeur.isRecharging){
				
				balles--;
				if(balles<0){
					if(!(munition<=0)||this.isInfiniteAmo()){
						chargeur.recharge();	
					}
					balles = 0 ;
				}
				else{
					if(balles == 0){
						chargeur.recharge();
					}
				
				//| B (mouse)
				//              / |
				//  (player) c /__| A               4578 rue chabot
			
			
				int AC = GamePane.mouseX - x;
				//if(AC < 0){ AC = -AC;}
				int AB = GamePane.mouseY - y;
				//if(AB < 0){ AB = -AB;}
				if(shoot){	
				try{
					for(int i = 0;i<this.nbDeBalle;i++){
					int pre = (int) ((Math.random()*(100-precision))-(100-precision)/2);
					int spe = (int) ((Math.random()*(speed*.1))/2);
				
					GamePane.projectiles.add(new Projectile(90-(Math.toDegrees(Math.atan2(AC,AB))+pre),x,y, this.speed+spe, this.domage, this.durabiliteBalles, this.explosif, this.knockback));
					}
					}catch(ArithmeticException e){}
				}
				shotingTimer = System.nanoTime();
				
				
				if(!auto){
					GamePane.player.setShoting(false);
				}
				}
			}
				
			}
		
		
		//mele attack
		else{
		
			elapsed = (System.nanoTime() - meleTimer)/1000000;
			if((elapsed > speed)){
				int AC = (int) (GamePane.mouseX - x);
				int AB = (int) (GamePane.mouseY - y);
				
				//double angle = 90-();
				double rad =  Math.toRadians(90-Math.toDegrees(Math.atan2(AC,AB)));
				//System.out.print(b);
				xx = (int)(Math.cos(rad)*GamePane.player.getWIDTH()/2)+GamePane.player.getX();//GamePane.player.getWIDTH()/2;
				yy = (int)(Math.sin(rad)*GamePane.player.getHEIGHT()/2)+GamePane.player.getY();//-GamePane.player.getHEIGHT()/2;
				
				
				for(int i = 0;i<GamePane.enemies.size();i++){
					
					Enemy en = GamePane.enemies.get(i);
					
					int d2 = (int)(((xx)-(en.getX())) * ((xx)-(en.getX())) + ((yy)-en.getY()) * ((yy)-en.getY()));
					
					if(!(d2 > (range + en.getWIDTH()/2)*(range + en.getHEIGHT()/2))){
					
						GamePane.enemies.get(i).hit(1);
						GamePane.enemies.get(i).knockback(knockback, Math.cos(rad), Math.sin(rad));
						return;
					}
				}
				
				
				meleTimer = System.nanoTime();
			}
		}
	}
	
	@SuppressWarnings("unused")
	public void draw(Graphics2D g){
		
		if(!mele){
			g.setColor(Color.black);
			g.setFont(new Font("Impact", 30, 30));
			
			if(infiniteAmo){
				g.drawString(""+balles+"/ Inf.", 10, GamePane.HEIGHT-20);
			}
			else{
				g.drawString(""+balles+"/"+munition, 10, GamePane.HEIGHT-20);
			}
			

				FontMetrics fm = g.getFontMetrics(new Font(g.getFont().getFontName(), g.getFont().getStyle(),g.getFont().getSize()));
				g.setColor(new Color(0,0,100,50));
				
				if(this.chargeur.isRecharging() == true){
					elapsed = (System.nanoTime() - this.chargeur.startPoint)/1000000;
					g.fillRect(10, GamePane.HEIGHT-20-fm.getHeight(), (int) ((infiniteAmo)?fm.stringWidth(""+balles+"/ Inf.")*((double)elapsed/(double)rechargeTime):(int)(fm.stringWidth(""+balles+"/"+munition)*((double)elapsed/(double)rechargeTime))), fm.getHeight());
				}
				
				g.setColor(new Color(0,0,200,50));
				//g.drawRect(10, GamePane.HEIGHT-20-fm.getHeight(), (infiniteAmo)?fm.stringWidth(""+chargeur+"/ Inf."):fm.stringWidth(""+chargeur+"/"+munition), fm.getHeight());
			
		}
		else{
			g.setFont(new Font("Impact", 30, 30));
			if(infiniteAmo){
				g.drawString("", 10, GamePane.HEIGHT-20);
			}
			else{
				g.drawString(""+durabilite, 10, GamePane.HEIGHT-20);
			}
			
			g.setColor(Color.blue);
			//System.out.println(xx+" "+yy);
			//show the hitbox of the mele attack
			if(false){g.drawOval(xx-range-GamePane.viewPosX, yy-range-GamePane.viewPosY, range*2, range*2);}
			
		}
		
		g.setFont(new Font("Impact", 15, 15));
		
		/*g.drawString(nom, 10,  GamePane.HEIGHT-68);
		g.drawString(type, 10,  GamePane.HEIGHT-50);*/
		
	}
	
	public void update(){
		
		if(chargeur == null){
			chargeur = new Chargeur(this);
		}
		
		chargeur.update();
		
		//gui
		/*
		maxW = 10;
		maxH = 30;
		*/
		if(selected){
			animationW+=5;
			animationH+=5;
			if(animationH>=maxH){
				animationH = maxH;
			}
			if(animationW>=maxW){
				animationW = maxW;
			}
		}
		else{
			animationW-=5;
			animationH-=5;
			if(animationW<=0){
				animationW = 0;
			}
			if(animationH<=0){
				animationH = 0;
			}
		}
		
	}
	
	public void amoFull(){
		munition = munitionMax;
		
	}
	
	public static Arme getRandomArme(){
		return DEFAULT_ARMES.get((int)(Math.random()*DEFAULT_ARMES.size()));
	}
	
	public int getRecharge(){return rechargeChargeur;}
	public int getRechargeTime(){return rechargeTime;}
	public int getBalles(){return balles;}
	public int getBallesMax(){return ballesMax;}
	public String getName(){ return nom;}
	public boolean isRecharged() {elapsed = (System.nanoTime() - shotingTimer)/1000000;return (elapsed > vitesse);}
	public int getAnimationW(){return animationW;}
	public int getAnimationH(){return animationH;}	
	public String getType() {return type;}
	public boolean isSelected() {return selected;}
	public int getMaxH(){return maxH;}
	public int getMaxW(){return maxW;}
	public boolean isInfiniteAmo() {return this.infiniteAmo;}
	public int getMunition() {return munition;}
	public int getMunitionMax(){return munitionMax;}
	public boolean isMele(){return mele;}
	public int getDurabilite() {return this.durabilite;}
	public Chargeur getChargeur(){return chargeur;}
	
	public void setRechargeChargeur(int rech){rechargeChargeur = rech;}
	public void setChargeur(int cha){balles = cha;}
	public void setAnimationW(int a){animationW = a;}
	public void setAnimationH(int a){animationH = a;}
	public void isSelected(boolean s) {selected = s;}
	public void setMaxH(int h){maxH = h;}
	public void setMaxW(int w){maxW = w;}
	public void setMunition(int mun) {munition = mun;}
	
}

class Chargeur{
	
	Arme a;
	long startPoint;
	
	int balles;
	int munition;
	
	boolean isRecharging;
	
	public Chargeur(Arme a){
		this.a = a;
		startPoint = 0;
		isRecharging = false;
	}
	
	public void recharge(){
		if(!(a.getMunition()<=0)||a.isInfiniteAmo()){
			startPoint = (System.nanoTime());
		
			isRecharging = true;
		
			if(a.isInfiniteAmo()){
				balles = a.getBallesMax();
			}
			else{
				if((a.getMunition()-a.getBallesMax())>=0){
					balles = a.getBallesMax();
					munition = a.getMunition()-(a.getBallesMax()-a.getBalles());
					//System.out.println(balles+" "+munition);
				}
				else if((a.getMunition()-a.getBallesMax())<0){
					balles = (a.getMunition()+a.getBalles()>a.getBallesMax())?40:(a.getMunition()+a.getBalles());
					munition = (a.getMunition()+a.getBalles()>a.getBallesMax())? a.getMunition()+a.getBalles()-40:0;
					//System.out.println(balles+" "+munition);
				}
			}
		}
	}
	
	
	public void update(){
		if(a.getBallesMax() == 10){
			
		}
		if(startPoint!=0&&(System.nanoTime() - startPoint)/1000000 >= a.getRechargeTime()){
			
			isRecharging = false;
			GamePane.player.getEquipedArme().setChargeur(balles);
			GamePane.player.getEquipedArme().setMunition(munition);
			startPoint = 0;
		}
	}
	
	public void stopRecharge(){
		startPoint = 0;
		balles = 0;
		munition = 0;
		isRecharging = false;
	}
	
	public void fullAmo(){
		munition = a.getMunition();
	}
	
	public boolean isRecharging(){return isRecharging;};
}



