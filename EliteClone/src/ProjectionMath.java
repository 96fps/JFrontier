
public class ProjectionMath {
	
	public static pos3d convert3dtoStretchedPolar(pos3d in, double amount){
		in=convert3dtoEqR(in);
//		in=PoleStretch(in);
		in=PoleStretch_B(in,amount); 
		if(in!=null)
		in=ConvertEqRtoPolar(in);
		
		return in;
	}
	
	public static pos3d convert3dtoEqR(pos3d in){//x/y/z -> long/lat/depth. (radians!) "equirect+depth"
		pos3d n=new pos3d();
		n.x=Math.atan2(in.x, in.y);// //Math.PI?
		double latDist=Math.sqrt(Math.pow(in.x,2)+ Math.pow(in.y,2));
		n.y=Math.atan2(latDist, in.z);// //Math.PI?
		n.z=Math.sqrt(Math.pow(latDist,2)+ Math.pow(in.z,2));

		return n;
	}
	
	public static pos3d PoleStretch(pos3d in){
		pos3d n= new pos3d();
		n.x=in.x;
		n.y=Math.tan(in.y/2);
		n.z=in.z;
		return n;
	}
	public static pos3d PoleStretch_B(pos3d in, double amount){ //1=Ultrafish-eye, 2= rectlinear, >2 is pincushion.
		pos3d n= new pos3d();
		n.x=in.x;
		double angle=in.y/2*amount;
		if (amount>=1&&angle>=Math.PI/2){
			angle=Math.PI/2;
			//return null;
		}
		
		else if (in.y/2>Math.PI/(2+amount)){
//			angle=Math.PI/2;
			return null;
		}
		if (amount>2&&angle>=Math.PI/2){
//			angle=Math.PI/4;
			return null;
		}
		if (amount>2&&angle>=Math.PI/3){
			angle=Math.PI/3;
			//return null;
		}
		n.y=(Math.tan(angle)*(.5+(0.5/amount)));
		n.y*=2;
		n.z=in.z;
		return n;
	}
	
	public static pos3d ConvertEqRtoPolar(pos3d in){ //project polar to x/y
		pos3d n= new pos3d();
		n.x=in.y * Math.cos(in.x);
		n.y=in.y * Math.sin(in.x);
		n.z=in.z;
		return n;
	}
	public static double getLatDist(pos3d in){ //x/y -> latDist
		double latDist=Math.sqrt(Math.pow(in.x,2)+ Math.pow(in.y,2));
		
		return latDist;
	}
	
}
