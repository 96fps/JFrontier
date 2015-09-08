
public class pos2d {
	public double x;
	public double y;
	
	public pos2d(){
		x=0;
		y=0;
	}
	public pos2d(double posX, double posY){
		x=posX;
		y=posY;
	}
	public pos2d(int posX, int posY){
		x=posX;
		y=posY;
	}
	public static pos2d rotate(pos2d pos, double rot){
		return new pos2d(pos.x * Math.cos((rot)) - pos.y * Math.sin(Math.toRadians(rot)),
				         pos.y * Math.cos((rot)) + pos.x * Math.sin(Math.toRadians(rot)));
	}
	public static double rotX(pos2d pos, double rot){
		return pos.x * Math.cos((rot)) - pos.y * Math.sin((rot));
	}public static double rotY(pos2d pos, double rot){
		return pos.y * Math.cos((rot)) + pos.x * Math.sin((rot));
}
}
