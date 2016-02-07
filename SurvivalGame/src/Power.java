
public class Power{
	
	public static final int DASH = 1; 

	public int id;
	
	public Power(int id){
		this.id = id;
	}
	
	public void cast(int x, int y){
		
		//DASH
		if(id == 1){
			if (GamePane.player.getStamina() < 30){
				return;
			}
			GamePane.player.consumeStamina(30);
			int AC = GamePane.mouseX - x;
			int AB = GamePane.mouseY - y;
			
			double rad = Math.toRadians(90-(Math.toDegrees(Math.atan2(AC,AB))));
			double dx = Math.cos(rad)*100;
			double dy = Math.sin(rad)*100;
			
			GamePane.player.setX(GamePane.player.getX()+dx);
			GamePane.player.setY(GamePane.player.getY()+dy); 
			
		}
	}
}
