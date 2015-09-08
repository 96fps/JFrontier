import java.awt.Color;

public class pos3d {
	public double x;
	public double y;
	public double z;
	
	public pos3d(){
		x=0;
		y=0;
		z=0;
	}
	public pos3d(double posX, double posY, double posZ){
		x=posX;
		y=posY;
		z=posZ;
	}
	public void add(double posX, double posY, double posZ){
		x+=posX;
		y+=posY;
		z+=posZ;
	}
	public void add(pos3d pos){
		x+=pos.x;
		y+=pos.y;
		z+=pos.z;
	}public static pos3d add(pos3d a, pos3d b){
		pos3d c= new pos3d();
		c.x=a.x+b.x;
		c.y=a.y+b.y;
		c.z=a.z+b.z;
		return c;
	}
	public pos3d mult(pos3d pos){
		return new pos3d(x*pos.x,
						 y*pos.y,
						 z*pos.z);
	}public pos3d mult(double n){
		return new pos3d(x*n,
				 		 y*n,
				 		 z*n);
	}
	public static pos3d mult(pos3d a, pos3d pos){
		return new pos3d(a.x*pos.x,
				 a.y*pos.y,
				 a.z*pos.z);
	}public static pos3d mult(pos3d a,double n){
		return new pos3d(a.x*n,
		 		 a.y*n,
		 		 a.z*n);
}
	public static pos3d negate(pos3d pos){
		return new pos3d(-pos.x, -pos.y, -pos.z);
	}
	public pos3d negate(){
		return new pos3d(-x,-y,-z);
	}
	public pos3d cloner(){
		return new pos3d(x,y,z);
	}
	public double magnitude(){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
	}
	public double latMagnitude(){
		return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	public Color toColor(){
		return toColor(256);
	}
	public Color toColor(double n){
		pos3d c=new pos3d(x, y, z);
		c=pos3d.mult(c, n);
		if(c.x>255)c.x=255;
		if(c.y>255)c.y=255;
		if(c.z>255)c.z=255;
		
		if(c.x<0)c.x=0;
		if(c.y<0)c.y=0;
		if(c.z<0)c.z=0;
		return new Color((int)c.x, (int)c.y, (int) c.z);
}
	
	public static pos3d rotate(pos3d pos, pos3d rot){
		double posX=pos2d.rotX(new pos2d(pos.x, pos.z), rot.y);
		double posY=pos.y;
		double posZ=pos2d.rotY(new pos2d(pos.x, pos.z), rot.y);
		pos3d temp= new pos3d();
		temp.x=posX;
		temp.y=pos2d.rotX(new pos2d(posY, posZ), rot.x);
		temp.z=pos2d.rotY(new pos2d(posY, posZ), rot.x);
		
		posX=pos2d.rotX(new pos2d(temp.x, temp.y), rot.z);
		posY=pos2d.rotY(new pos2d(temp.x, temp.y), rot.z);
		posZ=temp.z;
		
		
		return new pos3d(posX,posY,posZ);
	}
	public static pos3d rotateb(pos3d pos, pos3d rot){//do not use
		double posZ=pos2d.rotX(new pos2d(pos.x, pos.z), rot.y);
		double posY=pos.y;
		double posX=pos2d.rotY(new pos2d(pos.x, pos.z), rot.y);
		pos3d temp= new pos3d();
		temp.x=posX;
		temp.y=pos2d.rotX(new pos2d(posY, posZ), rot.x);
		temp.z=pos2d.rotY(new pos2d(posY, posZ), rot.x);
		
		posX=pos2d.rotX(new pos2d(temp.x, temp.y), rot.z);
		posY=pos2d.rotY(new pos2d(temp.x, temp.y), rot.z);
		posZ=temp.z;
		return new pos3d(posZ,posX,posY);
	}
	
}
