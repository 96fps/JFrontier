
public class Line3d {
	pos3d a;
	pos3d b;
	
	public Line3d(){
		a=new pos3d();
		b=new pos3d();
	}
	public Line3d(pos3d start, pos3d end){
		a=start;
		b=end;
	}
	public static pos3d lerp(pos3d a, pos3d b, double i){
		double x=0;
		double y=0;
		double z=0;
		x=a.x * i + b.x *(1-i);
		y=a.y * i + b.y *(1-i);
		z=a.z * i + b.z *(1-i);
		return new pos3d(x,y,z);
	}
	public String toString(){
		return new String("A.x="+a.x+" y="+a.y+" z="+a.z+"  B.x="+b.x+" y="+b.y+" z="+b.z);
	}	
}
